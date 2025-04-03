/*
 * Jesús Repiso Rio
 * Alejandro Cueto Díaz
 */

package org.mps.tree;

import java.util.Comparator;

public class BinarySearchTreeMain {
    public static void main(String[] args) {
        Comparator<Integer> comparator = Integer::compareTo;
        BinarySearchTree<Integer> bst = new BinarySearchTree<>(comparator);

        // Insert elements
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);
        bst.insert(12);
        bst.insert(18);
        bst.insert(25);

        // Render tree
        System.out.println("Tree: " + bst.render());

        // Check if tree contains certain values
        System.out.println("Contains 7: " + bst.contains(7));
        System.out.println("Contains 20: " + bst.contains(20));

        // Get minimum and maximum values
        System.out.println("Minimum: " + bst.minimum());
        System.out.println("Maximum: " + bst.maximum());

        // Check size and depth
        System.out.println("Size: " + bst.size());
        System.out.println("Depth: " + bst.depth());

        // Check if a node is a leaf
        System.out.println("Is leaf (10): " + bst.isLeaf());

        // Test inOrder traversal
        System.out.println("In-order traversal: " + bst.inOrder());

        // Test removeValue
        System.out.println("Removing value 15...");
        bst.removeValue(15);
        System.out.println("Tree after removing 15: " + bst.render());
        System.out.println("In-order traversal after removing 15: " + bst.inOrder());

        System.out.println("Removing value 10 (root)...");
        bst.removeValue(10);
        System.out.println("Tree after removing 10: " + bst.render());
        System.out.println("In-order traversal after removing 10: " + bst.inOrder());    
    }
}