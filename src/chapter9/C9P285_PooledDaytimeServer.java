package chapter9;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class C9P285_PooledDaytimeServer {

    private static final int PORT = 13;
    private static final int THREAD_NUM = 50;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_NUM);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // 这里不能使用try-with-resource,因为这里使用了线程，当主线程到达while循环末尾时，就会关闭socket，
            // 此时新生成的线程可能还没有使用完这个线程。
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    Thread task = new Thread(new DaytimeRunnable(socket));
                    pool.execute(task);
                } catch (IOException ex) {
                    System.err.println("Socket error: " + ex.toString());
                }


            }
        } catch (IOException ex) {
            System.err.println("Server Socket error:" + ex.toString());
        }
    }


    private static class DaytimeRunnable implements Runnable {
        private Socket socket;

        public DaytimeRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                Writer writer = new OutputStreamWriter(socket.getOutputStream());
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss z");
                writer.write(date.getTime() + " " + format.format(date));
                writer.write("\r\n");
                writer.flush();
            } catch (IOException e) {
                System.err.println("Socket error: " + e.toString());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        System.err.println(e.toString());
                    }
                }
            }
        }
    }
}

