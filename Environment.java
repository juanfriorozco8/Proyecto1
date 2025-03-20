import java.util.HashMap;

/**
 * La clase Environment almacena variables y funciones en un HashMap,
 * permitiendo que el intérprete recuerde valores definidos con setq o defun.
 */

public class Environment {
    private HashMap<String, Object> variables; // Mapa donde se guardan las variables

    /**
     * Constructor de Environment.
     * Inicializa el HashMap vacío.
     */
    public Environment() {
        this.variables = new HashMap<>();
    }

    /**
     * Guarda una variable en el entorno.
     * @param name Nombre de la variable (ejemplo: "x").
     * @param value Valor asociado (ejemplo: 10).
     */
    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }

    /**
     * Obtiene el valor de una variable almacenada.
     * @param name Nombre de la variable.
     * @return El valor almacenado, o null si la variable no existe.
     */
    public Object getVariable(String name) {
        return variables.getOrDefault(name, null);
    }

    /**
     * Verifica si una variable existe en el entorno.
     * @param name Nombre de la variable.
     * @return true si existe, false si no.
     */
    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }
}
