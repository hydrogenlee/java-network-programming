package chapter5;

// swing基本上已经不使用
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class C5P163_DialogAuthenticator extends Authenticator {

    private JDialog passwordDialog;
    private JTextField usernameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton okButton = new JButton("OK");
    private JButton cancelButton = new JButton("Cancel");
    private JLabel mainLabel = new JLabel("Please enter username and password: ");
    PasswordAuthentication response = null;

    public C5P163_DialogAuthenticator() {
        this("", new JFrame());
    }

    public C5P163_DialogAuthenticator(String username) {
        this(username, new JFrame());
    }

    public C5P163_DialogAuthenticator(JFrame parent) {
        this("", parent);
    }

    public C5P163_DialogAuthenticator(String username, JFrame parent) {
        this.passwordDialog = new JDialog(parent, true);
        Container pane = passwordDialog.getContentPane();
        pane.setLayout(new GridLayout(4, 1));

        JLabel userLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        pane.add(mainLabel);
        JPanel p2 = new JPanel();
        p2.add(userLabel);
        p2.add(usernameField);
        usernameField.setText(username);
        pane.add(p2);
        JPanel p3 = new JPanel();
        p3.add(passwordLabel);
        p3.add(passwordField);
        pane.add(p3);
        JPanel p4 = new JPanel();
        p4.add(okButton);
        p4.add(cancelButton);
        pane.add(p4);
        passwordDialog.pack();

        ActionListener al = new OKResponse();
        okButton.addActionListener(al);
        usernameField.addActionListener(al);
        passwordField.addActionListener(al);
        cancelButton.addActionListener(new CancelResponse());
    }

    private void show() {
        String prompt = this.getRequestingPrompt();
        if (prompt == null) {
            String site = this.getRequestingSite().getHostName();
            String protocol = this.getRequestingProtocol();
            int port = this.getRequestingPort();
            if (site != null && protocol != null) {
                prompt = protocol + "://" + site;
                if (port > 0) {
                    prompt += ":" + port;
                }
            } else {
                prompt = "";
            }
        }

        mainLabel.setText("Please enter username and password for " + prompt + ": ");
        passwordDialog.pack();
        passwordDialog.setVisible(true);
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        this.show();
        return this.response;
    }

    class OKResponse implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            passwordDialog.setVisible(false);
            // 出于安全考虑，口令作为char数组返回
            char[] password = passwordField.getPassword();
            String username = usernameField.getText();
            passwordField.setText("");
            response = new PasswordAuthentication(username, password);
        }
    }

    class CancelResponse implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            passwordDialog.setVisible(false);
            passwordField.setText("");
            response = null;
        }
    }

}

