package chapter7;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class C7P192_BinarySaver {
    public static void main(String[] args) {
        saveBinaryFile("https://cdn.oreillystatic.com/oreilly/images/app-store-logo.png");
    }

    public static void saveBinaryFile(String url) {
        try {
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            String contentType = connection.getContentType();
            if (contentType == null || contentType.startsWith("text/")) {
                throw new IOException("This is not a binary file.");
            }
            int length = connection.getContentLength();
            if (length <= 0) {
                throw new IOException("The length of binary file is less or equal than 0.");
            }

            try (InputStream raw = connection.getInputStream();
                 InputStream bin = new BufferedInputStream(raw)) {
                byte[] data = new byte[length];
                int offset = 0;
                while (offset < length) {
                    int bytesRead = bin.read(data, offset, length);
                    // 当读到-1的时候，表示已经全部读取完毕，也可能是意外结束，需要后续进行判断
                    if (bytesRead == -1) {
                        break;
                    }
                    offset += bytesRead;
                }
                if (offset != length) {
                    throw new IOException("Only read " + offset + " bytes; Expected " + length + " bytes");
                }
                String filename = u.getFile();
                filename = filename.substring(filename.lastIndexOf('/') + 1);

                // 保存文件
                // AutoCloseable
                try (FileOutputStream fout = new FileOutputStream(filename)) {
                    fout.write(data);
                    fout.flush();
                }
            }
        } catch (MalformedURLException e) {
            System.err.println(url + " is not a parsable URL.");
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
