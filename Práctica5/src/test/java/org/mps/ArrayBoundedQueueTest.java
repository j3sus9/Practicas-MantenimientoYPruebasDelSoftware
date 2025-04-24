package org.mps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.boundedqueue.ArrayBoundedQueue;

public class ArrayBoundedQueueTest {
    
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
