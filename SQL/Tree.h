#ifndef SQL1TREE
#define SQL1TREE

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdarg.h>

struct _tree_sql_select // sql语句树节点
{
// link select字段树节点
struct _tree_sql_select_columns *column ;
// link select from子句树节点
struct _tree_sql_select_from *from ;
// link select where子句树节点
struct _tree_sql_select_where *where ;
// link select group子句树节点
struct _tree_sql_select_group *group ;
// link select order子句树节点
struct _tree_sql_select_order *order ;
} ;

struct _tree_sql_select_columns // select字段树节点
{
// link字段标识开始节点（所有字段链接在一起）
struct _tree_sql_identifier *columns_start ;
} ;

struct _tree_sql_select_from // select from子句树节点
{
// link表标识符开始节点（所有表链接在一起）
struct _tree_sql_identifier *tables_start ;
} ;

struct _tree_sql_select_where // select where子句树节点
{
struct _tree_sql_select_where_condition *condition_root ;
} ;
struct _tree_sql_select_where_condition // select where 条件子节点
{
char type; // 1:等于表达式 2:逻辑表达式
char *oper ; // and、or、not、=
struct _tree_sql_select_where_condition *left_con ;
struct _tree_sql_select_where_condition *right_con ;
struct _tree_sql_identifier *left_iden ;
struct _tree_sql_identifier *right_iden ;
} ;

struct _tree_sql_select_group // select group by子句树节点
{
// link字段标识开始节点（所有字段链接在一起）
struct _tree_sql_identifier *columns_start ;
} ;

struct _tree_sql_select_order // select order by子句树节点
{
// link字段标识开始节点（所有字段链接在一起）
struct _tree_sql_identifier *columns_start ;
} ;

struct _tree_sql_identifier // 标识符叶子结点
{
// 标识符名
char *ident ;
// link下一个标识符
struct _tree_sql_identifier *next_ ;
} ;

// 中间变量
struct _tree_sql_identifier *start ;
struct _tree_sql_identifier *end ;

// 函数定义
/*
连接标识符。
*/
char * iden_cat (char *iden, ...)
{
// TODO:
return NULL;
}

/*
创建sql语句树节点
*/
struct _tree_sql_select *c_t_sql_s(
struct _tree_sql_select_columns *selection_node ,
struct _tree_sql_select_from *from_node ,
struct _tree_sql_select_where *where_node, 
struct _tree_sql_select_group *group_node,
struct _tree_sql_select_order *order_node
) 
{
struct _tree_sql_select *node = 
(struct _tree_sql_select *) malloc (sizeof(struct _tree_sql_select)) ;
node -> column = selection_node ;
node -> from = from_node ;
node -> where = where_node ;
node -> group = group_node ;
node -> order = order_node ;

return node ;
}

// 创建select columns子节点
struct _tree_sql_select_columns *c_t_sql_s_cols(struct _tree_sql_identifier *col_iden) 
{
struct _tree_sql_select_columns *node = 
(struct _tree_sql_select_columns *) malloc (sizeof(struct _tree_sql_select_columns)) ;
node -> columns_start = col_iden ;

return node;
}

// 创建from子节点
struct _tree_sql_select_from *c_t_sql_s_from(struct _tree_sql_identifier *table_iden)
{
struct _tree_sql_select_from *node = 
(struct _tree_sql_select_from *) malloc (sizeof(struct _tree_sql_select_from)) ;
node -> tables_start = table_iden ;

return node;
}

// 创建where子节点
struct _tree_sql_select_where *c_t_sql_s_where(struct _tree_sql_select_where_condition *con_node)
{
struct _tree_sql_select_where *node = 
(struct _tree_sql_select_where *) malloc (sizeof(struct _tree_sql_select_where)) ;
node -> condition_root = con_node ;

return node ;
}

// 创建算术表达式子节点（等于）
struct _tree_sql_select_where_condition *c_t_sql_s_where_con_a(
char *oper, 
struct _tree_sql_identifier *left, 
struct _tree_sql_identifier *right) 
{
struct _tree_sql_select_where_condition *node = 
(struct _tree_sql_select_where_condition *) malloc (sizeof(struct _tree_sql_select_where_condition)) ;
node -> type = '1' ;
node -> oper = oper ; 
node -> left_iden = left ;
node -> right_iden = right ;

return node ;
}

// 创建逻辑表达式子节点（not,and,or）
struct _tree_sql_select_where_condition *c_t_sql_s_where_con_l(
char *oper,
struct _tree_sql_select_where_condition *left,
struct _tree_sql_select_where_condition *right)
{
struct _tree_sql_select_where_condition *node =
(struct _tree_sql_select_where_condition *) malloc (sizeof(struct _tree_sql_select_where_condition)) ;
node -> type = '2' ;
node -> oper = oper ;
node -> left_con = left ;
node -> right_con = right ;

return node ;
}

// 创建group子节点
struct _tree_sql_select_group *c_t_sql_s_group(struct _tree_sql_identifier *col_node) 
{
struct _tree_sql_select_group *node = 
(struct _tree_sql_select_group *) malloc (sizeof(struct _tree_sql_select_group)) ;
node -> columns_start = col_node ; 

return node;
}

// 创建order子节点
struct _tree_sql_select_order *c_t_sql_s_order(struct _tree_sql_identifier *col_node)
{
struct _tree_sql_select_order *node = 
(struct _tree_sql_select_order *) malloc (sizeof(struct _tree_sql_select_order)) ;
node -> columns_start = col_node ;

return node;
}

// 创建标识符叶子结点
struct _tree_sql_identifier * c_t_sql_iden(char *str)
{
struct _tree_sql_identifier *node = 
(struct _tree_sql_identifier *) malloc (sizeof(struct _tree_sql_identifier)) ;
node -> ident = str ;
node -> next_ = NULL ;

return node ;
}

// 打印函数
void print_iden (unsigned int si, struct _tree_sql_identifier *iden_node)
{
// 打印缩进
unsigned int i = si ;
for (; i > 0; i--)
printf (" ") ;
printf ("%s", iden_node -> ident) ;

if (iden_node -> next_)
{
printf (" , \n") ; 
print_iden (si, iden_node -> next_ ) ;
} 
else 
{
printf ( "\n" ) ;
}
}
void print_condition (unsigned int s1, struct _tree_sql_select_where_condition *con_node) 
{
if (con_node -> type == '1')
{
// 打印缩进
unsigned int i = s1 ;
for (; i > 0; i-- )
printf (" ") ;

printf("%s %s %s\n", 
con_node -> left_iden -> ident, 
con_node -> oper, 
con_node -> right_iden -> ident ) ;
}
else 
{
if (con_node -> right_con) 
{
print_condition (s1, con_node -> left_con) ;

// 打印缩进
unsigned int i = s1 ;
for (; i > 0; i-- )
printf (" ") ; 
printf ("%s\n", con_node -> oper) ;

print_condition (s1, con_node -> right_con) ;
}
else 
{
// 打印缩进
unsigned int i = s1 ;
for (; i > 0; i-- )
printf (" ") ;
printf ("%s\n", con_node -> oper);

print_condition (s1, con_node -> left_con) ; 
}
} 
}

void print_sql (struct _tree_sql_select *sql)
{
// 打印sql
printf ("Print SQL：\n" ) ;

unsigned int si = 6 ;
// 打印select
printf (" select\n") ;
print_iden ( si, sql -> column -> columns_start ) ;

// 打印from
printf (" from\n") ;
print_iden ( si, sql -> from -> tables_start ) ;

// 打印where
if ( sql -> where )
{
printf (" where\n") ;
print_condition ( si, sql -> where -> condition_root ) ;
} 

// 打印group
if ( sql -> group ) 
{
printf (" group by\n") ;
print_iden ( si, sql -> group -> columns_start ) ;
}

// 打印order
if (sql -> order ) 
{
printf (" order by\n") ;
print_iden ( si, sql -> order -> columns_start ) ;
}
}



// free函数
void freeIdentifiers(struct _tree_sql_identifier *iden_node)
{
if (!iden_node -> next_ )
{
free(iden_node -> ident) ;
iden_node -> ident == NULL ;
free(iden_node) ;
}
else
{
freeIdentifiers(iden_node -> next_ );
iden_node -> next_ = NULL ;
free(iden_node) ;
}
}
void freeCondition(struct _tree_sql_select_where_condition *con)
{
if (con -> type == '1')
{
freeIdentifiers(con -> left_iden) ;
freeIdentifiers(con -> right_iden) ;
con -> left_iden = NULL;
con -> right_iden = NULL;
free(con) ;
}
else 
{
if (con -> left_con)
{
freeCondition(con -> left_con) ;
}
if (con -> right_con)
{
freeCondition(con -> right_con) ;
}
con -> left_con = NULL;
con -> right_con = NULL;
free (con) ;
}
}

void freeTree(struct _tree_sql_select *sqlNode) 
{
printf ("Free Tree!\n");
// free select
freeIdentifiers(sqlNode -> column -> columns_start) ;
sqlNode -> column -> columns_start = NULL ;
free(sqlNode -> column) ;
// free from
freeIdentifiers(sqlNode -> from -> tables_start) ; 
sqlNode -> from -> tables_start = NULL ;
free(sqlNode -> from) ;
// free where
if ( sqlNode -> where) 
{
freeCondition( sqlNode -> where -> condition_root ) ;
sqlNode -> where -> condition_root = NULL;
free(sqlNode -> where) ;
}
// free group
if ( sqlNode -> group)
{
freeIdentifiers(sqlNode -> group -> columns_start) ;
sqlNode -> group -> columns_start = NULL;
free(sqlNode -> group) ;
}
// free order
if (sqlNode -> order)
{
freeIdentifiers(sqlNode -> order -> columns_start) ; 
sqlNode -> order -> columns_start = NULL;
free(sqlNode -> order) ;
}

free(sqlNode) ;
}

#endif
