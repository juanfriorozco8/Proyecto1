import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EvaluatorTest {

    private Environment environment;
    private Evaluator evaluator;

    @BeforeEach
    public void setUp() {
        environment = new Environment();
        evaluator = new Evaluator(environment);
    }

    @Test
    public void testAddition() {
        List<Object> expr = Arrays.asList("+", 2, 3);
        assertEquals(5, evaluator.evaluateExpression(expr));
    }

    @Test
    public void testSubtraction() {
        List<Object> expr = Arrays.asList("-", 10, 4);
        assertEquals(6, evaluator.evaluateExpression(expr));
    }

    @Test
    public void testMultiplication() {
        List<Object> expr = Arrays.asList("*", 6, 7);
        assertEquals(42, evaluator.evaluateExpression(expr));
    }

    @Test
    public void testDivision() {
        List<Object> expr = Arrays.asList("/", 20, 4);
        assertEquals(5, evaluator.evaluateExpression(expr));
    }

    @Test
    public void testSetqAndVariableUse() {
        evaluator.evaluateExpression(Arrays.asList("setq", "x", 10));
        List<Object> expr = Arrays.asList("+", "x", 5);
        assertEquals(15, evaluator.evaluateExpression(expr));
    }

    @Test
    public void testAtomTrue() {
        List<Object> expr = Arrays.asList("atom", 5);
        assertEquals(true, evaluator.evaluateExpression(expr));
    }

    @Test
    public void testAtomFalse() {
        List<Object> expr = Arrays.asList("atom", Arrays.asList(1, 2));
        assertEquals(false, evaluator.evaluateExpression(expr));
    }

    @Test
    public void testListCreation() {
        List<Object> expr = Arrays.asList("list", 1, 2, 3);
        assertEquals(Arrays.asList(1, 2, 3), evaluator.evaluateExpression(expr));
    }

    @Test
    public void testEqualTrue() {
        List<Object> expr = Arrays.asList("equal", 3, 3);
        assertEquals(true, evaluator.evaluateExpression(expr));
    }

    @Test
    public void testEqualFalse() {
        List<Object> expr = Arrays.asList("equal", 3, 5);
        assertEquals(false, evaluator.evaluateExpression(expr));
    }

    @Test
    public void testCondWithTrueBranch() {
        List<Object> expr = Arrays.asList("cond",
            Arrays.asList(Arrays.asList("<", 3, 5), "menor"),
            Arrays.asList(Arrays.asList(">", 3, 5), "mayor")
        );
        assertEquals("menor", evaluator.evaluateExpression(expr));
    }

    @Test
    public void testQuote() {
        List<Object> expr = Arrays.asList("quote", Arrays.asList("+", 1, 2));
        assertEquals(Arrays.asList("+", 1, 2), evaluator.evaluateExpression(expr));
    }
}