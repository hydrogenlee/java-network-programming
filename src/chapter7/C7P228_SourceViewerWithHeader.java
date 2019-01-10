package chapter7;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class C7P228_SourceViewerWithHeader {
    public static void main(String[] args) {
        viewSource("http://www.oreilly.com");
    }

    public static void viewSource(String url) {
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            int code = conn.getResponseCode();
            String message = conn.getResponseMessage();
            System.out.println("HTTP/1.X " + code + " " + message);
            for (int i = 1; ; i++) {
                String header = conn.getHeaderField(i);
                String key = conn.getHeaderFieldKey(i);
                if (header == null || key == null) {
                    break;
                }
                System.out.println(key + ": " + header);
            }
            System.out.println();

            try (InputStream in = conn.getInputStream();
                 BufferedInputStream bin = new BufferedInputStream(in);
                 Reader reader = new InputStreamReader(bin)) {
                int c;
                while ((c = reader.read()) != -1) {
                    System.out.print((char) c);
                }
            }
        } catch (MalformedURLException e) {
            System.err.println(url + " is not a parsable URL.");
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
