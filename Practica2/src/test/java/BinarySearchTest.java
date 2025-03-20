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

    @Nested 
    @DisplayName("Pruebas de isLeaf")
    class isLeafTests{
        Comparator<Integer> comparator;
        BinarySearchTree<Integer> bst;

        @BeforeEach
        void setup(){
            comparator = Integer::compareTo;
            bst = new BinarySearchTree<>(comparator);
        }

        @Test
        @DisplayName("Si el árbol está vacío se lanza una excepción")
        void isLeaf_ValueNull_ThrowsException(){
            assertThrows(BinarySearchTreeException.class, () -> bst.isLeaf());
        }

        @Test
        @DisplayName("Si el nodo no está vacío y es hoja se devuelve true")
        void isLeaf_ValueNotNullAndIsLeaf_ReturnsTrue(){
            bst.insert(10);
            assertEquals(true, bst.isLeaf());
        }

        @Test
        @DisplayName("Si el nodo no está vacío y tiene hijo izquierdo se devuelve false")
        void isLeaf_ValueNotNullAndLeftChild_ReturnsFalse(){
            bst.insert(10);
            bst.insert(5);
            assertEquals(false, bst.isLeaf());
        }

        @Test
        @DisplayName("Si el nodo no está vacío y tiene hijo derecho se devuelve false")
        void isLeaf_ValueNotNullAndRightChild_ReturnsFalse(){
            bst.insert(10);
            bst.insert(12);
            assertEquals(false, bst.isLeaf());
        }
    }

    @Nested
    @DisplayName("Pruebas para contains")
    class containsTests{
        Comparator<Integer> comparator;
        BinarySearchTree<Integer> bst;

        @BeforeEach
        void setup(){
            comparator = Integer::compareTo;
            bst = new BinarySearchTree<>(comparator);
        }

        @Test
        @DisplayName("Si el value es null se devuelve false")
        void contains_ValueNull_ReturnsFalse(){
            assertEquals(false, bst.contains(10));
        }

        @Test
        @DisplayName("Si el value no es null pero no tiene hijo izquierdo (al ser un valor menor buscaría ahí) se devuelve false")
        void contains_ValueNotNullAndNotLeft_ReturnsFalse(){
            bst.insert(10);
            assertEquals(false, bst.contains(5));
        }

        @Test
        @DisplayName("Si el value no es null pero no tiene hijo derecho (al ser un valor mayor buscaría ahí) se devuelve false")
        void contains_ValueNotNullAndNotRight_ReturnsFalse(){
            bst.insert(10);
            assertEquals(false, bst.contains(12));
        }

        @Test
        @DisplayName("Si el value no es null y está en el hijo izquierdo se devuelve true")
        void contains_ValueNotNullAndLeftChild_ReturnsTrue(){
            bst.insert(10);
            bst.insert(5);
            assertEquals(true, bst.contains(5));
        }

        @Test
        @DisplayName("Si el value no es null y está en el hijo derecho se devuelve true")
        void contains_ValueNotNullAndRightChild_ReturnsTrue(){
            bst.insert(10);
            bst.insert(12);
            assertEquals(true, bst.contains(12));
        }
    }

    @Nested 
    @DisplayName("Pruebas para size")
    class sizeTests{
        Comparator<Integer> comparator;
        BinarySearchTree<Integer> bst;

        @BeforeEach
        void setup(){
            comparator = Integer::compareTo;
            bst = new BinarySearchTree<>(comparator);
        }

        @Test
        @DisplayName("Si el árbol está vacío se devuelve un 0")
        void size_ValueNull_ReturnsZero(){
            assertEquals(0, bst.size());
        }

        @Test
        @DisplayName("Si el árbol no está vacío y no tiene ni hijo izquierdo ni derecho se devuelve un 1")
        void size_ValueNullNoChildren_ReturnsOne(){
            bst.insert(10);
            assertEquals(1, bst.size());
        }

        @Test
        @DisplayName("Si el árbol no está vacío y tiene hijo izquierdo se devolverá el size correspondiente")
        void size_ValueNullLeftChild_ReturnsSize(){
            bst.insert(10);
            bst.insert(6);
            bst.insert(3);
            assertEquals(3, bst.size());
        }

        @Test
        @DisplayName("Si el árbol no está vacío y tiene hijo derecho se devolverá el size correspondiente")
        void size_ValueNullRightChild_ReturnsSize(){
            bst.insert(10);
            bst.insert(16);
            bst.insert(23);
            assertEquals(3, bst.size());
        }
    }
}
