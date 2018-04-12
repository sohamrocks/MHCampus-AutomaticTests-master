/**
 * 
 */
package com.mcgraw.test.automation.framework.core.exception;

/**
 * Marshalling/Unmarshalling exception
 * 
 * @author Andrei Varabyeu
 * 
 */
public class MarshallingException extends RuntimeException
{

  private static final long serialVersionUID = -8028203340004707900L;

  public MarshallingException( String message )
  {
    super( message );
  }

  public MarshallingException( String message, Throwable t )
  {
    super( message, t );
  }

}
