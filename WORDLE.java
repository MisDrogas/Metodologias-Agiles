package Tema1;

import java.util.Scanner;

public class WORDLE {


    public static Scanner sc = new Scanner(System.in);
    
    public static final int longitud = 5;
    public static String[] palabras = {"Avion", "Casas", "Arbol", "Toldo", "Juego", "Plaza", "Silla", "Noche", "Campo", };
    public static String palabraCorrecta = "Vacio";
    
    public static String[][] tablero = new String[longitud][longitud];
    public static String[][] colores = new String[longitud][longitud];

    // Colores ANSI de FONDO 
    public static final String reset = "\033[0m";
    public static final String verde = "\033[42m";     
    public static final String gris = "\033[100m";
    public static final String amarillo = "\033[43m";
   
    
   public static void mostrarTablero() {
        System.out.println();
        for (int x = 0; x < longitud; x++) {
            for (int z = 0; z < longitud; z++) {
                String letra = tablero[x][z] == null ? " " : tablero[x][z];
                String color = colores[x][z] == null ? "" : colores[x][z];
                
                if (tablero[x][z] == null) {
                    // Si no hay letra, mostramos corchetes vacíos
                    System.out.print("[" + letra + "]");
                } else {
                    // Si ya hay letra, mostramos solo letra con color
                    System.out.print(color + " " + letra + " " + reset);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void introducirPalabra(int fila) {
        String palabra;
        boolean valido;

        do {
            valido = true;

            System.out.print("Introduce la palabra " + (fila + 1) + ": ");
            palabra = sc.next().toUpperCase();

            if (!palabra.matches("[A-Z]+")) {
                System.out.println("Solo puedes introducir letras letras.");
                valido = false;
            } else if (palabra.length() != longitud) {
                System.out.println("La palabra introducida debe tener " + longitud + " letras.");
                valido = false;
            }

        } while (!valido);

        for (int x = 0; x < longitud; x++) {
            tablero[fila][x] = String.valueOf(palabra.charAt(x));
        }
    }

    public static void seleccionarPalabra() {
        int pos = (int) (Math.random() * palabras.length);
        palabraCorrecta = palabras[pos].toUpperCase();
    }
    
    
    public static boolean comprobarPalabra(int fila) {

        boolean[] esVerde = new boolean[longitud];
        int[] contador = new int[26];

        for (int x = 0; x < longitud; x++) {
            char intento = tablero[fila][x].charAt(0);
            char sol = palabraCorrecta.charAt(x);

            if (intento == sol) {
                colores[fila][x] = verde;
                esVerde[x] = true;
            }
        }

        for (int x = 0; x < longitud; x++) {
            if (!esVerde[x]) {
                contador[palabraCorrecta.charAt(x) - 'A']++;
            }
        }

        for (int x = 0; x < longitud; x++) {
            if (esVerde[x]) continue;

            char intento = tablero[fila][x].charAt(0);
            int aux = intento - 'A';

            if (aux >= 0 && aux < 26 && contador[aux] > 0) {
                colores[fila][x] = amarillo;
                contador[aux]--;
            } else {
                colores[fila][x] = gris;
            }
        }

        for (int x = 0; x < longitud; x++) {
            if (tablero[fila][x].charAt(0) != palabraCorrecta.charAt(x)) {
                return false;
            }
        }
        return true;
    }

    public static boolean comprobarJuego() {
        for (int x = 0; x < longitud; x++) {
            introducirPalabra(x);
            boolean resuelto = comprobarPalabra(x);
            mostrarTablero();

            if (resuelto) {
                System.out.println("¡Suerte!");
                return true;
            }

            if (x == longitud - 1) {
                System.out.println("La palabra era: " + palabraCorrecta);
            }
        }
        return false;
    }

    public static void main(String[] args) {

        char respuesta;

        do {

            tablero = new String[longitud][longitud];
            colores = new String[longitud][longitud];

            seleccionarPalabra();
            boolean resuelto = comprobarJuego();

            if (resuelto) {
                System.out.print("¿Quieres volver a jugar? (S/N): ");
                respuesta = sc.next().toUpperCase().charAt(0);
            } else {
                respuesta = 'N';
                
            }

        } while (respuesta == 'S');

        sc.close();
        System.out.println("Gracias por jugar");
    }
}
