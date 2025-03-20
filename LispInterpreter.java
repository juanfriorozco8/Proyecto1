import java.util.*;

/**
 * La clase LispInterpreter se encarga de interpretar las expresiones Lisp
 * y ejecutar las operaciones definidas en el entorno (environment).
 */
public class LispInterpreter {

    private Environment environment; // Entorno donde se almacenan variables y funciones

    // Metodo constructor del LispInterpreter
    public LispInterpreter(Environment environment) {
        this.environment = environment;
    }

    /**
     * Evalúa una expresión Lisp y devuelve el resultado.
     * 
     * @param expression La expresión Lisp a evaluar.
     * @return El resultado de la evaluación.
     */
    public Object evaluate(LispExpression expression) {
        return expression.evaluate(); // Devuelve el resultado de evaluar la expresión.
    }

    /**
     * Método que interpreta una lista de expresiones y ejecuta operaciones.
     * 
     * @param expressions Las expresiones Lisp que se deben ejecutar.
     * @return El resultado de la ejecución.
     */
    public Object evaluateList(List<LispExpression> expressions) {
        List<Object> results = new ArrayList<>();
        for (LispExpression expr : expressions) {
            results.add(evaluate(expr)); // Se evalua cada expresión y se almacena el resultado.
        }
        return results; // Se retorna una lista con los resultados de las expresiones.
    }
}


