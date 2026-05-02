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

    private Step3PlanPanel step3Panel;
    private Step4CollectPanel step4Panel;

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
    }

    public void goTo(int step) {
        currentStep = step;
        stepIndicator.setCurrentStep(step);

        if (step == 3) {
            if (step3Panel != null) {
                cardPanel.remove(step3Panel);
            }
            step3Panel = new Step3PlanPanel(sessionData, () -> goTo(4), () -> goTo(2));
            cardPanel.add(step3Panel, "STEP3");
            cardLayout.show(cardPanel, "STEP3");
            cardPanel.revalidate();
            cardPanel.repaint();
        } else if (step == 4) {
            if (step4Panel != null) {
                cardPanel.remove(step4Panel);
            }
            step4Panel = new Step4CollectPanel(sessionData, () -> goTo(5), () -> goTo(3));
            cardPanel.add(step4Panel, "STEP4");
            cardLayout.show(cardPanel, "STEP4");
            cardPanel.revalidate();
            cardPanel.repaint();
        } else if (step == 1) {
            cardLayout.show(cardPanel, "STEP1");
        } else if (step == 2) {
            cardLayout.show(cardPanel, "STEP2");
        }
    }
}