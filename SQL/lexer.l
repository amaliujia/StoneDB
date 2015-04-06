%{

#include "yacc.tab.h"
#include <string.h>
#include <stdio.h>

int lineno = 1;

%}

%e 			1200

%%
/* literal keyword tokens  */
select			{ return SELECT; }
from			{ return FROM; }
where			{ return WHERE; }
group			{ return GROUP; }
order			{ return ORDER; }
by			{ return BY; }
and			{ return AND; }
or			{ return OR; }
not			{ return NOT; }

/* puncutation */
"="			{ return COMPARISON; }

/* name */
[A-Za-z][A-Za-z0-9_]*	{ yylval.identifier = strdup(yytext); return NAME; }		
/* line plus plus */
\n 			lineno++ ;

/* ignore whitespace */
[ \t\r]+		;

[-+*/:(),.;]		{ return yytext[0]; }

"$"			{ lineno = 0; return 0; }

/* no other char */
.			yyerror("invalid character!\");

%%

int yyerror(char *error)
{
  printf("%d : %s at %s\n",lineno, error, yytext);
  lineno = 0;
  return -1;
}


int main()
{
  while(!feof(yyin))
  {
    yyparse();
    lineno = 0;
  }
}

int yywrap()
{
  return 1;
}
