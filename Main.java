import java.util.*;
import java.io.*;

/**
 * Clase principal del intérprete Lisp.
 * Aquí se inicializa el entorno, el evaluador y se permite ingresar expresiones
 * tanto por consola como desde un archivo externo.
 */
public class Main {
    public static void main(String[] args) {
        // Creamos el entorno y el evaluador
        Environment environment = new Environment();
        Evaluator evaluator = new Evaluator(environment);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al intérprete de Lisp en Java");
        System.out.println("¿Deseas cargar un archivo Lisp? (si/no)");

        String opcion = scanner.nextLine().trim().toLowerCase();

        if (opcion.equals("si")) {
            System.out.print("Ingresa el nombre del archivo: ");
            String fileName = scanner.nextLine();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue; // Salta las líneas vacías

                    try {
                        // Tokeniza la lista
                        Tokenizer tokenizer = new Tokenizer(line);
                        List<String> tokens = tokenizer.tokenize();

                        // Parsea los tokens
                        Parser parser = new Parser(tokens);
                        Object parsed = parser.parse();

                        // Evalua la expresion
                        Object result = evaluator.evaluateExpression(parsed);
                        System.out.println("Resultado: " + result);
                    } catch (Exception e) {
                        System.out.println("Error evaluando la línea: " + line);
                        System.out.println("Detalle: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.out.println("Error leyendo el archivo: " + e.getMessage());
            }
        }

        // CONSOLA
        System.out.println("¡Bienvenido!\nEscribe 'salir' para cerrar el programa.");

        while (true) {
            System.out.print("Ingresa una expresión Lisp: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("salir")) {
                break;
            }

            try {
                // Tokeniza la expresion
                Tokenizer tokenizer = new Tokenizer(input);
                List<String> tokens = tokenizer.tokenize();

                // Parsea los tokens
                Parser parser = new Parser(tokens);
                Object parsed = parser.parse();

                // Evalua la expresion
                Object result = evaluator.evaluateExpression(parsed);
                System.out.println("Resultado: " + result);
            } catch (Exception e) {
                System.out.println("Error en la evaluación: " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Gracias por usar el intérprete de Lisp en Java.");
    }
}




