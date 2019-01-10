package chapter4;


import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 读取Web服务器日志地址，显示各行时将IP地址转换为主机名
 */
public class C4P114_WebLog {
    public static void main(String[] args) {
        String path = "src/chapter4/Input_C4P114_WebLog.txt";
        // try with resource
        try (FileInputStream fin = new FileInputStream(path);
             Reader inr = new InputStreamReader(fin);
             BufferedReader br = new BufferedReader(inr)) {
            for (String entry = br.readLine(); entry != null; entry = br.readLine()) {
                int index = entry.indexOf(' ');
                String ip = entry.substring(0, index);
                String rest = entry.substring(index);
                try {
                    // 进行域名查询，如果找不到域名直接输出IP
                    InetAddress address = InetAddress.getByName(ip);
                    System.out.println(address.getHostName() + rest);
                } catch (UnknownHostException e) {
                    System.err.println("Exception: " + e + ". host: " + ip);
                }
            }
        } catch (IOException e) {
            System.err.println("Exception: " + e);
        }
    }
}
