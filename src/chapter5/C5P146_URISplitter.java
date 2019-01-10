package chapter5;

import java.net.URI;
import java.net.URISyntaxException;

public class C5P146_URISplitter {
    public static void main(String[] args) {
        splitURI("tel:+1-800-9988-9938");
        splitURI("http://www.xml.com/pub/a/2003/09/17/stax.html#id=_hbc");
        splitURI("urn:isbn:1-565-92870-9");
    }

    public static void splitURI(String uri) {
        try {
            URI u = new URI(uri);
            System.out.println("The URI is " + u);
            if (u.isOpaque()) {
                System.out.println("This is a opaque URI.");
                System.out.println("The scheme is " + u.getScheme());
                System.out.println("The scheme specific part is " + u.getSchemeSpecificPart());
                System.out.println("The fragment ID is " + u.getFragment());
            } else {
                System.out.println("This is s hierarchical URI.");
                System.out.println("The scheme is " + u.getScheme());
                try {
                    u = u.parseServerAuthority();
                    System.out.println("The host is " + u.getHost());
                    System.out.println("The user info is " + u.getUserInfo());
                    System.out.println("The port is " + u.getPort());
                } catch (URISyntaxException e) {
                    System.out.println("The authority is " + u.getAuthority());
                }

                System.out.println("The path is " + u.getPath());
                System.out.println("The query string is " + u.getQuery());
                System.out.println("The fragment ID is " + u.getFragment());
            }
        } catch (URISyntaxException e) {
            System.err.println(uri + " does not seem to be a URI.");
        }
        System.out.println();
    }
}
