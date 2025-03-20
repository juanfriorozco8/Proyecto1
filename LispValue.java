import java.util.*;

/**
 * La clase LispValue implementa LispExpression y se uso para ver los datos
 * de Lisp, como números, cadenas y listas.
 */
public class LispValue implements LispExpression {
    private Object value; // Un valor que puede ser varios tipos de datos de lisp como numeros cadenas o listas

    // constructor para tener un lispvalue de algun valor
    public LispValue(Object value) {
        this.value = value;
    }

    @Override
    public Object evaluate() { //devuelve el valor
        return value; 
    }

    // Se aseegura si es una lista
    public boolean isList() {
        return value instanceof List;
    }

    // Se asegura si es un numero
    public boolean isNumber() {
        return value instanceof Number;
    }

    // Asegura si es un simnbolo
    public boolean isSymbol() {
        return value instanceof String;
    }

    public Object getValue() {
        return value;
    }

    // para hacer el set del valor
    public void setValue(Object value) {
        this.value = value;
    }
}