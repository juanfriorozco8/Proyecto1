import java.util.HashMap;
import java.util.List;

/**
 * Esta clase representa el entorno donde se guardan las variables y funciones definidas en Lisp.
 * También permite crear entornos "anidados" (como cuando se llama a una función recursiva).
 */
public class Environment {

    // Mapa que guarda las variables y sus valores (ej. x -> 5)
    private HashMap<String, Object> variables;

    // Mapa que guarda las funciones definidas (ej. cuadrado -> función)
    private HashMap<String, LispFunction> functions;

    // Referencia al entorno padre (para funciones anidadas o recursividad)
    private Environment parent;

    /**
     * Constructor por defecto.
     * Inicializa los mapas de variables y funciones vacíos.
     */
    public Environment() {
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
        this.parent = null; // Por defecto no hay entorno padre
    }

    // ENTORNO PADRE (PARA MANEJO DE RECURSIVIDAD)

    /**
     * Asigna un entorno padre. Sirve cuando se entra a una función.
     * @param parent El entorno del cual este va a heredar.
     */
    public void setParent(Environment parent) {
        this.parent = parent;
    }

    /**
     * Devuelve el entorno padre (si lo hay).
     */
    public Environment getParent() {
        return parent;
    }

    /**
     * Verifica si el entorno tiene un padre.
     */
    public boolean hasParent() {
        return parent != null;
    }

    // VARIABLES

    /**
     * Define o actualiza una variable.
     * @param name Nombre de la variable.
     * @param value Valor que se le quiere asignar.
     */
    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }

    /**
     * Busca el valor de una variable.
     * Si no está en este entorno, la busca en el padre (y así sucesivamente).
     * @param name Nombre de la variable.
     * @return Valor de la variable o null si no existe.
     */
    public Object getVariable(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else if (parent != null) {
            return parent.getVariable(name);
        }
        return null;
    }

    /**
     * Verifica si la variable está definida (en este entorno o algún padre).
     */
    public boolean hasVariable(String name) {
        return variables.containsKey(name) || (parent != null && parent.hasVariable(name));
    }

    //FUNCIONES

    /**
     * Guarda una función en el entorno.
     * @param name Nombre de la función.
     * @param function Objeto LispFunction que representa la función.
     */
    public void setFunction(String name, LispFunction function) {
        functions.put(name, function);
    }

    /**
     * Devuelve una función por su nombre. La busca en este entorno o en el padre.
     */
    public LispFunction getFunction(String name) {
        if (functions.containsKey(name)) {
            return functions.get(name);
        } else if (parent != null) {
            return parent.getFunction(name);
        }
        return null;
    }

    /**
     * Verifica si una función está definida en este entorno o en alguno de los padres.
     */
    public boolean hasFunction(String name) {
        return functions.containsKey(name) || (parent != null && parent.hasFunction(name));
    }

    

    /**
     * Esta clase representa una función definida en Lisp.
     * Tiene un nombre, una lista de parámetros, y un cuerpo (una lista de expresiones).
     */
    public static class LispFunction {
        private String name;
        private List<String> parameters;
        private List<Object> body;

        /**
         * Crea una nueva función.
         * @param name Nombre de la función (ej. cuadrado).
         * @param parameters Lista de nombres de parámetros (ej. x, y).
         * @param body Lista de expresiones que forman el cuerpo de la función.
         */
        public LispFunction(String name, List<String> parameters, List<Object> body) {
            this.name = name;
            this.parameters = parameters;
            this.body = body;
        }

        // Getters (accesores)

        public String getName() {
            return name;
        }

        public List<String> getParameters() {
            return parameters;
        }

        public List<Object> getBody() {
            return body;
        }
    }
}
