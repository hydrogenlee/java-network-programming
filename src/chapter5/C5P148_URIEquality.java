package chapter5;

import java.net.URI;
import java.net.URISyntaxException;

public class C5P148_URIEquality {
    public static void main(String[] args) {
        try {
            URI uri1 = new URI("http://www.ibiblio.org/");
            URI uri2 = new URI("http://ibiblio.org/");
            // URI的equals方法会进行DNS解析
            if (uri1.equals(uri2)) {
                System.out.println(uri1 + " is the same as " + uri2);
            } else {
                System.out.println(uri1 + " is not same as " + uri2);
            }
        }  catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}