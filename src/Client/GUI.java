package Client;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GUI extends JFrame {

        public GUI() {
                renderContent();
                setTitle("Stickynote Board");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setBounds(100, 100, 800, 800);
                setVisible(true);
        }

        private void renderContent() {
                panelParent = new JPanel();
                panelParent.setBorder(new EmptyBorder(5, 5, 10, 10));
                setContentPane(panelParent);
                panelParent.setLayout(new BorderLayout(0, 0));

                panelHeader = new JPanel();
                panelHeader.setBorder(new EmptyBorder(5, 10, 20, 10));
                panelParent.add(panelHeader, BorderLayout.NORTH);
                panelHeader.setLayout(new BorderLayout(0, 0));

                // Header
                lblTitle = new JLabel("Stickynote Board");
                lblTitle.setFont(new Font("Serif", Font.PLAIN, 20));
                lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
                panelHeader.add(lblTitle, BorderLayout.CENTER);

                panelContent = new JPanel();
                panelParent.add(panelContent, BorderLayout.CENTER);
                panelContent.setLayout(new GridLayout(3, 0, 0, 0));

                panelLeft = new JPanel();
                panelLeft.setBorder(new EmptyBorder(10, 10, 10, 0));
                panelContent.add(panelLeft);
                panelLeft.setLayout(new BorderLayout(0, 0));

                // Request Section
                panelRequest = new JPanel();
                panelRequest.setBorder(new EmptyBorder(10, 0, 10, 0));
                panelLeft.add(panelRequest, BorderLayout.NORTH);
                panelRequest.setLayout(new GridLayout(1, 0, 0, 0));

                lblRequest = new JLabel("Request:");
                panelRequest.add(lblRequest);

                panelFields = new JPanel();
                panelFields.setBorder(new EmptyBorder(10, 0, 10, 0));
                panelLeft.add(panelFields, BorderLayout.CENTER);
                panelFields.setLayout(new BoxLayout(panelFields, BoxLayout.Y_AXIS));
                // IP ADDRESS Section
                panelIP = new JPanel();
                panelFields.add(panelIP);
                panelIP.setLayout(new GridLayout(0, 2, 0, 0));

                labelIP = new JLabel("IP Address:");
                panelIP.add(labelIP);

                txtIP = new JTextField();
                panelIP.add(txtIP);
                txtIP.setColumns(10);
                // PORT Section
                panelPort = new JPanel();
                panelFields.add(panelPort);
                panelPort.setLayout(new GridLayout(0, 2, 0, 0));

                lbPort = new JLabel("Port:");
                panelPort.add(lbPort);

                txtPort = new JTextField();
                panelPort.add(txtPort);
                txtPort.setColumns(10);

                // POST Section
                panelPost = new JPanel();
                panelFields.add(panelPost);
                panelPost.setLayout(new GridLayout(0, 2, 0, 0));

                lbPost = new JLabel("Data For POST:");
                panelPost.add(lbPost);

                txtPost = new JTextField();
                panelPost.add(txtPost);
                txtPost.setColumns(10);

                btnGet = new JButton("Get ");
                btnPin = new JButton("Pin ");
                btnUnpin = new JButton("Unpin ");

                btnPost = new JButton("Post ");
                btnClear = new JButton("Clear ");
                btnShake = new JButton("Shake ");

                panelPost.add(btnGet);
                panelPost.add(btnPost);
                panelPost.add(btnPin);
                panelPost.add(btnUnpin);

                panelPost.add(btnClear);
                panelPost.add(btnShake);

                // Output section
                panelOutput = new JScrollPane();
                panelOutput.setBorder(new EmptyBorder(10, 0, 10, 10));
                panelContent.add(panelOutput);

                lblOutput = new JLabel("Output");
                lblOutput.setBorder(new EmptyBorder(0, 0, 10, 0));
                panelOutput.setColumnHeaderView(lblOutput);

                txtOutput = new JTextArea();
                txtOutput.setEditable(false);
                txtOutput.setLineWrap(true);
                txtOutput.setBorder(new BevelBorder(BevelBorder.LOWERED));
                panelOutput.setViewportView(txtOutput);

                // Connect Button
                btnConnect = new JButton("Connect ");
                btnConnect.setEnabled(false);
                panelHeader.add(btnConnect, BorderLayout.WEST);

                // Disconnect Button
                btnDisconnect = new JButton("Disconnect");
                // btnDisconnect.addActionListener();
                btnDisconnect.setEnabled(false);
                panelHeader.add(btnDisconnect, BorderLayout.EAST);
        }

        JPanel panelParent;
        JPanel panelHeader;
        JLabel lblTitle;
        JPanel panelContent;
        JPanel panelLeft;
        JPanel panelRequest;
        JLabel lblRequest;

        JPanel panelFields;
        JPanel panelIP;
        JLabel labelIP;
        JTextField txtIP;
        JScrollPane panelOutput;
        JLabel lblOutput;
        JTextArea txtOutput;

        JPanel panelPort;
        JLabel lbPort;
        JTextField txtPort;

        JPanel panelPost;
        JLabel lbPost;
        JTextField txtPost;

        JButton btnDisconnect;
        JButton btnConnect;

        JButton btnPin;
        JButton btnUnpin;
        JButton btnGet;
        JButton btnPost;

        JButton btnClear;
        JButton btnShake;
}
