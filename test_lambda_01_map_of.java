import java.util.Map;
import java.util.function.Function;

public class Demo01 {
    private Map<String, Function<String, String>> functions;

    public void setFunctions() {
        functions = Map.of(
            "trimOrUpper", (String v) -> {
                if (v == null || v.isBlank()) {
                    return "";
                } else if (v.length() < 4) {
                    return v.trim();
                } else {
                    return v.toUpperCase();
                }
            },
            "suffix", (String v) -> {
                if ("test".equals(v)) {
                    return "the test";
                } else {
                    return v + "_suffix";
                }
            },
            "normalize", (String v) -> {
                if (v == null) {
                    return "N/A";
                }
                return v.replace("-", "_").toLowerCase();
            }
        );
    }
}
