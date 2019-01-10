package chapter5;

import java.net.MalformedURLException;
import java.net.URL;

public class C5P125_ProtocolTester {
    public static void main(String[] args) {
        // HTML
        testProtocol("http://www.adc.org");
        // HTTPS
        testProtocol("https://www.amazon.com/exec/obidos/order2");
        // FTP
        testProtocol("ftp://ibiblio.org/pub/languages/java/javafaq");
        // SMTP
        testProtocol("mailto://elharo@ibibilio.org");
        // Telnet
        testProtocol("telnet://dibner.poly.edu");
        // File
        testProtocol("file:///etc/passwd");
        // gopher
        testProtocol("gopher://gopher.anc.org.za/");
        // LDAP 轻量级目录方法协议
        testProtocol("ldap://ldap.itd.umich.edu/o=University%20of%20Michigan,c=US?postalAddress");
        // JAR
        testProtocol("jar:http://cafeaulait.org/books/javaio/ioexamples/javaio.jar!");
        // NFS
        testProtocol("nfs://utopia.ploy.edu/usr/tmp/");
        // JDBC
        testProtocol("jdbc:mysql://luna.ibiblio.org:3306/NEWS");
        // RMI
        testProtocol("rmi://ibiblio.org/RenderEngine");
        // HotSpot的定制协议
        testProtocol("doc:/UsersGuide/release.html");
        testProtocol("netdoc:/UserGuide/release.html");
        testProtocol("systemresource://www.adc.org/+/index.html");
        testProtocol("verbatim:http://www.adc.org/");
    }

    private static void testProtocol(String url) {
        try {
            URL u = new URL(url);
            System.out.println("Protocol \"" + u.getProtocol() + "\" is supported");
        } catch (MalformedURLException e) {
            String protocol = url.substring(0, url.indexOf(":"));
            System.out.println("Protocol \"" + protocol + "\" is not supported");
        }
    }
}
