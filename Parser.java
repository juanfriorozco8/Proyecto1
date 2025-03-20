import java.util.*;

/**
 * La clase Parser convierte una lista de tokens en estructuras que la computadora pueda operar, usando Stack.
 */
public class Parser {
    private List<String> tokens; // La lista de tokens generados por el Tokenizer
    private int index; // Indice que recorre la lista de tokens

    /**
     * Metodo constructor para el Parser.
     * @param tokens La lista de tokens generada por la clase Tokenizer.
     */
    public Parser(List<String> tokens) {
        this.tokens = tokens;
        this.index = 0; // El indice se inicializa en 0.
    }

    /**
     * Método principal que organiza la estructura usando Stack. 
     * Utiliza Object para poder almacenar cualquier tipo de dato.
     * @return Una Stack que representa la expresión parseada.
     */
    public Stack<Object> parse() {
        Stack<Object> stack = new Stack<>(); 
        parser(stack); // Se llama a si mismo para llenar la Stack (recursion).
        return stack;
    }

    /**
     * Método recursivo que llena la Stack con expresiones Lisp.
     * @param stack Pila donde se almacenarán los elementos de la expresión como tipo Object.
     */
    private void parser(Stack<Object> stack) {
        if (index >= tokens.size()) {
            return; // Si no se encuentran tokens, se termina la recursión.
        }

        String token = tokens.get(index); // Se obtiene el token actual de la lista tokens.
        index++; // Avanza al siguiente token.

        if (token.equals("(")) { // Si el token es un '(', se trata de una subexpresión.
            Stack<Object> subStack = new Stack<>(); // Se crea una nueva pila para la subexpresión.
            while (!tokens.get(index).equals(")")) { // Mientras no se encuentre un ')' se sigue llenando la subexpresión.
                parser(subStack); // Llamado recursivo para subexpresiones.
            }
            index++; // Saltamos el ')'
            stack.push(subStack); // Agregamos la substack a la stack principal.
        } else if (token.equals(")")) { // Si se encuentra un ')', se lanza una excepción. Esto debido a que no puede empezar una expresion con parentesis cerrado.
            throw new RuntimeException("Error: Parentesis en posicion incorrecta.");
        } else {
           
            try {
                stack.push(Integer.parseInt(token)); // Si el token es un número, se convierte a entero y se agrega al stack.
            } catch (NumberFormatException e) {
                stack.push(token); // Si no es un número, se agrega el token como caracter al stack.
            }
        }
    }
}
