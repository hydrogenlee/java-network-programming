package chapter4;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class C4P105_IPCharacteristics {
    public static void main(String[] args) throws UnknownHostException {
        printCharacteristics("127.0.0.1");
        printCharacteristics("192.168.254.32");
        printCharacteristics("www.oreilly.com");
        printCharacteristics("224.0.0.1");
        printCharacteristics("FF01::1");
        printCharacteristics("FF05::101");
        printCharacteristics("0::1");
    }

    public static void printCharacteristics(String addr) {
        System.out.println("----------" + addr + "----------");
        InetAddress address = null;
        try {
            address = InetAddress.getByName(addr);
            if (address.isAnyLocalAddress()) {
                System.out.println(address + " is a wildcard address.");
            }
            if (address.isLoopbackAddress()) {
                System.out.println(address + " is loopback address.");
            }
            // 判断是否为本地地址还是全球地址
            if (address.isLinkLocalAddress()) {
                System.out.println(address + " is a link-local address.");
            } else if (address.isSiteLocalAddress()) {
                System.out.println(address + " is a site-local address.");
            } else {
                System.out.println(address + " is a global address.");
            }

            // 判断是组播地址还是单播地址，对于组播地址判断是何种组播地址
            if (address.isMulticastAddress()) {
                if (address.isMCGlobal()) {
                    System.out.println(address + " is a global multicast address.");
                } else if (address.isMCOrgLocal()) {
                    System.out.println(address + " is an organization wide multicast address.");
                } else if (address.isMCSiteLocal()) {
                    System.out.println(address + " is a site wide multicast address.");
                } else if (address.isMCLinkLocal()) {
                    System.out.println(address + " is a subnet multicast address.");
                } else if (address.isMCNodeLocal()) {
                    System.out.println(address + " is an interface-local multicast address.");
                } else {
                    System.out.println(address + " is an unknown multicast address type.");
                }
            } else {
                System.out.println(address + " is a unicast address.");
            }
        } catch (UnknownHostException e) {
            System.err.println("Could not resolve " + addr);
        }
        System.out.println("---------- end ----------\n");
    }
}
