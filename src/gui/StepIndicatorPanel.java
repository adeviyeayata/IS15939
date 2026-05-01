package gui;

import javax.swing.*;
import java.awt.*;

public class StepIndicatorPanel extends JPanel {

    private static final String[] STEP_NAMES = {"Profile", "Define", "Plan", "Collect", "Analyse"};
    private int currentStep;

    private static final Color COLOR_DONE    = new Color(46, 160, 67);
    private static final Color COLOR_ACTIVE  = new Color(9, 105, 218);
    private static final Color COLOR_PENDING = new Color(110, 118, 129);
    private static final Color COLOR_LINE    = new Color(208, 215, 222);
    private static final Color COLOR_BG      = new Color(248, 249, 250);

    public StepIndicatorPanel(int currentStep) {
        this.currentStep = currentStep;
        setBackground(COLOR_BG);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(208, 215, 222)));
        setPreferredSize(new Dimension(800, 80));
    }

    public void setCurrentStep(int step) {
        this.currentStep = step;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int circleR = 16;
        int circleD = circleR * 2;
        int spacing = width / 6;
        int[] cx = new int[5];
        for (int i = 0; i < 5; i++) cx[i] = spacing * (i + 1);
        int cy = height / 2 - 8;

        // Lines
        g2.setStroke(new BasicStroke(2f));
        for (int i = 0; i < 4; i++) {
            g2.setColor(i + 1 < currentStep - 1 ? COLOR_DONE : COLOR_LINE);
            g2.drawLine(cx[i] + circleR, cy, cx[i + 1] - circleR, cy);
        }

        // Circles
        for (int i = 0; i < 5; i++) {
            int step = i + 1;
            boolean done   = step < currentStep;
            boolean active = step == currentStep;
            Color circleColor = done ? COLOR_DONE : active ? COLOR_ACTIVE : COLOR_PENDING;

            if (done || active) {
                g2.setColor(circleColor);
                g2.fillOval(cx[i] - circleR, cy - circleR, circleD, circleD);
            } else {
                g2.setColor(COLOR_BG);
                g2.fillOval(cx[i] - circleR, cy - circleR, circleD, circleD);
                g2.setColor(circleColor);
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(cx[i] - circleR, cy - circleR, circleD, circleD);
            }

            g2.setColor(done || active ? Color.WHITE : circleColor);
            g2.setFont(new Font("SansSerif", Font.BOLD, done ? 13 : 12));
            String label = done ? "✓" : String.valueOf(step);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(label, cx[i] - fm.stringWidth(label) / 2, cy + fm.getAscent() / 2 - 1);

            g2.setColor(circleColor);
            g2.setFont(new Font("SansSerif", active ? Font.BOLD : Font.PLAIN, 11));
            FontMetrics fm2 = g2.getFontMetrics();
            g2.drawString(STEP_NAMES[i], cx[i] - fm2.stringWidth(STEP_NAMES[i]) / 2, cy + circleR + 16);
        }
    }
}