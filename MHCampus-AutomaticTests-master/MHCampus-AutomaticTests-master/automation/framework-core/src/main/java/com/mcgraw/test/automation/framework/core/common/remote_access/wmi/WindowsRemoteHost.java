package com.mcgraw.test.automation.framework.core.common.remote_access.wmi;

import com.mcgraw.test.automation.framework.core.common.SpecialSymbols;

public class WindowsRemoteHost
{
  private String hostName;

  private String domain;

  private String userName;

  private String password;

  public String getHostName()
  {
    return hostName;
  }

  public void setHostName( String hostName )
  {
    this.hostName = hostName;
  }

  public String getDomain()
  {
    return domain;
  }

  public void setDomain( String domain )
  {
    this.domain = domain;
  }

  public String getUserName()
  {
    return userName;
  }

  public void setUserName( String userName )
  {
    this.userName = userName;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword( String password )
  {
    this.password = password;
  }

  @Override
  public String toString()
  {
    return "WindowsRemoteHost=" + SpecialSymbols.CURLY_OPEN_BRACKET.toString() + this.userName
      + SpecialSymbols.COLON.toString() + this.password + SpecialSymbols.DOG.toString() + this.domain
      + SpecialSymbols.DOT.toString() + this.hostName + SpecialSymbols.CURLY_CLOSE_BRACKET.toString();
  }

}
