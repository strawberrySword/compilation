/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/*************/
/* USER CODE */
/*************/
import java_cup.runtime.*;

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/
   
/*****************************************************/ 
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/ 
%class Lexer

/********************************************************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column

/*******************************************************************************/
/* Note that this has to be the EXACT same name of the class the CUP generates */
/*******************************************************************************/
%cupsym TokenNames

/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup

/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied verbatim (letter to letter) into the Lexer class code.     */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/*****************************************************************************/   
%{
	/*********************************************************************************/
	/* Create a new java_cup.runtime.Symbol with information about the current token */
	/*********************************************************************************/
	private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
	private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}

	/*******************************************/
	/* Enable line number extraction from main */
	/*******************************************/
	public int getLine() { return yyline + 1; } 

	/**********************************************/
	/* Enable token position extraction from main */
	/**********************************************/
	public int getTokenStartPosition() { return yycolumn + 1; } 
	
	private boolean isValidInteger(String num) {
		try {
			int value = Integer.parseInt(num);
			return value < 32768; // 2^15
		} catch (NumberFormatException e) {
			return false;
		}
  	}
%}

/***********************/
/* MACRO DECALARATIONS */
/***********************/
LineTerminator		= \r|\n|\r\n
CommentCharacters 	= [a-z]|[A-Z]|[0-9]|[ \t] | ; | \? | \!| \+| \-| \*| \(|\)| \[|\]| \{|\}| \.| \/
SingleLineComment	= "//"({CommentCharacters}*){LineTerminator}?
MultyLineComment	= {CommentCharacters} | {LineTerminator}
Comment 			= {SingleLineComment}
WhiteSpace			= {LineTerminator} | [ \t]
INTEGER				= 0 | [1-9][0-9]*
ID					= [a-zA-Z][a-zA-Z0-9]*
String				= (\")[a-zA-Z]*(\")

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%state COMMENT_STATE
%%

/************************************************************/
/* LEXER matches regular expressions to actions (Java code) */
/************************************************************/

/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**************************************************************/
<YYINITIAL> "/*"            {yybegin(COMMENT_STATE);}
<COMMENT_STATE> {
	"*/"					{yybegin(YYINITIAL);}
	{MultyLineComment} 	{}
	<<EOF>>					{ throw new Error("Unclosed Comment");}
	.						{ throw new Error("Illegal comment character");}
}
<YYINITIAL> "class"			{ return symbol(TokenNames.CLASS); }
<YYINITIAL> "nil"			{ return symbol(TokenNames.NIL); }
<YYINITIAL> "array"			{ return symbol(TokenNames.ARRAY); }
<YYINITIAL> "while"         { return symbol(TokenNames.WHILE); }
<YYINITIAL> "int"           { return symbol(TokenNames.TYPE_INT); }
<YYINITIAL> "void"          { return symbol(TokenNames.TYPE_VOID); }
<YYINITIAL> "extends"       { return symbol(TokenNames.EXTENDS); }
<YYINITIAL> "return"        { return symbol(TokenNames.RETURN); }
<YYINITIAL> "new"           { return symbol(TokenNames.NEW); }
<YYINITIAL> "if"            { return symbol(TokenNames.IF); }
<YYINITIAL> "string"		{ return symbol(TokenNames.TYPE_STRING); }
<YYINITIAL> "+"				{ return symbol(TokenNames.PLUS);}
<YYINITIAL> "-"				{ return symbol(TokenNames.MINUS);}
<YYINITIAL> "*"				{ return symbol(TokenNames.TIMES);}
<YYINITIAL> "/"				{ return symbol(TokenNames.DIVIDE);}
<YYINITIAL> "("				{ return symbol(TokenNames.LPAREN);}
<YYINITIAL> ")"				{ return symbol(TokenNames.RPAREN);}
<YYINITIAL> "["				{ return symbol(TokenNames.LBRACK);}
<YYINITIAL> "]"				{ return symbol(TokenNames.RBRACK);}
<YYINITIAL> "{"				{ return symbol(TokenNames.LBRACE);}
<YYINITIAL> "}"				{ return symbol(TokenNames.RBRACE);}
<YYINITIAL> ","				{ return symbol(TokenNames.COMMA);}
<YYINITIAL> "."				{ return symbol(TokenNames.DOT);}
<YYINITIAL> ";"				{ return symbol(TokenNames.SEMICOLON);}
<YYINITIAL> ":="			{ return symbol(TokenNames.ASSIGN);}
<YYINITIAL> "="				{ return symbol(TokenNames.EQ);}
<YYINITIAL> "<"				{ return symbol(TokenNames.LT);}
<YYINITIAL> ">"				{ return symbol(TokenNames.GT);}
<YYINITIAL> {

{INTEGER}			{
 						if (isValidInteger(yytext())) {
    						{ return symbol(TokenNames.INT, new Integer(yytext()));}
  						} else {
    						throw new Error("Integer value must be smaller than 2^15 (32768)");
  						}
					}
{ID}				{ return symbol(TokenNames.ID,     new String( yytext()));}   
{String}			{ return symbol(TokenNames.STRING,     new String( yytext()));}   
{Comment}			{ /* just skip what was found, do nothing */ }
{WhiteSpace}		{ /* just skip what was found, do nothing */ }
<<EOF>>				{ return symbol(TokenNames.EOF);}
}
