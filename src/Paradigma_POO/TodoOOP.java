package Paradigma_POO;

import java.util.*;

/**
 * Interfaz de usuario para la gestión de tareas en consola.
 * Implementa el menú, entrada de usuario e interacción con la lógica de negocio.
 */
public class TodoOOP {

    /** Escáner para manejar la entrada del usuario por consola. */
    private final Scanner sc = new Scanner(System.in);

    /** Servicio de gestión de tareas (CRUD). */
    private final GestorTareas gestor = new GestorTareas();

    /**
     * Ejecución principal del menú en bucle, gestionando la interacción con el usuario.
     */
    void run() {
        while (true) {
            mostrarMenu();
            int opcion = leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1 -> crearTarea();
                case 2 -> listarTareas();
                case 3 -> actualizarDescripcion();
                case 4 -> alternarEstado();
                case 5 -> eliminarTarea();
                case 0 -> {
                    System.out.println("Saliendo... ¡Hasta luego!");
                    return;
                }
                default -> System.out.println("Opción no válida.\n");
            }
        }
    }

    /**
     * Muestra el menú principal en la consola.
     */
    private void mostrarMenu() {
        System.out.println("========== GESTOR DE TAREAS (OOP) ==========");
        System.out.println("1. Crear tarea");
        System.out.println("2. Listar tareas");
        System.out.println("3. Actualizar descripción");
        System.out.println("4. Alternar estado (pendiente/finalizada)");
        System.out.println("5. Eliminar tarea");
        System.out.println("0. Salir");
    }

    /**
     * Solicita una descripción y crea una nueva tarea.
     */
    private void crearTarea() {
        String desc = leerTexto("Descripción de la nueva tarea: ");
        if (desc.isBlank()) {
            System.out.println("La descripción no puede estar vacía.\n");
            return;
        }
        Tarea t = gestor.crear(desc.trim());
        System.out.println("✅ Tarea creada con ID " + t.getId() + ".\n");
    }

    /**
     * Lista todas las tareas existentes.
     */
    private void listarTareas() {
        List<Tarea> todas = gestor.listar();
        if (todas.isEmpty()) {
            System.out.println("No hay tareas registradas.\n");
            return;
        }
        System.out.println("======= LISTA DE TAREAS =======");
        System.out.printf("%-5s | %-11s | %s%n", "ID", "Estado", "Descripción");
        System.out.println("-----------------------------------------------");
        todas.forEach(t -> System.out.printf("%-5d | %-11s | %s%n",
                t.getId(), t.estaFinalizada() ? "✔ Finalizada" : "Pendiente", t.getDescripcion()));
        System.out.println();
    }

    /**
     * Solicita el ID y una nueva descripción para actualizar una tarea.
     */
    private void actualizarDescripcion() {
        int id = leerEntero("ID de la tarea a actualizar: ");
        String nueva = leerTexto("Nueva descripción: ");
        if (nueva.isBlank()) {
            System.out.println("La descripción no puede estar vacía.\n");
            return;
        }
        boolean ok = gestor.actualizarDescripcion(id, nueva.trim());
        System.out.println(ok ? "✏️  Descripción actualizada.\n" : "ID no encontrado.\n");
    }

    /**
     * Alterna el estado de una tarea (pendiente/finalizada) según el ID.
     */
    private void alternarEstado() {
        int id = leerEntero("ID para alternar estado (pendiente/finalizada): ");
        boolean ok = gestor.alternarEstado(id);
        System.out.println(ok ? "🔁 Estado alternado.\n" : "ID no encontrado.\n");
    }

    /**
     * Elimina una tarea por su ID.
     */
    private void eliminarTarea() {
        int id = leerEntero("ID de la tarea a eliminar: ");
        boolean ok = gestor.eliminar(id);
        System.out.println(ok ? "🗑️  Tarea eliminada.\n" : "ID no encontrado.\n");
    }

    // ======= Métodos utilitarios de entrada por consola =======

    /**
     * Solicita y lee un entero desde la consola con validación.
     * @param prompt mensaje de solicitud al usuario.
     * @return entero introducido por el usuario.
     */
    private int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            try { return Integer.parseInt(line.trim()); }
            catch (NumberFormatException e) {
                System.out.println("Por favor ingresa un número entero.");
            }
        }
    }

    /**
     * Solicita y lee un texto (línea) desde la consola.
     * @param prompt mensaje de solicitud al usuario.
     * @return texto introducido por el usuario.
     */
    private String leerTexto(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}
