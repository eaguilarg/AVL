package arbolesavl;

public class Pelicula implements Comparable<Pelicula> {

    public int id;
    public int año;
    public String nombre;

    public Pelicula() {

    }

    public Pelicula(int id, int año, String nombre) {
        this.id = id;
        this.año = año;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int compareTo(Pelicula t) {
        if (this.id > t.id) {
            return 1;
        }
        if(this.id < t.id){
            return -1;
        }
        return 0;
        //To change body of generated methods, choose Tools | Templates.
    }

    public String toString() {
        StringBuilder cad = new StringBuilder();

        cad.append("Título: " + nombre);
        cad.append("  id: " + id);
        cad.append("   Año: " + año);

        return cad.toString();
    }
}
