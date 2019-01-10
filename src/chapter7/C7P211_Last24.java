package chapter7;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class C7P211_Last24 {
    public static void main(String[] args) {
        last24("https://www.oreilly.com");
    }

    public static void last24(String url) {
        Date today = new Date();
        long millisecondsPerHour = 60 * 60 * 1000;
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            System.out.println("Original if modified since: " + new Date(conn.getIfModifiedSince()));
            conn.setIfModifiedSince(today.getTime() - millisecondsPerHour);
            System.out.println("Will retrieve file if it's modified since " + new Date(conn.getIfModifiedSince()));
            System.out.println("The file last modified at " + new Date(conn.getLastModified()));
            if (conn.getLastModified() >= conn.getIfModifiedSince()) {
                System.out.println("So we will retrieve file.");
                System.out.println("-------------------------");
            } else  {
                System.out.println("So we will not retrieve file.");
            }
            try (InputStream raw = conn.getInputStream();
                 InputStream bin = new BufferedInputStream(raw);
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
