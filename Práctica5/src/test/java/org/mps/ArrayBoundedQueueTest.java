/*
 * Jesús Repiso Rio
 * Alejandro Cueto Díaz
 */

package org.mps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.boundedqueue.ArrayBoundedQueue;
import org.mps.boundedqueue.EmptyBoundedQueueException;
import org.mps.boundedqueue.FullBoundedQueueException;

public class ArrayBoundedQueueTest {

    @Nested
    @DisplayName("Test para el constructor de la lista circular")
    public class arrayBoundedQueueTests{

        @Test
        @DisplayName("La lista circular debe tener un argumento mayor que cero, si no, lanza IllegalArgumentException")
        public void arrayBoundedQueue_whenArgumentMinorThat1_returnsIllegalArgumentException(){
            assertThatThrownBy(() -> {ArrayBoundedQueue listaCircular = new ArrayBoundedQueue<>(0);
            }).isInstanceOf(IllegalArgumentException.class).hasMessage("ArrayBoundedException: capacity must be positive");
        }

       
        @Test
        @DisplayName("La lista circular se inicializa correctamente si recibe un argumento válido")
        public void arrayBoundedQueue_whenValidArgument_returnsNewArrayBoundedQueue(){
            ArrayBoundedQueue listaCircular = new ArrayBoundedQueue<>(1);
            assertThat(listaCircular.getFirst()).isEqualTo(0);
            assertThat(listaCircular.getLast()).isEqualTo(0);
            assertThat(listaCircular.size()).isEqualTo(0);
        }

    }

    @Nested
    @DisplayName("Test para el método put")
    public class putTests{

        @SuppressWarnings("unchecked")
        @Test
        @DisplayName("La cantidad de elementos de la lista no puede ser mayor que su tamaño predefinido")
        public void put_whenIsFull_returnsFullBoundedException(){
            ArrayBoundedQueue listaCircular = new ArrayBoundedQueue<>(1);
            listaCircular.put("LAPIZ");
            assertThatThrownBy(() -> {listaCircular.put("GOMA");}).isInstanceOf(FullBoundedQueueException.class).hasMessage("put: full bounded queue");
        }

        @SuppressWarnings("unchecked")
        @Test
        @DisplayName("El elemento añadido a la lista no puede ser nulo")
        public void put_whenArgumentNull_returnsIllegalArgumentException(){
            ArrayBoundedQueue listaCircular = new ArrayBoundedQueue<>(1);
            assertThatThrownBy(() -> {listaCircular.put(null);}).isInstanceOf(IllegalArgumentException.class).hasMessage("put: element cannot be null");
        }

        @SuppressWarnings("unchecked")
        @Test
        @DisplayName("El elemento ha sido añadido correctamente")
        public void put_whenArgumentCorrectAndIsNotFull_returnsValidArray(){
            ArrayBoundedQueue listaCircular = new ArrayBoundedQueue<>(2);
            listaCircular.put("LAPIZ");
            assertThat(listaCircular).contains("LAPIZ");
            assertThat(listaCircular.getFirst()).isEqualTo(0);
            assertThat(listaCircular.getLast()).isEqualTo(1);
            assertThat(listaCircular.size()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("Test para el método get")
    public class getTests{

        @Test
        @DisplayName("No puede eliminarse ningún elemento de una lista vacía")
        public void get_whenIsEmpty_returnsEmptyBoundedQueueException(){
            ArrayBoundedQueue listaCircular = new ArrayBoundedQueue<>(1);
            assertThatThrownBy(() -> {listaCircular.get();}).isInstanceOf(EmptyBoundedQueueException.class).hasMessage("get: empty bounded queue");
        }

        @SuppressWarnings("unchecked")
        @Test
        @DisplayName("Devuelve el first de la lista y actualiza sus valores")
        public void get_whenIsNotEmpty_returnsFirstElement(){

            ArrayBoundedQueue listaCircular = new ArrayBoundedQueue<>(2);
            listaCircular.put("LAPIZ");
            assertThat(listaCircular.get()).isEqualTo("LAPIZ");
            assertThat(listaCircular.getFirst()).isEqualTo(1);
            assertThat(listaCircular.getLast()).isEqualTo(1);
            assertThat(listaCircular.size()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Test IsFull")
    public class isFullTests{

        @SuppressWarnings("unchecked")
        @Test
        @DisplayName("Devuelve True cuando la lista está llena")
        public void get_whenIsFull_returnsTrue(){
            ArrayBoundedQueue listaCircular = new ArrayBoundedQueue<>(1);
            listaCircular.put("LAPIZ");
            assertThat(listaCircular.isFull()).isTrue();
        }
    }
    


    @Nested
    @DisplayName("Tests para isEmpty()")
    public class isEmptyTests {

        ArrayBoundedQueue<Integer> a = new ArrayBoundedQueue<>(4);
        
        @Test
        @DisplayName("La cola está vacía")
        public void isEmpty_True_ReturnsTrue(){
            assertThat(a.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("La cola no está vacía")
        public void isEmpty_False_ReturnsFalse(){
            a.put(3);
            assertThat(a.isEmpty()).isFalse();
        }
    }

    @Nested
    @DisplayName("Tests para size()")
    public class sizeTests {

        ArrayBoundedQueue<Integer> a = new ArrayBoundedQueue<>(4);
        
        @Test
        @DisplayName("La cola tiene size 1")
        public void size_One_ReturnsOne(){
            a.put(3);
            assertThat(a.size()).isEqualTo(1);
        }

        @Test
        @DisplayName("La cola tiene size 2")
        public void size_Two_ReturnsTwo(){
            a.put(3);
            a.put(4);
            assertThat(a.size()).isEqualTo(2);
        }

        @Test
        @DisplayName("La cola tiene size 2")
        public void size_TwoAndGetOne_ReturnsOne(){
            a.put(3);
            a.put(4);
            a.get();
            assertThat(a.size()).isEqualTo(1);
        }
    }

    @Nested 
    @DisplayName("Tests para getFirst()")
    public class getFirstTests {

        ArrayBoundedQueue<Integer> a = new ArrayBoundedQueue<>(4);

        @Test 
        @DisplayName("Si la cola está vacía devuelve cero")
        public void getFirst_EmptyQueue_ReturnsZero(){
            assertThat(a.getFirst()).isEqualTo(0);
        }

        @Test 
        @DisplayName("Si la cola no está vacía devuelve el primero")
        public void getFirst_NotEmptyQueue_ReturnsFirst(){
            a.put(4);
            assertThat(a.getFirst()).isEqualTo(0);
        }

        @Test 
        @DisplayName("Si la cola no está vacía y tiene dos elementos devuelve el primero")
        public void getFirst_NotEmptyQueueWithTwoElements_ReturnsFirst(){
            a.put(4);
            a.put(5);
            assertThat(a.getFirst()).isEqualTo(0);
        }

        @Test 
        @DisplayName("Si la cola no está vacía y tiene dos elementos pero quitas el primero devuelve el nuevo primero")
        public void getFirst_NotEmptyQueueWithTwoElementsAndGetFirst_ReturnsNewFirst(){
            a.put(4);
            a.get();
            a.put(5);
            assertThat(a.getFirst()).isEqualTo(1);
        }
    }

    @Nested 
    @DisplayName("Tests para getLast()")
    public class getLastTests {

        ArrayBoundedQueue<Integer> a = new ArrayBoundedQueue<>(4);

        @Test 
        @DisplayName("Si la cola está vacía devuelve cero")
        public void getLast_EmptyQueue_ReturnsZero(){
            assertThat(a.getLast()).isEqualTo(0);
        }

        @Test 
        @DisplayName("Si la cola no está vacía devuelve el primer libre")
        public void getLast_NotEmptyQueue_ReturnsFirstFree(){
            a.put(4);
            assertThat(a.getLast()).isEqualTo(1);
        }

        @Test 
        @DisplayName("Si la cola no está vacía y tiene dos elementos devuelve el primer libre")
        public void getLast_NotEmptyQueueWithTwoElements_ReturnsFirstFree(){
            a.put(4);
            a.put(5);
            assertThat(a.getLast()).isEqualTo(2);
        }

        @Test 
        @DisplayName("Si la cola no está vacía y tiene dos elementos pero quitas el primero devuelve el primer libre")
        public void getLast_NotEmptyQueueWithTwoElementsAndGetFirst_ReturnsFirstFree(){
            a.put(4);
            a.get();
            a.put(5);
            assertThat(a.getLast()).isEqualTo(2);
        }
    }

    @Nested 
    @DisplayName("Tests para la clase iterator")
    public class iteratorTests {

        ArrayBoundedQueue<Integer> a = new ArrayBoundedQueue<>(4);

        @Test 
        @DisplayName("El iterator tiene siguiente")
        public void hasNext_NotEmptyNotFull_ReturnsTrue(){
            a.put(4);
            a.put(3);
            Iterator<Integer> it = a.iterator();
            assertThat(it.hasNext()).isTrue();
        }

        @Test 
        @DisplayName("El iterator no tiene siguiente")
        public void hasNext_Empty_ReturnsFalse(){
            Iterator<Integer> it = a.iterator();
            assertThat(it.hasNext()).isFalse();
        }

        @Test
        @DisplayName("El iterator tiene siguiente asi que avanza")
        public void next_HasNext_Advance(){
            a.put(4);
            a.put(3);
            Iterator<Integer> it = a.iterator();
            assertThat(it.next()).isEqualTo(4);
            assertThat(it.next()).isEqualTo(3);
        }

        @Test
        @DisplayName("El iterator tiene siguiente asi que avanza")
        public void next_NotHasNext_NotAdvance(){
            Iterator<Integer> it = a.iterator();
            assertThatThrownBy(() -> {it.next();}).isInstanceOf(NoSuchElementException.class).hasMessage("next: bounded queue iterator exhausted");
        }
    }
}
