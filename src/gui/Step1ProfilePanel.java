package gui;

import model.SessionData;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Step1ProfilePanel extends JPanel {

    private final SessionData sessionData;
    private final Runnable onNext;

    private JTextField usernameField;
    private JTextField schoolField;
    private JTextField sessionNameField;

    public Step1ProfilePanel(SessionData sessionData, Runnable onNext) {
        this.sessionData = sessionData;
        this.onNext = onNext;
        buildUI();
    }

    private void buildUI() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(40, 60, 40, 60));

        JLabel title = new JLabel("Step 1: Profile");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        JLabel subtitle = new JLabel("Enter your user and session information to get started.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(87, 96, 106));

        JPanel titleBlock = new JPanel();
        titleBlock.setBackground(Color.WHITE);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(6));
        titleBlock.add(subtitle);
        add(titleBlock, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(30, 0, 0, 0));

        usernameField    = addFormField(form, "Username",     "Enter your name");
        schoolField      = addFormField(form, "School",       "Enter your school or university");
        sessionNameField = addFormField(form, "Session Name", "e.g., Spring 2025 — Group 3");

        add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.WHITE);
        JButton nextBtn = new JButton("Next →");
        nextBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        nextBtn.setBackground(new Color(9, 105, 218));
        nextBtn.setForeground(Color.WHITE);
        nextBtn.setFocusPainted(false);
        nextBtn.setBorderPainted(false);
        nextBtn.setPreferredSize(new Dimension(120, 38));
        nextBtn.addActionListener(e -> handleNext());
        btnPanel.add(nextBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private JTextField addFormField(JPanel parent, String labelText, String placeholder) {
        JPanel group = new JPanel();
        group.setBackground(Color.WHITE);
        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
        group.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField field = new JTextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(208, 215, 222), 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });

        group.add(label);
        group.add(Box.createVerticalStrut(6));
        group.add(field);
        parent.add(group);
        parent.add(Box.createVerticalStrut(16));
        return field;
    }

    private String getValue(JTextField field, String placeholder) {
        String val = field.getText().trim();
        return val.equals(placeholder) ? "" : val;
    }

    private void handleNext() {
        String username    = getValue(usernameField,    "Enter your name");
        String school      = getValue(schoolField,      "Enter your school or university");
        String sessionName = getValue(sessionNameField, "e.g., Spring 2025 — Group 3");

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your username to continue.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (school.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your school name to continue.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (sessionName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a session name to continue.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        sessionData.setUsername(username);
        sessionData.setSchool(school);
        sessionData.setSessionName(sessionName);
        onNext.run();
    }
}