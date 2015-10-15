package arbolesavl;

import java.util.LinkedList;
import java.util.Queue;

public class ArbolAVL<T extends Comparable> {

    public nodoAVL raiz;

    public ArbolAVL(T elem) {
        raiz = new nodoAVL(elem);
    }
    public ArbolAVL(){
        
    }

    //funcion que regresa papa del elemento buscado
    private nodoAVL<T> buscaAdd(nodoAVL papa, T elem) {
        if (papa == null) {
            return null;
        }
        if (papa.getElem().compareTo(elem) < 0) {
            if (papa.getDerecha() == null) {
                return papa;
            }
            return buscaAdd(papa.getDerecha(), elem);
        }
        if (papa.getElem().compareTo(elem) >= 0) {
            if (papa.getIzquierda() == null) {
                return papa;
            }
            return buscaAdd(papa.getIzquierda(), elem);
        }

        return papa;
    }

    public void add(T elem) {
        nodoAVL nuevo, papa, auxiliar;

        papa = buscaAdd(raiz, elem);
        nuevo = new nodoAVL(elem);

        if (elem.compareTo(papa.getElem()) <= 0) {//elem <papa
            papa.setIzquierda(nuevo);//agregar izq
        } else {
            papa.setDerecha(nuevo);//agregar der
        }
        nuevo.setPapa(papa);
        nuevo.setLevel(papa.getLevel() + 1);
        auxiliar = nuevo;
        if (papa == raiz) {
            //actualizacion FE
            papa.setFe(altura(papa.getDerecha(), 1) - altura(papa.getIzquierda(), 1));
        }
        else {//papa elem no fue la raiz
            do {//lo va ha ser hasta llegar a la raiz y Fe sean -1 o 1
                auxiliar = auxiliar.getPapa();//redefines aux
                if (auxiliar.getElem().compareTo(elem) >= 0) {//aux(papa elem)>elem
                    auxiliar.setFe(auxiliar.getFe() - 1);//actualizamos fe(elem fue agregado a la izq)
                } else {
                    auxiliar.setFe(auxiliar.getFe() + 1);//actualizamos fe(elem fue agregado a la der)
                }
            } while (auxiliar.getPapa() != null && (auxiliar.getFe() == 1 || auxiliar.getFe() == -1));
        }
        if (auxiliar.getFe() == 0) {
            //no se hace nada, agregar no desvalanceo
        } else {
            rota(auxiliar);//desvalaneco arbol= fe de 2 o -2
        }
    }

    private void rota(nodoAVL actual) {
        nodoAVL nodo1, nodo2, nodo3;
        nodo1 = actual;

        if (nodo1.getFe() == 2) {//arbol cargado a la derecha
            nodo2 = nodo1.getDerecha();
            if (nodo2.getFe() == 1) {//sub arbol cargado a la derecha
                nodo3 = nodo2.getDerecha();
                rotacionDerDer(nodo1, nodo2, nodo3);//rotacion Derecha-Derecha
            } else {//sub arbol cargado a la izquierda
                nodo3 = nodo2.getIzquierda();
                rotacionDerIzq(nodo1, nodo2, nodo3);//rotacion Derecha-Izquierda
            }

        } else {//arbol cargado a la izquierda

            if (nodo1.getFe() == -2) {
                nodo2 = nodo1.getIzquierda();
                if (nodo2.getFe() == -1) {//sub arbol cargado a la izquierda
                    nodo3 = nodo2.getIzquierda();
                    rotacionIzqIzq(nodo1, nodo2, nodo3);//rotacion Izquierda-Izquierda
                } else {//sub arbol cargado a la derecha
                    nodo3 = nodo2.getDerecha();
                    rotacionIzqDer(nodo1, nodo2, nodo3);//rotacion Izquierda-Derecha
                }
            }
        }

        updateNivel(raiz, 1);//actualizar niveles arbol balanceado
    }

    private void rotacionDerDer(nodoAVL nodo1, nodoAVL nodo2, nodoAVL nodo3) {
        nodoAVL papa, aux;

        papa = nodo1.getPapa();
        aux = nodo2.getIzquierda();

        if (aux != null) {// hay hijo izq
            aux.setPapa(nodo1);
        }
        nodo1.setDerecha(aux);
        nodo1.setPapa(nodo2);
        nodo2.setIzquierda(nodo1);
        nodo2.setPapa(papa);
        
        if (papa == null) {//nodo1 no tiene papa
            nodo2.setPapa(null);
            raiz = nodo2;
        } else {//nodo1 si tiene papa
            if (nodo2.getElem().compareTo(papa.getElem()) < 0) {//nodo2<nodo1.papa
                papa.setDerecha(nodo2);
            } else {
                papa.setIzquierda(nodo2);//nodo2>nodo1.papa
            }
        }

        //actualizar nivel y Fe
        nodo2.setLevel(nodo2.getLevel() - 1);
        nodo2.setFe(nodo2.getFe() - 1);
        
        nodo1.setLevel(nodo1.getLevel() + 1);
        nodo1.setFe(nodo1.getFe() - 2);
        
        nodo3.setLevel(nodo3.getLevel() - 1);
    }

    private void rotacionDerIzq(nodoAVL nodo1, nodoAVL nodo2, nodoAVL nodo3) {
        nodoAVL papa, aux1, aux2;

        papa = nodo1.getPapa();
        aux1 = nodo3.getIzquierda();
        aux2 = nodo3.getDerecha();

        if (aux1 != null) {//nodo3 tiene hijo izq
            aux1.setPapa(nodo1);
        }
        nodo1.setDerecha(aux1);
        if (aux2 != null) {// nodo3 tiene hijo der
            aux2.setPapa(nodo2);
        }
        nodo2.setIzquierda(aux2);
        nodo1.setPapa(nodo3);
        nodo2.setPapa(nodo3);
        nodo3.setIzquierda(nodo1);
        nodo3.setDerecha(nodo2);
        nodo3.setPapa(papa);
        
        if (papa == null) {//nodo1 no tiene papa
            nodo3.setPapa(null);
            raiz = nodo3;
        } else {//nodo1 si tiene papa
            if (nodo3.getElem().compareTo(papa.getElem()) < 0) {//nodo3<nodo1.papa
                papa.setIzquierda(nodo3);
            } else {//nodo3>nodo1.papa
                papa.setDerecha(nodo3);
            }
        }

        //actualizar Nivel y Fe
        nodo2.setFe(nodo2.getFe() + 1);
        nodo1.setLevel(nodo1.getLevel() + 1);
        nodo1.setFe(nodo1.getFe() - 2);
        nodo3.setLevel(nodo3.getLevel() - 2);
       
    }

    private void rotacionIzqIzq(nodoAVL nodo1, nodoAVL nodo2, nodoAVL nodo3) {
        nodoAVL papa, aux;
        papa = nodo1.getPapa();
        aux = nodo2.getDerecha();

        if (aux != null) {//nodo2 tiene hijo der
            aux.setPapa(nodo1);
        }
        nodo1.setIzquierda(aux);
        nodo1.setPapa(nodo2);
        nodo3.setPapa(nodo2);
        nodo2.setDerecha(nodo1);
        nodo2.setIzquierda(nodo3);
        nodo2.setPapa(papa);
        
        if (papa == null) {//nodo1 no tiene papa
            nodo2.setPapa(null);
            raiz = nodo2;
        } else {//nodo1 tiene papa
            if (nodo2.getElem().compareTo(papa.getElem()) < 0) {//nodo2<nodo1.papa
                papa.setIzquierda(nodo2);
            } else {//nodo2>nodo1.papa
                papa.setDerecha(nodo2);
            }
        }
        //actualizar Nivel y Fe
        nodo2.setLevel(nodo2.getLevel() - 1);
        nodo2.setFe(nodo2.getFe() + 1);
        nodo1.setLevel(nodo1.getLevel() + 1);
        nodo1.setFe(nodo1.getFe() + 2);
        nodo3.setLevel(nodo3.getLevel() - 1);
    }

    private void rotacionIzqDer(nodoAVL nodo1, nodoAVL nodo2, nodoAVL nodo3) {
        nodoAVL papa, aux1, aux2;

        papa = nodo1.getPapa();
        aux1 = nodo3.getIzquierda();
        aux2 = nodo3.getDerecha();

        if (aux1 != null) {//nodo3 tiene hijo izq
            aux1.setPapa(nodo2);
        }
        nodo2.setDerecha(aux1);
        if (aux2 != null) {//nodo3 tiene hijo der
            aux2.setPapa(nodo1);
        }
        nodo1.setIzquierda(aux2);
        nodo1.setPapa(nodo3);
        nodo2.setPapa(nodo3);
        nodo3.setIzquierda(nodo2);
        nodo3.setDerecha(nodo1);
        nodo3.setPapa(papa);
        
        if (papa == null) {//nodo1 no tiene papa
            nodo3.setPapa(null);
            raiz = nodo3;
        } else {//nodo1 tiene papa
            if (nodo3.getElem().compareTo(papa.getElem()) > 0) {//nodo3>nodo1.papa
                papa.setDerecha(nodo3);
            } else {//nodo3<nodo1.papa
                papa.setIzquierda(nodo3);
            }
        }
        //actualizar Nivel y Fe
        nodo2.setFe(nodo2.getFe() - 1);
        nodo1.setLevel(nodo1.getLevel() + 1);
        nodo1.setFe(nodo1.getFe() + 2);
        nodo3.setLevel(nodo3.getLevel() - 2);
    }

    private int altura(nodoAVL<T> actual, int nivel) {
        if (actual == null) {
            return nivel - 1;
        }
        int alturaDer = altura(actual.getDerecha(), nivel + 1);
        int alturaIzq = altura(actual.getIzquierda(), nivel + 1);
        if (alturaDer > alturaIzq) {
            return alturaDer;
        } else {
            return alturaIzq;
        }
    }

    public void imprmirNivel() {
        Queue<nodoAVL> cola = new LinkedList<nodoAVL>();
        nodoAVL<T> nodo;

        cola.add(raiz);
        while (!cola.isEmpty()) {
            nodo = cola.remove();
            System.out.println("\n" + nodo.toString());
            if (nodo.getIzquierda() != null) {
                cola.add(nodo.getIzquierda());
            }
            if (nodo.getDerecha() != null) {
                cola.add(nodo.getDerecha());
            }
        }
    }
//funcion que busca elemento en arbol, regresa el nodo si lo encuentra
    public nodoAVL<T> busca(T elem) {
        Queue<nodoAVL> q = new LinkedList<nodoAVL>();
        nodoAVL<T> nodo;

        q.add(raiz);
        while (!q.isEmpty()) {
            nodo = q.remove();
            if (nodo.getElem().equals(elem)) {
                return nodo;
            }
            if (nodo.getIzquierda() != null) {
                q.add(nodo.getIzquierda());
            }
            if (nodo.getDerecha() != null) {
                q.add(nodo.getDerecha());
            }
        }

        return null;
    }

    public nodoAVL<T> elimina(T elem) {
        nodoAVL<T> nodo = busca(elem), papa, abuelo, aux;

        if (nodo == null) {
            //si no esta el elem no hace nada
        } else {
            papa = nodo.getPapa();
            abuelo = papa.getPapa();

            if ((nodo.getIzquierda() == null) && nodo.getDerecha() == null) {//nodo es hoja
                if (nodo.getElem().compareTo(papa.getElem()) <= 0) {
                    papa.setIzquierda(null);
                } else {
                    papa.setDerecha(null);
                }
            }

            if (nodo.getIzquierda() == null) { //nodo tiene hijo derecho
                nodo.setElem(nodo.getDerecha().getElem());
                nodo.getDerecha().setPapa(null);
                nodo.setDerecha(null);
                nodo.setFe(nodo.getFe() - 1);
            } else if (nodo.getDerecha() == null) { //nodo tiene hijo izquierdo
                nodo.setElem(nodo.getIzquierda().getElem());
                nodo.getIzquierda().setPapa(null);
                nodo.setIzquierda(null);
                nodo.setFe(nodo.getFe() + 1);
            } else {// nodo tiene ambos hijos
                aux = nodo.getIzquierda();
                papa = aux.getPapa();
                papa.setDerecha(null);
                aux.setPapa(null);
                nodo.setElem(aux.getElem());
            }
            //actualizar Fe desde papa elem hasta raiz
            while (papa != null) {
                papa.setFe(altura(papa.getDerecha(), papa.getLevel()) - altura(papa.getIzquierda(), papa.getLevel()));
                papa = papa.getPapa();
            }
        }
        return nodo;
    }
                                         //raiz      //1      
     private void updateNivel(nodoAVL<T> actual, int level) {
        if (actual == null) {
            return;
        }
        actual.setLevel(level);
        updateNivel(actual.getIzquierda(), level + 1);
        updateNivel(actual.getDerecha(), level + 1);
    }
}
