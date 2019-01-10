package chapter5;

import java.net.MalformedURLException;
import java.net.URL;

public class C5P138_URLSplitter {
    public static void main(String[] args) {
        splitURL("ftp://mp3:mp3@138.247.121.61:21000/c%3a");
        splitURL("http://www.oreilly.com");
        splitURL("http://www.ibiblio.org/nywc/compositions.phtml?category=Piano");
        splitURL("http://admin@www.balckstar.com:8080/");
        splitURL("http://www.ibiblio.org/javafaq/javafaq.html#xtocid1902914");
    }

    public static void splitURL(String url) {
        try {
            URL u = new URL(url);
            System.out.println("The URL is " + u);
            System.out.println("The scheme is " + u.getProtocol());
            System.out.println("The user info is " + u.getUserInfo());
            System.out.println("The host is " + u.getHost());
            System.out.println("The port is " + u.getPort());
            System.out.println("The path is " + u.getPath());
            System.out.println("The file is " + u.getFile());
            System.out.println("The ref is " + u.getRef());
            System.out.println("The query string is " + u.getQuery());
            System.out.println();
        } catch (MalformedURLException e) {
            System.err.println(url + " is not a parsable URL.");
        }
    }
}
