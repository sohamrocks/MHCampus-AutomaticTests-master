package com.mcgraw.test.automation.framework.core.common;

/**
 * @author yyudzitski
 */
public enum SpecialSymbols
{
  EMPTY( "" ),
  TAB( "\t" ),
  NEW_LINE("\n"),
  CARRIAGE_RETURN("\r"),
  FORM_FEET("\f"),
  SPACE(" "),
  DOG( "@" ),
  DOT("."),
  COMMA(","),
  COLON(":"),
  SEMMICOLON(";"),
  SLASH("/"),
  BACK_SLASH("\\"),
  STAR("*"),
  ROUND_OPEN_BRACKET("("),
  ROUND_CLOSE_BRACKET(")"),
  SQUARE_OPEN_BRACKET("["),
  SQUARE_CLOSE_BRACKET("]"),
  CURLY_OPEN_BRACKET("{"),
  CURLY_CLOSE_BRACKET("}"),
  SINGLE_QUOTE("'"),
  DOBLE_QUOTE("\""),
  PERCENT("%"),
  UNDERLINE("_"),
  LATTICE("#"),
  AMP("&"),
  PLUS("+"),
  PATH_SEPARATOR(System.getProperty("path.separator")),
  FILE_SEPARATOR(System.getProperty("file.separator"));

  private String alias;

  private SpecialSymbols( String alias )
  {
    this.alias = alias;
  }

  @Override
  public String toString()
  {
    return alias;
  }
}