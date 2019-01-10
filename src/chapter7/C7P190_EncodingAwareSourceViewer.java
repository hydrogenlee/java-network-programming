package chapter7;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class C7P190_EncodingAwareSourceViewer {
    public static void main(String[] args) {
        // viewSource("http://www.oreilly.com");
        viewSource("http://www.baidu.com");
    }

    public static void viewSource(String uri) {
        String encoding = "UTF-8";
        try {
            URL u = new URL(uri);
            URLConnection connection = u.openConnection();
            String contentType = connection.getContentType();
            if (contentType != null) {
                int index = contentType.indexOf("charset=");
                if (index != -1) {
                    encoding = contentType.substring(index + 8);
                }
            }
            try (InputStream in = connection.getInputStream();
                 InputStream bin = new BufferedInputStream(in);
                 Reader reader = new InputStreamReader(bin, encoding)) {
                int c;
                while ((c = reader.read()) != -1) {
                    System.out.print((char) c);
                }
            }
        } catch (MalformedURLException e) {
            System.err.println(uri + "is not a parsable URL.");
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
