package chapter4;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class C4P115_PooledWebLog {

    private static final int THREADS_NUM = 4;

    public static void main(String[] args) {
        String path = "src/chapter4/Input_C4P114_WebLog.txt";
        ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUM);
        Queue<LogEntry> results = new LinkedList<>();

        try (FileInputStream fin = new FileInputStream(path);
             Reader inr = new InputStreamReader(fin, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(inr)) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                C4P115_LookupTask task = new C4P115_LookupTask(line);
                Future<String> future = executor.submit(task);
                results.add(new LogEntry(line, future));
            }
        } catch (IOException e) {
            System.err.println("Exception: " + e);
        }

        // print
        for (LogEntry entry : results) {
            try {
                System.out.println(entry.future.get());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(entry.original);
            }
        }

        // 关闭线程池
        executor.shutdown();
    }

}


class LogEntry {
    String original;
    Future<String> future;

    public LogEntry(String original, Future<String> future) {
        this.original = original;
        this.future = future;
    }
}


