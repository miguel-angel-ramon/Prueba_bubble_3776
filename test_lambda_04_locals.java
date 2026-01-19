import java.util.Map;
import java.util.function.Function;

public class Demo04 {

    private Map<String, Function<String, String>> fns;

    public void init() {
        fns = Map.of(
            "complex", (String v) -> {
                String base = (v == null) ? "" : v.trim();
                if (base.isEmpty()) return "EMPTY";

                int len = base.length();
                if (len < 3) return base.toLowerCase();
                if (len < 8) return base.toUpperCase();

                return "LEN_" + len;
            },
            "guarded", (String v) -> {
                if (v == null) return "N/A";
                if (v.startsWith("X")) return "X_" + v.substring(1);
                return v;
            }
        );
    }
}
