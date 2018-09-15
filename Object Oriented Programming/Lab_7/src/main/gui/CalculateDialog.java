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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

public class CalculateDialog extends JDialog {
    private final String PATH_TO_RESOURCES = "gui/resources/languagePackage";


    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel drawPanel;
    private JLabel leftBorder;
    private JLabel rightBorder;
    private JTextField leftBorderField;
    private JTextField rightBorderField;
    private JTextField samplingStepField;
    private JLabel samplingStepLabel;
    private JComboBox integrateComboBox;

    private TableModel tableModel;
    private TabulatedFunctionImpl function;
    private Double result = null;

    public CalculateDialog(TableModel tableModel, TabulatedFunctionImpl function) {
        this.tableModel = tableModel;
        this.function = function;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        double sampling = Double.parseDouble(samplingStepField.getText());
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
        double x1 = (double) tableModel.getValueAt(0, 0);
        double x2 = (double) tableModel.getValueAt(count - 1, 0);
        double dx = (x2 - x1) / count;
        leftBorderField.setText(String.valueOf(x1));
        rightBorderField.setText(String.valueOf(x2));
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
        drawPanel.add(draw());
    }
}
