package chapter4;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class C4P98_MyAddress {
    public static void main(String[] args) {
        try {
            InetAddress me = InetAddress.getLocalHost();
            System.out.println(me);
        } catch (UnknownHostException e) {
            System.err.println("Could not find this computer's address");
        }
    }
}
