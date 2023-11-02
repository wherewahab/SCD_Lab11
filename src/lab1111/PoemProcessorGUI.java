package lab1111;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PoemProcessorGUI {
    private List<String> poemLines = new ArrayList<>();
    private JTextArea textArea;
    private JComboBox<String> rootsDropdown;
    private JTextField poemTextField;

    public PoemProcessorGUI() {
        JFrame frame = new JFrame("Poem Processor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea(20, 40);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        rootsDropdown = new JComboBox<>();
        rootsDropdown.addItem("Select Root");
        rootsDropdown.addItem("ورد");
        rootsDropdown.addItem("فتح");
        rootsDropdown.addItem("عطي");
        rootsDropdown.addItem("سعد");
        rootsDropdown.addItem("قلب");
        rootsDropdown.addItem("حزن");
        inputPanel.add(rootsDropdown, BorderLayout.WEST);

        poemTextField = new JTextField(30);
        inputPanel.add(poemTextField, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AddPoemListener());
        inputPanel.add(addButton, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SavePoemListener());
        buttonPanel.add(saveButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeletePoemListener());
        buttonPanel.add(deleteButton);

        JButton tokenizeButton = new JButton("Tokenize");
        tokenizeButton.addActionListener(new TokenizeListener());
        buttonPanel.add(tokenizeButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private class AddPoemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String root = (String) rootsDropdown.getSelectedItem();
            String poemText = poemTextField.getText();
            String verse = root + " " + poemText;

            poemLines.add(verse);
            displayPoem();
        }
    }

    private class SavePoemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                try (PrintWriter writer = new PrintWriter(file)) {
                    for (String line : poemLines) {
                        writer.println(line);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error saving the poem: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class DeletePoemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            poemLines.clear();
            textArea.setText("");
        }
    }

    private class TokenizeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.setText(""); // Clear the text area

            for (String line : poemLines) {
                String[] tokens = line.split(" ");
                for (String token : tokens) {
                    textArea.append(token + "\n");
                }
                textArea.append("\n");
            }
        }
    }

    private void displayPoem() {
        textArea.setText(""); // Clear the text area

        for (String line : poemLines) {
            textArea.append(line + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PoemProcessorGUI());
    }
}
