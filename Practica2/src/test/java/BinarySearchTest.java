/*
 * Jesús Repiso Rio
 * Alejandro Cueto Díaz
 */

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.tree.BinarySearchTree;
import org.mps.tree.BinarySearchTreeException;

public class BinarySearchTest {

    @Test
    @DisplayName("Constructor correcto")
    void BinarySearchTree_Constructor_CreateTree(){
        Comparator<Integer> comparator = Integer::compareTo;
        assertDoesNotThrow(() -> new BinarySearchTree(comparator));
    }

    @Nested
    @DisplayName("Pruebas para insert")
    class insertTests{

        Comparator<Integer> comparator;
        BinarySearchTree<Integer> bst;

        @BeforeEach
        void setup(){
            comparator = Integer::compareTo;
            bst = new BinarySearchTree<>(comparator);
        }

        @Test
        @DisplayName("Insert nodo nulo")
        void insert_ValueNull_ValueInserted(){
            bst.insert(10);
            assertEquals("10", bst.render());
        }

        @Test 
        @DisplayName("Insert nodo izquierdo vacío")
        void insert_LeftNodeNull_ValueInserted(){
            bst.insert(10);
            bst.insert(5);
            assertEquals("10(5,)", bst.render());
        }

        @Test 
        @DisplayName("Insert nodo izquierdo no vacío")
        void insert_LeftNodeNotNull_ValueInserted(){
            bst.insert(10);
            bst.insert(5);
            bst.insert(3);
            assertEquals("10(5(3,),)", bst.render());
        }

        @Test 
        @DisplayName("Insert nodo derecho")
        void insert_RightNodeNull_ValueInserted(){
            bst.insert(10);
            bst.insert(15);
            assertEquals("10(,15)", bst.render());
        }

        @Test 
        @DisplayName("Insert nodo derecho no vacío")
        void insert_RightNodeNotNull_ValueInserted(){
            bst.insert(10);
            bst.insert(12);
            bst.insert(15);
            assertEquals("10(,12(,15))", bst.render());
        }

        @Test 
        @DisplayName("Insert valor ya en el arbol lanza excepción")
        void insert_ValueConatined_ThrowsException(){
            bst.insert(10);
            assertThrows(BinarySearchTreeException.class, () -> bst.insert(10));
        }
    }
}
