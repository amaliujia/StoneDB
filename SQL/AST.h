#ifndef _AST_H_
#define _AST_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdarg.h>

struct _tree_sql_select //tree node
{
// link select 
  struct _tree_sql_select_columns *column;
// link select from
  struct _tree_sql_select_from *from;
// link select where
  struct _tree_sql_select_where *where;
// link select group
  struct _tree_sql_select_group *group;
// link select order
  struct _tree_sql_select_order *order;
};

struct _tree_sql_select_column 
{
// link all field together
  struct _tree_sql_identifier *columns_start;
};

struct _tree_sql_select_from
{
// link all table together
  struct _tree_sql_identifier *tables_start;
};

struct _tree_sql_select_where 
{
  struct _tree_sql_select_where_condition *condition_root;
};

struct _tree_sql_select_where_condition
{
  char type; // 1: equation 2: logic expression
  char *oper;
  struct _tree_sql_select_where_condition *left_con;
  struct _tree_sql_select_where_condition *right_con;
  struct _tree_sql_identifier *left_iden;
  struct _tree_sql_identifier *right_iden;
};

struct _tree_sql_select_group
{
  struct _tree_sql_identifier *columns_start;
};

struct _tree_sql_select_order
{
  struct _tree_sql_identifier *columns_start;
};

struct _tree_sql_identifier
{
  char *ident;
  struct _tree_sql_identifier *next;
};

// variables
struct _tree_sql_identifier *start;
struct _tree_sql_identifier *end;

// function definition
/* linker token  */
char *iden_cat(char *iden, ...)
{
  // to do:
  return NULL;
}

/* create sql setence tree node */
struct _tree_sql_select *c_t_sql_s(
struct _tree_sql_select_columns *selection_node,
struct _tree_sql_select_from *from_node,
struct _tree_sql_select_where *where_node,
struct _tree_sql_select_group *group_node,
struct _tree_sql_select_order *order_node
}
{
struct _tree_sql_select *node = (struct _tree_sql_select *)malloc(sizeof(struct _tree_sql_select));
node -> column = selection_node;
node -> from = from_node;
node -> where = where_node;
node -> group = group_node;
node -> order = order_node;

return node;
}

// create select columns node
struct _tree_sql_select_column *c_t_sql_s_cols(struct _tree_sql_identifier *col_iden)
{
  struct _tree_sql_select_columns *node = (struct _tree_select_sql_column *)malloc(sizeof(struct _tree_sql_select_columns));
  node -> columns_start = col_iden;
 
  return node;
}

// create from node
struct _tree_sql_select_from *c_t_sql_s_from(struct _tree_sql_identifier *table_iden)
{
  struct _tree_sql_select_from *node = (struct _tree_sql_select_from *)malloc = (sizeof(struct _tree_sql_select_from));
  node -> tables_start = table_iden;

  return node;
}

// create where child node
struct _tree_sql_select_where *c_t_sql_s_where(struct _tree_sql_select_where_conditon *con_node)
{
  struct _tree_sql_select_where *node = (struct _tree_sql_
}
#endif
