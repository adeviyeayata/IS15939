package gui;

import data.ScenarioRepository;
import model.Scenario;
import model.SessionData;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class Step2DefinePanel extends JPanel {

    private final SessionData sessionData;
    private final Runnable onNext;
    private final Runnable onBack;

    private ButtonGroup typeGroup;
    private JRadioButton rbProduct, rbProcess;
    private ButtonGroup modeGroup;
    private JRadioButton rbHealth, rbEducation;
    private ButtonGroup scenarioGroup;
    private JPanel scenarioPanel;

    public Step2DefinePanel(SessionData sessionData, Runnable onNext, Runnable onBack) {
        this.sessionData = sessionData;
        this.onNext = onNext;
        this.onBack = onBack;
        buildUI();
    }

    private void buildUI() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 60, 30, 60));

        JLabel title = new JLabel("Step 2: Define Quality Dimensions");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        JLabel subtitle = new JLabel("Select quality type, mode, and scenario.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(87, 96, 106));

        JPanel titleBlock = new JPanel();
        titleBlock.setBackground(Color.WHITE);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(4));
        titleBlock.add(subtitle);
        add(titleBlock, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setBackground(Color.WHITE);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(20, 0, 0, 0));
        center.add(buildTypeSection());
        center.add(Box.createVerticalStrut(20));
        center.add(buildModeSection());
        center.add(Box.createVerticalStrut(20));
        center.add(buildScenarioSection());

        JScrollPane scroll = new JScrollPane(center);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(new EmptyBorder(16, 0, 0, 0));

        JButton backBtn = new JButton("← Back");
        backBtn.setBackground(new Color(108, 117, 125));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setPreferredSize(new Dimension(110, 38));
        backBtn.addActionListener(e -> onBack.run());

        JButton nextBtn = new JButton("Next →");
        nextBtn.setBackground(new Color(9, 105, 218));
        nextBtn.setForeground(Color.WHITE);
        nextBtn.setFocusPainted(false);
        nextBtn.setBorderPainted(false);
        nextBtn.setPreferredSize(new Dimension(110, 38));
        nextBtn.addActionListener(e -> handleNext());

        btnPanel.add(backBtn, BorderLayout.WEST);
        btnPanel.add(nextBtn, BorderLayout.EAST);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private JPanel buildTypeSection() {
        JPanel section = createSection("2a. Quality Type");
        typeGroup = new ButtonGroup();
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        row.setBackground(Color.WHITE);
        rbProduct = new JRadioButton("Product Quality");
        rbProduct.setBackground(Color.WHITE);
        rbProcess = new JRadioButton("Process Quality");
        rbProcess.setBackground(Color.WHITE);
        typeGroup.add(rbProduct);
        typeGroup.add(rbProcess);
        rbProduct.addActionListener(e -> refreshScenarios());
        rbProcess.addActionListener(e -> refreshScenarios());
        row.add(rbProduct);
        row.add(rbProcess);
        section.add(row);
        return section;
    }

    private JPanel buildModeSection() {
        JPanel section = createSection("2b. Mode");
        modeGroup = new ButtonGroup();
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        row.setBackground(Color.WHITE);
        rbHealth    = new JRadioButton("Health");
        rbHealth.setBackground(Color.WHITE);
        rbEducation = new JRadioButton("Education");
        rbEducation.setBackground(Color.WHITE);
        modeGroup.add(rbHealth);
        modeGroup.add(rbEducation);
        rbHealth.addActionListener(e -> refreshScenarios());
        rbEducation.addActionListener(e -> refreshScenarios());
        row.add(rbHealth);
        row.add(rbEducation);
        section.add(row);
        return section;
    }

    private JPanel buildScenarioSection() {
        JPanel section = createSection("2c. Scenario");
        scenarioGroup = new ButtonGroup();
        scenarioPanel = new JPanel();
        scenarioPanel.setBackground(Color.WHITE);
        scenarioPanel.setLayout(new BoxLayout(scenarioPanel, BoxLayout.Y_AXIS));
        JLabel hint = new JLabel("Please select a Quality Type and Mode first.");
        hint.setForeground(Color.GRAY);
        scenarioPanel.add(hint);
        section.add(scenarioPanel);
        return section;
    }

    private void refreshScenarios() {
        String type = getSelected(typeGroup);
        String mode = getSelected(modeGroup);
        scenarioPanel.removeAll();
        scenarioGroup = new ButtonGroup();

        if (type == null || mode == null) {
            JLabel hint = new JLabel("Please select a Quality Type and Mode first.");
            hint.setForeground(Color.GRAY);
            scenarioPanel.add(hint);
        } else {
            List<Scenario> list = ScenarioRepository.getScenarios(mode, type);
            if (list.isEmpty()) {
                scenarioPanel.add(new JLabel("No scenarios for this combination."));
            } else {
                for (Scenario sc : list) {
                    JRadioButton rb = new JRadioButton(sc.getName());
                    rb.setBackground(Color.WHITE);
                    rb.putClientProperty("scenarioId", sc.getId());
                    scenarioGroup.add(rb);
                    scenarioPanel.add(rb);
                    scenarioPanel.add(Box.createVerticalStrut(6));
                }
            }
        }
        scenarioPanel.revalidate();
        scenarioPanel.repaint();
    }

    private String getSelected(ButtonGroup group) {
        for (java.util.Enumeration<AbstractButton> e = group.getElements(); e.hasMoreElements();) {
            AbstractButton b = e.nextElement();
            if (b.isSelected()) return b.getText();
        }
        return null;
    }

    private String getSelectedScenarioId() {
        for (java.util.Enumeration<AbstractButton> e = scenarioGroup.getElements(); e.hasMoreElements();) {
            AbstractButton b = e.nextElement();
            if (b.isSelected()) return (String) b.getClientProperty("scenarioId");
        }
        return null;
    }

    private void handleNext() {
        if (getSelected(typeGroup) == null) {
            JOptionPane.showMessageDialog(this, "Please select a quality type to continue.", "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (getSelected(modeGroup) == null) {
            JOptionPane.showMessageDialog(this, "Please select a mode to continue.", "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (getSelectedScenarioId() == null) {
            JOptionPane.showMessageDialog(this, "Please select a scenario to continue.", "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        sessionData.setQualityType(getSelected(typeGroup));
        sessionData.setMode(getSelected(modeGroup));
        sessionData.setScenario(ScenarioRepository.getById(getSelectedScenarioId()));
        onNext.run();
    }

    private JPanel createSection(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(208, 215, 222), 1, true),
                        title, TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("SansSerif", Font.BOLD, 13)),
                new EmptyBorder(8, 12, 12, 12)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        return panel;
    }
}