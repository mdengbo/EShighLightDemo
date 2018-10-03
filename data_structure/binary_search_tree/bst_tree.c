#include <stdio.h>
#include <stdlib.h>
#include "link_bi_tree.h"

//先序遍历方式
void preorder_bsttree(bst_tree tree)
{
    if(tree != NULL)
    {
        printf("%d ", tree->key);
        preorder_bsttree(tree->left);
        preorder_bsttree(tree->right);
    }
}
//中序遍历方式
void inorder_bstree(bst_tree tree)
{
    if(tree != NULL)
    {
        inorder_bstree(tree->left);
        printf("%d ", tree->key);
        inorder_bstree(tree->right);
    }
}
//后序遍历方式
void postorder_bstree(bst_tree tree)
{
    if(tree != NULL)
    {
        postorder_bstree(tree->left);
        postorder_bstree(tree->right);
        printf("%d ", tree->key);
    }
}

static node * create_bstree_node (type key, node * parent, node * left, node * right)
{
    node * p;
    if((p = (node * ) malloc(sizeof(node))) == NULL)
        return NULL;
    p->key = key;
    p->left = left;
    p->right = right;
    p->parent = parent;

    return p;
}
/**
 * 算法理解方式是找准位置，然后插入。哈哈，很有意思。切记：找准位置最重要
 */
// tree是根结点 z是插入结点
static node * bstree_insert(bst_tree tree, node * z)
{
    node * y = NULL;
    node * x = tree;

    //循环找到适合插入的叶子结点位置
    while(x != NULL)
    {
        y = x;
        if(z->key < x->key)
            x = x->left;
        else if(z->key > x->key)
            x = x->right;
        else
        {
            free(z);    //如果相同key值结点，释放此结点不执行插入
            return tree;
        }
    }
    //找到合适的位置后，再插入结点
    z->parent = y;
    if(y == NULL)
        tree=z;
    else if(z->key < y->key)
        y->left = z;
    else
        y->right = z;
    return tree;
}

//插入结点
node * insert_bstree(bst_tree tree, type key)
{
    node * z;

    if((z=create_bstree_node(key, NULL, NULL, NULL)) == NULL)
        return tree;

    return bstree_insert(tree, z);
}

/**
 * 删除一个结点存在三种情况，我认为这个是二叉搜索树中的一个难点，在Introduction to Algorithms [MIT press]
 * 这本书里有详细介绍。以前对这块模模糊糊，现在彻底弄懂了，分享出来。
 * @param tree
 * @param z
 * @return
 */
//删除结点 z是要删除的结点
static node * bstree_delete(bst_tree tree, node * z)
{
    node * x = NULL;//x充当 要删除结点z的左结点或者右结点或者空结点，
    node * y = NULL;//y充当 要删除结点z

    /**
     * 如果z结点存在左右结点，那么就查找他的后继结点，他的后继结点当作y
     * 如果存在一个结点或者不存在结点，那么本身结点就当作y
     */
    if((z->left == NULL) || (z->right == NULL))
        y=z;
    else
        y=bstree_successor(z);

    if(y->left != NULL)
        x = y->left;
    else
        x = y->right;

    //调整x结点的指向父节点位置
    if(x != NULL)
        x->parent = y->parent;

    //调整y父节点指向x
    if(y->parent == NULL)
        tree = x;
    else if(y == y->parent->left)
        y->parent->left = x;
    else
        y->parent->right = x;

    if(y != z)
        z->key = y->key;

    free(y);

    return NULL;
}

/**
 * 根据值 key，查找对应的结点并返回对应的地址
 * 第一步要判断这个值 key是否合法，当然了这个是很简单的
 * 下面一步就体现出二叉搜索树的特点了
 *      第一步到根节点，key如果大于根节点那么到右结点下面去比较
 *                     key如果小于根节点那么到左结点下面去比较
 *      就是这样执行下去知道结点为空为止
 */
node * bstree_search(bst_tree tree, type key)
{
    if(tree == NULL || tree->key == key)
        return tree;

    if(key < tree->key)
        return bstree_search(tree->left, key);
    else
        return bstree_search(tree->right, key);
}
//查找某个结点的后继结点 比如 1 2 3 4 5，查找3的后继结点就是4
node * bstree_successor(node * x)
{
    //如果x存在右子树，则"x的后继结点"为 "以其右孩子为根的子树的最小结点"
    if(x->right != NULL)
        return bstree_minimum(x->right);

    //如果x没有右子树
    //x是一个左孩子，则x的后继节点为他的父亲结点
    //x是一个右孩子，则x的最低父亲结点，并且该父亲结点的左结点等于x
    node * y = x->parent;
    while((y!=NULL) && (x == y->right))
    {
        x=y;
        y = y->parent;
    }
    return y;
}

/**
 * 二叉搜索树是一个很有意思的东西，
 *  如果要寻找是最大的结点，那么就在右子树里寻找，如果右子树为空，那么就是当前的根节点
 *  同理：如果要寻找是最小的结点，那么就在左子树里寻找，如果左子树为空，那么就是当前的根节点
 */
//左子树最左一个结点值是最小的一个结点
node * bstree_minimum(bst_tree tree)
{
    if(tree == NULL)
        return NULL;
    while(tree->left != NULL)
        tree = tree->left;
    return tree;
}
//右子树最右的一个结点值是最大的一个结点
node * bstree_maximum(bst_tree tree)
{
    if(tree == NULL)
        return NULL;
    while(tree->right != NULL)
        tree= tree->right;
    return tree;
}


