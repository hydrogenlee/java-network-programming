package chapter7;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class C7P230_SourceViewerWithExceptionHandler {
    public static void main(String[] args) {
        viewSource("http://www.ibiblio.org/file-not-found.html");
    }

    public static void viewSource(String url) {
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            try (InputStream raw = conn.getInputStream()) {
                printFromStream(raw);
            } catch (IOException e) {
                printFromStream(conn.getErrorStream());
            }
        } catch (MalformedURLException e) {
            System.err.println(url + " is not a parsable URL.");
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }


    public static void printFromStream(InputStream in) throws IOException {
        try (InputStream bin = new BufferedInputStream(in);
             Reader reader = new InputStreamReader(bin)) {
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
        }
    }
}
