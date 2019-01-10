package chapter9;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class C9P284_MultithreadedDaytimeServer {

    private static final int PORT = 13;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // 这里不能使用try-with-resource,因为这里使用了线程，当主线程到达while循环末尾时，就会关闭socket，
                // 此时新生成的线程可能还没有使用完这个线程。
                try {
                    Socket socket = serverSocket.accept();
                    Thread task = new DaytimeThread(socket);
                    task.start();
                } catch (IOException ex) {
                    System.err.println("Socket error: " + ex.toString());
                }
            }
        } catch (IOException ex) {
            System.err.println("Server Socket error: " + ex.toString());
        }
    }


    private static class DaytimeThread extends Thread {
        private Socket socket;

        public DaytimeThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                Writer writer = new OutputStreamWriter(socket.getOutputStream());
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss z");
                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                writer.write(date.getTime() + " " + format.format(date));
                writer.write("\r\n");
                writer.flush();
            } catch (IOException ex) {
                System.err.println("Socket error: " + ex.toString());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

