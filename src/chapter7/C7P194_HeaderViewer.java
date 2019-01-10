package chapter7;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class C7P194_HeaderViewer {
    public static void main(String[] args) {
        viewHeader("https://www.oreilly.com");
        viewHeader("https://www.oreilly.com/favicon.ico");
    }

    public static void viewHeader(String url) {
        System.out.println("\n---------- " + url + " ----------");
        try {
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            if (connection.getContentType() != null) {
                System.out.println("Content-type: " + connection.getContentType());
            }
            if (connection.getContentEncoding() != null) {
                System.out.println("Content-encoding: " + connection.getContentEncoding());
            }
            if (connection.getDate() != 0) {
                System.out.println("Date: " + new Date(connection.getDate()));
            }
            if (connection.getLastModified() != 0) {
                System.out.println("Last Modified: " + new Date(connection.getLastModified()));
            }
            if (connection.getExpiration() != 0) {
                System.out.println("Expiration Date: " + new Date(connection.getExpiration()));
            }
            if (connection.getContentLength() != -1) {
                System.out.println("Content-length: " + connection.getContentLength());
            }
        } catch (MalformedURLException e) {
            System.err.println(url + "is not a parsable URL.");
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
