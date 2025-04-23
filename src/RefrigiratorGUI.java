import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RefrigiratorGUI extends JFrame implements ActionListener {
    JButton button, button2;
    JLabel label1, label2, label3, labelImage;
    JTextField textField1, textField2;
    private Main main;
    public RefrigiratorGUI(Main main) {
        this.main = main;
        setTitle("Refrigerator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout(10, 10));

        // Top panel with input fields and buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        label1 = new JLabel("Amount of Hours Refrigerator is used:");
        label2 = new JLabel("Wattage of Your Refrigerator:");
        label3 = new JLabel(" ");
        label3.setForeground(Color.BLUE);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        label1.setFont(labelFont);
        label2.setFont(labelFont);
        label3.setFont(new Font("Segoe UI", Font.BOLD, 16));

        textField1 = new JTextField(10);
        textField2 = new JTextField(10);

        button = new JButton("Calculate");
        button2 = new JButton("Choose Another Appliance");

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button2.setFont(new Font("Segoe UI", Font.BOLD, 14));

        button.addActionListener(this);
        button2.addActionListener(this);

        topPanel.add(label1);
        topPanel.add(textField1);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(label2);
        topPanel.add(textField2);
        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(button);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(button2);
        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(label3);

        // Image panel
        JPanel imagePanel = new JPanel();
        try {
            ImageIcon icon = new ImageIcon("F:\\Java_Project\\Approximate-Electricity-Bill-Calculator\\src\\pngegg.png");
            Image scaledImg = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            labelImage = new JLabel(new ImageIcon(scaledImg));
            imagePanel.add(labelImage);
        } catch (Exception ex) {
            labelImage = new JLabel("Image not found");
            imagePanel.add(labelImage);
        }

        add(topPanel, BorderLayout.CENTER);
        add(imagePanel, BorderLayout.EAST);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            try {
                double hours = Double.parseDouble(textField1.getText());
                double watts = Double.parseDouble(textField2.getText());
                double kwh = ((watts * hours) * 30) / 1000;
                double unitprice = 0;

                if (hours < 1 || hours > 24) {
                    label3.setText("Please enter valid hours (1-24).");
                    return;
                }

                if (kwh <= 100) unitprice = 39;
                else if (kwh <= 200) unitprice = 41;
                else if (kwh <= 300) unitprice = 42;
                else if (kwh <= 400) unitprice = 43;
                else if (kwh <= 500) unitprice = 43;
                else if (kwh <= 600) unitprice = 43;
                else if (kwh <= 700) unitprice = 44;
                else unitprice = 45;


                double bill = kwh * unitprice;
                label3.setText("Approx. Monthly Bill: PKR " + String.format("%.2f", bill) + "Units:" + String.format("%.2f", kwh) + "KWH");
            } catch (Exception ex) {
                label3.setText("Invalid Input");
            }
        } else if (e.getSource() == button2) {
            dispose();

            main.showApplianceSelection();
        }
    }
}
