import java.util.*;

/**
 * La clase Evaluator evalúa expresiones Lisp representadas como listas de objetos.
 * Soporta operaciones aritméticas, condicionales, definición de funciones, quote,
 * manipulación de variables, y evaluación recursiva de funciones.
 */ 
public class Evaluator {
    private Environment environment; // Entorno actual donde se almacenan variables y funciones

    /**
     * Constructor del Evaluator.
     * @param environment El entorno donde se definen variables y funciones.
     */
    public Evaluator(Environment environment) {
        this.environment = environment;
    }

    /**
     * Evalúa cualquier expresión Lisp.
     * @param expression La expresión (lista, número, símbolo, etc.) a evaluar.
     * @return El resultado de la evaluación.
     */
    public Object evaluateExpression(Object expression) {
        if (expression instanceof List) {
            List<Object> exprList = (List<Object>) expression;
            if (exprList.isEmpty()) return null;

            Object first = exprList.get(0);

            if (first instanceof String) {
                String operator = (String) first;

                // Evaluar según el operador o nombre de función
                switch (operator) {
                    case "+": return evaluateAddition(exprList);
                    case "-": return evaluateSubtraction(exprList);
                    case "*": return evaluateMultiplication(exprList);
                    case "/": return evaluateDivision(exprList);
                    case "setq": return evaluateSetq(exprList);
                    case "atom": return evaluateAtom(exprList);
                    case "list": return evaluateList(exprList);
                    case "equal": return evaluateEqual(exprList);
                    case "<": return evaluateLessThan(exprList);
                    case ">": return evaluateGreaterThan(exprList);
                    case "<=": return evaluateLessThanOrEqual(exprList);
                    case ">=": return evaluateGreaterThanOrEqual(exprList);
                    case "cond": return evaluateCond(exprList);
                    case "quote": return evaluateQuote(exprList);
                    case "defun": return evaluateDefun(exprList);
                    default:
                        if (environment.hasVariable(operator)) {
                            return environment.getVariable(operator);
                        }
                        if (environment.hasFunction(operator)) {
                            return evaluateFunctionCall(operator, exprList.subList(1, exprList.size()));
                        }
                        throw new RuntimeException("Operador desconocido: " + operator);
                }
            }
        }

        // Si es una variable suelta, la retorna desde el entorno
        if (expression instanceof String && environment.hasVariable((String) expression)) {
            return environment.getVariable((String) expression);
        }

        // Si no es lista ni variable, devuelve directamente (ej. número)
        return expression;
    }

    /**
     * Evalúa una llamada a una función definida con defun.
     */
    private Object evaluateFunctionCall(String name, List<Object> args) {
        Environment.LispFunction function = environment.getFunction(name);
        Environment localEnv = new Environment();
        localEnv.setParent(environment); // Hereda entorno padre

        // Asignar argumentos a parámetros
        for (int i = 0; i < function.getParameters().size(); i++) {
            Object argValue = evaluateExpression(args.get(i));
            localEnv.setVariable(function.getParameters().get(i), argValue);
        }

        // Evaluar cuerpo en entorno local
        Evaluator evaluator = new Evaluator(localEnv);
        Object result = null;
        for (Object expr : function.getBody()) {
            result = evaluator.evaluateExpression(expr);
        }

        return result;
    }

    /**
     * Define una función en el entorno.
     */
    private Object evaluateDefun(List<Object> expr) {
        String functionName = (String) expr.get(1);
        List<String> params = new ArrayList<>();
        for (Object o : (List<?>) expr.get(2)) {
            params.add((String) o);
        }
        List<Object> body = expr.subList(3, expr.size());
        Environment.LispFunction fn = new Environment.LispFunction(functionName, params, body);
        environment.setFunction(functionName, fn);
        return functionName;
    }

    /**
     * Retorna el valor de una expresión sin evaluarla.
     */
    private Object evaluateQuote(List<Object> expr) {
        return expr.get(1);
    }

    /**
     * Evalúa una estructura condicional tipo COND.
     */
    private Object evaluateCond(List<Object> expr) {
        for (int i = 1; i < expr.size(); i++) {
            List<Object> conditionPair = (List<Object>) expr.get(i);
            Object condition = evaluateExpression(conditionPair.get(0));
            if ((condition instanceof Boolean && (Boolean) condition)
                || (!(condition instanceof Boolean) && condition != null)) {
                return evaluateExpression(conditionPair.get(1));
            }
        }
        return null;
    }

    /**
     * Asigna un valor a una variable (SETQ).
     */
    private Object evaluateSetq(List<Object> expr) {
        String name = (String) expr.get(1);
        Object value = evaluateExpression(expr.get(2));
        environment.setVariable(name, value);
        return value;
    }

    /**
     * Devuelve true si la expresión no es una lista (es un átomo).
     */
    private Object evaluateAtom(List<Object> expr) {
        Object val = evaluateExpression(expr.get(1));
        return !(val instanceof List);
    }

    /**
     * Evalúa una lista de expresiones.
     */
    private Object evaluateList(List<Object> expr) {
        List<Object> result = new ArrayList<>();
        for (int i = 1; i < expr.size(); i++) {
            result.add(evaluateExpression(expr.get(i)));
        }
        return result;
    }

    /**
     * Evalúa si dos expresiones son iguales.
     */
    private Object evaluateEqual(List<Object> expr) {
        Object a = evaluateExpression(expr.get(1));
        Object b = evaluateExpression(expr.get(2));
        return a.equals(b);
    }

    // OPERACIONES DE COMPARACION
    private Object evaluateLessThan(List<Object> expr) { // Menor que (<)
        return getNumber(expr.get(1)) < getNumber(expr.get(2));
    }

    private Object evaluateGreaterThan(List<Object> expr) { // Mayor que (>)
        return getNumber(expr.get(1)) > getNumber(expr.get(2));
    }

    private Object evaluateLessThanOrEqual(List<Object> expr) { // Menor o igual que (<=)
        return getNumber(expr.get(1)) <= getNumber(expr.get(2));
    }

    private Object evaluateGreaterThanOrEqual(List<Object> expr) { // Mayor o igual que (>=)
        return getNumber(expr.get(1)) >= getNumber(expr.get(2));
    }

    // OPERACIONES ARITMETICAS
    private Object evaluateAddition(List<Object> expr) { // Suma
        return getNumber(expr.get(1)) + getNumber(expr.get(2));
    }

    private Object evaluateSubtraction(List<Object> expr) { // Resta
        return getNumber(expr.get(1)) - getNumber(expr.get(2));
    }

    private Object evaluateMultiplication(List<Object> expr) { // Multiplicacion
        return getNumber(expr.get(1)) * getNumber(expr.get(2));
    }

    private Object evaluateDivision(List<Object> expr) { // Division
        int divisor = getNumber(expr.get(2));
        if (divisor == 0) throw new ArithmeticException("No se puede dividir por 0");
        return getNumber(expr.get(1)) / divisor;
    }

    /**
     * Obtiene un número desde una expresión o variable.
     * Lanza error si no puede convertirlo a entero.
     */
    private int getNumber(Object obj) {
        Object val = obj;

        if (val instanceof String && environment.hasVariable((String) val)) {
            val = environment.getVariable((String) val);
        } else if (val instanceof List) {
            val = evaluateExpression(val);
        }

        if (val instanceof Integer) {
            return (int) val;
        }

        throw new RuntimeException("Se esperaba un número, pero se obtuvo: " + val);
    }
}


    