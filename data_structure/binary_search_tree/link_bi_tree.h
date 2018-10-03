//
// Created by ecarx on 2018/8/17.
//

#ifndef C_CODE_SOURCE_LINK_BI_TREE_H
#define C_CODE_SOURCE_LINK_BI_TREE_H

typedef int type;

typedef struct bst_tree_node
{
    type key;
    struct bst_tree_node * left;
    struct bst_tree_node * right;
    struct bst_tree_node * parent;
} node, * bst_tree;

void preorder_bsttree(bst_tree tree);

void inorder_bstree(bst_tree tree);

void postorder_bstree(bst_tree tree);

node * insert_bstree(bst_tree tree, type key);

// 找结点(x)的后继结点。即，查找"二叉树中数据值大于该结点"的"最小结点"。
node * bstree_successor(node * x);

node * delete_bstree(bst_tree tree, type key);

//查找最小值的
node * bstree_minimum(bst_tree tree);

//查找最大的一个结点
node * bstree_maximum(bst_tree ree);
//根据type值查找结点
node * bstree_search(bst_tree tree, type key);

#endif //C_CODE_SOURCE_LINK_BI_TREE_H
