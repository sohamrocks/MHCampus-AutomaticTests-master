package com.mcgraw.test.automation.framework.core.results.logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;

/**
 * Logging levels for logger {@link Logger}
 *
 * @author yyudzitski
 */
@SuppressWarnings( "serial" )
public class LoggerLevel extends org.apache.log4j.Level
{
  private static final Map<Integer, LoggerLevel> LOGGER_LEVELS;
  static
  {
    LOGGER_LEVELS = Collections.synchronizedMap( new HashMap<Integer, LoggerLevel>() );
  }

  final static public int TEST_STARTED_INT = FATAL_INT - 1;

  final static public int TEST_FINISHED_INT = FATAL_INT - 2;;

  final static public int METHOD_STARTED_INT = FATAL_INT - 3;

  final static public int METHOD_SKIPPED_INT = FATAL_INT - 4;

  final static public int METHOD_FAILED_INT = FATAL_INT - 5;

  final static public int METHOD_SUCCESS_INT = FATAL_INT - 6;

  final static public int CONFIGURATION_STARTED_INT = FATAL_INT - 10;

  final static public int CONFIGURATION_SKIPPED_INT = FATAL_INT - 11;

  final static public int CONFIGURATION_FAILED_INT = FATAL_INT - 12;

  final static public int CONFIGURATION_SUCCESS_INT = FATAL_INT - 13;

  final static public int OPERATION_INT = FATAL_INT - 14;

  final static public int ACTION_INT = FATAL_INT - 15;

  final static public int HTML_OUTPUT_INT = FATAL_INT - 16;
  
  final static public int VERIFY_FAILED_INT = FATAL_INT - 17;

  final static public Level TEST_STARTED = new LoggerLevel( TEST_STARTED_INT, "TEST_STARTED", 0 );

  final static public Level TEST_FINISHED = new LoggerLevel( TEST_FINISHED_INT, "TEST_FINISHED", 0 );

  final static public Level METHOD_STARTED = new LoggerLevel( METHOD_STARTED_INT, "METHOD_STARTED", 0 );

  final static public Level METHOD_SKIPPED = new LoggerLevel( METHOD_SKIPPED_INT, "METHOD_SKIPPED", 0 );

  final static public Level METHOD_FAILED = new LoggerLevel( METHOD_FAILED_INT, "METHOD_FAILED", 0 );

  final static public Level METHOD_SUCCESS = new LoggerLevel( METHOD_SUCCESS_INT, "METHOD_SUCCESS", 0 );

  final static public Level CONFIGURATION_STARTED = new LoggerLevel( CONFIGURATION_STARTED_INT,
    "CONFIGURATION_STARTED", 0 );

  final static public Level CONFIGURATION_SKIPPED = new LoggerLevel( CONFIGURATION_SKIPPED_INT,
    "CONFIGURATION_SKIPPED", 0 );

  final static public Level CONFIGURATION_FAILED =
    new LoggerLevel( CONFIGURATION_FAILED_INT, "CONFIGURATION_FAILED", 0 );

  final static public Level CONFIGURATION_SUCCESS = new LoggerLevel( CONFIGURATION_SUCCESS_INT,
    "CONFIGURATION_SUCCESS", 0 );

  final static public Level OPERATION = new LoggerLevel( OPERATION_INT, "OPERATION", 0 );

  final static public Level ACTION = new LoggerLevel( ACTION_INT, "ACTION", 0 );

  final static public Level HTML_OUTPUT = new LoggerLevel( HTML_OUTPUT_INT, "HTML_OUTPUT", 0 );
  
  final static public Level VERIFY_FAILED = new LoggerLevel( VERIFY_FAILED_INT, "VERIFY_FAILED", 0 );

  public static Level toLoggerLevel( int val, Level defaultLevel )
  {
    Level level = toLevel( val, null );
    if( level != null )
    {
      return level;
    }

    level = LOGGER_LEVELS.get( val );
    if( level != null )
    {
      return level;
    }

    return defaultLevel;
  }

  protected LoggerLevel( int level, String levelStr, int syslogEquivalent )
  {
    super( level, levelStr, syslogEquivalent );
    LOGGER_LEVELS.put( level, this );
  }

  public static Level toLevel( int val )
  {
    return LOGGER_LEVELS.get( val );
  }
}
