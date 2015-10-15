/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolesavl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PeliculasAVL {

    public static void main(String[] args) {
        ArrayList peliculas = new ArrayList();
        String linea, nombre;
        Pelicula p;
        int id, año, cuantas = 9999999;
        Scanner lec = null;
        File ent = new File("PruebaAVLlight.txt");
        String datosPelicula[];

        
        
        //lectura archivo
        
        try {
            lec = new Scanner(ent);
        } catch (FileNotFoundException fnfe) {
            System.err.println("ERROR" + fnfe);
        }

        linea = "";
        while (lec.hasNextLine()) {
            linea = lec.nextLine();
            datosPelicula = linea.split("\\,");
            if (datosPelicula.length == 3) {
                id = Integer.parseInt(datosPelicula[0]);
                año = Integer.parseInt(datosPelicula[1]);
                nombre = datosPelicula[2];
                p = new Pelicula(id, año, nombre);
                peliculas.add(p);
            }
        }
        lec.close();

        Pelicula[] pA = new Pelicula[14];
        
        int aux;

        for (int i = 0; i < 14; i++) {
            
            pA[i] = (Pelicula) peliculas.get(i);
        }
        while (cuantas > pA.length) {
            System.out.println("¿Cuántas películas?");
            lec = new Scanner(System.in);
            cuantas = Integer.parseInt(lec.next());
            if (cuantas > pA.length) {
                System.out.println("Solo tengo " + pA.length + " peliculas!!!");
            }
        }
        System.out.println("\nPara estas " + cuantas + " peliculas:\n");
        System.out.println(1 + ".- " + pA[0].toString());
        ArbolAVL a = new ArbolAVL(pA[0]);
        for (int i = 1; i < cuantas; i++) {
            System.out.println(i + 1 + ".- " + pA[i].toString());
            a.add(pA[i]);
        }
       
        a.elimina(pA[1]);
        a.imprmirNivel();

    }
}
