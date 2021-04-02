import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class Test {
    public static void main(String[] args) throws InterruptedException {

        EvictionMap<String, Integer> testMap = new EvictionMap<>(10);
        String a = "";
        int b;
        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            a = lol();
            b = new Random().nextInt();
            testMap.put(a, b);
            strings.add(a);
        }

        Thread.sleep(5000);

        for (int i = 0; i < 1000; i++) {
            a = lol();
            b = new Random().nextInt();
            testMap.put(a, b);
            strings.add(a);
        }

        Thread.sleep(6000);

        for (String str : strings) {
            System.out.println(testMap.get(str));
        }
        System.out.println(testMap.size());
        System.out.println("END");

//        System.out.println(testMap.toString());
//        System.out.println(testMap.get(a));
    }



    public static String lol() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);

        return new String(array, StandardCharsets.UTF_8);
    }
}
