package Examen; 
import static Examen.Token.*;
%%
%class NewLexer
%type Token


digito=[0-9]
entero={digito}+
flotante={entero}"."{entero}
suma=((entero)?(flotante)?"+"(entero)?(flotante)?)

parl="("
pard=")"
multiplicacion=((entero)?(flotante)?"*"(entero)?(flotante)?)

exponente={flotante} [Ee] [+-] {entero}


white=[ \t\r\n]+

%{
	public String lexeme;
%}
%%


{white} {/*Ignore*/}
"//" {/*Ignore*/}

{entero} {lexeme =yytext(); return entero;}
{flotante} {lexeme =yytext(); return flotante;}
{suma} {lexeme =yytext(); return suma;}

{parl} {lexeme =yytext(); return parl;}
{pard} {lexeme =yytext(); return pard;}
{multiplicacion} {lexeme =yytext(); return multiplicacion;}
{exponente} {lexeme =yytext(); return exponente;}
. {lexeme =yytext(); return ERROR;}