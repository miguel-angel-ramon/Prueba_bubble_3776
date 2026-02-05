package com.example;

public class ComplexS3776Demo {

    // =========================
    // 1) Infra: “map” propio
    // =========================
    static class SimpleMap {
        static class Entry {
            String key;
            Fn value;
            Entry(String k, Fn v) { key = k; value = v; }
        }

        private Entry[] entries;
        private int size;

        SimpleMap(int capacity) {
            entries = new Entry[capacity];
            size = 0;
        }

        void put(String key, Fn value) {
            // overwrite si existe
            for (int i = 0; i < size; i++) {
                if (entries[i].key.equals(key)) {
                    entries[i].value = value;
                    return;
                }
            }
            entries[size++] = new Entry(key, value);
        }

        Fn get(String key) {
            for (int i = 0; i < size; i++) {
                if (entries[i].key.equals(key)) {
                    return entries[i].value;
                }
            }
            return null;
        }
    }

    // =========================
    // 2) Dominio
    // =========================
    static class User {
        String id;
        String name;
        int age;
        String country;
        boolean active;

        User(String id, String name, int age, String country, boolean active) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.country = country;
            this.active = active;
        }
    }

    static class Order {
        String id;
        String userId;
        int amountCents;
        String currency;
        String status; // NEW, PAID, CANCELED

        Order(String id, String userId, int amountCents, String currency, String status) {
            this.id = id;
            this.userId = userId;
            this.amountCents = amountCents;
            this.currency = currency;
            this.status = status;
        }
    }

    // =========================
    // 3) Repositorio en memoria
    // =========================
    static class InMemoryDB {
        private User[] users = new User[10];
        private int uSize = 0;

        private Order[] orders = new Order[20];
        private int oSize = 0;

        void addUser(User u) { users[uSize++] = u; }
        void addOrder(Order o) { orders[oSize++] = o; }

        User findUserById(String id) {
            for (int i = 0; i < uSize; i++) {
                if (users[i].id.equals(id)) return users[i];
            }
            return null;
        }

        Order[] findOrdersByUserId(String userId) {
            // devuelve un array “recortado”
            Order[] tmp = new Order[oSize];
            int n = 0;
            for (int i = 0; i < oSize; i++) {
                if (orders[i].userId.equals(userId)) tmp[n++] = orders[i];
            }
            Order[] out = new Order[n];
            for (int i = 0; i < n; i++) out[i] = tmp[i];
            return out;
        }
    }

    // =========================
    // 4) “Reglas” con lambdas
    // =========================
    interface Fn {
        String apply(String input);
    }

    private final InMemoryDB db = new InMemoryDB();
    private final SimpleMap rules = new SimpleMap(20);

    // Mucha lógica alrededor (no es el objetivo de S3776)
    public void seedData() {
        db.addUser(new User("u1", "Miguel", 29, "ES", true));
        db.addUser(new User("u2", "Ana", 16, "ES", true));
        db.addUser(new User("u3", "John", 42, "US", false));

        db.addOrder(new Order("o1", "u1", 1500, "EUR", "PAID"));
        db.addOrder(new Order("o2", "u1", 9999, "EUR", "NEW"));
        db.addOrder(new Order("o3", "u2", 2500, "EUR", "PAID"));
        db.addOrder(new Order("o4", "u3", 2000, "USD", "CANCELED"));
    }

    public String process(String userId, String ruleKey) {
        User u = db.findUserById(userId);
        if (u == null) return "ERR:NO_USER";

        Fn fn = rules.get(ruleKey);
        if (fn == null) return "ERR:NO_RULE";

        String payload = u.id + "|" + u.name + "|" + u.age + "|" + u.country + "|" + u.active;
        return fn.apply(payload);
    }

    private String[] split5(String s) {
        // split muy básico sin imports, asume 4 separadores '|'
        String[] out = new String[5];
        int part = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '|') {
                out[part++] = s.substring(start, i);
                start = i + 1;
            }
        }
        out[part] = s.substring(start);
        return out;
    }

    // ===========================================
    // ✅ ESTE es el método que debe disparar S3776
    // ===========================================
    public void setupRules() {
		String mode = "STRICT";
        // Regla 1: normalización + validación
        rules.put("normalize", (String input) -> {
            if (input == null) return "NULL";
            String[] parts = split5(input);

            if (parts[1] == null || parts[1].length() == 0) {
                return "NO_NAME";
            } else if (parts[1].length() < 3) {
                return "NAME_SHORT";
            } else {
                return parts[1].toLowerCase();
            }
        });

        // Regla 2: chequeo de edad (varios if/else)
        rules.put("ageCheck", (String input) -> {
            if (input == null) return "ERR";
            String[] parts = split5(input);

            int age = 0;
            try {
                age = Integer.parseInt(parts[2]);
            } catch (Exception e) {
                return "BAD_AGE";
            }

            if (age < 0) return "BAD_AGE";
            if (age < 18) return "MINOR";
            if (age < 65) return "ADULT";
            return "SENIOR";
        });

        // Regla 3: depende de mode con if anidados
        rules.put("modeRule", (String input) -> {
            if (input == null) return "ERR";
            String[] parts = split5(input);
            String country = parts[3];

            if ("STRICT".equals(mode)) {
                if (country == null) return "STRICT:NO_COUNTRY";
                if ("ES".equals(country)) return "STRICT:ES";
                if ("US".equals(country)) return "STRICT:US";
                return "STRICT:OTHER";
            } else if ("RELAXED".equals(mode)) {
                if (country == null) return "RELAXED:UNKNOWN";
                if ("ES".equals(country)) return "RELAXED:ES";
                return "RELAXED:OK";
            } else {
                if (country == null) return "DEFAULT:UNKNOWN";
                return "DEFAULT:" + country;
            }
        });

        // Regla 4: usa DB (más realista) + condiciones
        rules.put("orderSummary", (String input) -> {
            if (input == null) return "ERR";
            String[] parts = split5(input);
            String userId = parts[0];

            Order[] os = db.findOrdersByUserId(userId);
            if (os == null || os.length == 0) return "NO_ORDERS";

            int paid = 0;
            int total = 0;
            int canceled = 0;

            for (int i = 0; i < os.length; i++) {
                total += os[i].amountCents;
                if ("PAID".equals(os[i].status)) paid += os[i].amountCents;
                else if ("CANCELED".equals(os[i].status)) canceled += os[i].amountCents;
            }

            if (paid == 0) {
                return "ORDERS:UNPAID_ONLY total=" + total;
            } else if (canceled > 0) {
                return "ORDERS:HAS_CANCELED paid=" + paid + " total=" + total;
            } else {
                return "ORDERS:OK paid=" + paid + " total=" + total;
            }
        });

        // Regla 5: boolean + varias ramas
        rules.put("activeFlag", (String input) -> {
            if (input == null) return "ERR";
            String[] parts = split5(input);
            String activeStr = parts[4];

            boolean active = "true".equals(activeStr);
            if (active) {
                if ("STRICT".equals(mode)) return "ACTIVE_STRICT";
                return "ACTIVE";
            } else {
                if ("STRICT".equals(mode)) return "INACTIVE_STRICT";
                return "INACTIVE";
            }
        });
    }

    // =========================
    // 5) Demo main
    // =========================
    public static void main(String[] args) {
        ComplexS3776Demo app = new ComplexS3776Demo();
        app.seedData();
        app.setupRules("STRICT");

        System.out.println(app.process("u1", "normalize"));
        System.out.println(app.process("u1", "ageCheck"));
        System.out.println(app.process("u1", "modeRule"));
        System.out.println(app.process("u1", "orderSummary"));
        System.out.println(app.process("u3", "activeFlag"));
    }
}

