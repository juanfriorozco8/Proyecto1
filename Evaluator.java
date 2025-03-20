import java.util.*;

public class Evaluator {
    private HashMap<String, Object> environment; // Map que almacena funciones y variables ya definidas
    private Stack<Object> stack; // Stack que almacena la expresión a evaluar

    // Metodo constructor para el Evaluator
    public Evaluator(Stack<Object> stack, HashMap<String, Object> environment) {
        this.stack = stack;
        this.environment = environment;
    }

    // Método principal para evaluar la expresión Lisp
    public Object evaluate() {
        return evaluateExpression(stack);
    }

    // Método recursivo para evaluar las expresiones en la pila
    private Object evaluateExpression(Stack<Object> stack) {
        if (stack.isEmpty()) { // Si la pila está vacía, retonamos null.
            return null;
        }

        Object token = stack.pop(); // Se obtiene el token del stack.

        if (token instanceof String) { // Si es un operador o palabra clave se evaluan las posibles operaciones dependiendo del caracter.
            String operator = (String) token;
            switch (operator) {
                case "+":
                    return evaluateAddition(stack); // Operacion suma
                case "*":
                    return evaluateMultiplication(stack); // Operacion multiplicacion
                case "quote":
                    return evaluateQuote(stack); // Operacion quote (no se evalua la expresion)
                case "defun":
                    return evaluateDefun(stack); // Define una funcion
                case "setq":
                    return evaluateSetq(stack); // Operacion Setq: Asigna un valor a una variable
                case "atom":
                    return evaluateAtom(stack); // Operacion Atom: Verifica si un elemento es un atomo
                case "list":
                    return evaluateList(stack); // Agrupa los valores dados en una lista
                case "equal":
                    return evaluateEqual(stack); // Evalua si dos elementos son iguales.
                case "<":
                    return evaluateLessThan(stack); // Evalua si el primer elemento es menor que el segundo.
                case ">":
                    return evaluateGreaterThan(stack); // Evalua si el primer elemento es mayor que el segundo.
                case "cond":
                    return evaluateCond(stack); // Evalua una serie de condiciones. Si una es verdadera, se ejecuta la operacion asociada. De lo contrario, se evalua la siguiente condicion. Si ninguna es verdadera, se retorna nil.
                default:
                    throw new RuntimeException("Operador desconocido: " + operator);
            }
        } else if (token instanceof Integer) {
            // Si es un número, lo retornamos tal cual
            return token;
        } else if (token instanceof Stack) {
            // Si encontramos una subexpresión, la evaluamos
            return evaluateExpression((Stack<Object>) token); // Evalua la subexpresión recursivamente
        }

        return null;
    }

}

