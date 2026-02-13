import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class ComplexS3776Demo  {

    // Raw type en atributo (tu fix actual no lo toca, pero sirve para probar límites)
    private List listaGlobal;

    public void ejemploList() {
        List lista = new ArrayList();
        lista.add("Hola");
        lista.add(123);
    }

    public void ejemploMap() {
        Map mapa = new HashMap();
        mapa.put("clave", 100);
    }

    public void ejemploSet() {
        Set conjunto = new HashSet();
        conjunto.add("valor");
    }

    public void ejemploOptional() {
        Optional opt = Optional.empty();
    }

    public void ejemploQueue() {
        Queue cola = new ArrayDeque();
        cola.add("dato");
    }

    public void ejemploFuture() {
        Future future = CompletableFuture.completedFuture("ok");
    }

    public void ejemploClass() {
        Class clazz = String.class;
    }

    public void ejemploCorrecto() {
        // Este método NO debería modificarse
        List<String> nombres = new ArrayList<>();
        Map<String, Integer> edades = new HashMap<>();
        Set<Long> ids = new HashSet<>();
    }
}
