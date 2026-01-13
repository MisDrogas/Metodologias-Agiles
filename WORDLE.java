package Tema1;

import java.util.Scanner;

public class WORDLE {


    public static Scanner sc = new Scanner(System.in);
    
    public static final int TAM = 5;
    public static String[] palabras = {"AVION", "CASAS", "JUEGO", "TOLDO", "ADIOS"};
    public static String palabraCorrecta = "EMPTY";
    
    public static String[][] tablero = new String[TAM][TAM];
    public static String[][] colores = new String[TAM][TAM];

    // Colores ANSI de FONDO 
    public static final String VERDE = "\033[42m";     
    public static final String AMARILLO = "\033[43m";
    public static final String GRIS = "\033[100m";
    public static final String RESET = "\033[0m";
    
   

    public static void seleccionarPalabra() {
        int pos = (int) (Math.random() * palabras.length);
        palabraCorrecta = palabras[pos].toUpperCase();
    }
    public static void introducirPalabra(int fila) {
        String palabra;
        boolean valida;

        do {
            valida = true;

            System.out.print("Introduce la palabra " + (fila + 1) + ": ");
            palabra = sc.next().toUpperCase();

            if (!palabra.matches("[A-Z]+")) {
                System.out.println("Solo letras.");
                valida = false;
            } else if (palabra.length() != TAM) {
                System.out.println("Debe tener " + TAM + " letras.");
                valida = false;
            }

        } while (!valida);

        for (int x = 0; x < TAM; x++) {
            tablero[fila][x] = String.valueOf(palabra.charAt(x));
        }
    }

    public static void mostrarTablero() {
        System.out.println();
        for (int x = 0; x < TAM; x++) {
            for (int z = 0; z < TAM; z++) {
                String letra = tablero[x][z] == null ? " " : tablero[x][z];
                String color = colores[x][z] == null ? "" : colores[x][z];
                System.out.print("[" + color + " " + letra + " " + RESET + "]");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean comprobarPalabra(int fila) {

        boolean[] esVerde = new boolean[TAM];
        int[] contador = new int[26];

        for (int x = 0; x < TAM; x++) {
            char intento = tablero[fila][x].charAt(0);
            char sol = palabraCorrecta.charAt(x);

            if (intento == sol) {
                colores[fila][x] = VERDE;
                esVerde[x] = true;
            }
        }

        for (int x = 0; x < TAM; x++) {
            if (!esVerde[x]) {
                contador[palabraCorrecta.charAt(x) - 'A']++;
            }
        }

        for (int x = 0; x < TAM; x++) {
            if (esVerde[x]) continue;

            char intento = tablero[fila][x].charAt(0);
            int aux = intento - 'A';

            if (aux >= 0 && aux < 26 && contador[aux] > 0) {
                colores[fila][x] = AMARILLO;
                contador[aux]--;
            } else {
                colores[fila][x] = GRIS;
            }
        }

        for (int x = 0; x < TAM; x++) {
            if (tablero[fila][x].charAt(0) != palabraCorrecta.charAt(x)) {
                return false;
            }
        }
        return true;
    }

    public static boolean comprobarJuego() {
        for (int x = 0; x < TAM; x++) {
            introducirPalabra(x);
            boolean ganado = comprobarPalabra(x);
            mostrarTablero();

            if (ganado) {
                System.out.println("¡SUERTE!");
                return true;
            }

            if (x == TAM - 1) {
                System.out.println("La palabra era: " + palabraCorrecta);
            }
        }
        return false;
    }

    public static void main(String[] args) {

        char respuesta;

        do {

            tablero = new String[TAM][TAM];
            colores = new String[TAM][TAM];

            seleccionarPalabra();
            boolean ganado = comprobarJuego();

            if (ganado) {
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
