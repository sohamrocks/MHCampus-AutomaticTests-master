package com.mcgraw.test.automation.framework.core.common.test_data.random_utils;

import java.util.Random;

/**
 * Random numbers utils.
 * 
 * @author yyudzitski
 */
public class NumberUtils
{
  private static final int ONE = 1;

  private static final int TEN = 10;

  private static final Random random = new Random();

  public static int getRandomInt()
  {
    return random.nextInt();
  }

  public static int getRandomInt( int limit )
  {
    return random.nextInt( limit );
  }

  public static double getRandomDouble()
  {
    return random.nextDouble();
  }

  public static double getRandomDouble( double limit )
  {
    int degree = 0;
    double limitValue = limit;
    double value = 0;
    while( limitValue > ONE )
    {
      limitValue /= TEN;
      degree++;
    }
    while( true )
    {
      value = random.nextDouble();
      if( value <= limitValue )
      {
        break;
      }
    }
    return value * Math.pow( TEN, degree );
  }

  public static float getRandomFloat()
  {
    return random.nextFloat();
  }

  public static float getRandomFloat( float limit )
  {
    int degree = 0;
    float limitValue = limit;
    float value = 0;
    while( limitValue > ONE )
    {
      limitValue /= TEN;
      degree++;
    }
    while( true )
    {
      value = random.nextFloat();
      if( value <= limitValue )
      {
        break;
      }
    }
    return ( float ) ( value * Math.pow( TEN, degree ) );
  }

  public static long getRandomLong()
  {
    return random.nextLong();
  }

  public static long getRandomLong( long limit )
  {
    long value = 0;
    while( true )
    {
      value = random.nextLong();
      if( value <= limit )
      {
        return value;
      }
    }
  }
}