import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    final static String LETTERS = "RLRFR";
    final static int LENGTH = 100;
    final static int AMOUNT_OF_ROUTS = 1000;

    public static void main(String[] args) {

        for (int i = 0; i < AMOUNT_OF_ROUTS; i++) {
            new Thread(() -> {
                String rout = generateRoute(LETTERS, LENGTH);
                int freq = (int) rout
                        .chars()
                        .filter(r -> r == 'R')
                        .count();

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(freq)) {
                        sizeToFreq.put(freq, sizeToFreq.get(freq) + 1);
                    } else {
                        sizeToFreq.put(freq, 1);
                    }
                }

            }).start();
        }
        Map.Entry<Integer, Integer> max = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Самое частое количество повторений " + max.getKey() +
                " (встретилось " + max.getValue() + " раз)");

        System.out.println("Другие размеры: ");
        sizeToFreq
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(o -> System.out.println(" - " + o.getKey() + "(" + o.getValue() + " раз)"));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}

