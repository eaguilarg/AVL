/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolesavl;

/**
 *
 * @author polo
 */
public class nodoAVL<T extends Comparable> {

    T elem;
    nodoAVL<T> left;
    nodoAVL<T> right, papa;
    int fe;
    int level;

    public nodoAVL() {
        this.fe=0;
        this.level=1;
    }
    
    public nodoAVL(T elem){
        this.elem=elem;
        this.fe=0;
        this.level=1;
    }

    public T getElem() {
        return elem;
    }

    public void setElem(T elem) {
        this.elem = elem;
    }

    public void setLeft(nodoAVL<T> left) {
        this.left = left;
    }

    public void setRight(nodoAVL<T> right) {
        this.right = right;
    }

    public void setPapa(nodoAVL<T> papa) {
        this.papa = papa;
    }

    public void setFe(int fc) {
        this.fe = fc;
    }

    public nodoAVL<T> getLeft() {
        return left;
    }

    public nodoAVL<T> getRight() {
        return right;
    }

    public nodoAVL<T> getPapa() {
        return papa;
    }

    public int getFe() {
        return fe;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int compareTo(nodoAVL<T> otro) {
        return otro.getElem().compareTo(this.elem);
    }

    public String toString() {
        StringBuilder cad = new StringBuilder();

        cad.append("Elemento: " + elem.toString()+"\n");
        cad.append("Factor de Equilibrio: "+fe + "\n");
        cad.append("Nivel: "+level);

        return cad.toString();
    }
    
    
}
