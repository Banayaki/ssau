package gui;

import functions.Functions;
import functions.TabulatedFunctionImpl;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

public class CalculateDialog extends JDialog {
    private final String PATH_TO_RESOURCES = "gui/resources/languagePackage";
    Icon errorIcon = new ImageIcon("./src/main/gui/resources/sad_cat.png");

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel drawPanel;
    private JLabel leftBorderLabel;
    private JLabel rightBorderLabel;
    private JTextField leftBorderField;
    private JTextField rightBorderField;
    private JTextField samplingStepField;
    private JLabel samplingStepLabel;
    private JComboBox integrateComboBox;

    private TableModel tableModel;
    private TabulatedFunctionImpl function;
    private Double result = null;
    private double leftBorder;
    private double rightBorder;

    public CalculateDialog(TableModel tableModel, TabulatedFunctionImpl function) {
        this.tableModel = tableModel;
        this.function = function;
        drawPanel.add(draw());

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setMinimumSize(new Dimension(720, 620));
        setResizable(false);
        setIconImage((new ImageIcon("./src/main/gui/resources/icon.png")).getImage());

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)
                , JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        comboBoxInit();
        filesInit();

    }

    private void onOK() {
        double x1 = Double.parseDouble(leftBorderField.getText());
        double x2 = Double.parseDouble(rightBorderField.getText());
        if (x1 < leftBorder) {
            String text = ResourceBundle.getBundle(PATH_TO_RESOURCES).getString("error.leftx");
            JOptionPane.showMessageDialog(this, text, "Warning", JOptionPane.WARNING_MESSAGE, errorIcon);
            return;
        } else if (x2 > rightBorder) {
            String text = ResourceBundle.getBundle(PATH_TO_RESOURCES).getString("error.rightx");
            JOptionPane.showMessageDialog(this, text, "Warning", JOptionPane.WARNING_MESSAGE, errorIcon);
            return;
        }

        double sampling = Double.parseDouble(samplingStepField.getText());
        if (sampling < 1E-5) sampling = 1E-5;
        if (integrateComboBox.getSelectedIndex() == 0) {
            result = Functions.adaptiveIntegrate(function, x1, x2, sampling);
        } else if (integrateComboBox.getSelectedIndex() == 1) {
            result = Functions.trapezoidIntegrate(function, x1, x2, sampling);
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void comboBoxInit() {
        integrateComboBox.addItem(ResourceBundle.getBundle(PATH_TO_RESOURCES).getString("adaptive.integrate"));
        integrateComboBox.addItem(ResourceBundle.getBundle(PATH_TO_RESOURCES).getString("trapezoid.integrate"));
        integrateComboBox.setSelectedIndex(0);
    }

    private void filesInit() {
        int count = tableModel.getRowCount();
        leftBorder = (double) tableModel.getValueAt(0, 0);
        rightBorder = (double) tableModel.getValueAt(count - 1, 0);
        double dx = (rightBorder - leftBorder) / count;
        leftBorderField.setText(String.valueOf(leftBorder));
        rightBorderField.setText(String.valueOf(rightBorder));
        samplingStepField.setText(String.valueOf(dx));

        leftBorderField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkForNoneDigits(e, leftBorderField);
            }
        });

        rightBorderField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkForNoneDigits(e, rightBorderField);
            }
        });

        samplingStepField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkForNoneDigits(e, samplingStepField);
            }
        });
    }

    private void checkForNoneDigits(KeyEvent e, JTextField field) {
        char ch = e.getKeyChar();
        if (ch == '.' && field.getText().chars().filter(dots -> dots == '.').count() != 0 ||
                ch == '-' && !field.getText().isEmpty()) {
            e.consume();
        } else if (!Character.isDigit(ch) && ch != '.' && ch != '-') {
            e.consume();
        }
    }

    private ChartPanel draw() {
        XYSeries series = new XYSeries("function");
        int count = tableModel.getRowCount();

        for (int i = 0; i < count; ++i) {
            double xVal = (double) tableModel.getValueAt(i, 0);
            double yVal = (double) tableModel.getValueAt(i, 1);

            series.add(xVal, yVal);
        }

        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "function graph", "x", "y",
                xyDataset, PlotOrientation.VERTICAL, true, true, true);

        return new ChartPanel(chart);
    }

    public Double showDialog() {
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        return result;
    }

    private void createUIComponents() {
        drawPanel = new JPanel();
    }
}
