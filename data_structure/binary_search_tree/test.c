#include <stdio.h>
#include "link_bi_tree.h"

static int arr[] = {1, 5, 4, 3, 2, 6};
#define TBL_SIZE(a) (sizeof(a) / sizeof(a[0]))
/**
 * @author chenrui
 * @email chenrui@marsdl.com
 */
void main()
{
    int i, ilen;
    bst_tree  root = NULL;
    printf("==insert node: ");
    ilen = TBL_SIZE(arr);

    for(i=0; i<ilen; i++)
    {
        printf("%d ", arr[i]);
        root = insert_bstree(root, arr[i]);
    }

    printf("\n== : preorder_bsttree");
    preorder_bsttree(root);

    printf("\n==: inorder_bstree");
    inorder_bstree(root);

    printf("\n==: postorder_bstree");
    postorder_bstree(root);

    bst_tree min_tree_node = root;
    node * min_node = bstree_minimum(min_tree_node);
    printf("\n==: bstree_minimum %d ", min_node->key);

    bst_tree max_tree_node = root;
    node * max_node = bstree_maximum(max_tree_node);
    printf("\n==: bstree_maximun %d ", max_node->key);


};

