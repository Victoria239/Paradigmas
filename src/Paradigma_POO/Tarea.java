package Paradigma_POO;

import java.util.Objects;

/**
 * Entidad de dominio que representa una tarea.
 * Tiene identificador único, descripción y estado de finalización.
 */
public class Tarea {
    /** Identificador único de la tarea.*/
    private final int id;

    /** Descripción (breve) de la tarea.*/
    private String descripcion;

    /** Estado de la tarea: true si finalizada, false si pendiente.*/
    private boolean finalizada;

    /**
     * Crea una nueva tarea con ID y descripción.
     * Por defecto, la tarea inicia como pendiente.
     * @param id identificador único.
     * @param descripcion texto de la tarea (no nulo ni vacío).
     */
    Tarea(int id, String descripcion) {
        this.id = id;
        this.descripcion = Objects.requireNonNull(descripcion);
        this.finalizada = false;
    }

    /** @return identificador único.*/
    public int getId() { return id; }

    /** @return descripción textual de la tarea.*/
    public String getDescripcion() { return descripcion; }

    /** @return true si finalizada, false si pendiente.*/
    public boolean estaFinalizada() { return finalizada; }

    /**
     * Modifica la descripción de la tarea.
     * @param descripcion nueva descripción (no nula ni vacía).
     */
    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank())
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        this.descripcion = descripcion.trim();
    }

    /**
     * Alterna el estado de la tarea entre pendiente y finalizada.
     */
    public void alternar() { this.finalizada = !this.finalizada; }

    /**
     * Devuelve representación textual para depuración.
     */
    @Override
    public String toString() {
        return "Tarea{id=" + id + ", desc='" + descripcion + "', finalizada=" + finalizada + "}";
    }
}
