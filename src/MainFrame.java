import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private ClinicManager clinicManager;

    private JTextField idField;
    private JTextField nameField;
    private JTextField serviceField;
    private JCheckBox priorityCheck;
    private JComboBox<String> priorityLevelBox;
    private JTextArea outputArea;

    public MainFrame() {
        clinicManager = new ClinicManager();

        setTitle("Smart Queue System");
        setSize(500, 350); // 🔥 smaller window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(250, 248, 240));

        initUI();
    }

    private void initUI() {
        Color bgColor = new Color(250, 248, 240);
        Color beige = new Color(245, 245, 220);
        Color darkText = new Color(60, 60, 60);
        Font smallFont = new Font("Arial", Font.PLAIN, 11);
        Dimension btnSize = new Dimension(100, 25); // 🔥 smaller buttons

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 3, 3)); // 🔥 tighter spacing
        inputPanel.setBackground(bgColor);

        idField = new JTextField();
        nameField = new JTextField();
        serviceField = new JTextField();
        priorityCheck = new JCheckBox("Priority");
        priorityLevelBox = new JComboBox<>(new String[]{"1", "2", "3"});

        idField.setFont(smallFont);
        nameField.setFont(smallFont);
        serviceField.setFont(smallFont);
        priorityLevelBox.setFont(smallFont);
        priorityCheck.setFont(smallFont);
        priorityCheck.setBackground(bgColor);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(255, 255, 250));
        outputArea.setFont(new Font("Arial", Font.PLAIN, 11));

        JLabel idLabel = new JLabel("ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel serviceLabel = new JLabel("Service:");
        JLabel priorityStatusLabel = new JLabel("Priority:");
        JLabel priorityLevelLabel = new JLabel("Level:");

        idLabel.setFont(smallFont);
        nameLabel.setFont(smallFont);
        serviceLabel.setFont(smallFont);
        priorityStatusLabel.setFont(smallFont);
        priorityLevelLabel.setFont(smallFont);

        inputPanel.add(idLabel);
        inputPanel.add(idField);

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);

        inputPanel.add(serviceLabel);
        inputPanel.add(serviceField);

        inputPanel.add(priorityStatusLabel);
        inputPanel.add(priorityCheck);

        inputPanel.add(priorityLevelLabel);
        inputPanel.add(priorityLevelBox);

        JButton addButton = new JButton("Add");
        JButton serveButton = new JButton("Serve");
        JButton viewButton = new JButton("View");
        JButton historyButton = new JButton("History");
        JButton searchButton = new JButton("Search");

        JButton[] buttons = {addButton, serveButton, viewButton, historyButton, searchButton};

        for (JButton button : buttons) {
            button.setBackground(beige);
            button.setForeground(darkText);
            button.setFocusPainted(false);
            button.setFont(smallFont);
            button.setPreferredSize(btnSize);
        }

        JPanel topButtonPanel = new JPanel();
        topButtonPanel.setBackground(bgColor);
        topButtonPanel.add(addButton);
        topButtonPanel.add(serveButton);

        JPanel bottomButtonPanel = new JPanel();
        bottomButtonPanel.setBackground(bgColor);
        bottomButtonPanel.add(viewButton);
        bottomButtonPanel.add(historyButton);
        bottomButtonPanel.add(searchButton);

        JPanel northPanel = new JPanel(new BorderLayout(3, 3));
        northPanel.setBackground(bgColor);
        northPanel.add(inputPanel, BorderLayout.CENTER);
        northPanel.add(topButtonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(480, 120)); // 🔥 smaller output

        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addPatient());
        serveButton.addActionListener(e -> serveNextPatient());
        viewButton.addActionListener(e -> outputArea.setText(clinicManager.viewQueues()));
        historyButton.addActionListener(e -> outputArea.setText(clinicManager.viewHistory()));
        searchButton.addActionListener(e -> searchPatient());
    }

    private void addPatient() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            String service = serviceField.getText().trim();
            boolean isPriority = priorityCheck.isSelected();

            int priorityLevel = isPriority
                    ? Integer.parseInt((String) priorityLevelBox.getSelectedItem())
                    : Integer.MAX_VALUE;

            Patient patient = new Patient(id, name, service, isPriority, priorityLevel);
            clinicManager.addPatient(patient);

            outputArea.setText("Added!\n\n" + clinicManager.viewQueues());

            idField.setText("");
            nameField.setText("");
            serviceField.setText("");
            priorityCheck.setSelected(false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void serveNextPatient() {
        Patient served = clinicManager.serveNextPatient();

        if (served == null) {
            outputArea.setText("No patients.");
        } else {
            outputArea.setText("Serving:\n" + served + "\n\n" + clinicManager.viewQueues());
        }
    }

    private void searchPatient() {
        String input = JOptionPane.showInputDialog(this, "Enter ID:");

        if (input == null || input.isEmpty()) return;

        try {
            int id = Integer.parseInt(input);
            Patient patient = clinicManager.searchPatient(id);

            outputArea.setText(patient == null ? "Not found." : patient.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }
}