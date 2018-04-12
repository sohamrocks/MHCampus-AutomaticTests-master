package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import org.openqa.selenium.WebElement;

public class RadioButton extends Element
{

  public RadioButton( WebElement webElement )
  {
    super( webElement );   
  }
  
  public void activate( )
  {
    super.click();
  }

  /**
   * @return radio button is checked or not
   */
  public boolean isChecked() {

    String checkedAttr = webElement.getAttribute( "checked" );
    return ( null != checkedAttr );
  }

}
