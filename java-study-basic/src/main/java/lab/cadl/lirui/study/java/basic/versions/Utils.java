package lab.cadl.lirui.study.java.basic.versions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static void timeIt(Runnable runnable) {
        long t0 = System.nanoTime();
        runnable.run();
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.printf("cost %dms\n", millis);
    }

    public static List<String> stringList(int size) {
        List<String> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            UUID uuid = UUID.randomUUID();
            result.add(uuid.toString());
        }

        return result;
    }
}
