package chapter5;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class C5P133_ContentGetter {
    public static void main(String[] args) {
        printContentClass("http://www.oreilly.com");
        printContentClass("https://cdn.oreillystatic.com/oreilly/images/app-store-logo.png");
        printContentClass("http://www.cafeaulait.org/RelativeURLTest.class");
        printContentClass("http://www.cafeaulait.org/course/week9/spacemusic.au");
    }

    public static void printContentClass(String url) {
        try {
            URL u = new URL(url);
            Object o = u.getContent();
            System.out.println("I got a " + o.getClass().getName());
        } catch (MalformedURLException e) {
            System.err.println(url + " is not a parsable URL");
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
