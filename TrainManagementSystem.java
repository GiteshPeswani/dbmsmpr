import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TrainManagementSystem extends JFrame implements ActionListener {

    // Components for train management system
    JLabel labelTitle, labelTrainName, labelTrainNumber, labelTrainType, labelStatus;
    JTextField textTrainName, textTrainNumber;
    JComboBox<String> comboTrainType, comboStatus;
    JButton buttonAddTrain, buttonViewTrains, buttonDeleteTrain, buttonExit;
    JTextArea textAreaDisplay;

    // List to store train information
    ArrayList<String> trainList = new ArrayList<>();

    // Constructor to setup the GUI components
    public TrainManagementSystem() {
        // Set the frame title
        setTitle("Train Management System");

        // Set the layout for the frame
        setLayout(new GridLayout(8, 2, 10, 10));

        // Initialize components
        labelTitle = new JLabel("Train Management System", JLabel.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 18));
        labelTrainName = new JLabel("Train Name: ");
        labelTrainNumber = new JLabel("Train Number: ");
        labelTrainType = new JLabel("Train Type: ");
        labelStatus = new JLabel("Current Status: ");
        textTrainName = new JTextField(20);
        textTrainNumber = new JTextField(10);

        // ComboBox for train type (Local or Express)
        String[] trainTypes = {"Local", "Express"};
        comboTrainType = new JComboBox<>(trainTypes);

        // ComboBox for train status (On Time or Late)
        String[] statuses = {"On Time", "Late"};
        comboStatus = new JComboBox<>(statuses);

        // Buttons for actions
        buttonAddTrain = new JButton("Add Train");
        buttonViewTrains = new JButton("View Trains");
        buttonDeleteTrain = new JButton("Delete Train");
        buttonExit = new JButton("Exit");

        // Text area to display train information
        textAreaDisplay = new JTextArea(10, 40);
        textAreaDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaDisplay);

        // Add components to the frame
        add(labelTitle);
        add(new JLabel(""));  // Empty label for spacing
        add(labelTrainName);
        add(textTrainName);
        add(labelTrainNumber);
        add(textTrainNumber);
        add(labelTrainType);
        add(comboTrainType);
        add(labelStatus);
        add(comboStatus);
        add(buttonAddTrain);
        add(buttonViewTrains);
        add(buttonDeleteTrain);
        add(buttonExit);
        add(new JLabel("Train Information:"));
        add(scrollPane);

        // Set size and visibility of the frame
        setSize(600, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add action listeners to buttons
        buttonAddTrain.addActionListener(this);
        buttonViewTrains.addActionListener(this);
        buttonDeleteTrain.addActionListener(this);
        buttonExit.addActionListener(this);
    }

    // Handle button actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonAddTrain) {
            // Get input train details
            String trainName = textTrainName.getText();
            String trainNumber = textTrainNumber.getText();
            String trainType = comboTrainType.getSelectedItem().toString();
            String status = comboStatus.getSelectedItem().toString();

            // Validate input
            if (trainName.isEmpty() || trainNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in both fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add train to list
            trainList.add("Train Name: " + trainName + ", Train Number: " + trainNumber +
                    ", Type: " + trainType + ", Status: " + status);
            JOptionPane.showMessageDialog(this, "Train added successfully!");

            // Clear input fields
            textTrainName.setText("");
            textTrainNumber.setText("");
        } else if (e.getSource() == buttonViewTrains) {
            // Display all trains in the text area
            if (trainList.isEmpty()) {
                textAreaDisplay.setText("No trains available.");
            } else {
                StringBuilder trainInfo = new StringBuilder();
                for (String train : trainList) {
                    trainInfo.append(train).append("\n");
                }
                textAreaDisplay.setText(trainInfo.toString());
            }
        } else if (e.getSource() == buttonDeleteTrain) {
            // Get train number to delete
            String trainNumberToDelete = JOptionPane.showInputDialog("Enter Train Number to delete:");
            boolean found = false;

            // Search and delete train from list
            for (int i = 0; i < trainList.size(); i++) {
                if (trainList.get(i).contains("Train Number: " + trainNumberToDelete)) {
                    trainList.remove(i);
                    found = true;
                    JOptionPane.showMessageDialog(this, "Train deleted successfully!");
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Train not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == buttonExit) {
            // Exit the application
            System.exit(0);
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        new TrainManagementSystem();
    }
}
