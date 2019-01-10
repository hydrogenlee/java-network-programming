package chapter9;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class C9P280_DaytimeServer {

    private static final int PORT = 13;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    Writer writer = new OutputStreamWriter(socket.getOutputStream());
                    Date date = new Date();
                    // 下面的格式设置是为了和C8P239_DaytimeClient相匹配
                    DateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss z");
                    format.setTimeZone(TimeZone.getTimeZone("UTC"));
                    writer.write(date.getTime() + " " + format.format(date));
                    writer.write("\r\n");
                    writer.flush();
                } catch (IOException ex) {
                    System.err.println(ex.toString());
                }
            }
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
