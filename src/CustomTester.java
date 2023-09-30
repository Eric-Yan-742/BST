/*
 * Name: Eric Yan
 * PID: A17341154
 * E-mail: yuy061@ucsd.edu
 * Reference: writeup
 * CustomTester.java tests the implementati onof MyBST.java. It tests each
 * function with various inputs. 
 */
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
public class CustomTester {
    MyBST<Integer, Integer> tree;
    MyBST<Integer, Integer> rootTree;

    /**
     * Set up test fixture
     */
    @Before
    public void setup() {
        MyBST.MyBSTNode<Integer, Integer> root =
                new MyBST.MyBSTNode<>(4, 1, null);
        MyBST.MyBSTNode<Integer, Integer> two =
                new MyBST.MyBSTNode<>(2, 1, root);
        MyBST.MyBSTNode<Integer, Integer> six =
                new MyBST.MyBSTNode<>(6, 1, root);
        MyBST.MyBSTNode<Integer, Integer> one =
                new MyBST.MyBSTNode<>(1, 2, two);
        MyBST.MyBSTNode<Integer, Integer> three =
                new MyBST.MyBSTNode<>(3, 30, two);
        MyBST.MyBSTNode<Integer, Integer> five =
                new MyBST.MyBSTNode<>(5, 50, six);
        this.tree = new MyBST<>();
        this.tree.root = root;
        root.setLeft(two);
        root.setRight(six);
        two.setLeft(one);
        two.setRight(three);
        six.setLeft(five);
        tree.size = 6;

        rootTree = new MyBST<>();
        MyBST.MyBSTNode<Integer, Integer> threeRoot = new MyBST.MyBSTNode<Integer,Integer>(3, 1, null);
        rootTree.root = threeRoot;
        rootTree.size = 1;

    }
    /**
     * Create a MyBST
     * 
     * @return a MyBST
     */
    private MyBST<Integer, Integer> treeFactory() {
        MyBST<Integer, Integer> tree2 = new MyBST<>();
        MyBST.MyBSTNode<Integer, Integer> root =
                new MyBST.MyBSTNode<>(4, 1, null);
        MyBST.MyBSTNode<Integer, Integer> two =
                new MyBST.MyBSTNode<>(2, 1, root);
        MyBST.MyBSTNode<Integer, Integer> six =
                new MyBST.MyBSTNode<>(6, 1, root);
        MyBST.MyBSTNode<Integer, Integer> one =
                new MyBST.MyBSTNode<>(1, 2, two);
        MyBST.MyBSTNode<Integer, Integer> three =
                new MyBST.MyBSTNode<>(3, 30, two);
        MyBST.MyBSTNode<Integer, Integer> five =
                new MyBST.MyBSTNode<>(5, 50, six);
        MyBST.MyBSTNode<Integer, Integer> seven = 
                new MyBST.MyBSTNode<Integer,Integer>(7, 70, six);
        MyBST.MyBSTNode<Integer, Integer> nine = 
                new MyBST.MyBSTNode<Integer,Integer>(9, 90, seven);
        tree2.root = root;
        root.setLeft(two);
        root.setRight(six);
        two.setLeft(one);
        two.setRight(three);
        six.setLeft(five);
        six.setRight(seven);
        seven.setRight(nine);
        tree2.size = 8;
        return tree2;
    }

    /**
     * test successor
     */
    @Test
    public void testSuccessor() {
        assertSame(null, tree.root.getRight().successor()); //6
        assertSame(tree.root.getRight(), tree.root.getRight().getLeft().successor()); //5
        assertSame(tree.root, tree.root.getLeft().getRight().successor()); //3
        assertSame(tree.root.getRight().getLeft(), tree.root.successor()); //4(root)
    }
    /**
     * test insert
     */
    @Test
    public void testInsert() {
        //test insert after root
        assertSame(null, rootTree.insert(4, 1));
        assertEquals("element should be inserted", 4, rootTree.root.getRight().getKey().intValue());
        assertEquals("element should be inserted", 1, rootTree.root.getRight().getValue().intValue());
        assertSame("Parent is set", rootTree.root, rootTree.root.getRight().getParent());
        assertEquals("Size is incremented by 1", 2, rootTree.size);
        //test replacement
        assertEquals(1, tree.insert(6, 2).intValue());
        assertEquals("original value should be replaced", 2, tree.root.getRight().getValue().intValue());
        assertEquals("size should not change", 6, tree.size());
        //test add to a complex tree
        assertSame(null, tree.insert(7, 1));
        assertEquals("element should be inserted", 7, tree.root.getRight().getRight().getKey().intValue());
        assertEquals("element should be inserted", 1, tree.root.getRight().getRight().getValue().intValue());
        assertSame("Parent is set", tree.root.getRight(), tree.root.getRight().getRight().getParent());
        assertEquals("Size is incremented by 1", 7, tree.size);
    }
    /**
     * test search
     */
    @Test
    public void testSearch() {
        assertSame(null, tree.search(null));
        assertEquals(Integer.valueOf(1), rootTree.search(3));
        assertEquals(Integer.valueOf(50), tree.search(5));
        assertSame(null, tree.search(7));
    }
    /**
     * test remove
     */
    @Test
    public void testRemove() {
        MyBST.MyBSTNode<Integer, Integer> root = tree.root;

        assertSame("Should return null if remove(null)", null, tree.remove(null));
        assertSame("Should return null if remove a non-exist element", null, tree.remove(7));
        //Remove a leaf
        assertEquals(2, tree.remove(1).intValue());
        assertNull(root.getLeft().getLeft());
        //Remove a node with only left child
        assertEquals(1, tree.remove(6).intValue());
        assertEquals(5, root.getRight().getKey().intValue());
        assertEquals("size of tree", 4, tree.size);
        //Remove a node with only right child
        assertEquals(1, tree.remove(2).intValue());
        assertEquals(3, root.getLeft().getKey().intValue());

        MyBST<Integer, Integer> tree2 = treeFactory();
        //Remove a node with 2 child
        assertEquals(1, tree2.remove(2).intValue());
        assertEquals(7, tree2.size);
        assertEquals(3, tree2.root.getLeft().getKey().intValue());
        assertEquals(1, tree2.root.getLeft().getLeft().getKey().intValue());
        //Remove a node with 2 child (successor has right node)
        assertEquals(1, tree2.remove(6).intValue());
        assertEquals(7, tree2.root.getRight().getKey().intValue());
        assertEquals(9, tree2.root.getRight().getRight().getKey().intValue());
    }
    /**
     * test inorder
     */
    @Test
    public void testInorder() {
        MyBST<Integer, Integer> empty = new MyBST<>();
        assertEquals(Arrays.asList(), empty.inorder());
        ArrayList<Integer> actualKey = new ArrayList<>();
        for(MyBST.MyBSTNode<Integer, Integer> n : tree.inorder()) {
                actualKey.add(n.getKey());
        }
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), actualKey);
    }
    
}
