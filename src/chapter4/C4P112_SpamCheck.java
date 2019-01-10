package chapter4;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class C4P112_SpamCheck {
    private static final String BLOCKHOLE = "sbl.spamhaus.org";
    public static void main(String[] args) {
        String[] ips = {"207.34.56.23", "125.12.32.4", "130.130.130.130"};
        for (String ip : ips) {
            if (isSpammer(ip)) {
                System.out.println(ip + " is a known spammer.");
            } else {
                System.out.println(ip + " appears legitimate.");
            }
        }
    }

    private static boolean isSpammer(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);    // 目的是为了获取点分十进制数组
            byte[] quad = address.getAddress();                 // 获取的是带符号的IP地址，应转为不带符号的字节
            StringBuilder queryStr = new StringBuilder(BLOCKHOLE);
            for (byte b : quad) {
                int unsignedByte = b < 0 ? b + 256 : b;
                queryStr.insert(0, '.');
                queryStr.insert(0, unsignedByte);
            }
            InetAddress.getByName(queryStr.toString());
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
