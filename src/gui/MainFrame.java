package gui;

import model.SessionData;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final SessionData sessionData;
    private final StepIndicatorPanel stepIndicator;
    private int currentStep = 1;

    public MainFrame() {
        this.sessionData = new SessionData();
        this.cardLayout  = new CardLayout();
        this.cardPanel   = new JPanel(cardLayout);

        setTitle("ISO/IEC 15939 Measurement Process Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(820, 640));
        setPreferredSize(new Dimension(900, 680));
        setLocationRelativeTo(null);

        stepIndicator = new StepIndicatorPanel(currentStep);
        buildStepPanels();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(stepIndicator, BorderLayout.NORTH);
        getContentPane().add(cardPanel, BorderLayout.CENTER);

        cardLayout.show(cardPanel, "STEP1");
        pack();
    }

    private void buildStepPanels() {
        cardPanel.add(new Step1ProfilePanel(sessionData, () -> goTo(2)), "STEP1");
        cardPanel.add(new Step2DefinePanel(sessionData, () -> goTo(3), () -> goTo(1)), "STEP2");
        cardPanel.add(buildPlaceholder("Step 3: Plan — Coming in Week 2"),    "STEP3");
        cardPanel.add(buildPlaceholder("Step 4: Collect — Coming in Week 2"), "STEP4");
        cardPanel.add(buildPlaceholder("Step 5: Analyse — Coming in Week 3"), "STEP5");
    }

    private JPanel buildPlaceholder(String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(message, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        lbl.setForeground(new Color(130, 140, 150));
        panel.add(lbl, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        JButton back = new JButton("← Back");
        back.addActionListener(e -> goTo(currentStep - 1));
        btnPanel.add(back, BorderLayout.WEST);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    public void goTo(int step) {
        currentStep = step;
        stepIndicator.setCurrentStep(step);
        String[] cards = {"STEP1", "STEP2", "STEP3", "STEP4", "STEP5"};
        cardLayout.show(cardPanel, cards[step - 1]);
    }
}