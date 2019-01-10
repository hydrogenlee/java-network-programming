package chapter2;

import java.io.IOException;
import java.io.OutputStream;

public class C2P35_RecursivePrintCharacters {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    /**
     * 循环打印可输出字符，每次写入一个字节
     * @param out 输出流
     * @throws IOException IO异常
     */
    public static void generateCharacters(OutputStream out) throws IOException {
        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharacterPerLine = 72;

        int start = firstPrintableCharacter;
        while (true) {
            // 循环输出
            for (int i = start; i < start + numberOfCharacterPerLine; i++) {
                out.write((i - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter);
            }
            out.write('\r');
            out.write('\n');
            start =(start + 1 - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
        }
    }

    /**
     * 循环打印可输出字节，但是先缓存到数组里，然后每次写入一行
     * @param out 输出流
     * @throws IOException IO异常
     */
    public static void generateCharactersWithArray(OutputStream out) throws IOException{
        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharacterPerLine = 72;
        int start = firstPrintableCharacter;
        byte[] buffer = new byte[numberOfCharacterPerLine + 2];
        buffer[buffer.length - 2] = '\r';
        buffer[buffer.length - 1] = '\n';

        while (true) {
            for (int i = start; i < start + numberOfCharacterPerLine; i++) {
                buffer[i - start] = (byte) ((i - start) % numberOfPrintableCharacters + firstPrintableCharacter);
            }
            out.write(buffer);
            start = (start + 1) % numberOfPrintableCharacters + firstPrintableCharacter;
        }
    }
}
