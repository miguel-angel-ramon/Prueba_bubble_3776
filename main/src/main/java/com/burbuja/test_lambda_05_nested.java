import java.util.Map;
import java.util.function.Function;

public class Demo05 {
    private Map<String, Function<String, Function<String, String>>> nested;

    public void initNested() {
        nested = Map.of(
            "outer", (String prefix) -> (String v) -> {
                if (v == null) return prefix + ":null";
                if (v.isBlank()) return prefix + ":blank";
                return prefix + ":" + v.trim();
            },
            "outer2", (String prefix) -> (String v) -> {
                if ("A".equals(prefix)) {
                    if (v == null) return "A_NULL";
                    return "A_" + v;
                }
                return "OTHER";
            }
        );
    }
}
