package chapter8;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class C8P268_Whois {
    static final String DEFAULT_HOST = "whois.internic.net";
    static final int DEFAULT_PORT = 43;

    private int port = DEFAULT_PORT;
    private InetAddress host;

    public C8P268_Whois(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    public C8P268_Whois(InetAddress host) {
        this(host, DEFAULT_PORT);
    }

    public C8P268_Whois(String hostname, int port) throws UnknownHostException {
        this(InetAddress.getByName(hostname), port);
    }

    public C8P268_Whois(String hostname) throws UnknownHostException {
        this(hostname, DEFAULT_PORT);
    }

    public C8P268_Whois() throws UnknownHostException {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }


    public enum SearchFor {
        ANY(""), NETWORK("Network"), PERSON("Person"), HOST("Host"),
        DOMAIN("Domain"), ORGANIZATION("Organization"), GROUP("Group"),
        GATEWAY("Gateway"), ASN("ASN");

        private String label;
        private SearchFor(String label) {
            this.label = label;
        }
    }

    public enum SearchIn {
        ALL(""), NAME("Name"), MAILBOX("Mailbox"), HANDLE("!");
        private String label;
        private SearchIn(String label) {
            this.label = label;
        }
    }

    public InetAddress getHost() {
        return host;
    }

    public void setHost(String hostName) throws UnknownHostException {
        this.host = InetAddress.getByName(hostName);
    }


    public String lookUpNames(String target, SearchFor category,
                              SearchIn group, boolean exactMatch) throws IOException {
        String suffix = "";
        if (!exactMatch) {
            suffix = ".";
        }

        String prefix = category.label + " " + group.label;
        String query = prefix + target + suffix;

        try (Socket socket = new Socket()) {
            SocketAddress address = new InetSocketAddress(host, port);
            socket.connect(address);

            // write query string to output stream
            Writer out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.US_ASCII);
            out.write(query);
            out.write("\r\n");
            out.flush();

            // get result from input stream
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.US_ASCII));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }

            return sb.toString();
        }
    }
}

