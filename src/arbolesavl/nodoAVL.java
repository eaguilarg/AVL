
package arbolesavl;

public class nodoAVL<T extends Comparable> {
//raiz tiene nivel 1;
    T elem;
    nodoAVL<T> hijoIzquierdo;
    nodoAVL<T> hijoDerecho, papa;
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

    public void setIzquierda(nodoAVL<T> izq) {
        this.hijoIzquierdo = izq;
    }

    public void setDerecha(nodoAVL<T> derecha) {
        this.hijoDerecho = derecha;
    }

    public void setPapa(nodoAVL<T> papa) {
        this.papa = papa;
    }

    public void setFe(int fc) {
        this.fe = fc;
    }

    public nodoAVL<T> getIzquierda() {
        return hijoIzquierdo;
    }

    public nodoAVL<T> getDerecha() {
        return hijoDerecho;
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
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n"+"Elemento: " + elem.toString()+"\n");
        sb.append("Fe: "+fe + "\n");
        sb.append("Nivel: "+level);
        
        return sb.toString();
    }
    
    
}
