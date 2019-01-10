package chapter3;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class C3P69_DigestThreadUsingCallBack implements C3P69_DigestPrintCallBack{
    public static void main(String[] args) {
        C3P69_DigestThreadUsingCallBack callBack = new C3P69_DigestThreadUsingCallBack("src/chapter3/Input_C3P61.txt");
        callBack.calculateDigest();
    }

    private final String filename;

    public C3P69_DigestThreadUsingCallBack(String filename) {
        this.filename = filename;
    }

    public void calculateDigest() {
        Thread thread = new Thread(new DigestRunnable(filename, this));
        thread.start();
    }

    @Override
    public void printDigest(byte[] digest, String name) {
        StringBuilder sb = new StringBuilder(name);
        sb.append(": ");
        sb.append(DatatypeConverter.printHexBinary(digest));
        System.out.println(sb.toString());
    }

    public static class DigestRunnable implements Runnable {
        private final String filename;
        private final C3P69_DigestThreadUsingCallBack callBackThread;
        public DigestRunnable(String filename, C3P69_DigestThreadUsingCallBack callBackThread) {
            this.filename = filename;
            this.callBackThread = callBackThread;
        }

        @Override
        public void run() {
            FileInputStream fin = null;
            DigestInputStream din = null;
            try {
                fin = new FileInputStream(filename);
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                din = new DigestInputStream(fin, md);
                while (din.read() != -1) {}
                callBackThread.printDigest(md.digest(), filename);
            } catch (IOException | NoSuchAlgorithmException e) {
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
}
