/*
 * Jesús Repiso Rio
 * Alejandro Cueto Díaz
 */

import java.util.Comparator;
import java.util.List;

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
        void insert_ValueConatined_ThrowsBinarySearchTreeException(){
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
        void isLeaf_ValueNull_ThrowsBinarySearchTreeException(){
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

    @Nested
    @DisplayName("Pruebas para minimum")
    class minimumTests{

        Comparator<Integer> comparator;
        BinarySearchTree<Integer> bst;

        @BeforeEach
        void setup(){
            comparator = Integer::compareTo;
            bst = new BinarySearchTree<>(comparator);
        }

        @Test
        @DisplayName("Arbol Binario nulo")
        void minimum_whenBinarySearchTreeIsNull_returnsBinarySearchTreeException(){
            assertThrows(BinarySearchTreeException.class, ()-> bst.minimum());
        }

        @Test
        @DisplayName("Hijo izquierdo existente")
        void minimum__whenTreeHasLeftChild_returnsLeftMostValue(){
            bst.insert(10);
            bst.insert(5);
            bst.insert(3);
            assertEquals(3, bst.minimum());
        }
    }

    @Nested
    @DisplayName("Pruebas para maximum")
    class maximumTests{

        Comparator<Integer> comparator;
        BinarySearchTree<Integer> bst;

        @BeforeEach
        void setup(){
            comparator = Integer::compareTo;
            bst = new BinarySearchTree<>(comparator);
        }

        @Test
        @DisplayName("Arbol Binario nulo")
        void maximum_whenBinarySearchTreeIsNull_returnsBinarySearchTreeException(){
            assertThrows(BinarySearchTreeException.class, ()-> bst.maximum());
        }

        @Test
        @DisplayName("Hijo derecho existente")
        void maximum__whenTreeHasRightChild_returnsRightMostValue(){
            bst.insert(10);
            bst.insert(12);
            bst.insert(15);
            assertEquals(15, bst.maximum());
        }
    }

    @Nested
    @DisplayName("Pruebas para removeBranch")
    class removeBranchTests{

        Comparator<Integer> comparator;
        BinarySearchTree<Integer> bst;

        @BeforeEach
        void setup(){
            comparator = Integer::compareTo;
            bst = new BinarySearchTree<>(comparator);
        }

        @Test
        @DisplayName("Valor no encontrado")
        void removeBranch_whenValueIsNotFound_returnsBinarySearchTreeException(){
            assertThrows(BinarySearchTreeException.class, ()->bst.removeBranch(10));
        }

        @Test
        @DisplayName("Valor encontrado a la derecha")
        void removeBranch_whenValueIsFound_removeRightNodeFromTree(){
            bst.insert(10);
            bst.insert(12);
            bst.insert(2);
            bst.insert(15);
            bst.removeBranch(12);
            assertEquals("10(2,)", bst.render());
        }

        @Test
        @DisplayName("Valor encontrado a la izquierda")
        void removeBranch_whenValueIsFound_removeLeftNodeFromTree(){
            bst.insert(10);
            bst.insert(12);
            bst.insert(2);
            bst.insert(5);
            bst.removeBranch(2);
            assertEquals("10(,12)", bst.render());
        }

    }

    @Nested
    @DisplayName("Pruebas para depth")
    class depthTests{

        Comparator<Integer> comparator;
        BinarySearchTree<Integer> bst;

        @BeforeEach
        void setup(){
            comparator = Integer::compareTo;
            bst = new BinarySearchTree<>(comparator);
        }

        @Test
        @DisplayName("Arbol Nulo")
        void depth_whenTreeIsNull_returnsZero(){
            assertEquals(0, bst.depth());
        }

        @Test
        @DisplayName("Arbol con altura mayor que cero")
        void depth_whenTreeNotNull_returnsDepthOfTree(){
            bst.insert(10);
            bst.insert(8);
            bst.insert(9);
            bst.insert(11);
            assertEquals(3, bst.depth());
        }

    }

    @Nested
    @DisplayName("Pruebas para inOrder")
    class inOrderTests{

        Comparator<Integer> comparator;
        BinarySearchTree<Integer> bst;

        @BeforeEach
        void setup(){
            comparator = Integer::compareTo;
            bst = new BinarySearchTree<>(comparator);
        }

        @Test
        @DisplayName("Árbol vacío devuelve lista vacía")
        void inOrder_EmptyTree_ReturnsEmptyList(){
            assertEquals(List.of(), bst.inOrder());
        }

        @Test
        @DisplayName("Árbol con un nodo devuelve lista con ese nodo")
        void inOrder_ValueNotNull_ReturnsValue(){
            bst.insert(10);
            assertEquals(List.of( 10), bst.inOrder());
        }

        @Test
        @DisplayName("Árbol con hijo izquierdo devuelve lista con hijo izquierdo")
        void inOrder_LeftChild_ReturnsLeft(){
            bst.insert(10);
            bst.insert(8);
            assertEquals(List.of(8, 10), bst.inOrder());
        }

        @Test
        @DisplayName("Árbol con hijo derecho devuelve lista con hijo derecho")
        void inOrder_RightChild_ReturnsRight(){
            bst.insert(10);
            bst.insert(12);
            assertEquals(List.of( 10, 12), bst.inOrder());
        }
    }

    @Nested
    @DisplayName("Pruebas para balance")
    class balanceTests{

        Comparator<Integer> comparator;
        BinarySearchTree<Integer> bst;

        @BeforeEach
        void setup(){
            comparator = Integer::compareTo;
            bst = new BinarySearchTree<>(comparator);
        }

        @Test
        @DisplayName("Un arbol desbalanceado devuelve el arbol balanceado")
        void balance_whenTreeNotBalanced_returnsTreeBalanced(){

            bst.insert(5);
            bst.insert(4);
            bst.insert(6);
            bst.insert(7);
            bst.insert(8);
            bst.insert(9);

            bst.balance();

            assertEquals("6(4(,5),8(7,9))", bst.render());
        }
    
    }

    @Nested
    @DisplayName("Pruebas para removeValue")
    class removeValue{

        Comparator<Integer> comparator;
        BinarySearchTree<Integer> bst;

        @BeforeEach
        void setup(){
            comparator = Integer::compareTo;
            bst = new BinarySearchTree<>(comparator);
        }

        @Test
        @DisplayName("Borrar un elemento de un arbol vacio lanza una excepcion")
        void removeValue_whenValueNull_returnsBinarySearchTreeException(){
            assertThrows(BinarySearchTreeException.class, ()->bst.removeValue(1));
        }

        @Test 
        @DisplayName("Eliminar el unico valor del arbol")
        void removeValue_whenOneValue_returnsEmptyTree(){
            bst.insert(1);
            bst.removeValue(1);
            assertEquals("", bst.render());
        }

        @Test
        @DisplayName("Eliminar valor izquierdo del arbol")
        void removeValue_whenValueIsLeft_returnsUpdatedTree(){
            bst.insert(10);
            bst.insert(11);
            bst.insert(9);
            bst.insert(8);
            bst.removeValue(9);
            assertEquals("10(8,11)", bst.render());
        }

        @Test
        @DisplayName("Eliminar valor derecho del arbol")
        void removeValue_whenValueIsRight_returnsUpdatedTree(){
            bst.insert(10);
            bst.insert(9);
            bst.insert(11);
            bst.insert(12);
            bst.removeValue(11);
            assertEquals("10(9,12)", bst.render());
        }
        
        @Test
        @DisplayName("Eliminar valor con dos hijos del arbol")
        void removeValue_whenValueHasLeftRight_returnsUpdatedTree(){
            bst.insert(10);
            bst.insert(9);
            bst.insert(12);
            bst.insert(11);
            bst.removeValue(10);
            assertEquals("11(9,12)", bst.render());
        }

        @Test
        @DisplayName("Buscar valor inexistente")
        void removeValue_whenValueNoExist_returnsUpdatedTree(){
            bst.insert(10);
            bst.removeValue(9);
            bst.removeValue(11);
            assertEquals("10", bst.render());
        }
    }
}
