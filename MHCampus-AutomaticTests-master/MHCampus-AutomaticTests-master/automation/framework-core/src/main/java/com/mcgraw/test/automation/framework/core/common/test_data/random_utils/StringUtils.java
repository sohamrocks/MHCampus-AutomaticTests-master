package com.mcgraw.test.automation.framework.core.common.test_data.random_utils;

import java.util.Random;
import java.util.Vector;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * String with random symbols utils.
 *
 * @author yyudzitski
 */
public class StringUtils extends RandomStringUtils
{
  /** Default length of string with special symbols */
  private static final int DEFAULT_LENGTH = 5;

  /** Special symbols intervals in ASCII codes table */
  private static final Vector<int[]> SPEC_SYMBOLS_INTERVALS = new Vector<int[]>();

  static
  {
    /** ! " # $ % & ' ( ) * + - . / */
    SPEC_SYMBOLS_INTERVALS.add( new int[] {32, 47} );
    /** : ; < = > ? */
    SPEC_SYMBOLS_INTERVALS.add( new int[] {58, 64} );
    /** [ \ ] ^ _ ` */
    SPEC_SYMBOLS_INTERVALS.add( new int[] {91, 96} );
    /** { | } ~ */
    SPEC_SYMBOLS_INTERVALS.add( new int[] {123, 126} );
  }

  private static boolean isSpecSymbol( int value )
  {
    for( int i = 0; i < SPEC_SYMBOLS_INTERVALS.size(); i++ )
    {
      int[] interval = SPEC_SYMBOLS_INTERVALS.get( i );
      /** Interval must have start and end indexes */
      if( interval.length < 2 )
      {
        continue;
      }
      if( value >= interval[0] && value <= interval[1] )
      {
        return true;
      }
    }
    return false;
  }

  private static char randomSpecSymbol()
  {
    int value = new Random().nextInt( 127 );
    return ( char ) value;
  }

  /**
   * Generate string with special symbols only.
   *
   * @param length
   * @return
   */
  public static String randomSpecSymbolsString( int length )
  {
    StringBuilder builder = new StringBuilder();
    int toGenerate = length;
    while( toGenerate > 0 )
    {
      char randValue = randomSpecSymbol();
      if( isSpecSymbol( randValue ) )
      {
        builder.append( randValue );
        toGenerate--;
      }
    }
    return builder.toString().trim();
  }

  /**
   * Generate string with special symbols only. Symbols will be used from <code>validSpecSymbols</code>
   *
   * @param length
   * @param validSpecSymbols
   * @return
   */
  public static String randomSpecSymbolsString( int length, char[] validSpecSymbols )
  {
    StringBuilder builder = new StringBuilder();
    int toGenerate = length;
    while( toGenerate > 0 )
    {
      char randValue = randomSpecSymbol();
      for( int i = 0; i < validSpecSymbols.length; i++ )
      {
        if( randValue == validSpecSymbols[i] )
        {
          builder.append( randValue );
          toGenerate--;
        }
      }
    }
    return builder.toString().trim();
  }

  /**
   * Generate string with only special symbols with {@link StringUtils.DEFAULT_LENGTH}
   *
   * @return
   */
  public static String randomSpecSymbolsString()
  {
    return randomSpecSymbolsString( DEFAULT_LENGTH );
  }

  /**
   * Generate string like <code>RandomStringUtils.randomAscii()</code> with special symbols. Special symbols string
   * length is {@link StringUtils.DEFAULT_LENGTH}
   *
   * @param length
   * @return
   */
  public static String randomSpecAsciiString( int length )
  {
    return randomAscii( length ) + randomSpecSymbolsString( DEFAULT_LENGTH );
  }

  /**
   * Generate string like <code>RandomStringUtils.randomAlphabetic()</code> with special symbols. Special symbols string
   * length is {@link StringUtils.DEFAULT_LENGTH}
   *
   * @param length
   * @return
   */
  public static String randomSpecAlphabetic( int length )
  {
    return randomAlphabetic( length ) + randomSpecSymbolsString( DEFAULT_LENGTH );
  }

  /**
   * Generate string like <code>RandomStringUtils.randomAlphanumeric()</code> with special symbols. Special symbols
   * string length is {@link StringUtils.DEFAULT_LENGTH}
   *
   * @param length
   * @return
   */
  public static final String randomSpecAlphanumeric( int length )
  {
    return randomAlphanumeric( length ) + randomSpecSymbolsString( DEFAULT_LENGTH );
  }

  /**
   * Generate string like <code>RandomStringUtils.randomNumeric()</code> with special symbols. Special symbols string
   * length is {@link StringUtils.DEFAULT_LENGTH}
   *
   * @param length
   * @return
   */
  public static final String randomSpecNumeric( int length )
  {
    return randomNumeric( length ) + randomSpecSymbolsString( DEFAULT_LENGTH );
  }

  /**
   * Generate string like <code>randomSpecSymbolsString()</code> with special symbols. Special symbols string length is
   * {@link StringUtils.DEFAULT_LENGTH}
   *
   * @param validSpecSymbols - symbols witch accessible to generate
   * @return
   */
  public static String randomSpecSymbolsString( char[] validSpecSymbols )
  {
    return randomSpecSymbolsString( DEFAULT_LENGTH, validSpecSymbols );
  }

  /**
   * Generate string like <code>RandomStringUtils.randomAscii()</code> with special symbols. Special symbols string
   * length is {@link StringUtils.DEFAULT_LENGTH}
   *
   * @param length
   * @param validSpecSymbols - symbols witch accessible to generate
   * @return
   */
  public static String randomSpecAsciiString( int length, char[] validSpecSymbols )
  {
    return randomAscii( length ) + randomSpecSymbolsString( DEFAULT_LENGTH, validSpecSymbols );
  }

  /**
   * Generate string like <code>RandomStringUtils.randomAlphabetic()</code> with special symbols. Special symbols string
   * length is {@link StringUtils.DEFAULT_LENGTH}
   *
   * @param length
   * @param validSpecSymbols - symbols witch accessible to generate
   * @return
   */
  public static String randomSpecAlphabetic( int length, char[] validSpecSymbols )
  {
    return randomAlphabetic( length ) + randomSpecSymbolsString( DEFAULT_LENGTH, validSpecSymbols );
  }

  /**
   * Generate string like <code>RandomStringUtils.randomAlphanumeric()</code> with special symbols. Special symbols
   * string length is {@link StringUtils.DEFAULT_LENGTH}
   *
   * @param length
   * @param validSpecSymbols - symbols witch accessible to generate
   * @return
   */
  public static final String randomSpecAlphanumeric( int length, char[] validSpecSymbols )
  {
    return randomAlphanumeric( length ) + randomSpecSymbolsString( DEFAULT_LENGTH, validSpecSymbols );
  }

  /**
   * Generate string like <code>RandomStringUtils.randomNumeric()</code> with special symbols. Special symbols string
   * length is {@link StringUtils.DEFAULT_LENGTH}
   *
   * @param length
   * @param validSpecSymbols - symbols witch accessible to generate
   * @return
   */
  public static final String randomSpecNumeric( int length, char[] validSpecSymbols )
  {
    return randomNumeric( length ) + randomSpecSymbolsString( DEFAULT_LENGTH, validSpecSymbols );
  }
}