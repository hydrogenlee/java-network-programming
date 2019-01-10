package chapter3;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class C3P61_DigestThread extends Thread{
    public static void main(String[] args) {
        C3P61_DigestThread thread = new C3P61_DigestThread("src/chapter3/Input_C3P61.txt");
        thread.start();
    }
    private final String filename;

    public C3P61_DigestThread(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        FileInputStream fin = null;
        DigestInputStream din = null;
        try {
            fin = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            din = new DigestInputStream(fin, sha);
            while (din.read() != -1){}
            System.out.println(filename + ": " + DatatypeConverter.printHexBinary(sha.digest()));
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            if (din != null) {
                try {
                    din.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
