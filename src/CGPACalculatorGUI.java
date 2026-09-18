import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Professional GUI for CGPA Calculator
 * Anna University 2025 - With Grade Dropdown Selection
 */
public class CGPACalculatorGUI extends JFrame {
    private CGPACalculator calculator;
    private JTabbedPane tabbedPane;
    private JTable semI_Table, semII_Table;
    private JLabel cgpaLabel, sem1_gpaLabel, sem2_gpaLabel;
    private DefaultTableModel sem1Model, sem2Model;

    // Grade options
    private static final String[] GRADES = {"S", "A+", "A", "B+", "B", "C+", "C", "U"};

    public CGPACalculatorGUI() {
        calculator = new CGPACalculator();
        initializeUI();
        updateAllTables();
    }

    private void initializeUI() {
        setTitle("CGPA Calculator - Anna University 2025");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Tabbed Pane for semesters
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.addTab("Semester I", createSemesterPanel(1));
        tabbedPane.addTab("Semester II", createSemesterPanel(2));
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Create Header Panel with title and CGPA display
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 60, 114));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("📊 CGPA CALCULATOR");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        // CGPA Display Panel
        JPanel cgpaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 0));
        cgpaPanel.setBackground(new Color(30, 60, 114));

        // CGPA
        JLabel cgpaTextLabel = new JLabel("Your CGPA:");
        cgpaTextLabel.setFont(new Font("Arial", Font.BOLD, 14));
        cgpaTextLabel.setForeground(Color.WHITE);

        cgpaLabel = new JLabel("9.00");
        cgpaLabel.setFont(new Font("Arial", Font.BOLD, 28));
        cgpaLabel.setForeground(new Color(255, 215, 0));  // Gold color

        // Semester labels
        JLabel semLabel = new JLabel("Sem I GPA:");
        semLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        semLabel.setForeground(Color.WHITE);

        sem1_gpaLabel = new JLabel("9.00");
        sem1_gpaLabel.setFont(new Font("Arial", Font.BOLD, 12));
        sem1_gpaLabel.setForeground(new Color(144, 238, 144));

        JLabel sem2Label = new JLabel("Sem II GPA:");
        sem2Label.setFont(new Font("Arial", Font.PLAIN, 12));
        sem2Label.setForeground(Color.WHITE);

        sem2_gpaLabel = new JLabel("9.00");
        sem2_gpaLabel.setFont(new Font("Arial", Font.BOLD, 12));
        sem2_gpaLabel.setForeground(new Color(144, 238, 144));

        cgpaPanel.add(cgpaTextLabel);
        cgpaPanel.add(cgpaLabel);
        cgpaPanel.add(Box.createHorizontalStrut(30));
        cgpaPanel.add(semLabel);
        cgpaPanel.add(sem1_gpaLabel);
        cgpaPanel.add(Box.createHorizontalStrut(20));
        cgpaPanel.add(sem2Label);
        cgpaPanel.add(sem2_gpaLabel);

        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(cgpaPanel, BorderLayout.EAST);

        return panel;
    }

    /**
     * Create panel for each semester
     */
    private JPanel createSemesterPanel(int semester) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 240, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create table
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"S.No", "Code", "Subject Name", "Credits", "Grade", "Grade Point", "C×GP"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 4;  // Only Grade column is editable
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 11));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);

        // Set Grade column as ComboBox
        JComboBox<String> gradeCombo = new JComboBox<>(GRADES);
        table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(gradeCombo));

        // Add subjects to table
        java.util.List<Subject> subjects = (semester == 1) ? calculator.getSemesterI() : calculator.getSemesterII();
        int sNo = 1;
        for (Subject subject : subjects) {
            model.addRow(new Object[]{
                    sNo++,
                    subject.getSubjectCode(),
                    subject.getSubjectName(),
                    subject.getCredits(),
                    subject.getGrade(),
                    String.format("%.1f", subject.getGradePoint()),
                    String.format("%.1f", subject.getQualityPoints())
            });
        }

        // Store model reference
        if (semester == 1) {
            sem1Model = model;
            semI_Table = table;
        } else {
            sem2Model = model;
            semII_Table = table;
        }

        // Add listener for grade changes
        table.getModel().addTableModelListener(e -> {
            if (e.getColumn() == 4) {  // Grade column
                updateSubjectGrade(semester, e.getFirstRow(), (String) table.getValueAt(e.getFirstRow(), 4));
            }
        });

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Update subject grade when changed in table
     */
    private void updateSubjectGrade(int semester, int index, String newGrade) {
        calculator.updateGrade(semester, index, newGrade);
        updateAllTables();
    }

    /**
     * Update all table displays
     */
    private void updateAllTables() {
        // Update Semester I
        updateTableDisplay(1, sem1Model, semI_Table);
        // Update Semester II
        updateTableDisplay(2, sem2Model, semII_Table);
        // Update CGPA displays
        updateCGPADisplays();
    }

    /**
     * Update table data
     */
    private void updateTableDisplay(int semester, DefaultTableModel model, JTable table) {
        java.util.List<Subject> subjects = (semester == 1) ? calculator.getSemesterI() : calculator.getSemesterII();

        for (int i = 0; i < subjects.size(); i++) {
            Subject subject = subjects.get(i);
            model.setValueAt(subject.getGrade(), i, 4);
            model.setValueAt(String.format("%.1f", subject.getGradePoint()), i, 5);
            model.setValueAt(String.format("%.1f", subject.getQualityPoints()), i, 6);
        }
    }

    /**
     * Update CGPA display labels
     */
    private void updateCGPADisplays() {
        cgpaLabel.setText(String.format("%.2f", calculator.calculateCGPA()));
        sem1_gpaLabel.setText(String.format("%.2f", calculator.calculateSemesterGPA(1)));
        sem2_gpaLabel.setText(String.format("%.2f", calculator.calculateSemesterGPA(2)));
    }

    /**
     * Create Footer Panel with buttons
     */
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(new Color(240, 240, 245));

        // View Report Button
        JButton reportBtn = createStyledButton("📋 View Report");
        reportBtn.addActionListener(e -> calculator.printReport());

        // Export to Excel Button
        JButton exportBtn = createStyledButton("📥 Export to Excel");
        exportBtn.addActionListener(e -> exportToExcel());

        // Reset Button
        JButton resetBtn = createStyledButton("🔄 Reset All");
        resetBtn.addActionListener(e -> resetAllGrades());

        // Exit Button
        JButton exitBtn = createStyledButton("❌ Exit");
        exitBtn.addActionListener(e -> System.exit(0));

        panel.add(reportBtn);
        panel.add(exportBtn);
        panel.add(resetBtn);
        panel.add(exitBtn);

        return panel;
    }

    /**
     * Create styled button
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(150, 35));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(100, 160, 210));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }
        });

        return button;
    }

    /**
     * Export results to Excel (CSV format for simplicity)
     */
    private void exportToExcel() {
        try {
            String fileName = "CGPA_Report_" + System.currentTimeMillis() + ".csv";
            FileOutputStream fos = new FileOutputStream(fileName);
            StringBuilder csv = new StringBuilder();

            // Header
            csv.append("CGPA CALCULATOR - ANNA UNIVERSITY 2025\n");
            csv.append("Generated on,").append(new java.util.Date()).append("\n\n");

            // Semester I
            csv.append("SEMESTER I\n");
            csv.append("S.No,Code,Subject Name,Credits,Grade,Grade Point,C×GP\n");
            for (Subject s : calculator.getSemesterI()) {
                csv.append(String.format("%s,%s,%s,%d,%s,%.1f,%.1f\n",
                        "", s.getSubjectCode(), s.getSubjectName(), s.getCredits(),
                        s.getGrade(), s.getGradePoint(), s.getQualityPoints()));
            }
            csv.append("Total,,,").append(calculator.getTotalCredits(1)).append(",,")
                    .append(calculator.getTotalQualityPoints(1)).append("\n");
            csv.append("GPA,,,").append(calculator.calculateSemesterGPA(1)).append("\n\n");

            // Semester II
            csv.append("SEMESTER II\n");
            csv.append("S.No,Code,Subject Name,Credits,Grade,Grade Point,C×GP\n");
            for (Subject s : calculator.getSemesterII()) {
                csv.append(String.format("%s,%s,%s,%d,%s,%.1f,%.1f\n",
                        "", s.getSubjectCode(), s.getSubjectName(), s.getCredits(),
                        s.getGrade(), s.getGradePoint(), s.getQualityPoints()));
            }
            csv.append("Total,,,").append(calculator.getTotalCredits(2)).append(",,")
                    .append(calculator.getTotalQualityPoints(2)).append("\n");
            csv.append("GPA,,,").append(calculator.calculateSemesterGPA(2)).append("\n\n");

            // CGPA
            csv.append("OVERALL CGPA,").append(calculator.calculateCGPA());

            fos.write(csv.toString().getBytes());
            fos.close();

            JOptionPane.showMessageDialog(this, "✅ Exported successfully to: " + fileName, 
                    "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "❌ Export failed: " + ex.getMessage(),
                    "Export Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Reset all grades to default (S)
     */
    private void resetAllGrades() {
        int confirm = JOptionPane.showConfirmDialog(this, "Reset all grades to 'S'?",
                "Confirm Reset", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            for (Subject s : calculator.getAllSubjects()) {
                s.setGrade("S");
            }
            updateAllTables();
            JOptionPane.showMessageDialog(this, "✅ All grades reset to 'S'",
                    "Reset Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CGPACalculatorGUI());
    }
}