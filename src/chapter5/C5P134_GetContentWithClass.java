package chapter5;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class C5P134_GetContentWithClass {

    private static final String PATH = "http://www.oreilly.org";

    public static void main(String[] args) {
        try {
            URL u = new URL(PATH);
            Class<?>[] types = new Class[3];
            types[0] = String.class;
            types[1] = Reader.class;
            types[2] = InputStream.class;
            Object o = u.getContent(types);
            if (o instanceof String) {
                System.out.println((String) o);
            } else if (o instanceof Reader) {
                int c;
                Reader r = (Reader) o;
                while ((c = r.read()) != -1) {
                    // 字节流
                    System.out.print((char) c);
                }
                r.close();
            } else if (o instanceof InputStream) {
                int c;
                InputStream in = (InputStream) o;
                while ((c = in.read()) != -1) {
                    // 字符流
                    System.out.write(c);
                }
                in.close();
            } else {
                System.out.println("Error: unexpected type " + o.getClass().getName());
            }
        } catch (MalformedURLException e) {
            System.err.println(PATH + " is not a parsable URL");
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
