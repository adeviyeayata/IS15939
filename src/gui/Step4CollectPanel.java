package gui;

import model.SessionData;
import model.Metric;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Step4CollectPanel extends JPanel {

    private final SessionData sessionData;
    private final Runnable onNext;
    private final Runnable onBack;

    public Step4CollectPanel(SessionData sessionData, Runnable onNext, Runnable onBack) {
        this.sessionData = sessionData;
        this.onNext = onNext;
        this.onBack = onBack;
        buildUI();
    }

    private void buildUI() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 60, 30, 60));

        JLabel title = new JLabel("Step 4: Collect Data");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        JLabel subtitle = new JLabel("Raw values and calculated scores (1–5) for each metric.");
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
            for (model.Dimension dim : sessionData.getScenario().getDimensions()) {
                content.add(buildDimensionBlock(dim));
                content.add(Box.createVerticalStrut(16));
            }
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
        backBtn.setPreferredSize(new Dimension(110, 38));
        backBtn.addActionListener(e -> onBack.run());

        JButton nextBtn = new JButton("Next →");
        nextBtn.setBackground(new Color(9, 105, 218));
        nextBtn.setForeground(Color.WHITE);
        nextBtn.setFocusPainted(false);
        nextBtn.setBorderPainted(false);
        nextBtn.setPreferredSize(new Dimension(110, 38));
        nextBtn.addActionListener(e -> onNext.run());

        btnPanel.add(backBtn, BorderLayout.WEST);
        btnPanel.add(nextBtn, BorderLayout.EAST);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private JPanel buildDimensionBlock(model.Dimension dim) {
        JPanel block = new JPanel();
        block.setBackground(Color.WHITE);
        block.setLayout(new BoxLayout(block, BoxLayout.Y_AXIS));
        block.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(208, 215, 222), 1, true),
                new EmptyBorder(12, 16, 12, 16)
        ));
        block.setMaximumSize(new Dimension(Integer.MAX_VALUE, 999));

        JLabel dimLabel = new JLabel(dim.getDisplayName());
        dimLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        dimLabel.setForeground(new Color(9, 105, 218));
        dimLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        block.add(dimLabel);
        block.add(Box.createVerticalStrut(10));

        String[] columns = {"Metric", "Direction", "Range", "Value", "Score (1–5)", "Coeff / Unit"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        for (Metric m : dim.getMetrics()) {
            double score = m.calculateScore();
            tableModel.addRow(new Object[]{
                    m.getName(),
                    m.getDirectionLabel(),
                    m.getRangeLabel(),
                    String.valueOf(m.getRawValue()),
                    String.valueOf(score),
                    m.getCoefficient() + " / " + m.getUnit()
            });
        }

        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(246, 248, 250));
        table.setGridColor(new Color(208, 215, 222));
        table.setShowGrid(true);
        table.setAlignmentX(Component.LEFT_ALIGNMENT);

        table.getColumnModel().getColumn(4).setCellRenderer(new ScoreCellRenderer());

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                table.getRowHeight() * (dim.getMetrics().size() + 1) + 30));
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(208, 215, 222)));
        block.add(tableScroll);

        return block;
    }

    static class ScoreCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int col) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            setHorizontalAlignment(SwingConstants.CENTER);
            try {
                double score = Double.parseDouble(value.toString());
                if (score >= 4.0) {
                    setBackground(new Color(212, 245, 220));
                } else if (score >= 3.0) {
                    setBackground(new Color(255, 243, 205));
                } else {
                    setBackground(new Color(255, 220, 220));
                }
            } catch (Exception e) {
                setBackground(Color.WHITE);
            }
            return this;
        }
    }
}