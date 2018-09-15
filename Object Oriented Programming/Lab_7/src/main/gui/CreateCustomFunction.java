package gui;

import functions.ArrayTabulatedFunction;
import functions.CustomTabulatedFunction;
import functions.ExpressionHandler;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

public class CreateCustomFunction extends JDialog {

    private final String PATH_TO_RESOURCES = "gui/resources/languagePackage";

    Icon errorIcon = new ImageIcon("./src/main/gui/resources/sad_cat.png");

    TabulatedFunctionHandler function;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea functionTextArea;
    private JTextField filenameField;
    private JLabel functionLabel;
    private JLabel filenameLabel;
    private JPanel upperPanel;
    private JPanel bottomPanel;
    private JPanel centerPanel;
    private JTextField leftBorderField;
    private JTextField rightBorderField;
    private JSpinner pointsCountSpinner;
    private JLabel pointCountLabel;
    private JLabel rightBorderLabel;
    private JLabel leftBorderLabel;
    private JPanel buttonsPanel;

    public CreateCustomFunction() {
        setIconImage((new ImageIcon("./src/main/gui/resources/icon.png")).getImage());
        setTitle("Create Function");
        setMinimumSize(new Dimension(325, 250));
        setResizable(true);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);

        functionTextArea.setTabSize(0);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // provides input only numbers
        leftBorderField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkForNoneDigits(e, leftBorderField);
            }
        });

        // provides input only numbers
        rightBorderField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkForNoneDigits(e, rightBorderField);
            }
        });

        functionTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    onOK();
                }
            }
        });
    }

    public static void main(String[] args) {
        CreateCustomFunction dialog = new CreateCustomFunction();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
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

    @SuppressWarnings("Duplicates")
    private void onOK() {
        boolean nonCustom = functionTextArea.getText().isEmpty();
        Expression expression = null;
        if (!nonCustom) {
            try {
                expression = ExpressionHandler.buildExpression(functionTextArea.getText());
            } catch (UnknownFunctionOrVariableException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Warning!", JOptionPane.WARNING_MESSAGE, errorIcon);
                return;
            }
        }
        String filename = filenameField.getText();
        if (filename.isEmpty()) {
            String errorMsg = setUpText("error.filename");
            System.err.println(errorMsg);
            JOptionPane.showMessageDialog(this, errorMsg,
                    "Attention", JOptionPane.ERROR_MESSAGE, errorIcon);
            return;
        }

        String leftDomainStr = leftBorderField.getText();
        String rightDomainStr = rightBorderField.getText();
        if (leftDomainStr.isEmpty()) {
            String errorMsg = setUpText("error.leftx");
            System.err.println(errorMsg);
            JOptionPane.showMessageDialog(this, errorMsg,
                    "Attention", JOptionPane.ERROR_MESSAGE, errorIcon);
            return;
        }
        if (rightDomainStr.isEmpty()) {
            String errorMsg = setUpText("error.rightx");
            System.err.println(errorMsg);
            JOptionPane.showMessageDialog(this, errorMsg,
                    "Attention", JOptionPane.ERROR_MESSAGE, errorIcon);
            return;
        }

        double leftBorder = Double.parseDouble(leftDomainStr);
        double rightBorder = Double.parseDouble(rightDomainStr);
        if (rightBorder < leftBorder) {
            String msg = setUpText("error.order");
            JOptionPane.showMessageDialog(this, msg);
            double tmp = leftBorder;
            leftBorder = rightBorder;
            rightBorder = tmp;
        }

        int countOfPoints = (int) pointsCountSpinner.getValue();
        if (nonCustom) {
            function = new TabulatedFunctionHandler(filename,
                    new ArrayTabulatedFunction(leftBorder, rightBorder, countOfPoints));
        } else {
            function = new TabulatedFunctionHandler(filename,
                    new CustomTabulatedFunction(expression, leftBorder, rightBorder, countOfPoints));
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public TabulatedFunctionHandler showDialog() {
        this.pack();
        this.setVisible(true);
        return function;
    }

    private String setUpText(String key) {
        return ResourceBundle.getBundle(PATH_TO_RESOURCES).getString(key);
    }

    private void createUIComponents() {
        pointsCountSpinner = new JSpinner(new SpinnerNumberModel(10, 2, 1000, 1));
    }

}