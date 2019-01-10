package chapter8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

public class C8P270_WhoisGUI extends JFrame {

    public static void main(String[] args) {
        try {
            C8P268_Whois server = new C8P268_Whois();
            C8P270_WhoisGUI gui = new C8P270_WhoisGUI(server);
            gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            gui.pack();
            EventQueue.invokeLater(new FrameShower(gui));
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "Could not locate default host " +
                    C8P268_Whois.DEFAULT_HOST, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }


    private JTextField searchString = new JTextField(30);
    private JTextArea names = new JTextArea(15, 80);
    private JButton findButton = new JButton("Find");
    private ButtonGroup searchIn = new ButtonGroup();
    private ButtonGroup searchFor = new ButtonGroup();
    private JCheckBox exactMatch = new JCheckBox("Exact Match", true);
    private JTextField chosenServer = new JTextField();

    private C8P268_Whois server;

    public C8P270_WhoisGUI(C8P268_Whois whois) {
        super("C8P270_WhoisGUI");
        this.server = whois;
        Container pane = this.getContentPane();

        Font f = new Font("Monospaced", Font.PLAIN, 12);
        names.setFont(f);
        names.setEnabled(false);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 1, 10, 10));
        JScrollPane jsp = new JScrollPane(names);
        centerPanel.add(jsp);
        pane.add("Center", centerPanel);

        // 不希望南边和北边的按钮占满整个区域
        // 所以在那里添加Panel，并在Panel中使用FlowLayout
        JPanel northPanel = new JPanel();
        JPanel northPanelTop = new JPanel();
        northPanelTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        northPanelTop.add(new JLabel("Whois: "));
        northPanelTop.add("North", searchString);
        northPanelTop.add(exactMatch);
        northPanelTop.add(findButton);
        northPanel.setLayout(new BorderLayout(2, 1));
        northPanel.add("North", northPanelTop);

        JPanel northPanelBottom = new JPanel();
        northPanelBottom.setLayout(new GridLayout(1, 3, 5, 5));
        northPanelBottom.add(initRecordType());
        northPanelBottom.add(initSearchFields());
        northPanelBottom.add(initServerChoice());
        northPanel.add("Center", northPanelBottom);

        pane.add("North", northPanel);

        ActionListener al = new LookupNames();
        findButton.addActionListener(al);
        searchString.addActionListener(al);
    }


    private JPanel initRecordType() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(6, 2, 5, 5));
        p.add(new JLabel("Search for: "));
        p.add(new Label(""));

        p.add(makeRadioButton(searchFor, "Any", true));
        p.add(makeRadioButton(searchFor, "Network", false));
        p.add(makeRadioButton(searchFor, "Person", false));
        p.add(makeRadioButton(searchFor, "Host", false));
        p.add(makeRadioButton(searchFor, "Domain", false));
        p.add(makeRadioButton(searchFor, "Organization", false));
        p.add(makeRadioButton(searchFor, "Group", false));
        p.add(makeRadioButton(searchFor, "Gateway", false));
        p.add(makeRadioButton(searchFor, "ASN", false));

        return p;
    }

    private JPanel initSearchFields() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(6, 1, 5, 2));
        p.add(new JLabel("Search In: "));
        p.add(makeRadioButton(searchIn, "All", true));
        p.add(makeRadioButton(searchIn, "Name", false));
        p.add(makeRadioButton(searchIn, "Mailbox", false));
        p.add(makeRadioButton(searchIn, "Handle", false));
        return p;
    }

    private JPanel initServerChoice() {
        final JPanel p = new JPanel();
        p.setLayout(new GridLayout(6, 1, 5, 2));
        p.add(new JLabel("Search At: "));

        chosenServer.setText(server.getHost().getHostName());
        p.add(chosenServer);
        chosenServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    server = new C8P268_Whois(chosenServer.getText());
                } catch (UnknownHostException ex) {
                    JOptionPane.showMessageDialog(p, ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        return p;
    }

    private JRadioButton makeRadioButton(ButtonGroup buttonGroup, String label, boolean selected) {
        JRadioButton button = new JRadioButton(label, selected);
        button.setActionCommand(label);
        buttonGroup.add(button);
        return button;
    }


    private class LookupNames implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            names.setText("");
            SwingWorker<String, Object> worker = new Lookup();
            worker.execute();
        }
    }


    private class Lookup extends SwingWorker<String, Object> {

        @Override
        protected String doInBackground() throws Exception {
            C8P268_Whois.SearchIn group = C8P268_Whois.SearchIn.ALL;
            C8P268_Whois.SearchFor category = C8P268_Whois.SearchFor.ANY;

            String searchInLabel = searchIn.getSelection().getActionCommand();
            String searchForLabel = searchFor.getSelection().getActionCommand();

            switch (searchInLabel) {
                case "Name":
                    group = C8P268_Whois.SearchIn.NAME;
                    break;
                case "Mailbox":
                    group = C8P268_Whois.SearchIn.MAILBOX;
                    break;
                case "Handle":
                    group = C8P268_Whois.SearchIn.HANDLE;
                    break;
            }

            switch (searchForLabel) {
                case "Network":
                    category = C8P268_Whois.SearchFor.NETWORK;
                    break;
                case "Person":
                    category = C8P268_Whois.SearchFor.PERSON;
                    break;
                case "Host":
                    category = C8P268_Whois.SearchFor.HOST;
                    break;
                case "Domain":
                    category = C8P268_Whois.SearchFor.DOMAIN;
                    break;
                case "Organization":
                    category = C8P268_Whois.SearchFor.ORGANIZATION;
                    break;
                case "Group":
                    category = C8P268_Whois.SearchFor.GROUP;
                    break;
                case "Gateway":
                    category = C8P268_Whois.SearchFor.GATEWAY;
                    break;
                case "ASN":
                    category = C8P268_Whois.SearchFor.ASN;
                    break;
            }

            server.setHost(chosenServer.getText());
            return server.lookUpNames(searchString.getText(), category, group, exactMatch.isSelected());
        }

        @Override
        protected void done() {
            try {
                names.setText(get());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(C8P270_WhoisGUI.this,
                        ex.getMessage(), "Lookup Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static class FrameShower implements Runnable {
        private final Frame frame;

        public FrameShower(Frame frame) {
            this.frame = frame;
        }
        @Override
        public void run() {
            frame.setVisible(true);
        }
    }
}

