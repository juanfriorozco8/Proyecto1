import java.util.List;
public class LispValue implements LispExpression {
    private int value;
    private String name;
    private List<LispExpression> elements;

    public LispValue (int value) {
        this.value = value;
    }
    
    @Override
    public Object evaluate (Environment entorno) {
        return null;
    }
}
