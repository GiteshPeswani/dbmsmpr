import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class RailwayBookingSystem extends JFrame implements ActionListener {

    // Swing components
    JLabel lTrain, lSeats, lName, lAge, lPrice;
    JComboBox<String> cbTrain;
    JTextField tfSeats, tfName, tfAge;
    JButton btnCalculate, btnGenerateReceipt;
    
    // Train pricing (hardcoded here, but ideally fetched from AWS DynamoDB)
    Map<String, Integer> trainPrices;

    // Variables to store user details
    String userName;
    int userAge, userSeats, totalPrice;

    public RailwayBookingSystem() {
        // Set the window title
        setTitle("Railway Ticket Booking System with Receipt");

        // Initialize pricing details for trains
        trainPrices = new HashMap<>();
        trainPrices.put("Shatabdi", 1200);
        trainPrices.put("Rajdhani", 1500);
        trainPrices.put("Duronto", 1000);
        trainPrices.put("Garib Rath", 800);

        // Initialize GUI components
        lTrain = new JLabel("Select Train:");
        cbTrain = new JComboBox<>(new String[]{"Shatabdi", "Rajdhani", "Duronto", "Garib Rath"});
        
        lName = new JLabel("Enter your name:");
        tfName = new JTextField(10);
        
        lAge = new JLabel("Enter your age:");
        tfAge = new JTextField(10);
        
        lSeats = new JLabel("Enter number of seats:");
        tfSeats = new JTextField(10);
        
        btnCalculate = new JButton("Calculate Price");
        lPrice = new JLabel("Total Price: ");
        
        btnGenerateReceipt = new JButton("Generate Receipt");
        btnGenerateReceipt.setEnabled(false);  // Disabled until price is calculated

        // Add action listeners to the buttons
        btnCalculate.addActionListener(this);
        btnGenerateReceipt.addActionListener(this);

        // Set layout for the window
        setLayout(new GridLayout(7, 2));

        // Add components to the frame
        add(lTrain);
        add(cbTrain);
        add(lName);
        add(tfName);
        add(lAge);
        add(tfAge);
        add(lSeats);
        add(tfSeats);
        add(btnCalculate);
        add(lPrice);
        add(btnGenerateReceipt);

        // Set the window size
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCalculate) {
            // When the calculate button is clicked
            try {
                // Get selected train
                String selectedTrain = cbTrain.getSelectedItem().toString();
                
                // Get user inputs
                userName = tfName.getText();
                userAge = Integer.parseInt(tfAge.getText());
                userSeats = Integer.parseInt(tfSeats.getText());

                // Check if fields are filled
                if (userName.isEmpty() || tfAge.getText().isEmpty() || tfSeats.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all the fields.");
                    return;
                }

                // Get price per seat for the selected train
                int pricePerSeat = trainPrices.get(selectedTrain);

                // Calculate total price
                totalPrice = userSeats * pricePerSeat;

                // Display total price
                lPrice.setText("Total Price: ₹" + totalPrice);
                
                // Enable receipt generation button
                btnGenerateReceipt.setEnabled(true);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid data.");
            }
        }

        if (e.getSource() == btnGenerateReceipt) {
            // When the generate receipt button is clicked
            generateReceipt();
        }
    }

    // Method to generate and display the receipt
    public void generateReceipt() {
        JFrame receiptFrame = new JFrame("Booking Receipt");
        JTextArea receiptArea = new JTextArea(10, 30);
        receiptArea.setText(
                "Railway Ticket Booking Receipt\n" +
                "-------------------------------\n" +
                "Name: " + userName + "\n" +
                "Age: " + userAge + "\n" +
                "Train: " + cbTrain.getSelectedItem().toString() + "\n" +
                "Number of seats: " + userSeats + "\n" +
                "Total Price: ₹" + totalPrice + "\n" +
                "-------------------------------\n" +
                "Thank you for booking with us!"
        );
        receiptArea.setEditable(false);
        
        receiptFrame.add(new JScrollPane(receiptArea));
        receiptFrame.setSize(400, 300);
        receiptFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new RailwayBookingSystem();
    }
}
