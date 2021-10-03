package Client;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class GUI extends JFrame {
        private final ClientHandler clientHandler;
        private boolean connectFlag = true;
        public GUI() {

                setTitle("Stickynote Board");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setBounds(0, 0, 2000, 2000);
                clientHandler = new ClientHandler();
                renderContent();
                setVisible(true);
        }

        private void connectDialog() {
                txtIPAddress = new JTextField("127.0.0.1");
                txtPort = new JTextField("3000");
                Object[] fields = {"IP Address", txtIPAddress, "Port Number", txtPort};
                Object[] options = {"Connect", "Exit"};
                int option = JOptionPane.showOptionDialog(this, fields, "Connect to Server", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                switch (option) {
                        case JOptionPane.YES_OPTION:
                                try {
                                        clientHandler.connect(txtIPAddress.getText(),
                                                Integer.parseInt(txtPort.getText()));
                                        btnDisconnect.setEnabled(true);
                                } catch (NumberFormatException exception) {
                                        JOptionPane.showMessageDialog(this, "Invalid Port Number", "Error", JOptionPane.ERROR_MESSAGE);
                                        connectDialog();
                                } catch (IOException exception) {
                                        JOptionPane.showMessageDialog(this, "Unable to connect please check IP/Port", "Error", JOptionPane.ERROR_MESSAGE);
                                        connectDialog();
                                }
                                break;
                        case JOptionPane.NO_OPTION:
                                System.exit(0);
                                break;
                }
        }

        private void btnDisconnectHandler(ActionEvent e) {
                try {
                        btnDisconnect.setEnabled(false);
                        clientHandler.disconnect();
                        connectDialog();
                } catch (IOException exception) {
                        exception.printStackTrace();
                }
        }


        private void btnSubmitHandler(ActionEvent e) {
                if (clientHandler.isConnected()) {

                        try {
                                System.out.println("isConnected");
                                // Handle Title
                                String TITLE = txtTITLE.getText().trim();

                                // Handle All Get request
//                                if (e.getSource() == btnSend) {
//                                        System.out.println("BTN GET CLICKED");
//                                        txtOutput.setText(clientHandler.sendMessage(Request.GET, "test"));
//                                } else if(e.getSource() == btnPost) {
//                                        System.out.println("BTN POST CLICKED");
//                                        txtOutput.setText(clientHandler.sendMessage(Request.POST, "test"));
//                                }else if (e.getSource()==btnClear){
//                                        System.out.println("BTN CLEAR CLICKED");
//                                        txtOutput.setText(clientHandler.sendMessage(Request.CLEAR, "test"));
//                                }


                                if (comboBoxRequests.getSelectedItem() == Request.GET) {
                                        System.out.println("COMBO BOX GET CLICKED");
                                        txtOutput.setText(clientHandler.sendMessage(Request.GET, "test", 1, 5, "matt", 100, 200));
                                }
                                if (comboBoxRequests.getSelectedItem() == Request.POST) {
                                        System.out.println("BTN POST CLICKED");
                                        txtOutput.setText(clientHandler.sendMessage(Request.POST, "test",1,5, "matt", 100, 200));
                                }
                                if (comboBoxRequests.getSelectedItem() == Request.CLEAR) {
                                        System.out.println("BTN CLEAR CLICKED");
                                       txtOutput.setText(clientHandler.sendMessage(Request.CLEAR, "test",1,5, "matt", 100, 200));
                                }
                                connectFlag = false;
                                return;

                        } catch (NumberFormatException exception) {
                                JOptionPane.showMessageDialog(this, "Invalid ISBN", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (IOException exception) {
                                exception.printStackTrace();
                        }
                } else {
                     connectDialog();
                }
        }


        private void comboBoxRequestsHandler(ActionEvent e) {

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
//                panelIP = new JPanel();
//                panelFields.add(panelIP);
//                panelIP.setLayout(new GridLayout(0, 2, 0, 0));
//
//                labelIP = new JLabel("IP Address:");
//                panelIP.add(labelIP);
//
//                txtIP = new JTextField();
//                panelIP.add(txtIP);
//                txtIP.setColumns(10);
                // PORT Section
//                panelPort = new JPanel();
//                panelFields.add(panelPort);
//                panelPort.setLayout(new GridLayout(0, 2, 0, 0));
//
//                lbPort = new JLabel("Port:");
//                panelPort.add(lbPort);
//
//                txtPort = new JTextField();
//                panelPort.add(txtPort);
//                txtPort.setColumns(10);

                //Color
                panelTITLE = new JPanel();
                panelFields.add(panelTITLE);
                panelTITLE.setLayout(new GridLayout(0, 2, 0, 0));

                lblTITLE = new JLabel("TITLE:");
                panelTITLE.add(lblTITLE);

                txtTITLE = new JTextField();
                panelTITLE.add(txtTITLE);
                txtTITLE.setColumns(10);


                //COMBO BOX

                comboBoxRequests = new JComboBox<>(Request.values());
                comboBoxRequests.addActionListener(this::comboBoxRequestsHandler);
                panelRequest.add(comboBoxRequests);

                // POST Section
                panelPost = new JPanel();
                panelFields.add(panelPost);
                panelPost.setLayout(new GridLayout(0, 2, 0, 0));

                lbPost = new JLabel("Data For POST:");
                panelPost.add(lbPost);

                txtPost = new JTextField();
                panelPost.add(txtPost);
                txtPost.setColumns(10);

                btnSend = new JButton("SEND REQUEST ");
                btnSend.addActionListener(this::btnSubmitHandler);


                panelPost.add(btnSend);

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
                txtOutput.setBorder(new EmptyBorder(0, 0, 500, 500));
                panelOutput.setViewportView(txtOutput);

                // Connect Button
                btnConnect = new JButton("Connect ");
                btnConnect.setEnabled(false);
                panelHeader.add(btnConnect, BorderLayout.WEST);

                // Disconnect Button
                btnDisconnect = new JButton("Disconnect");
                btnDisconnect.addActionListener(this::btnDisconnectHandler);
                btnDisconnect.setEnabled(false);
                panelHeader.add(btnDisconnect, BorderLayout.EAST);
                setVisible(true);
                connectDialog();
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
        JButton btnSend;
        JButton btnPost;

        JButton btnClear;
        JButton btnShake;

        JTextField txtIPAddress;


        JTextField txtTITLE;
        JLabel lblTITLE;
        JPanel panelTITLE;
        JComboBox<Request> comboBoxRequests;

}
