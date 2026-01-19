import java.util.Map;
import java.util.function.Function;

public class Demo07 {
    private Map<String, Function<String, String>> fns;

    public void init() {
        fns = Map.of(
            "x", v -> {
                if ("a".equals(v)) return "A";
                return v + "!";
            }
        );
    }
}
