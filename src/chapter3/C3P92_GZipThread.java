package chapter3;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class C3P92_GZipThread {
    public static final int THREAD_COUNT = 4;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        String filename = "src/chapter3/Input_C3P61.txt";
        File file = new File(filename);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; files != null && i < files.length; i++) {
                    if (!files[i].isDirectory()) {
                        executor.submit(new C3P92_GZipRunnable(files[i]));
                    }
                }
            } else {
                executor.submit(new C3P92_GZipRunnable(file));
            }
        }

        executor.shutdown();
    }
}



