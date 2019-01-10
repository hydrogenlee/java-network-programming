package chapter8;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class C8P239_DaytimeClient {
    public static void main(String[] args) {
        // 不一定每一次都成功，需要多试几次
        // String host = "time.nist.gov";
        String host = "localhost";
        int port = 13;

        // try with resource
        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(15000);
            StringBuilder sb = new StringBuilder();
            try (BufferedInputStream bin = new BufferedInputStream(socket.getInputStream());
                 Reader reader = new InputStreamReader(bin, StandardCharsets.US_ASCII)) {
                int c;
                while ((c = reader.read()) != -1) {
                    sb.append((char) c);
                }
                System.out.println("Time now: " + parseDate(sb.toString()));
            }

        } catch (IOException e) {
            System.err.println("Could not connect to " + host + ":" + port);
        }
    }


    public static Date parseDate(String s) {
        Date date = null;
        if (s != null && s.length() > 0) {
            String[] pieces = s.split(" ");
            if (pieces.length >= 3) {
                String time = pieces[1] + " " + pieces[2] + " UTC";
                DateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss z");
                try {
                    date =  format.parse(time);
                } catch (ParseException e) {
                    System.err.println("Could not parse time: " + s);
                }
            }
        }
        return date;
    }
}
