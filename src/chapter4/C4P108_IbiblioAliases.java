package chapter4;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class C4P108_IbiblioAliases {
    public static void main(String[] args) {
        try {
            InetAddress ibiblio = InetAddress.getByName("www.ibiblio.org");
            InetAddress answers = InetAddress.getByName("answers.ibiblio.org");
            if (ibiblio.equals(answers)) {
                System.out.println("www.ibiblio.org is the same as answers.ibiblio.org");
            } else {
                System.out.println("www.ibiblio.org is not the same as answers.ibiblio.org");
            }
        } catch (UnknownHostException e) {
            System.err.println("Host lookup failed.");
        }
    }
}
