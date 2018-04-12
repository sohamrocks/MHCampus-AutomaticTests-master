package com.mcgraw.test.automation.framework.core.common.remote_access.wmi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.common.JISystem;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JIProgId;
import org.jinterop.dcom.core.JISession;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;
import org.jinterop.dcom.impls.JIObjectFactory;
import org.jinterop.dcom.impls.automation.IJIDispatch;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class WindowsRemoteCommandLauncher
{
  private WindowsRemoteCommandLauncher()
  {
    throw new AssertionError();
  }

  /** Size of a buffer for reading command output via smb:// */
  private static final int BUFFER_SIZE = 1024;

  /** An interval (ms) for the thread to sleep between process termination checks. */
  private static final int SLEEP_INTERVAL = 500;

  /** Random number generator for naming output files uniquely. */
  private static final Random rand = new Random( System.currentTimeMillis() );

  /**
   * Launch cmd.exe command on the remote host.
   * 
   * @param command - single command to be passed to cmd.exe. No pipelining or redirects are allowed. In fact it is run
   *          like the following:
   * 
   *          <pre>
   * cmd.exe /q /c %your command%  >  %some file%
   * </pre>
   * @param host - host to run command on.
   * @param domain - logon user's domain.
   * @param userName - logon username.
   * @param password - logon user's password.
   * @param workingDirectory - cmd.exe working directory. Can be null.
   * @return Command launch result.
   */
  public static String launchCmdCommand( String command, WindowsRemoteHost remoteHost, String workingDirectory )
  {
    Logger.debug( "WMICMD: Command to run: " + command );
    Logger.debug( "WMICMD: Working directory: " + ( workingDirectory != null ? workingDirectory : "<undefined>" ) );

    /** Return value */
    StringBuilder strBuilder = new StringBuilder();

    /** Configuring session */
    JISession dcomSession = null;
    try
    {
      dcomSession = configAndConnectDCom( remoteHost.getDomain(), remoteHost.getUserName(), remoteHost.getPassword() );
      IJIDispatch wbemLocator = getWmiLocator( remoteHost.getHostName(), dcomSession );

      /** Connect to the remote host, get wbemServices */
      JIVariant results[] =
        wbemLocator.callMethodA(
          "ConnectServer",
          new Object[] {new JIString( remoteHost.getHostName() ), new JIString( "\\root\\cimv2" ),
            JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(),
            JIVariant.OPTIONAL_PARAM(), Integer.valueOf( 0 ), JIVariant.OPTIONAL_PARAM()} );

      /** Retrieve Win32_Process instance for creating our own process */
      IJIDispatch wbemServices = toIDispatch( results[0] );
      results =
        wbemServices.callMethodA( "Get",
          new Object[] {new JIString( "Win32_Process" ), Integer.valueOf( 0 ), JIVariant.OPTIONAL_PARAM()} );

      /** Generating output file name (should be unique, but that's not too critical) */
      String fileName = "WMICMDOUT" + Integer.valueOf( rand.nextInt( Integer.MAX_VALUE ) ).toString() + ".txt";

      /** Creating process for the command */
      IJIDispatch win32Process = toIDispatch( results[0] );
      results =
        win32Process.callMethodA( "Create", new Object[] {
        /**
         * /q - suppress echo<br>
         * /c - run the<br>
         * specified command and exit, redirecting output to the file in e.g. C:\Windows\Temp with the name generated
         * above
         */
        new JIString( "cmd.exe /q /c " + command + " > %windir%\\Temp\\" + fileName ),
          workingDirectory != null ? new JIString( workingDirectory ) : JIVariant.NULL(), JIVariant.OPTIONAL_PARAM(),
          new JIVariant( 0, true )} );

      /** Retrieving result code of Create method and PID */
      int retCode = results[0].getObjectAsInt();
      if( retCode != 0 )
      {
        /** Creating process failed, throw exception */
        throw new RuntimeException( "Creating process failed with result code: " + retCode );
      }
      int pid = results[1].getObjectAsInt();

      Logger.debug( "WMICMD: Created process with PID: " + pid );
      Logger.debug( "WMICMD: Output to file: " + fileName );

      /** Wait for process to terminate in a loop */
      while( true )
      {
        /** We query the remote system for out process and break if it's not there */
        results =
          wbemServices.callMethodA( "ExecQuery", new Object[] {
            new JIString( "SELECT * FROM Win32_Process WHERE ProcessId = " + pid ), JIVariant.OPTIONAL_PARAM(),
            JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM()} );
        IJIDispatch array = toIDispatch( results[0] );
        /** No such process */
        if( array.get( "Count" ).getObjectAsInt() == 0 )
          break;
        Thread.sleep( SLEEP_INTERVAL );
      }

      Logger.debug( "WMICMD: Process terminated." );
      Logger.debug( "WMICMD: Reading output..." );

      /** Retrieving the output file via smb:// */
      SmbFile outFile =
        new SmbFile( "smb://" + remoteHost.getHostName() + "/ADMIN$/Temp/" + fileName, new NtlmPasswordAuthentication(
          remoteHost.getDomain(), remoteHost.getUserName(), remoteHost.getPassword() ) );
      InputStream input = outFile.getInputStream();

      byte[] buf = new byte[BUFFER_SIZE];

      int count;

      while( ( count = input.read( buf ) ) > 0 )
        strBuilder.append( new String( buf, 0, count ) );

      input.close();

      Logger.debug( "WMICMD: Done." );

      /** Removing file by calling "del" cmd.exe command */
      results =
        win32Process.callMethodA(
          "Create",
          new Object[] {new JIString( "cmd.exe /q /c del /f /q %windir%\\Temp\\" + fileName ),
            JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM()} );

      /** Retrieving result code of Create method */
      retCode = results[0].getObjectAsInt();
      if( retCode != 0 )
      {
        Logger.error( "WMICMD: Creating process for deleting file failed with result code: " + retCode );
      }

      Logger.debug( "WMICMD: Process for deleting file created succesfully." );
      /** Not waiting for deleting file process to terminate - no need */
    }
    catch( Exception e )
    {
      throw new RuntimeException( e.getMessage(), e );
    }
    finally
    {
      /** Not quite sure why we need that, but better to be safe than sorry */
      if( dcomSession != null )
      {
        try
        {
          JISession.destroySession( dcomSession );
        }
        catch( JIException e )
        {
          throw new RuntimeException( "Can not close DCOM session!", e );
        }
      }
    }

    Logger.debug( "WMICMD: Quitting." );

    return strBuilder.toString();
  }

  /**
   * Launch cmd.exe command on the remote host.
   * 
   * @see WindowsRemoteCommandLauncher#launchCmdCommand(String, String, String, String, String, String)
   */
  public static String launchCmdCommand( String command, WindowsRemoteHost remoteHost )
  {
    return launchCmdCommand( command, remoteHost, null );
  }

  /**
   * Launch PowerShell command on the remote host.
   * 
   * @param command - command to be passed to PowerShell. Treat it as run like the following:
   * 
   *          <pre>
   * cmd.exe /q /c powershell.exe -PSConsoleFile "%console file%" -command "%your command%"  >  %some file%
   * </pre>
   * @param host - host to run command on.
   * @param domain - logon user's domain.
   * @param userName - logon username.
   * @param password - logon user's password.
   * @param consoleFilePath - a path to console file. Can be null - no console file used.
   * @param workingDirectory - PowerShell working directory. Can be null.
   * @return Command launch result.
   */
  public static String launchPsCommand( String command, WindowsRemoteHost remoteHost, String consoleFilePath,
    String workingDirectory )
  {
    return launchCmdCommand( "cmd.exe /q /c powershell.exe "
      + ( consoleFilePath != null ? "-PSConsoleFile \"" + consoleFilePath + "\" " : "" ) + "-command \"" + command
      + "\"", remoteHost, workingDirectory );
  }

  /**
   * Launch PowerShell command on the remote host.
   * 
   * @see WindowsRemoteCommandLauncher#launchPsCommand(String, String, String, String, String, String, String)
   */
  public static String launchPsCommand( String command, WindowsRemoteHost remoteHost, String consoleFilePath )
  {
    return launchCmdCommand( "cmd.exe /q /c powershell.exe "
      + ( consoleFilePath != null ? "-PSConsoleFile \"" + consoleFilePath + "\" " : "" ) + "-command \"" + command
      + "\"", remoteHost, null );
  }

  /**
   * Launch PowerShell command on the remote host.
   * 
   * @see WindowsRemoteCommandLauncher#launchPsCommand(String, String, String, String, String, String, String)
   */
  public static String launchPsCommand( String command,WindowsRemoteHost remoteHost )
  {
    return launchCmdCommand( "cmd.exe /q /c powershell.exe " + "-command \"" + command + "\"", remoteHost, null );
  }

  private static JISession configAndConnectDCom( String domain, String user, String pass ) throws Exception
  {
    JISystem.getLogger().setLevel( Level.OFF );

    try
    {
      JISystem.setInBuiltLogHandler( false );
    }
    catch( IOException ignored )
    {
      ;
    }

    JISystem.setAutoRegisteration( true );
    JISession dcomSession = JISession.createSession( domain, user, pass );
    dcomSession.useSessionSecurity( true );
    return dcomSession;
  }

  private static IJIDispatch getWmiLocator( String host, JISession dcomSession ) throws Exception
  {
    JIComServer wbemLocatorComObj =
      new JIComServer( JIProgId.valueOf( "WbemScripting.SWbemLocator" ), host, dcomSession );
    return ( IJIDispatch ) JIObjectFactory.narrowObject( wbemLocatorComObj.createInstance().queryInterface(
      IJIDispatch.IID ) );
  }

  private static IJIDispatch toIDispatch( JIVariant comObjectAsVariant ) throws JIException
  {
    return ( IJIDispatch ) JIObjectFactory.narrowObject( comObjectAsVariant.getObjectAsComObject() );
  }
}