package chapter5;

import java.net.MalformedURLException;
import java.net.URL;

public class C5P140_URLEquality {
    public static void main(String[] args) {
        try {
            URL url1 = new URL("http://www.ibiblio.org/");
            URL url2 = new URL("http://ibiblio.org/");
            // URL的equals方法会进行DNS解析来判断两个主机名是否相同
            if (url1.equals(url2)) {
                System.out.println(url1 + " is the same as " + url2);
            } else {
                System.out.println(url1 + " is not the same as " + url2);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
