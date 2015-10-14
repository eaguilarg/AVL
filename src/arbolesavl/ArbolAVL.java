package arbolesavl;

import java.util.LinkedList;
import java.util.Queue;

public class ArbolAVL<T extends Comparable> {

    public nodoAVL raiz;

    public ArbolAVL(T elem) {
        raiz = new nodoAVL(elem);
    }

    private nodoAVL<T> buscaAdd(nodoAVL actual, T elem) {
        if (actual == null) {
            return null;
        }
        if (actual.getElem().compareTo(elem) < 0) {
            if (actual.getRight() == null) {
                return actual;
            }
            return buscaAdd(actual.getRight(), elem);
        }
        if (actual.getElem().compareTo(elem) >= 0) {
            if (actual.getLeft() == null) {
                return actual;
            }
            return buscaAdd(actual.getLeft(), elem);
        }

        return actual;
    }

    public void add(T elem) {
        nodoAVL nuevo, papa, auxP;

        papa = buscaAdd(raiz, elem);
        nuevo = new nodoAVL(elem);

        if (elem.compareTo(papa.getElem()) <= 0) {
            papa.setLeft(nuevo);
        } else {
            papa.setRight(nuevo);
        }
        nuevo.setPapa(papa);
        nuevo.setLevel(papa.getLevel() + 1);
        auxP = nuevo;
        if (papa == raiz) {
            papa.setFe(altura(papa.getRight(), 1) - altura(papa.getLeft(), 1));
        } else {
            do {
                auxP = auxP.getPapa();
                if (auxP.getElem().compareTo(elem) >= 0) {
                    auxP.setFe(auxP.getFe() - 1);
                } else {
                    auxP.setFe(auxP.getFe() + 1);
                }
            } while (auxP.getPapa() != null && (auxP.getFe() == 1 || auxP.getFe() == -1));
        }
        if (auxP.getFe() == 0) {

        } else {
            rota(auxP);
        }
    }

    private void rota(nodoAVL raiz0) {
        nodoAVL alfa, beta, gamma;
        alfa = raiz0;

        //System.out.println("alfa:\n"+alfa);
        //System.out.println(alfa.getFe());
        if (alfa.getFe() == 2) {
            beta = alfa.getRight();
            if (beta.getFe() == 1) {
                gamma = beta.getRight();
                rotaDD(alfa, beta, gamma);
            } else {
                gamma = beta.getLeft();
                rotaDI(alfa, beta, gamma);
            }

        } else {

            if (alfa.getFe() == -2) {
                beta = alfa.getLeft();
                if (beta.getFe() == -1) {
                    gamma = beta.getLeft();
                    rotaII(alfa, beta, gamma);
                } else {
                    gamma = beta.getRight();
                    rotaID(alfa, beta, gamma);
                }
            }
        }

        setLevels(raiz, 1);
    }

    private void rotaDD(nodoAVL alfa, nodoAVL beta, nodoAVL gamma) {
        nodoAVL father, b;

        father = alfa.getPapa();
        b = beta.getLeft();

        if (b != null) {
            b.setPapa(alfa);
        }
        alfa.setRight(b);
        alfa.setPapa(beta);
        beta.setLeft(alfa);
        beta.setPapa(father);
        if (father == null) {
            beta.setPapa(null);
            raiz = beta;
        } else {
            if (beta.getElem().compareTo(father.getElem()) < 0) {
                father.setRight(beta);
            } else {
                father.setLeft(beta);
            }
        }

        beta.setLevel(beta.getLevel() - 1);
        beta.setFe(beta.getFe() - 1);
        alfa.setLevel(alfa.getLevel() + 1);
        alfa.setFe(alfa.getFe() - 2);
        gamma.setLevel(gamma.getLevel() - 1);
    }

    private void rotaDI(nodoAVL alfa, nodoAVL beta, nodoAVL gamma) {
        nodoAVL father, b, c;

        father = alfa.getPapa();
        b = gamma.getLeft();
        c = gamma.getRight();

        if (b != null) {
            b.setPapa(alfa);
        }
        alfa.setRight(b);
        if (c != null) {
            c.setPapa(beta);
        }
        beta.setLeft(c);
        alfa.setPapa(gamma);
        beta.setPapa(gamma);
        gamma.setLeft(alfa);
        gamma.setRight(beta);
        gamma.setPapa(father);
        
        if (father == null) {
            gamma.setPapa(null);
            raiz = gamma;
        } else {
            if (gamma.getElem().compareTo(father.getElem()) < 0) {
                father.setLeft(gamma);
            } else {
                father.setRight(gamma);
            }
        }

        beta.setFe(beta.getFe() + 1);
        alfa.setLevel(alfa.getLevel() + 1);
        alfa.setFe(alfa.getFe() - 2);
       // if (alfa.getLeft() != null) {
         //7   alfa.setFe(alfa.getFe() - 1);
        //}
        gamma.setLevel(gamma.getLevel() - 2);
        //gamma.setFe(gamma.getFe() - 1);
    }

    private void rotaII(nodoAVL alfa, nodoAVL beta, nodoAVL gamma) {
        nodoAVL father, b;
        father = alfa.getPapa();
        b = beta.getRight();

        if (b != null) {
            b.setPapa(alfa);
        }
        alfa.setLeft(b);
        alfa.setPapa(beta);
        gamma.setPapa(beta);
        beta.setRight(alfa);
        beta.setLeft(gamma);
        beta.setPapa(father);
        if (father == null) {
            beta.setPapa(null);
            raiz = beta;
        } else {
            if (beta.getElem().compareTo(father.getElem()) < 0) {
                father.setLeft(beta);
            } else {
                father.setRight(beta);
            }
        }
        beta.setLevel(beta.getLevel() - 1);
        beta.setFe(beta.getFe() + 1);
        alfa.setLevel(alfa.getLevel() + 1);
        alfa.setFe(alfa.getFe() + 2);
        gamma.setLevel(gamma.getLevel() - 1);
    }

    private void rotaID(nodoAVL alfa, nodoAVL beta, nodoAVL gamma) {
        nodoAVL father, b, c;

        father = alfa.getPapa();
        b = gamma.getLeft();
        c = gamma.getRight();

        if (b != null) {
            b.setPapa(beta);
        }
        beta.setRight(b);
        if (c != null) {
            c.setPapa(alfa);
        }
        alfa.setLeft(c);
        alfa.setPapa(gamma);
        beta.setPapa(gamma);
        gamma.setLeft(beta);
        gamma.setRight(alfa);
        gamma.setPapa(father);
        if (father == null) {
            gamma.setPapa(null);
            raiz = gamma;
        } else {
            if (gamma.getElem().compareTo(father.getElem()) > 0) {
                father.setRight(gamma);
            } else {
                father.setLeft(gamma);
            }
        }

        beta.setFe(beta.getFe() - 1);
        alfa.setLevel(alfa.getLevel() + 1);
        alfa.setFe(alfa.getFe() + 2);
        gamma.setLevel(gamma.getLevel() - 2);
    }

    private int altura(nodoAVL<T> actual, int prio) {
        if (actual == null) {
            return prio - 1;
        }
        int prio1 = altura(actual.getRight(), prio + 1);
        int prio2 = altura(actual.getLeft(), prio + 1);
        if (prio1 > prio2) {
            return prio1;
        } else {
            return prio2;
        }
    }

    public void printByLevel() {
        Queue<nodoAVL> q = new LinkedList<nodoAVL>();
        nodoAVL<T> nodo;

        q.add(raiz);
        while (!q.isEmpty()) {
            nodo = q.remove();
            System.out.println("\n" + nodo.toString());
            if (nodo.getLeft() != null) {
                // System.out.println(nodo.getLeft().toString());
                q.add(nodo.getLeft());
            }
            if (nodo.getRight() != null) {
                //System.out.println(nodo.getRight().toString());
                q.add(nodo.getRight());
            }
        }
    }

    public nodoAVL<T> busca(T elem) {
        Queue<nodoAVL> q = new LinkedList<nodoAVL>();
        nodoAVL<T> nodo;

        q.add(raiz);
        while (!q.isEmpty()) {
            nodo = q.remove();
            if (nodo.getElem().equals(elem)) {
                return nodo;
            }
            if (nodo.getLeft() != null) {
                // System.out.println(nodo.getLeft().toString());
                q.add(nodo.getLeft());
            }
            if (nodo.getRight() != null) {
                //System.out.println(nodo.getRight().toString());
                q.add(nodo.getRight());
            }
        }

        return null;
    }

    public nodoAVL<T> elimina(T elem) {
        nodoAVL<T> nodo = busca(elem), papa, abuelo, susIO;

        if (nodo == null) {

        } else {
            papa = nodo.getPapa();
            abuelo = papa.getPapa();

            if ((nodo.getLeft() == null) && nodo.getRight() == null) {//Si el nodo es una hoja
                if (nodo.getElem().compareTo(papa.getElem()) <= 0) {
                    papa.setLeft(null);
                } else {
                    papa.setRight(null);
                }
            }

            if (nodo.getLeft() == null) { //Si tiene solo hijo derecho
                nodo.setElem(nodo.getRight().getElem());
                nodo.getRight().setPapa(null);
                nodo.setRight(null);
                nodo.setFe(nodo.getFe() - 1);
            } else if (nodo.getRight() == null) { //si tiene solo hijo izquierdo
                nodo.setElem(nodo.getLeft().getElem());
                nodo.getLeft().setPapa(null);
                nodo.setLeft(null);
                nodo.setFe(nodo.getFe() + 1);
            } else {// Si tiene dos hijos
                susIO = nodo.getLeft();
                papa = susIO.getPapa();
                papa.setRight(null);
                susIO.setPapa(null);
                nodo.setElem(susIO.getElem());
            }

            while (papa != null) {
                papa.setFe(altura(papa.getRight(), papa.getLevel()) - altura(papa.getLeft(), papa.getLevel()));
                papa = papa.getPapa();
            }
        }
        return nodo;
    }

    public nodoAVL<T> getMax(nodoAVL<T> raiz) {
        nodoAVL<T> aux = raiz.getLeft();
        while (aux.getRight() != null) {
            aux = aux.getRight();
        }
        return aux;
    }

    private void setLevels(nodoAVL<T> actual, int level) {//actual inicia en raiz, level inicia en 1
        if (actual == null) {
            return;
        }
        actual.setLevel(level);
        setLevels(actual.getLeft(), level + 1);
        setLevels(actual.getRight(), level + 1);
    }
}
