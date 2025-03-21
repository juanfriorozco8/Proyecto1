import java.util.*;

public class Parser {
    private List<String> tokens; // Lista de tokens generados por el Tokenizer
    private int index; // Índice que recorre la lista de tokens

    /**
     * Constructor del Parser.
     * @param tokens Lista de tokens generada por el Tokenizer.
     */
    public Parser(List<String> tokens) {
        this.tokens = tokens;
        this.index = 0; // El índice comienza en 0.
    }

    /**
     * Método principal que organiza la estructura en una lista de objetos.
     * @return Lista de objetos representando la expresión parseada.
     */
    public Object parse() {
        if (index >= tokens.size()) {
            throw new RuntimeException("Error: Expresión incompleta.");
        }

        String token = tokens.get(index);
        index++;

        if (token.equals("(")) { 
            List<Object> list = new ArrayList<>(); // Se crea una lista para la subexpresión
            while (!tokens.get(index).equals(")")) { 
                list.add(parse()); // Llamado recursivo para agregar elementos a la lista
            }
            index++; // Saltamos el ')'
            return list; // Devolvemos la lista completa
        } 
        else if (token.equals(")")) {
            throw new RuntimeException("Error: Paréntesis en posición incorrecta.");
        } 
        else if (token.equals("quote")) { 
            List<Object> quotedList = new ArrayList<>();
            quotedList.add("quote");
            quotedList.add(parse()); // La siguiente expresión debe tratarse como una lista sin evaluar
            return quotedList;
        } 
        else {
            try {
                return Integer.parseInt(token); // Si es un número, lo devolvemos como entero
            } catch (NumberFormatException e) {
                return token; // Si no es un número, lo tratamos como un símbolo
            }
        }
    }
}

