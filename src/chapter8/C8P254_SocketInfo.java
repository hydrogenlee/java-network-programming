package chapter8;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class C8P254_SocketInfo {
    public static void main(String[] args) {
        String[] servers = {"www.oreilly.com", "www.oreilly.com", "www.baidu.com", "login.ibiblio.org"};

        for (String server : servers) {
            try (Socket socket = new Socket(server, 80)) {
                System.out.println("Connected to " + socket.getInetAddress() + " on port " +
                        socket.getPort() + " from port " + socket.getLocalPort() + " of " + socket.getLocalAddress());
            } catch (UnknownHostException e) {
                System.err.println("I can't find " + server);
            } catch (SocketException e) {
                System.err.println("Could not connect to " + server);
            } catch (IOException e) {
                System.err.println(e.toString());
            }
        }
    }
}
