package chapter4;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

public class C4P115_LookupTask implements Callable<String> {
    private String line;

    public C4P115_LookupTask(String line) {
        this.line = line;
    }

    @Override
    public String call() {
        int index = line.indexOf(' ');
        String ip = line.substring(0, index);
        String rest = line.substring(index);
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.getHostName() + rest;
        } catch (UnknownHostException e) {
            System.err.println("Exception: " + e + ". host: " + ip);
            return line;
        }
    }
}
