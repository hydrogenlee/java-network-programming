package chapter3;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class C3P92_GZipRunnable implements Runnable {
    private final File input;

    public C3P92_GZipRunnable(File input) {
        this.input = input;
    }

    @Override
    public void run() {
        if (!input.getName().endsWith(".gz")) {
            File output = new File(input.getParent(), input.getName() + ".gz");
            if (!output.exists()) {
                try (
                        // try with resource(newer than 1.7)
                        InputStream in = new BufferedInputStream(new FileInputStream(input));
                        OutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(output)))
                ) {

                    int b;
                    while ((b = in.read()) != -1) {
                        out.write(b);
                    }
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
