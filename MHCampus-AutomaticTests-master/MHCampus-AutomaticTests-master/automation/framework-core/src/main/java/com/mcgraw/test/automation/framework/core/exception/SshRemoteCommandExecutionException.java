package com.mcgraw.test.automation.framework.core.exception;

/**
 * @author yyudzitski
 */
public class SshRemoteCommandExecutionException extends RuntimeException
{
  private static final long serialVersionUID = -4846316716256812074L;

  public SshRemoteCommandExecutionException( String errorMessage )
  {
    super( errorMessage );
  }

  public SshRemoteCommandExecutionException( String message, Throwable cause )
  {
    super( message, cause );
  }
}