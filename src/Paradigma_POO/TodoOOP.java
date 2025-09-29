package Paradigma_POO;

import java.util.*;

/**
 * Aplicación de consola OOP para gestionar tareas.
 * - Dominio: clase Tarea (entidad).
 * - Lógica de negocio: GestorTareas (CRUD).
 * - Interfaz consola: TodoOOP (menú e I/O).
 */
public class TodoOOP {

    private final Scanner sc = new Scanner(System.in);
    private final GestorTareas gestor = new GestorTareas();

    public static void main(String[] args) {
        new TodoOOP().run();
    }

    private void run() {
        while (true) {
            mostrarMenu();
            int opcion = leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1 -> crearTarea();
                case 2 -> listarTareas();
                case 3 -> actualizarDescripcion();
                case 4 -> alternarEstado();
                case 5 -> eliminarTarea();
                case 0 -> { System.out.println("Saliendo... ¡Hasta luego!"); return; }
                default -> System.out.println("Opción no válida.\n");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("========== GESTOR DE TAREAS (OOP) ==========");
        System.out.println("1. Crear tarea");
        System.out.println("2. Listar tareas");
        System.out.println("3. Actualizar descripción");
        System.out.println("4. Alternar estado (pendiente/finalizada)");
        System.out.println("5. Eliminar tarea");
        System.out.println("0. Salir");
    }

    private void crearTarea() {
        String desc = leerTexto("Descripción de la nueva tarea: ");
        if (desc.isBlank()) {
            System.out.println("La descripción no puede estar vacía.\n");
            return;
        }
        Tarea t = gestor.crear(desc.trim());
        System.out.println("✅ Tarea creada con ID " + t.getId() + ".\n");
    }

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

    private void alternarEstado() {
        int id = leerEntero("ID para alternar estado (pendiente/finalizada): ");
        boolean ok = gestor.alternarEstado(id);
        System.out.println(ok ? "🔁 Estado alternado.\n" : "ID no encontrado.\n");
    }

    private void eliminarTarea() {
        int id = leerEntero("ID de la tarea a eliminar: ");
        boolean ok = gestor.eliminar(id);
        System.out.println(ok ? "🗑️  Tarea eliminada.\n" : "ID no encontrado.\n");
    }

    // ===== Utilidades de entrada =====
    private int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            try { return Integer.parseInt(line.trim()); }
            catch (NumberFormatException e) { System.out.println("Por favor ingresa un número entero."); }
        }
    }

    private String leerTexto(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}

/** Entidad de dominio: Tarea */
class Tarea {
    private final int id;
    private String descripcion;
    private boolean finalizada;

    Tarea(int id, String descripcion) {
        this.id = id;
        this.descripcion = Objects.requireNonNull(descripcion);
        this.finalizada = false;
    }

    public int getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public boolean estaFinalizada() { return finalizada; }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank())
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        this.descripcion = descripcion.trim();
    }

    public void alternar() { this.finalizada = !this.finalizada; }

    @Override
    public String toString() {
        return "Tarea{id=" + id + ", desc='" + descripcion + "', finalizada=" + finalizada + "}";
    }
}

/** Servicio/Lógica de negocio: CRUD sobre Tarea */
class GestorTareas {
    // Elegimos List para mantener orden de inserción; un Map auxiliar acelera búsquedas.
    private final List<Tarea> tareas = new ArrayList<>();
    private final Map<Integer, Tarea> indice = new HashMap<>();
    private int siguienteId = 1;

    public Tarea crear(String descripcion) {
        Tarea t = new Tarea(siguienteId++, descripcion);
        tareas.add(t);
        indice.put(t.getId(), t);
        return t;
    }

    public List<Tarea> listar() {
        // Devolvemos copia inmutable para proteger el estado interno
        return Collections.unmodifiableList(new ArrayList<>(tareas));
    }

    public boolean actualizarDescripcion(int id, String nuevaDescripcion) {
        Tarea t = indice.get(id);
        if (t == null) return false;
        t.setDescripcion(nuevaDescripcion);
        return true;
    }

    public boolean alternarEstado(int id) {
        Tarea t = indice.get(id);
        if (t == null) return false;
        t.alternar();
        return true;
    }

    public boolean eliminar(int id) {
        Tarea t = indice.remove(id);
        if (t == null) return false;
        return tareas.remove(t);
    }
}
