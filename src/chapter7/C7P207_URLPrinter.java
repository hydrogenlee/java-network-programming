package chapter7;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class C7P207_URLPrinter {
    public static void main(String[] args) {
        try {
            URL u = new URL("https://www.oreilly.com");
            URLConnection conn = u.openConnection();
            System.out.println(conn.getURL());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
