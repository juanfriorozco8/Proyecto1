import java.util.*;

/**
 * La clase LispValue representa los valores básicos en Lisp.
 */
public class LispValue {
    private Object value; // Puede ser un número, lista o string

    /**
     * Constructor para crear un LispValue con un valor asignado.
     * @param value Valor del objeto (puede ser un número, lista o símbolo).
     */
    public LispValue(Object value) {
        this.value = value;
    }

    /**
     * Método para obtener el valor almacenado en la expresión.
     * @return El valor almacenado.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Método para asignar un nuevo valor a la expresión.
     * @param value Nuevo valor a asignar.
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Verifica si el valor almacenado es una lista.
     * @return true si es una lista, false en caso contrario.
     */
    public boolean isList() {
        return value instanceof List;
    }

    /**
     * Verifica si el valor almacenado es un número.
     * @return true si es un número, false en caso contrario.
     */
    public boolean isNumber() {
        return value instanceof Number;
    }

    /**
     * Verifica si el valor almacenado es un símbolo (string).
     * @return true si es un string (símbolo de Lisp), false en caso contrario.
     */
    public boolean isSymbol() {
        return value instanceof String;
    }
}
