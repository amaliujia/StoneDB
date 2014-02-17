%{

#include <stdio.h>
#include <stdlib.h>
/* #include "AST.h" */
#include "Tree.h"

%}

%union
{
  char *identifier;
  
  /* AST nodes */
  struct _tree_sql_select *_t_sql_s;
  struct _tree_sql_select_columns *_t_sql_s_cols;
  struct _tree_sql_select_from *_t_sql_s_from;
  struct _tree_sql_select_where *_t_sql_s_where;
  struct _tree_sql_select_where_condition *_t_sql_s_where_con;
  struct _tree_sql_select_group *_t_sql_s_group;
  struct _tree_sql_select_order *_t_sql_s_order;
  struct _tree_sql_select_identifier *_t_sql_iden;  
}


%token <identifier> NAME

/* operators */
%left OR
%left AND
%left NOT
%left COMPARISON /* = <> < > <= >= */
%nonassoc UMINUS

/* literal keyword tokens */
%token BY FROM GROUP ORDER SELECT WHERE

%type <_t_sql_s> select_statement
%type <_t_sql_s_cols> selection
%type <_t_sql_s_iden> scalar_exp_commalist scalar_exp
%type <_t_sql_s_from> from_clause
%type <_t_sql_s_iden> table_ref_commalist table_ref
%type <_t_sql_s_where> opt_where_clause where_clause
%type <_t_sql_s_where_con> search_condition predicate comparison_predicate
%type <_t_sql_s_group> opt_group_by_clause
%type <_t_sql_iden> column_ref_commalist
%type <_t_sql_s_order> opt_order_by_clause
%type <identifier> column_ref
%type <identifier> table range_variable

%%

sql_list : sql ';'
	 ;
/* manipulative statements */
sql : manipulative_statement
	 ;
manipulative_statement : select_statement
	 ;
select_statement : SELECT selection from_clause opt_where_clause opt_group_by_clause opt_order_by_clause
{
  $$ = c_t_sql_s($2,$3,$4,$5,$6);
  print_sql($$);
  freeTree($$);
}
	;

selection : scaler_exp_commalist
{
  $$ = c_t_sql_s_cols($1);
}
| '*'
{
  char *str = (char *)malloc(sizeof(char) * 2);
  strcat(str, "*");
  $$ = c_t_sql_s_cols(c_t_sql_iden(str));
}
	;
scalar_exp_commalist : 
scalar_exp
{
  $$ = $1;
  end = $$;
}
| scalar_exp_commalist ',' scalar_exp
{
  $$ = $1;
  start = $3;
  end -> next = start;
  end = start;
}
	;
scalar_exp :
column_ref
{
  $$ = c_t_sql_iden($1);
}
	;

/* from subsequence */
from_clause :
FROM table_ref_commalist
{
 $$ = c_t_sql_s_from($2);
}
	;
table_ref_commalist : 
table_ref
{
  $$ = $1;
  end = $$;
}
| table_ref_commalist ',' table_ref :
{
  $$ = $1;
  start = $3;
  end -> next = start;
  end = start;
}
	;
table_ref :
table { $$ = c_t_sql_iden($1); }
| table range_variable { $$ = c_t_sql_iden($1); }
	;
table :
NAME {$$ = $1;}
| NAME '.' NAME
{
  int len1 = strlen($1);
  int len2 = 1;
  int len3 = strlen($3);
  int len_str = len1 + len2 + len3 + 1;
  char *str = (char *)malloc(sizeof(char) * len_str);
  strcat(str, $1);
  strcat(str, ".");
  strcat(str, $3);
  free($1);
  free($3);
  $$ = str;
}
	;
range_variable :
NAME { $$ = $1; }
	;

/* where */
opt_where_clause:
	{ $$ = NULL; }
| where_clause { $$ = $1; }
	;

where_clause :
WHERE search_condition
{
  $$ = c_t_sql_s_where($2);
}
	;
search_condition :
search_condition OR search_condition
{
  $$ = c_t_sql_s_where_con_l("or", $1, $3);
}
| search_condition AND search_condition
{
  $$ = c_t_sql_s_where_con_l('and', $1, $3);
}
| NOT search_condition
{
  $$ = c_t_sql_s_where_con_l('not', $2, NULL);
}
| predicate
{
  $$ = $1;
}
	;
predicate :
comparison_predicate
{
  $$ = $1;
}
	;
comparison_predicate :
scalar_exp COMPARISON scalar_exp
{
  $$ = c_t_sql_s_where_con_a("=", $1, $3);
}
	;
/* group by, order by */
opt_group_by_clause :
	{ $$ = NULL; }
| GROUP BY column_ref_commalist 
{
  $$ = c_t_sql_s_group($3);
}
	;
opt_order_by_clause:
	{ $$ = NULL; }
| ORDER BY column_ref_commalist
{
  $$ = c_t_sql_s_order($3);
}
	;
column_ref_commalist :
column_ref
{
  $$ = c_t_sql_iden($1);
  end = $$;
}
| column_ref_commalist ',' column_ref
{
  $$ = $1;
  start = c_t_sql_iden($3);
  end -> next = start;
  end = start;
}
	;
column_ref :
NAME { $$ = $1; }
| NAME '.' NAME
{
  int len1 = strlen($1);
  int len2 = 1;
  int len3= strlen($3);
  int len_str = len1 + len2 + len3 + 1;
  char *str = (char *)malloc(sizeof(char) * len_str);
  strcat(str, $1);
  strcat(str, '.');
  strcat(str, $3);
  free($1);
  free($3);
  $$ = str;
}
	;
%%

