package chapter7;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class C7P217_FormPoster {
    public static void main(String[] args) {
        // username=&
        String baseURL = "http://www.cafeaulait.org/books/jnp4/postquery.phtml";
        QueryStringEncoder encoder = new QueryStringEncoder();
        encoder.add("name", "Elliotte Rusty Harold");
        encoder.add("email", "elharo@ibiblio.org");
        C7P217_FormPoster poster = new C7P217_FormPoster(encoder);
        try (InputStream in = poster.postRequest(baseURL)) {
            poster.printResponse(in);
        } catch (MalformedURLException e) {
            System.err.println(baseURL + " is not a parsable URL.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private URL u;
    private QueryStringEncoder encoder;

    public C7P217_FormPoster(QueryStringEncoder encoder) {
        this.encoder = encoder;
    }

    public URL getURL() {
        return u;
    }

    public InputStream postRequest(String url) throws IOException {
        u = new URL(url);
        if (!u.getProtocol().toLowerCase().startsWith("http")) {
            throw new IllegalArgumentException("Posting only works for http[s] URLs. Illegal URL: " + url);
        }

        URLConnection conn = u.openConnection();
        conn.setDoOutput(true);

        try (OutputStream out = conn.getOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(out)) {
            writer.write(encoder.getEncodedQueryString());
            // 额外发送一个回车换行，表示结束
            writer.write("\r\n");
            writer.flush();
        }

        return conn.getInputStream();
    }

    public void printResponse(InputStream in) {
        try (InputStream bin = new BufferedInputStream(in);
             Reader reader = new InputStreamReader(bin)) {
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static class QueryStringEncoder {
        private StringBuilder queryString = new StringBuilder();
        private boolean isFirst = true;

        public void add(String name, String value) {
            if (isFirst) {
                isFirst = false;
                queryString.append("?");
            } else {
                queryString.append("&");
            }
            encode(name, value);
        }

        public void encode(String name, String value) {
            encode(name, value, "UTF-8");
        }

        public void encode(String name, String value, String encoding) {
            queryString.append(name);
            queryString.append("=");
            try {
                value = URLEncoder.encode(value, encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            queryString.append(value);
        }

        public String getEncodedQueryString() {
            return queryString.toString();
        }
    }
}

