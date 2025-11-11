package connectsphere;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FriendUI extends JFrame {
    private JTable friendTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, updateButton, deleteButton, searchButton, refreshButton;

    public FriendUI() {
        setTitle("ConnectSphere Lite - Friends Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top panel - Search bar
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBackground(new Color(240, 240, 245));
        topPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));

        JLabel searchLabel = new JLabel("Search:");
        searchField = new JTextField(20);
        searchButton = new JButton("🔍 Search");
        refreshButton = new JButton("🔄 Refresh");

        searchButton.addActionListener(e -> searchFriends());
        refreshButton.addActionListener(e -> refreshTable());
        searchField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchFriends();
                }
            }
        });

        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(refreshButton);

        // Center panel - Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone", "City"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        friendTable = new JTable(tableModel);
        friendTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        friendTable.setRowHeight(25);
        friendTable.getTableHeader().setBackground(new Color(70, 130, 180));
        friendTable.getTableHeader().setForeground(Color.WHITE);
        friendTable.setGridColor(new Color(220, 220, 220));

        JScrollPane scrollPane = new JScrollPane(friendTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));

        // Bottom panel - Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(245, 245, 250));

        addButton = new JButton("➕ Add Friend");
        updateButton = new JButton("✏️  Update");
        deleteButton = new JButton("❌ Delete");
        JButton aboutButton = new JButton("ℹ️  About");

        addButton.addActionListener(e -> showAddFriendDialog());
        updateButton.addActionListener(e -> showUpdateFriendDialog());
        deleteButton.addActionListener(e -> deleteFriend());
        aboutButton.addActionListener(e -> showAboutDialog());

        bottomPanel.add(addButton);
        bottomPanel.add(updateButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(aboutButton);

        // Add components
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Load data on startup
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Friend> friends = FriendDAO.getAllFriends();
        for (Friend friend : friends) {
            tableModel.addRow(new Object[]{
                    friend.getId(),
                    friend.getName(),
                    friend.getEmail(),
                    friend.getPhone(),
                    friend.getCity()
            });
        }
    }

    private void searchFriends() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            refreshTable();
            return;
        }

        tableModel.setRowCount(0);
        List<Friend> friends = FriendDAO.searchFriends(searchTerm);
        for (Friend friend : friends) {
            tableModel.addRow(new Object[]{
                    friend.getId(),
                    friend.getName(),
                    friend.getEmail(),
                    friend.getPhone(),
                    friend.getCity()
            });
        }

        if (friends.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No friends found matching: " + searchTerm, "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showAddFriendDialog() {
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField cityField = new JTextField(20);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("City:"));
        panel.add(cityField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Friend", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String city = cityField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || city.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Friend friend = new Friend(name, email, phone, city);
            if (FriendDAO.addFriend(friend)) {
                JOptionPane.showMessageDialog(this, "Friend added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                searchField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error adding friend!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showUpdateFriendDialog() {
        int selectedRow = friendTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a friend to update!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Friend friend = FriendDAO.getFriendById(id);

        if (friend == null) {
            JOptionPane.showMessageDialog(this, "Friend not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField(friend.getName(), 20);
        JTextField emailField = new JTextField(friend.getEmail(), 20);
        JTextField phoneField = new JTextField(friend.getPhone(), 20);
        JTextField cityField = new JTextField(friend.getCity(), 20);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("City:"));
        panel.add(cityField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Update Friend", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String city = cityField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || city.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            friend.setName(name);
            friend.setEmail(email);
            friend.setPhone(phone);
            friend.setCity(city);

            if (FriendDAO.updateFriend(friend)) {
                JOptionPane.showMessageDialog(this, "Friend updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                searchField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error updating friend!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteFriend() {
        int selectedRow = friendTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a friend to delete!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + name + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (FriendDAO.deleteFriend(id)) {
                JOptionPane.showMessageDialog(this, "Friend deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                searchField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting friend!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showAboutDialog() {
        String aboutText = "ConnectSphere Lite v1.0\n\n" +
                "A modern, lightweight friend management application\n" +
                "Built with Java Swing & SQLite\n\n" +
                "Features:\n" +
                "✓ Add Friends\n" +
                "✓ View All Friends\n" +
                "✓ Update Friend Details\n" +
                "✓ Delete Friends\n" +
                "✓ Search Functionality\n\n" +
                "Developed for VS Code with JDK 17+";

        JTextArea textArea = new JTextArea(aboutText);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(15, 15, 15, 15));
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "About ConnectSphere Lite", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FriendUI());
    }
}
