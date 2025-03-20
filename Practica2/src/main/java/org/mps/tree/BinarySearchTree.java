/*
 * Jesús Repiso Rio
 * Alejandro Cueto Díaz
 */

package org.mps.tree;

import java.util.Comparator;

public class BinarySearchTree<T> implements BinarySearchTreeStructure<T> {
    private Comparator<T> comparator;
    private T value;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;

    public String render(){
        String render = "";

        if (value != null) {
            render += value.toString();
        }

        if (left != null || right != null) {
            render += "(";
            if (left != null) {
                render += left.render();
            }
            render += ",";
            if (right != null) {
                render += right.render();
            }
            render += ")";
        }

        return render;
    }

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
        value = null;
        left = null;
        right = null;
    }

    @Override
    public void insert(T value) {
        if(this.value == null){
            this.value = value;
        }else{
            int aux = comparator.compare(this.value, value);
            if(aux > 0){
                if(left == null){
                    this.left = new BinarySearchTree<>(comparator);
                }
                left.insert(value);
            }else if(aux < 0){
                if(right == null){
                    this.right = new BinarySearchTree<>(comparator);
                }
                right.insert(value);
            }else{
                throw new BinarySearchTreeException("El elemento ya se encuentra en el arbol");
            }
        }
    }

    @Override
    public boolean isLeaf() {
        if(this.value == null){
            throw new BinarySearchTreeException("El arbol esta vacio");
        }else{
            return ((this.left == null) && (this.right == null));
        }
    }

    @Override
    public boolean contains(T value) {
        boolean res = false;
        if(this.value != null){
            int aux = comparator.compare(this.value, value);
            if(aux > 0){
                if(left != null){
                    res = this.left.contains(value);
                }
            }else if(aux < 0){
                if(right != null){
                    res = this.right.contains(value);
                }
            }else{
                res = true;
            }
        }
        return res;
    }

    @Override
    public T minimum() {
        if(this.value == null){
            throw new BinarySearchTreeException("Arbol Binario Vacio");

        }else if(this.left != null){
            return this.left.minimum();
        }
        return this.value;         
    }

    @Override
    public T maximum() {
        if(this.value == null){
            throw new BinarySearchTreeException("Arbol Binario Vacio");

        }else if(this.right != null){
            return this.right.maximum();
        }
        return this.value; 
    }

    @Override
    public void removeBranch(T value){
        if(!contains(value)){
            throw new BinarySearchTreeException("Valor No Hallado");
        }
        borrarRama(value);
    }
    
    private void borrarRama(T value){
            int aux = comparator.compare(this.value, value);
            if(aux > 0){
                    this.left.borrarRama(value);
            }else if(aux < 0){
                    this.right.borrarRama(value);
            }else{
                this.value = null;
                this.left = null;
                this.right = null;
            }
    }

    @Override
    public int size() {
        int num = 0;
        if(this.value != null){
            num++;
            if(this.left != null){
                num += this.left.size();
            }
            if(this.right != null){
                num += this.right.size();
            }
        }
        return num;
    }

    @Override
    public int depth() {
        int num1 = 0;
        int num2 = 0;
        if(this.value != null){
            num1++;
            num2++;
            if(this.left != null){
                num1 += this.left.depth();
            }
            if(this.right != null){
                num2 += this.right.depth();
            }
        }
        return (num1 > num2 ? num1 : num2);
    }

    // Complex operations
    // (Estas operaciones se incluirán más adelante para ser realizadas en la segunda
    // sesión de laboratorio de esta práctica)
}
