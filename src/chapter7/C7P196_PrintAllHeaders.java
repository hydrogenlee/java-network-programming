package chapter7;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class C7P196_PrintAllHeaders {
    public static void main(String[] args) {
        printAllHeaders("https://www.oreilly.com");
        printAllHeaders("https://www.oreilly.com/favicon.ico");
    }

    public static void printAllHeaders(String url) {
        System.out.println("\n---------- " + url + " ----------");
        try {
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            for (int i = 1; ; i++) {
                if (connection.getHeaderField(i) == null) {
                    break;
                }
                System.out.println(connection.getHeaderFieldKey(i) + ": " + connection.getHeaderField(i));
            }
        } catch (MalformedURLException e) {
            System.err.println(url + " is not a parsable URL.");
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
