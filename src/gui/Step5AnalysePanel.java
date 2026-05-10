package gui;

import model.SessionData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class Step5AnalysePanel extends JPanel {

    private final SessionData sessionData;
    private final Runnable onBack;

    public Step5AnalysePanel(SessionData sessionData, Runnable onBack) {
        this.sessionData = sessionData;
        this.onBack = onBack;
        buildUI();
    }

    private void buildUI() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 60, 30, 60));

        JLabel title = new JLabel("Step 5: Analyse");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        JLabel subtitle = new JLabel("Dimension scores, radar chart, and gap analysis.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(87, 96, 106));

        JPanel titleBlock = new JPanel();
        titleBlock.setBackground(Color.WHITE);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(4));
        titleBlock.add(subtitle);
        add(titleBlock, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(20, 0, 0, 0));

        if (sessionData.getScenario() != null) {
            List<model.Dimension> dimensions = sessionData.getScenario().getDimensions();
            content.add(buildScoresSection(dimensions));
            content.add(Box.createVerticalStrut(20));
            content.add(buildRadarSection(dimensions));
            content.add(Box.createVerticalStrut(20));
            content.add(buildGapSection(dimensions));
        }

        JScrollPane scroll = new JScrollPane(content);
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
        backBtn.setPreferredSize(new java.awt.Dimension(110, 38));
        backBtn.addActionListener(e -> onBack.run());
        btnPanel.add(backBtn, BorderLayout.WEST);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private JPanel buildScoresSection(List<model.Dimension> dimensions) {
        JPanel section = new JPanel();
        section.setBackground(Color.WHITE);
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(208, 215, 222), 1, true),
                        "5a. Dimension Scores", TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("SansSerif", Font.BOLD, 13)),
                new EmptyBorder(8, 12, 12, 12)));
        section.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 999));

        for (model.Dimension dim : dimensions) {
            double score = dim.calculateScore();
            int percent = (int) ((score - 1.0) / 4.0 * 100);

            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.setBackground(Color.WHITE);
            row.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 36));

            JLabel nameLabel = new JLabel(dim.getName());
            nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
            nameLabel.setPreferredSize(new java.awt.Dimension(200, 28));

            JProgressBar bar = new JProgressBar(0, 100);
            bar.setValue(percent);
            bar.setStringPainted(false);
            bar.setBackground(new Color(235, 237, 240));
            bar.setForeground(scoreColor(score));

            JLabel scoreLabel = new JLabel(String.format("%.2f / 5.00", score));
            scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
            scoreLabel.setForeground(scoreColor(score));
            scoreLabel.setPreferredSize(new java.awt.Dimension(90, 28));

            row.add(nameLabel, BorderLayout.WEST);
            row.add(bar, BorderLayout.CENTER);
            row.add(scoreLabel, BorderLayout.EAST);

            section.add(row);
            section.add(Box.createVerticalStrut(8));
        }

        return section;
    }

    private JPanel buildRadarSection(List<model.Dimension> dimensions) {
        JPanel section = new JPanel();
        section.setBackground(Color.WHITE);
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(208, 215, 222), 1, true),
                        "5b. Radar Chart (Bonus)", TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("SansSerif", Font.BOLD, 13)),
                new EmptyBorder(8, 12, 12, 12)));
        section.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 380));

        RadarChartPanel radar = new RadarChartPanel(dimensions);
        radar.setAlignmentX(Component.CENTER_ALIGNMENT);
        section.add(radar);
        return section;
    }

    private JPanel buildGapSection(List<model.Dimension> dimensions) {
        JPanel section = new JPanel();
        section.setBackground(Color.WHITE);
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(208, 215, 222), 1, true),
                        "5c. Gap Analysis", TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("SansSerif", Font.BOLD, 13)),
                new EmptyBorder(8, 12, 12, 12)));
        section.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 999));

        model.Dimension worst = dimensions.get(0);
        for (model.Dimension dim : dimensions) {
            if (dim.calculateScore() < worst.calculateScore()) {
                worst = dim;
            }
        }

        double score = worst.calculateScore();
        double gap = 5.0 - score;
        String level;
        Color levelColor;

        if (score >= 4.5) {
            level = "Excellent";
            levelColor = new Color(46, 160, 67);
        } else if (score >= 3.5) {
            level = "Good";
            levelColor = new Color(0, 120, 200);
        } else if (score >= 2.5) {
            level = "Needs Improvement";
            levelColor = new Color(220, 140, 0);
        } else {
            level = "Poor";
            levelColor = new Color(200, 50, 50);
        }

        addGapRow(section, "Dimension:", worst.getName());
        addGapRow(section, "Score:", String.format("%.2f / 5.00", score));
        addGapRow(section, "Gap:", String.format("%.2f", gap));

        JPanel levelRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        levelRow.setBackground(Color.WHITE);
        JLabel levelKey = new JLabel("Quality Level: ");
        levelKey.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JLabel levelVal = new JLabel(level);
        levelVal.setFont(new Font("SansSerif", Font.BOLD, 13));
        levelVal.setForeground(levelColor);
        levelRow.add(levelKey);
        levelRow.add(levelVal);
        section.add(levelRow);
        section.add(Box.createVerticalStrut(10));

        JLabel note = new JLabel("⚠ This dimension has the lowest score and requires the most improvement.");
        note.setFont(new Font("SansSerif", Font.ITALIC, 13));
        note.setForeground(new Color(150, 80, 0));
        note.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.add(note);

        return section;
    }

    private void addGapRow(JPanel parent, String key, String value) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setBackground(Color.WHITE);
        JLabel k = new JLabel(key + " ");
        k.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JLabel v = new JLabel(value);
        v.setFont(new Font("SansSerif", Font.BOLD, 13));
        row.add(k);
        row.add(v);
        parent.add(row);
        parent.add(Box.createVerticalStrut(6));
    }

    private Color scoreColor(double score) {
        if (score >= 4.0) return new Color(46, 160, 67);
        if (score >= 3.0) return new Color(220, 140, 0);
        return new Color(200, 50, 50);
    }

    static class RadarChartPanel extends JPanel {

        private final List<model.Dimension> dimensions;

        RadarChartPanel(List<model.Dimension> dimensions) {
            this.dimensions = dimensions;
            setBackground(Color.WHITE);
            setPreferredSize(new java.awt.Dimension(340, 320));
            setMaximumSize(new java.awt.Dimension(500, 340));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int cx = getWidth() / 2;
            int cy = getHeight() / 2;
            int maxR = Math.min(cx, cy) - 50;
            int n = dimensions.size();
            if (n < 3) return;

            double[] angles = new double[n];
            for (int i = 0; i < n; i++) {
                angles[i] = Math.PI / 2 + 2 * Math.PI * i / n;
            }

            // Grid rings
            for (int ring = 1; ring <= 5; ring++) {
                int r = maxR * ring / 5;
                g2.setColor(new Color(220, 225, 230));
                g2.setStroke(new BasicStroke(1f));
                int[] px = new int[n];
                int[] py = new int[n];
                for (int i = 0; i < n; i++) {
                    px[i] = cx + (int)(r * Math.cos(angles[i]));
                    py[i] = cy - (int)(r * Math.sin(angles[i]));
                }
                g2.drawPolygon(px, py, n);
                g2.setColor(new Color(150, 155, 160));
                g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
                g2.drawString(String.valueOf(ring), cx + r + 2, cy + 4);
            }

            // Axes
            g2.setColor(new Color(200, 205, 210));
            g2.setStroke(new BasicStroke(1f));
            for (int i = 0; i < n; i++) {
                int ex = cx + (int)(maxR * Math.cos(angles[i]));
                int ey = cy - (int)(maxR * Math.sin(angles[i]));
                g2.drawLine(cx, cy, ex, ey);
            }

            // Data polygon
            int[] dpx = new int[n];
            int[] dpy = new int[n];
            for (int i = 0; i < n; i++) {
                double score = dimensions.get(i).calculateScore();
                double r = maxR * (score - 1.0) / 4.0;
                dpx[i] = cx + (int)(r * Math.cos(angles[i]));
                dpy[i] = cy - (int)(r * Math.sin(angles[i]));
            }

            g2.setColor(new Color(9, 105, 218, 60));
            g2.fillPolygon(dpx, dpy, n);
            g2.setColor(new Color(9, 105, 218));
            g2.setStroke(new BasicStroke(2f));
            g2.drawPolygon(dpx, dpy, n);

            for (int i = 0; i < n; i++) {
                g2.setColor(new Color(9, 105, 218));
                g2.fillOval(dpx[i] - 4, dpy[i] - 4, 8, 8);
            }

            // Labels
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            for (int i = 0; i < n; i++) {
                double score = dimensions.get(i).calculateScore();
                String label = dimensions.get(i).getName();
                int lx = cx + (int)((maxR + 20) * Math.cos(angles[i]));
                int ly = cy - (int)((maxR + 20) * Math.sin(angles[i]));
                FontMetrics fm = g2.getFontMetrics();
                int sw = fm.stringWidth(label);
                if (lx < cx) lx -= sw;
                if (ly < cy) ly -= 4;
                else ly += 14;
                g2.setColor(new Color(36, 41, 47));
                g2.drawString(label, lx, ly);
                g2.setColor(new Color(9, 105, 218));
                g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
                g2.drawString(String.format("%.1f", score), lx, ly + 12);
                g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            }
        }
    }
}