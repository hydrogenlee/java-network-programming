package chapter5;

import java.io.*;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;

public class C5P165_SecureSourceViewer {
    private static final String PATH = "https://www.oreilly.com";

    public static void main(String[] args) {
        Authenticator.setDefault(new C5P163_DialogAuthenticator());
        try {
            URL u = new URL(PATH);
            try (InputStream in = new BufferedInputStream(u.openStream())) {
                Reader r = new InputStreamReader(in);
                int c;
                while ((c = r.read()) != -1) {
                    System.out.print((char) c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // 打印一个空行，进行页面分隔
        System.out.println();
        // 由于我们使用了AWT，所以必须显示的退出
        System.exit(0);

    }
}
