package chapter8;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class C8P244_DictClient {
    private static final String SERVER = "dict.org";
    private static final int PORT = 2628;
    private static final int TIMEOUT = 15000;

    private static String[] words = {"gold", "uranium", "silver", "copper", "lead"};

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket(SERVER, PORT);
            socket.setSoTimeout(TIMEOUT);
            OutputStream out = socket.getOutputStream();
            Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            for (String word : words) {
                define(word, writer, reader);
            }

            writer.write("quit\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    // close方法会自动关闭输入输出流
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void define(String word, Writer writer, BufferedReader reader) throws IOException {
        writer.write("DEFINE fd-eng-lat " + word + "\r\n");
        writer.flush();

        String line = reader.readLine();
        while (line != null) {
            if (line.startsWith("250 ")) {
                break;
            } else if (line.startsWith("552 ")) {
                // 没有匹配项
                System.err.println("No definition found for " + word);
                break;
            } else if (!(line.matches("\\d\\d\\d .*") || line.trim().equals("."))) {
                System.out.println(line);
            }
            line = reader.readLine();
        }
    }
}
