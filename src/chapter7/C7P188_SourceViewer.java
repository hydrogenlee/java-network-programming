package chapter7;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class C7P188_SourceViewer {
    public static void main(String[] args) {
        viewSource("http://www.oreilly.com");
    }

    public static void viewSource(String url) {
        try {
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            try (InputStream raw = connection.getInputStream();
                 InputStream bin = new BufferedInputStream(raw);
                 Reader reader = new InputStreamReader(bin)) {
                int c;
                while ((c = reader.read()) != -1) {
                    System.out.print((char) c);
                }
            }
        } catch (MalformedURLException e) {
            System.err.println(url + "is not a parsable URL.");
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
