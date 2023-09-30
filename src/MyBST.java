/*
 * Name: Eric Yan
 * PID: A17341154
 * E-mail: yuy061@ucsd.edu
 * Reference: JDK Document
 * MyBST.java implements a binary serach tree. It supports dictionary
 * operations like insert, remove, and search. 
 */
import java.util.ArrayList;

/**
 * MyBST implements a binary search tree. It has a BST node as the root and
 * an integer that records the size of the BST.
 */
public class MyBST<K extends Comparable<K>, V> {
    MyBSTNode<K, V> root = null;
    int size = 0;

    /**
     * Get the size of the BST
     * 
     * @return size
     */
    public int size() {
        return size;
    }
    /**
     * Insert a node into the BST. If the key is duplicate, replaced the 
     * original value with the new value.
     * 
     * @param key key of the node that will be inserted
     * @param value value of the node that will be inserted
     * @return Value of node that was replaced
     */
    public V insert(K key, V value) {
        if(key == null) {
            throw new NullPointerException();
        }
        MyBSTNode<K, V> curr = root;
        while(true) {
            if(curr.getKey().compareTo(key) > 0) {
                if(curr.getLeft() == null) {
                    MyBSTNode<K, V> newNode = new MyBSTNode<>(key, value,
                            curr);
                    curr.setLeft(newNode);
                    ++size;
                    return null;
                } else {
                    curr = curr.getLeft();
                }
            } else if(curr.getKey().compareTo(key) < 0) {
                if(curr.getRight() == null) {
                    MyBSTNode<K, V> newNode = new MyBSTNode<>(key, value,
                            curr);
                    curr.setRight(newNode);
                    ++size;
                    return null;
                } else {
                    curr = curr.getRight();
                }
            } else {
                //if duplicate keys occur
                V result = curr.getValue();
                curr.setValue(value);
                return result;
            }
        }
    }
    /**
     * Searcha a key in the BST
     * 
     * @param key key of the node that will be searched
     * @return Value of the node if the node exists. If the node doesn't 
     * exist, return null.
     */
    public V search(K key) {
        if(key == null) {
            return null;
        }
        MyBSTNode<K, V> curr = root;
        while(curr != null) {
            if(curr.getKey().compareTo(key) > 0) {
                curr = curr.getLeft();
            } else if(curr.getKey().compareTo(key) < 0) {
                curr = curr.getRight();
            } else {
                return curr.getValue();
            }
        }
        return null;
    }
    /**
     * Remove an element in the BST.
     * 
     * @param key key of the element that will be removed
     * @return value of the element that was removed
     */
    public V remove(K key) {
        if(key == null) {
            return null;
        }
        MyBSTNode<K, V> curr = root;
        while(curr != null) {
            if(curr.getKey().compareTo(key) > 0) {
                curr = curr.getLeft();
            } else if(curr.getKey().compareTo(key) < 0) {
                curr = curr.getRight();
            } else {
                //If target key is found
                V removedValue = curr.getValue();
                if(curr.getLeft() == null && curr.getRight() == null) {
                    //If remove a leaf
                    removeLeaf(curr); 
                } else if(curr.getLeft() != null
                        && curr.getRight() == null) {
                    //If the node only has left child
                    removeOnlyLeftChild(curr);
                } else if(curr.getLeft() == null
                        && curr.getRight() != null) {
                    //If the node only has right child
                    removeOnlyRightChild(curr);
                } else {
                    //If the node has two childs
                    MyBSTNode<K, V> successor = curr.successor();
                    curr.setKey(successor.getKey());
                    curr.setValue(successor.getValue());
                    if(successor.getRight() != null) {
                        //If the successor has a right child
                        removeOnlyRightChild(successor);
                    } else {
                        //If the successor is a leaf
                        removeLeaf(successor);
                    }
                }
                --size;
                return removedValue;
            }
        }
        return null;
    }
    /**
     * Helper method of remove. Fix the BST if the node only has left child
     * 
     * @param curr node that will be removed
     */
    private void removeOnlyLeftChild(MyBSTNode<K, V> curr) {
        if(curr.getParent() == null) {
            //If the node is the root
            root = curr.getLeft();
        } else if(curr == curr.getParent().getLeft()){
            //If the node is a left child
            curr.getLeft().setParent(curr.getParent());
            curr.getParent().setLeft(curr.getLeft());
        } else {
            //If the node is a right child
            curr.getLeft().setParent(curr.getParent());
            curr.getParent().setRight(curr.getLeft());
        }
    }
    /**
     * Helper method of remove. Fix the BST if the node only has right child
     * 
     * @param curr node that will be removed
     */
    private void removeOnlyRightChild(MyBSTNode<K, V> curr) {
        if(curr.getParent() == null) {
            //If the node is the root
            root = curr.getRight();
        } else if(curr == curr.getParent().getLeft()) {
            //If the node is a left child
            curr.getRight().setParent(curr.getParent());
            curr.getParent().setLeft(curr.getRight());
        } else {
            //If the node is a right child
            curr.getRight().setParent(curr.getParent());
            curr.getParent().setRight(curr.getRight());
        }
    }
    /**
     * Helper method of remove. Fix the BST if the node is a leaf
     * 
     * @param curr node that will be removed
     */
    private void removeLeaf(MyBSTNode<K, V> curr) {
        if(curr.getParent() == null) {
            //If the leaf is root
            root = null;
        } else if(curr == curr.getParent().getLeft()) {
            //If the leaf is a left child
            curr.getParent().setLeft(null);
        } else {
            //If the leaf is a right child
            curr.getParent().setRight(null);
        }
    }
    /**
     * Return a list tha contains all nodes in BST in order. Node is sorted
     * based on their key values. 
     * 
     * @return an ArrayList that contains all nodes in order
     */
    public ArrayList<MyBSTNode<K, V>> inorder() {
        ArrayList<MyBSTNode<K, V>> sorted = new ArrayList<>();
        inorderHelper(root, sorted);
        return sorted;
    }
    /**
     * Helper method of in order. Conduct an inorder traversal to BST using
     * recursion
     * 
     * @param curr current node
     * @param sorted an ArrayList that will sotre all nodes in order
     */
    private void inorderHelper(MyBSTNode<K, V> curr,
            ArrayList<MyBSTNode<K, V>> sorted) {
        if(curr != null) {
            inorderHelper(curr.getLeft(), sorted);
            sorted.add(curr);
            inorderHelper(curr.getRight(), sorted);
        }
    }
    /**
     * MyBSTNode implements a node in BST. Every node has a key and a value.
     * Every node has access to its parent, left, and right child.
     */
    static class MyBSTNode<K, V> {
        private static final String TEMPLATE = "Key: %s, Value: %s";
        private static final String NULL_STR = "null";

        private K key;
        private V value;
        private MyBSTNode<K, V> parent;
        private MyBSTNode<K, V> left = null;
        private MyBSTNode<K, V> right = null;

        /**
         * Creates a MyBSTNode<K,V> storing specified data
         *
         * @param key    the key the MyBSTNode<K,V> will
         * @param value  the data the MyBSTNode<K,V> will store
         * @param parent the parent of this node
         */
        public MyBSTNode(K key, V value, MyBSTNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * Return the key stored in the the MyBSTNode<K,V>
         *
         * @return the key stored in the MyBSTNode<K,V>
         */
        public K getKey() {
            return key;
        }

        /**
         * Set the key stored in the MyBSTNode<K,V>
         *
         * @param newKey the key to be stored
         */
        public void setKey(K newKey) {
            this.key = newKey;
        }

        /**
         * Return data stored in the MyBSTNode<K,V>
         *
         * @return the data stored in the MyBSTNode<K,V>
         */
        public V getValue() {
            return value;
        }

        /**
         * Set the data stored in the MyBSTNode<K,V>
         *
         * @param newValue the data to be stored
         */
        public void setValue(V newValue) {
            this.value = newValue;
        }

        /**
         * Return the parent
         *
         * @return the parent
         */
        public MyBSTNode<K, V> getParent() {
            return parent;
        }

        /**
         * Set the parent
         *
         * @param newParent the parent
         */
        public void setParent(MyBSTNode<K, V> newParent) {
            this.parent = newParent;
        }

        /**
         * Return the left child
         *
         * @return left child
         */
        public MyBSTNode<K, V> getLeft() {
            return left;
        }

        /**
         * Set the left child
         *
         * @param newLeft the new left child
         */
        public void setLeft(MyBSTNode<K, V> newLeft) {
            this.left = newLeft;
        }

        /**
         * Return the right child
         *
         * @return right child
         */
        public MyBSTNode<K, V> getRight() {
            return right;
        }

        /**
         * Set the right child
         *
         * @param newRight the new right child
         */
        public void setRight(MyBSTNode<K, V> newRight) {
            this.right = newRight;
        }

        /**
         * Get the successor node of this node
         * 
         * @return the successor node of this node
         */
        public MyBSTNode<K, V> successor() {
            MyBSTNode<K, V> successor;
            if(getRight() != null) {
                successor = getRight();
                while(successor.getLeft() != null){
                    successor = successor.getLeft();
                }
                return successor;
            }
            MyBSTNode<K, V> ancester = getParent();
            MyBSTNode<K, V> child = this;
            while(ancester != null) {
                if(ancester.getLeft() == child) {
                    return ancester;
                }
                child = ancester;
                ancester = ancester.getParent();
            }
            return null;
        }

        /**
         * This method compares if two node objects are equal.
         *
         * @param obj The target object that the currect object compares to.
         * @return Boolean value indicates if two node objects are equal
         */
        public boolean equals(Object obj) {
            if (!(obj instanceof MyBSTNode))
                return false;

            MyBSTNode<K, V> comp = (MyBSTNode<K, V>) obj;

            return ((this.getKey() == null ? comp.getKey() == null :
                    this.getKey().equals(comp.getKey()))
                    && (this.getValue() == null ? comp.getValue() == null :
                    this.getValue().equals(comp.getValue())));
        }

        /**
         * This method gives a string representation of node object.
         *
         * @return "Key:Value" that represents the node object
         */
        public String toString() {
            return String.format(
                    TEMPLATE,
                    this.getKey() == null ? NULL_STR : this.getKey(),
                    this.getValue() == null ? NULL_STR : this.getValue());
        }
    }

}