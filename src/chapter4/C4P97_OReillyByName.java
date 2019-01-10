package chapter4;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class C4P97_OReillyByName {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("www.oreilly.com");             // 获取一个地址
            System.out.println("address: " + Arrays.toString(address.getAddress()));    // IP地址数组
            System.out.println("name: " + address.getHostName());       // 获取域名，如果缓存了域名，则不进行DNS查询
            System.out.println("Canonical host name: " + address.getCanonicalHostName());   // 获取域名，每次都进行DNS查询
            System.out.println("host: " + address.getHostAddress());    // 获取IP地址

            System.out.println("\nPrint all address information of www.oreilly.com");
            InetAddress[] allAddresses = InetAddress.getAllByName("www.oreilly.com"); // 获取所有地址
            for (InetAddress ad : allAddresses) {
                System.out.println("address: " + ad);
            }
        } catch (UnknownHostException e) {
            System.err.println("Could not find www.oreilly.com");
        }
    }
}
