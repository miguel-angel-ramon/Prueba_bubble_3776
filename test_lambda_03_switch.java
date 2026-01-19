import java.util.Map;
import java.util.function.Function;

public class Demo03 {
    private Map<String, Function<String, String>> rules;

    public void configure(String mode) {
        rules = Map.of(
            "rule1", (String v) -> {
                if (v == null) return "null";
                switch (mode) {
                    case "A":
                        if (v.length() > 5) return "A_LONG";
                        return "A_SHORT";
                    case "B":
                        if (v.contains("x")) return "B_X";
                        return "B_OTHER";
                    default:
                        return "DEFAULT";
                }
            },
            "rule2", (String v) -> {
                if (v == null) return "0";
                if (v.matches("\\d+")) return "NUM";
                return "TXT";
            }
        );
    }
}
