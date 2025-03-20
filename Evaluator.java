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

    // Suma
    private int evaluateAddition(Stack<Object> stack) {
        int operand1 = (int) evaluateExpression(stack);
        int operand2 = (int) evaluateExpression(stack);
        return operand1 + operand2;
    }

    // Multiplicación
    private int evaluateMultiplication(Stack<Object> stack) {
        int operand1 = (int) evaluateExpression(stack);
        int operand2 = (int) evaluateExpression(stack);
        return operand1 * operand2;
    }

    // QUOTE
    private Object evaluateQuote(Stack<Object> stack) {
        return stack.pop(); // Devuelve el token tal como está (sin evaluarlo)
    }

    // DEFUN
    private Object evaluateDefun(Stack<Object> stack) {
        String functionName = (String) stack.pop(); // Nombre de la función
        List<String> parameters = (List<String>) stack.pop(); // Parámetros de la función
        Stack<Object> functionBody = (Stack<Object>) stack.pop(); // Cuerpo de la función
        environment.put(functionName, new LispFunction(functionName, parameters, functionBody)); // Guarda la función en el entorno
        return functionName;
    }

    // SETQ
    private Object evaluateSetq(Stack<Object> stack) {
        String variableName = (String) stack.pop(); // Nombre de la variable
        Object value = evaluateExpression(stack); // Evaluar el valor a asignar
        environment.put(variableName, value); // Asignar el valor al entorno
        return value;
    }

    // ATOM
    private boolean evaluateAtom(Stack<Object> stack) {
        Object element = evaluateExpression(stack); // Evaluar el elemento
        return !(element instanceof Stack); // Si el elemento no es una lista, es un átomo
    }

    // LIST
    private List<Object> evaluateList(Stack<Object> stack) {
        List<Object> list = new ArrayList<>();
        while (!stack.isEmpty()) {
            list.add(evaluateExpression(stack)); // Evaluamos cada elemento y lo agregamos a la lista
        }
        return list; // Devolvemos la lista
    }

    // EQUAL
    private boolean evaluateEqual(Stack<Object> stack) {
        Object operand1 = evaluateExpression(stack); // Evaluar el primer operando
        Object operand2 = evaluateExpression(stack); // Evaluar el segundo operando
        return operand1.equals(operand2); // Compara si los operandos son iguales
    }

    // LessThan (<)
    private boolean evaluateLessThan(Stack<Object> stack) {
        int operand1 = (int) evaluateExpression(stack);
        int operand2 = (int) evaluateExpression(stack);
        return operand1 < operand2;
    }

    // GreaterThan (>)
    private boolean evaluateGreaterThan(Stack<Object> stack) {
        int operand1 = (int) evaluateExpression(stack);
        int operand2 = (int) evaluateExpression(stack);
        return operand1 > operand2;
    }

    // COND
    private Object evaluateCond(Stack<Object> stack) {
        while (!stack.isEmpty()) {
            Stack<Object> conditionPair = (Stack<Object>) stack.pop(); // Sacamos el par (condición, expresión)
            Object condition = conditionPair.pop(); // La condición
            Object conditionResult = evaluateExpression(new Stack<>(Collections.singletonList(condition))); // Evaluamos la condición

            // Si la condición es verdadera, ejecutamos la expresión asociada
            if (conditionResult instanceof Boolean && (boolean) conditionResult) {
                Stack<Object> resultExpression = (Stack<Object>) conditionPair.pop();
                return evaluateExpression(resultExpression); // Evaluamos y devolvemos el resultado
            }
        }
        return null; // Si ninguna condición fue verdadera, retornamos null

    }

}

    