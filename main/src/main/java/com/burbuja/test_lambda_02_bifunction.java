import java.util.Map;
import java.util.function.BiFunction;

public class Demo02 {
    private Map<String, BiFunction<Integer, Integer, Integer>> ops;

    public void initOps() {
        ops = Map.ofEntries(
            Map.entry("addOrMax", (Integer a, Integer b) -> {
                if (a == null || b == null) return 0;
                if (a + b > 100) return Math.max(a, b);
                return a + b;
            }),
            Map.entry("safeDiv", (Integer a, Integer b) -> {
                if (a == null || b == null) return 0;
                if (b == 0) return 0;
                return a / b;
            }),
            Map.entry("mulOrClamp", (Integer a, Integer b) -> {
                if (a == null || b == null) return 0;
                int r = a * b;
                if (r < 0) return 0;
                if (r > 1000) return 1000;
                return r;
            })
        );
    }
}
