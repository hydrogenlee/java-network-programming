package chapter5;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class C5P130_SourceViewer {

    private static final String PATH = "http://www.oreilly.com";

    public static void main(String[] args) {
        InputStream in = null;
        try {
            URL url = new URL(PATH);
            in = url.openStream();
            in = new BufferedInputStream(in);
            Reader rin = new InputStreamReader(in);
            int c;
            while ((c = rin.read()) != -1) {
                System.out.print((char)c);
            }
        } catch (MalformedURLException e) {
            System.out.println(PATH + " is not a parsable URL");
        } catch (IOException e) {
            System.err.println(e.toString());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
