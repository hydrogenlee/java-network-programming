package chapter6;

import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;

public class C6P184_NoGovernmentCookies implements CookiePolicy {
    @Override
    public boolean shouldAccept(URI uri, HttpCookie cookie) {
        if (uri.getAuthority().toLowerCase().endsWith(".gov")
            || cookie.getDomain().toLowerCase().endsWith(".gov")) {
            return false;
        }
        return true;
    }
}
