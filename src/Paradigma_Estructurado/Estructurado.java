package Paradigma_Estructurado;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Estructurado {

    // ===== Estado "global" (estructurado): listas paralelas =====
    static List<Integer> ids = new ArrayList<>();
    static List<String> descripciones = new ArrayList<>();
    static List<Boolean> finalizadas = new ArrayList<>();
    static int siguienteId = 1;

    // ===== Entrada estándar =====
    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Bucle principal del programa (flujo paso a paso)
        while (true) {
            mostrarMenu();
            int opcion = leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1: crearTarea(); break;
                case 2: listarTareas(); break;
                case 3: actualizarDescripcion(); break;
                case 4: alternarFinalizada(); break;
                case 5: eliminarTarea(); break;
                case 0:
                    System.out.println("Saliendo... ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.\n");
            }
        }
    }

    // ===== CRUD =====
    // C: Crear
    static void crearTarea() {
        String desc = leerTexto("Descripción de la nueva tarea: ");
        if (desc.isBlank()) {
            System.out.println("La descripción no puede estar vacía.\n");
            return;
        }
        ids.add(siguienteId++);
        descripciones.add(desc.trim());
        finalizadas.add(false);
        System.out.println("✅ Tarea creada con éxito.\n");
    }

    // R: Leer/Listar
    static void listarTareas() {
        if (ids.isEmpty()) {
            System.out.println("No hay tareas registradas.\n");
            return;
        }
        System.out.println("======= LISTA DE TAREAS =======");
        System.out.printf("%-5s | %-11s | %s%n", "ID", "Estado", "Descripción");
        System.out.println("-----------------------------------------------");
        for (int i = 0; i < ids.size(); i++) {
            String estado = finalizadas.get(i) ? "✔ Finalizada" : "Pendiente";
            System.out.printf("%-5d | %-11s | %s%n", ids.get(i), estado, descripciones.get(i));
        }
        System.out.println();
    }

    // U: Actualizar (editar descripción)
    static void actualizarDescripcion() {
        if (ids.isEmpty()) {
            System.out.println("No hay tareas para actualizar.\n");
            return;
        }
        listarTareas();
        int id = leerEntero("Ingresa el ID de la tarea a actualizar: ");
        int idx = indicePorId(id);
        if (idx == -1) {
            System.out.println("ID no encontrado.\n");
            return;
        }
        String nueva = leerTexto("Nueva descripción: ");
        if (nueva.isBlank()) {
            System.out.println("La descripción no puede estar vacía.\n");
            return;
        }
        descripciones.set(idx, nueva.trim());
        System.out.println("✏️  Descripción actualizada.\n");
    }

    // (Extra útil) Alternar estado finalizada
    static void alternarFinalizada() {
        if (ids.isEmpty()) {
            System.out.println("No hay tareas para cambiar estado.\n");
            return;
        }
        listarTareas();
        int id = leerEntero("Ingresa el ID para alternar estado (pendiente/finalizada): ");
        int idx = indicePorId(id);
        if (idx == -1) {
            System.out.println("ID no encontrado.\n");
            return;
        }
        finalizadas.set(idx, !finalizadas.get(idx));
        System.out.println("🔁 Estado alternado.\n");
    }

    // D: Eliminar
    static void eliminarTarea() {
        if (ids.isEmpty()) {
            System.out.println("No hay tareas para eliminar.\n");
            return;
        }
        listarTareas();
        int id = leerEntero("Ingresa el ID de la tarea a eliminar: ");
        int idx = indicePorId(id);
        if (idx == -1) {
            System.out.println("ID no encontrado.\n");
            return;
        }
        // Eliminar en las tres listas (mantener consistencia)
        ids.remove(idx);
        descripciones.remove(idx);
        finalizadas.remove(idx);
        System.out.println("🗑️  Tarea eliminada.\n");
    }

    // ===== Utilidades =====
    static void mostrarMenu() {
        System.out.println("========== GESTOR DE TAREAS (Estructurado) ==========");
        System.out.println("1. Crear tarea");
        System.out.println("2. Listar tareas");
        System.out.println("3. Actualizar descripción");
        System.out.println("4. Alternar estado (pendiente/finalizada)");
        System.out.println("5. Eliminar tarea");
        System.out.println("0. Salir");
    }

    static int indicePorId(int idBuscado) {
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i) == idBuscado) return i;
        }
        return -1;
    }

    static int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingresa un número entero.");
            }
        }
    }

    static String leerTexto(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}
