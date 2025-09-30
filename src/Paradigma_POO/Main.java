package Paradigma_POO;

/**
 * Clase de arranque de la aplicación.
 * Contiene únicamente el método main, punto de entrada estándar en Java.
 * Lanza la interfaz de consola de gestión de tareas basada en POO.
 */
public class Main {
    /**
     * Punto de entrada principal de la aplicación.
     * @param args argumentos de línea de comando (no utilizados).
     */
    public static void main(String[] args) {
        new TodoOOP().run();
    }
}
