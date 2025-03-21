import java.util.*;

/**
 * La clase LispInterpreter se encarga de interpretar las expresiones Lisp
 * y ejecutar las operaciones definidas en el entorno (environment).
 */
public class LispInterpreter {
    private Environment environment; // Entorno donde se almacenan variables y funciones
    private Evaluator evaluator; // Evaluador de expresiones Lisp

    /**
     * Constructor del LispInterpreter.
     * @param environment El entorno en el que se almacenan variables y funciones.
     */
    public LispInterpreter(Environment environment) {
        this.environment = environment;
        this.evaluator = new Evaluator(environment); // Crear un evaluador para manejar expresiones
    }

    /**
     * Evalúa una expresión Lisp y devuelve el resultado.
     * @param expression La expresión Lisp a evaluar.
     * @return El resultado de la evaluación.
     */
    public Object evaluate(Object expression) {
        return evaluator.evaluateExpression(expression); // Usa el Evaluator para evaluar la expresión
    }

    /**
     * Método que interpreta una lista de expresiones y ejecuta operaciones.
     * @param expressions Lista de expresiones Lisp que se deben ejecutar.
     * @return Lista con los resultados de la ejecución.
     */
    public List<Object> evaluateList(List<Object> expressions) {
        List<Object> results = new ArrayList<>();
        for (Object expr : expressions) {
            results.add(evaluate(expr)); // Evalúa cada expresión y almacena el resultado.
        }
        return results; // Retorna una lista con los resultados de las expresiones.
    }
}



