import java.util.ArrayList;
import java.util.List;

/**
 * La clase Tokenizer convierte una expresión Lisp en una lista de tokens.
 */
public class Tokenizer {
    private String input; // Cadena que contiene la expresión de Lisp.
    private int index;    // Puntero que recorre la cadena.

    /**
     * Método constructor del Tokenizer.
     * 
     * @param input Expresión en Lisp que se tokenizará.
     */
    public Tokenizer(String input) {
        this.input = input;
        this.index = 0; // El índice se inicializa en 0.
    }

    /**
     * Analiza la cadena y genera los tokens usando recursión.
     * 
     * @param tokens Lista donde se almacenan los tokens generados.
     */
    private void tokenizeRecursive(List<String> tokens) {
        if (index >= input.length()) {
            return; // Si el índice llega al final de la cadena, se termina la recursión.
        }

        char c = input.charAt(index); // Se almacena el carácter actual.
        index++; // El índice avanza al siguiente carácter.

        if (c == '(' || c == ')') { 
            tokens.add(Character.toString(c)); // Se agregan los paréntesis como tokens individuales.
        } 
        else if (c == '\'') { 
            tokens.add("quote"); // Convierte el ' en el token "quote".
        } 
        else if (Character.isWhitespace(c)) {
            // Ignorar espacios en blanco.
        } 
        else if ("+-*/".indexOf(c) != -1) { 
            tokens.add(Character.toString(c)); // Guarda operadores como tokens individuales.
        }
        else {
            StringBuilder token = new StringBuilder(); // Se crea un StringBuilder para almacenar el token.

            token.append(c); // Se agrega el primer carácter al token.

            while (index < input.length()) {
                char nextChar = input.charAt(index); // Se almacena el siguiente carácter.
                if (Character.isWhitespace(nextChar) || nextChar == '(' || nextChar == ')' || "+-*/".indexOf(nextChar) != -1) {
                    break; // Termina el token si encuentra un espacio, un paréntesis o un operador.
                }
                token.append(nextChar); // Agrega el siguiente carácter al token.
                index++; // Avanza al siguiente carácter.
            }

            tokens.add(token.toString()); // Se agrega el token como String a la lista de tokens.
        }

        tokenizeRecursive(tokens); // Llamado recursivo para procesar el siguiente carácter.
    }

    /**
     * Método que se encarga de iniciar la tokenización de la cadena.
     * 
     * @return La lista con los tokens generados.
     */
    public List<String> tokenize() {
        List<String> tokens = new ArrayList<>(); // Se crea una lista para almacenar los tokens.
        tokenizeRecursive(tokens); // Se llama al método recursivo para realizar la tokenización.
        return tokens; // Se devuelve la lista con los tokens generados.
    }
}

