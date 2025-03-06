import java.util.List;
public class LispFunction {
    private String name;
    private List<String> parameters;
    private LispExpression body;

    public LispFunction (String name, List<String> parameters, LispExpression body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    
}
