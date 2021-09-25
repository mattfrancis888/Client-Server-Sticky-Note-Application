import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GUI extends JFrame{


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


        lblTitle = new JLabel("Stickynote Board");
        lblTitle.setFont(new Font("Serif", Font.PLAIN, 14));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        panelHeader.add(lblTitle, BorderLayout.CENTER);


        panelContent = new JPanel();
        panelParent.add(panelContent, BorderLayout.CENTER);
        panelContent.setLayout(new GridLayout(1, 0, 0, 0));

        panelLeft = new JPanel();
        panelLeft.setBorder(new EmptyBorder(10, 10, 10, 0));
        panelContent.add(panelLeft);
        panelLeft.setLayout(new BorderLayout(0, 0));

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

        panelIP = new JPanel();
        panelFields.add(panelIP);
        panelIP.setLayout(new GridLayout(0, 2, 0, 0));

        labelIP = new JLabel("IP Address:");
        panelIP.add(labelIP);

        txtIP = new JTextField();
        panelIP.add(txtIP);
        txtIP.setColumns(1);


        panelRight = new JScrollPane();
        panelRight.setBorder(new EmptyBorder(10, 0, 10, 10));
        panelContent.add(panelRight);

        lblOutput = new JLabel("Output");
        lblOutput.setBorder(new EmptyBorder(0, 0, 10, 0));
        panelRight.setColumnHeaderView(lblOutput);

        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        txtOutput.setLineWrap(true);
        txtOutput.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panelRight.setViewportView(txtOutput);
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
    JScrollPane panelRight;
    JLabel lblOutput;
    JTextArea txtOutput;

}
