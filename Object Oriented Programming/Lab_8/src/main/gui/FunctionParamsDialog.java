package gui;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;


public class FunctionParamsDialog extends JDialog {

    private final String PATH_TO_RESOURCES = "gui/resources/languagePackage";
    private final int OK = 1;
    private final int CANCEL = -1;
    public int returnCode;
    Icon errorIcon = new ImageIcon("./src/main/gui/resources/sad_cat.png");
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField leftDomainField;
    private JTextField rightDomainField;
    private JSpinner pointCountSpinner;
    private JLabel leftDomainLabel;
    private JLabel rightDomainLabel;
    private JLabel pointsCountLabel;
    private JPanel okOrCancelPanel;
    private JPanel fieldPanel;

    private Object[] array;

    private double leftX;
    private double rightX;

    public FunctionParamsDialog(double leftX, double rightX) {
        this.rightX = rightX;
        this.leftX = leftX;

        setContentPane(contentPane);
        setIconImage((new ImageIcon("./src/main/gui/resources/icon.png")).getImage());
        setTitle("Function Parameters");
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);

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

        // provides input only numbers
        leftDomainField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkForNoneDigits(e, leftDomainField);
            }
        });

        // provides input only numbers
        rightDomainField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkForNoneDigits(e, rightDomainField);
            }
        });
    }

    public static void main(String[] args) {
        FunctionParamsDialog dialog = new FunctionParamsDialog(-1, 1);
        dialog.showDialog();
        System.exit(1);
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
        String leftDomainStr = leftDomainField.getText();
        String rightDomainStr = rightDomainField.getText();
        if (leftDomainStr.isEmpty() || Double.parseDouble(leftDomainStr) < leftX) {
            String errorMsg = setUpText("error.leftx");
            System.err.println(errorMsg);
            JOptionPane.showMessageDialog(this, errorMsg, "Attention", JOptionPane.ERROR_MESSAGE,
                    errorIcon);
            return;
        }
        if (rightDomainStr.isEmpty() || Double.parseDouble(rightDomainStr) > rightX) {
            String errorMsg = setUpText("error.rightx");
            System.err.println(errorMsg);
            JOptionPane.showMessageDialog(this, errorMsg, "Attention", JOptionPane.ERROR_MESSAGE,
                    errorIcon);
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

        int countOfPoints = (int) pointCountSpinner.getValue();
        returnCode = OK;
        array = new Object[4];
        array[0] = returnCode;
        array[1] = leftBorder;
        array[2] = rightBorder;
        array[3] = countOfPoints;
        // Destroy the JPanel
        dispose();
    }

    private void onCancel() {
        returnCode = CANCEL;
        // Destroy the JPanel
        array = new Object[1];
        array[0] = returnCode;
        dispose();
    }

    public Object[] showDialog() {
        this.pack();
        this.setVisible(true);
        return array;
    }

    private String setUpText(String key) {
        return ResourceBundle.getBundle(PATH_TO_RESOURCES).getString(key);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        pointCountSpinner = new JSpinner(new SpinnerNumberModel(10, 2, 1000, 1));
    }

}
