import java.util.List;
import java.util.function.Function;

public class Demo06 {

    public List<String> applyAll(List<String> input) {
        List<Function<String, String>> transforms = List.of(
            (String v) -> {
                if (v == null) return "";
                if (v.length() > 10) return v.substring(0, 10);
                return v;
            },
            (String v) -> {
                if (v == null) return "N/A";
                if (v.contains(" ")) return v.replace(" ", "_");
                return v;
            }
        );

        return input.stream()
                .map(v -> {
                    String out = v;
                    for (Function<String, String> t : transforms) {
                        out = t.apply(out);
                    }
                    if (out.isBlank()) return "EMPTY";
                    return out;
                })
                .toList();
    }
}
