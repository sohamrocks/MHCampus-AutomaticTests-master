package com.mcgraw.test.automation.api.blackboard.exceptions;

import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;

public class NullGradebookInfoException extends CommonTestRuntimeException{

	private static final long serialVersionUID = 1112651366609175897L;
	
	public NullGradebookInfoException( String errorMessage )
	  {
	    super( errorMessage );
	  }
	
	
}
