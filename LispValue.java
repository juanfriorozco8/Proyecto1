import java.util.*;

/**
 * La clase LispValue implementa LispExpression y se uso para
 * representar los valores básicos de Lisp.
 */
public class LispValue implements LispExpression {
    private Object value; // El valor de la expresión (numero, lista o string)

    // Metodo constructor para crear un lispValue con valor asignado
    public LispValue(Object value) {
        this.value = value;
    }

    @Override // Se sobreescribe el metodo evaluate de LispExpression
    public Object evaluate() { // Devuelve el valor almacenado en la expresión
        return value; 
    }

    // Comprueba si es una lista
    public boolean isList() {
        return value instanceof List;
    }

    // Comprueba si es un número
    public boolean isNumber() {
        return value instanceof Number;
    }

    // Compueba si es un símbolo
    public boolean isSymbol() {
        return value instanceof String;
    }

    // Metodo get para obtener el valor dentro de la expresión
    public Object getValue() {
        return value;
    }

    // Metodo set para asignar un valor a la expresión
    public void setValue(Object value) {
        this.value = value;
    }
}