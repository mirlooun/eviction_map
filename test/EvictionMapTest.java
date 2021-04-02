import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;


class EvictionMapTest {

    private static final int EntityExpirationTime = 3; // in seconds

    @Test
    void frequentPutAndInfrequentGet() {
        EvictionMap<String, String> map = new EvictionMap<>(EntityExpirationTime);
        ArrayList<String> keys = new ArrayList<>();

        Random rand = new Random();

        long startTime = System.currentTimeMillis();

        String key;

        int ratio = 70; // from 100

        for (int i = 0; i < 10000000; i++) {
            if (rand.nextInt(100) <= ratio) {
                key = generateRandomString();
                map.put(key, generateRandomString());
                keys.add(key);
            } else {
                if (keys.size() > 1) map.get(keys.get(rand.nextInt(keys.size())));
            }
        }

        long time = System.currentTimeMillis() - startTime;

        System.out.println("Time " + time + "ms");
    }

    @Test
    void infrequentPutAndFrequentGet() {
        EvictionMap<String, String> map = new EvictionMap<>(EntityExpirationTime);
        ArrayList<String> keys = new ArrayList<>();

        Random rand = new Random();

        long startTime = System.currentTimeMillis();

        String key;

        int ratio = 30; // from 100

        for (int i = 0; i < 10000000; i++) {
            if (rand.nextInt(100) <= ratio) {
                key = generateRandomString();
                map.put(key, generateRandomString());
                keys.add(key);
            } else {
                if (keys.size() > 1) map.get(keys.get(rand.nextInt(keys.size())));
            }
        }

        long time = System.currentTimeMillis() - startTime;

        System.out.println("Time " + time + "ms");
    }

    @Test
    void frequentPutAndFrequentGet() {
        EvictionMap<String, String> map = new EvictionMap<>(EntityExpirationTime);
        ArrayList<String> keys = new ArrayList<>();

        Random rand = new Random();

        long startTime = System.currentTimeMillis();

        String key;

        int ratio = 50; // from 100

        for (int i = 0; i < 10000000; i++) {
            if (rand.nextInt(100) <= ratio) {
                key = generateRandomString();
                map.put(key, generateRandomString());
                keys.add(key);
            } else {
                if (keys.size() > 1) map.get(keys.get(rand.nextInt(keys.size())));
            }
        }

        long time = System.currentTimeMillis() - startTime;

        System.out.println("Time " + time + "ms");
    }

    public static String generateRandomString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);

        return new String(array, StandardCharsets.UTF_8);
    }
}