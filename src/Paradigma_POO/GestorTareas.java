package Paradigma_POO;

import java.util.*;

/**
 * Servicio/gestor para operaciones CRUD sobre Tarea.
 * Maneja almacenamiento, recuperación y actualización de tareas.
 */
public class GestorTareas {
    /** Lista ordenada de tareas (mantiene el orden de inserción). */
    private final List<Tarea> tareas = new ArrayList<>();

    /** Mapa auxiliar para acceso rápido a tareas por ID. */
    private final Map<Integer, Tarea> indice = new HashMap<>();

    /** Contador incremental para asignar IDs únicos a tareas. */
    private int siguienteId = 1;

    /**
     * Crea una nueva tarea con la descripción dada.
     * @param descripcion descripción breve de la tarea.
     * @return la Tarea recién creada.
     */
    public Tarea crear(String descripcion) {
        Tarea t = new Tarea(siguienteId++, descripcion);
        tareas.add(t);
        indice.put(t.getId(), t);
        return t;
    }

    /**
     * Obtiene una lista inmutable de todas las tareas.
     * @return lista de tareas.
     */
    public List<Tarea> listar() {
        return Collections.unmodifiableList(new ArrayList<>(tareas));
    }

    /**
     * Actualiza la descripción de una tarea por ID.
     * @param id identificador único de la tarea.
     * @param nuevaDescripcion texto con la nueva descripción.
     * @return true si la tarea fue actualizada, false si no existe.
     */
    public boolean actualizarDescripcion(int id, String nuevaDescripcion) {
        Tarea t = indice.get(id);
        if (t == null) return false;
        t.setDescripcion(nuevaDescripcion);
        return true;
    }

    /**
     * Alterna el estado (pendiente/finalizada) de la tarea indicada.
     * @param id identificador único de la tarea.
     * @return true si fue alternado, false si no existe.
     */
    public boolean alternarEstado(int id) {
        Tarea t = indice.get(id);
        if (t == null) return false;
        t.alternar();
        return true;
    }

    /**
     * Elimina una tarea por su ID.
     * @param id identificador único de la tarea.
     * @return true si fue eliminada, false si no existe.
     */
    public boolean eliminar(int id) {
        Tarea t = indice.remove(id);
        if (t == null) return false;
        return tareas.remove(t);
    }
}
