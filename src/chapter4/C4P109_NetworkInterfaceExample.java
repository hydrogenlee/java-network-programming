package chapter4;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class C4P109_NetworkInterfaceExample {
    public static void main(String[] args) {
        formatPrompt("getName(\"eth0\")");
        getName("eth0");
        formatPrompt("getByInetAddress(\"127.0.0.1\")");
        getByInetAddress("127.0.0.1");
        formatPrompt("getNetworkInterfaces()");
        getNetworkInterfaces();
        formatPrompt("getInetAddresses(\"eth0\")");
        getInetAddresses("eth0");
        formatPrompt("printName(\"127.0.0.1\")");
        printName("127.0.0.1");
    }

    private static void formatPrompt(String s) {
        System.out.println("\n----------" + s + "----------");
    }
    /**
     * 查询网络接口是否存在
     * @param name 接口名
     */
    public static void getName(String name) {
        try {
            NetworkInterface networkInterface = NetworkInterface.getByName(name);
            if (networkInterface == null) {
                System.err.println("No such interface: \"" + name + "\"");
            } else {
                System.out.println("Network interface \"" + name + "\" is exited.");
            }
        } catch (SocketException e) {
            System.err.println("Could not list sockets.");
        }
    }

    /**
     * 根据网络地址查询网络接口
     * @param name 网络地址/域名
     */
    public static void getByInetAddress(String name) {
        try {
            InetAddress address = InetAddress.getByName(name);
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(address);
            if (networkInterface == null) {
                System.err.println("No such interface for address: \"" + name + "\"");
            } else {
                System.out.println("Network interface for \"" + name + "\" is exited.");
            }
        } catch (UnknownHostException e) {
            System.err.println("Could not lookup address: \"" + name + "\"");
        } catch (SocketException e) {
            System.err.println("Could not list network interfaces.");
        }
    }

    /**
     * 获取系统所有的网络接口
     */
    public static void getNetworkInterfaces() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                System.out.println(networkInterface);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     *  查询某个网络接口对应的所有网络地址
     * @param name 接口名
     */
    public static void getInetAddresses(String name) {
        try {
            NetworkInterface ni = NetworkInterface.getByName(name);
            Enumeration<InetAddress> addresses = ni.getInetAddresses();
            while (addresses.hasMoreElements()) {
                System.out.println(addresses.nextElement());
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据域名或者ip地址查找相应的网络接口，并打印网络接口名
     * @param name IP地址/域名
     */
    public static void printName(String name) {
        try {
            InetAddress address = InetAddress.getByName(name);
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            System.out.println("name: " + ni.getName());
            System.out.println("display name: " + ni.getDisplayName());
        } catch (UnknownHostException e) {
            System.err.println("Could not lookup address: \"" + name + "\"");
        } catch (SocketException e) {
            System.err.println("Could not list network interfaces.");
        }
    }
}
