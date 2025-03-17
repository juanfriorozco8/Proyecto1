import java.util.ArrayList;
import java.util.List;

/**
 * La clase Tokenizer convierte una expresión Lisp en una lista de tokens.
 * Ignora la primera palabra (defun).
 */
public class Tokenizer {
    private String input; // Cadena que contiene la expresión de Lisp.
    private int index;    // Puntero que recorre la cadena.

    /**
     * Metodo constructor del Tokenizer.
     * 
     * @param input Expresión en Lisp que se tokenizará.
     */
    public Tokenizer(String input) {
        this.input = input;
        this.index = 0;
        skipDefun(); // Función que ignora la primera palabra (defun).
    }

     /**
     * Metodo 1 (Tokenize): analiza la cadena y genera los tokens (con recursión).
     * 
     * @param tokens Lista donde se almacenan los tokens generados.
     */
    private void Tokenize(List<String> tokens) {
        if (index >= input.length()) {
            return; // Si el indice llega al final de la cadena, se termina la recursión.
        }

        char c = input.charAt(index); // Se almacena el carácter actual en la variable c.
        index++; // El indice avanza al siguiente carácter.

        if (c == '(' || c == ')') { 
            tokens.add(Character.toString(c)); // Se agregan los paréntesis como tokens individuales.
        }
        else if (Character.isWhitespace(c)) {
        // El index ignorara los espacios en blanco.
        } 
        else {
            StringBuilder token = new StringBuilder(); // Se crea un StringBuilder para almacenar el token. Esto con el fin de evitar 
            token.append(c);                           // la creación de múltiples cadenas (lo cual generaria mas espacio en memoria).

            while (index < input.length()) {
                char nextChar = input.charAt(index); // Se almacena el siguiente carácter en la variable nextChar.
                if (Character.isWhitespace(nextChar) || nextChar == '(' || nextChar == ')') {
                    break; // Se termina el tokenizado de un numero si se encuentra un espacio o parentesis.
                }
                token.append(nextChar); // Dicho carácter se agrega al token.
                index++; // El index avanza al siguiente carácter.
            }

            tokens.add(token.toString()); // Se agrega el token como String a la lista de tokens.
        }

        Tokenize(tokens); // El método se llama a sí mismo para continuar con la recursión.
    }

    /**
     * Metodo 2 (tokenize): Se encarga de realizar la tokenización de la cadena.
     * 
     * @return La lista con los tokens generados.
     */
    public List<String> tokenize() {
        List<String> tokens = new ArrayList<>(); // Se crea una lista para almacenar los tokens.
        Tokenize(tokens); // Se llama al método Tokenize para realizar la tokenización.
        return tokens; // Se devuelve la lista con los tokens generados.
    }

    /**
     * Metodo 3 (skipDefun): Sirve para ignorar la primera palabra de la entrada antes de tokenizar.
     * Busca el primer espacio y ajusta el indice para saltar la palabra clave.
     */
    private void skipDefun() {
        while (index < input.length() && !Character.isWhitespace(input.charAt(index))) { 
            index++; // El indice avanza al siguiente carácter.
        }
        
        while (index < input.length() && Character.isWhitespace(input.charAt(index))) { 
            index++; // El indice avanza al siguiente carácter.
        }
    }
}

