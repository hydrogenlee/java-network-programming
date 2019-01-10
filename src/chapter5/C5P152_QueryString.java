package chapter5;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class C5P152_QueryString {
    public static void main(String[] args) {
        C5P152_QueryString qs = new C5P152_QueryString();
        qs.add("hl", "en");
        qs.add("as_q", "Java");
        qs.add("as_epq", "I/O");
        String url = "http://www.google.com/search?" + qs;
        System.out.println("Encoded url: " + url);
        System.out.println("Decoded url: " + qs.decode(url));
    }

    private StringBuilder queryString = new StringBuilder();

    public synchronized void add(String name, String value) {
        queryString.append("&");
        encode(name, value);
    }

    private synchronized void encode(String name, String value) {
        try {
            queryString.append(URLEncoder.encode(name, "UTF-8"));
            queryString.append("=");
            queryString.append(URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }

    private synchronized String decode(String url) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }

    private synchronized String getQuery() {
        return queryString.toString();
    }

    @Override
    public String toString() {
        return getQuery();
    }
}
