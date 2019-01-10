package chapter7;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class C7P223_LastModified {
    public static void main(String[] args) {
        String url = "http://www.ibiblio.org/xml";
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("HEAD");
            System.out.println(url + " is last modified at " + new Date(conn.getLastModified()));
        } catch (MalformedURLException e) {
            System.err.println(url + " is not a parsable URL.");
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
