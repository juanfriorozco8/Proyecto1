import java.util.*;

/**
 * La clase Environment almacena variables y funciones en un HashMap,
 * permitiendo que el intérprete recuerde valores definidos previamente.
 */
public class Environment {

    // Mapa donde se almacenan las variables definidas.
    private HashMap<String, Object> variables;

    // Mapa donde se almacenan las funciones definidas.
    private HashMap<String, LispFunction> functions;

    /**
     * Metodo constructor de la clase Environment.
     * Inicializa los HashMaps vacíos.
     */
    public Environment() {
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
    }

    // VARIABLES
    /**
     * Metodo 1 (setVariable): Guarda o actualiza una variable en el entorno.
     * 
     * @param name Nombre de la variable (ejemplo: "y").
     * @param value Valor de la variable (ejemplo: 5).
     */
    public void setVariable(String name, Object value) {
        variables.put(name, value); // Añade o actualiza la variable en el HashMap.
    }

    /**
     * Metodo 2 (getVariable): Obtiene el valor de una variable almacenada en el entorno.
     * 
     * @param name Nombre de la variable.
     * @return El valor almacenado, o null si la variable no existe.
     */
    public Object getVariable(String name) {
        return variables.getOrDefault(name, null); // Si la variable no existe, retorna null.
    }

    /**
     * Metodo 3 (hasVariable): Verifica si una variable existe en el entorno.
     * 
     * @param name Nombre de la variable.
     * @return true si la variable se encuentra en el entorno, false si no.
     */
    public boolean hasVariable(String name) {
        return variables.containsKey(name); // Verifica si la variable está definida.
    }

    // FUNCIONES
    /**
     * Metodo 1 (setFunction): Guarda una función en el entorno.
     * 
     * @param name Nombre de la función ("sum" por ejemplo).
     * @param function Definición de la función (LispFunction).
     */
    public void setFunction(String name, LispFunction function) {
        functions.put(name, function); // Añade la función al HashMap.
    }

    /**
     * Metodo 2 (getFunction): Obtiene una función del entorno por su nombre.
     * 
     * @param name Nombre de la función.
     * @return La definición de la función (LispFunction), o null si no existe.
     */
    public LispFunction getFunction(String name) {
        return functions.getOrDefault(name, null); // Si la función no existe, retorna null.
    }

    /**
     * Metodo 3 (hasFunction): Verifica si una función existe en el entorno.
     * 
     * @param name Nombre de la función.
     * @return true si la función está definida, false si no.
     */
    public boolean hasFunction(String name) {
        return functions.containsKey(name); // Verifica si la función está definida
    }

    /**
     * Clase LispFunction: tomamos la decision de definirla dentro de la clase Environment para mantener 
     * la logica de almacenamiento y acceso a las variables y funciones en una misma clase, favoreciendo 
     * la encapsulacion y cohesion del codigo.
     */
    
    /**
     * Clase interna que representa una función Lisp.
     * Almacena el nombre, los parámetros y el cuerpo de la función.
     */
    public static class LispFunction {
        private String name; // Nombre de la función
        private List<String> parameters; // Parámetros de la función
        private Stack<Object> body; // Cuerpo de la función (expresiones a evaluar)

        /**
         * Metodo constructor para crear una función Lisp.
         * 
         * @param name Nombre de la función.
         * @param parameters Parámetros de la función.
         * @param body Cuerpo de la función (expresiones a evaluar).
         */
        public LispFunction(String name, List<String> parameters, Stack<Object> body) {
            this.name = name;
            this.parameters = parameters;
            this.body = body;
        }

        // Getters para acceder a los atributos de la función, de ser necesario.

        public String getName() {
            return name;
        }

        public List<String> getParameters() {
            return parameters;
        }

        public Stack<Object> getBody() {
            return body;
        }
    }
}

