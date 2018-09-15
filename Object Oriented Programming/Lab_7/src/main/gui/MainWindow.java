package gui;

import functions.exceptions.InappropriateFunctionPointException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@SuppressWarnings({"BoundFieldAssignment", "Duplicates"})
public class MainWindow extends JFrame {
    private final String PATH_TO_RESOURCES = "gui/resources/languagePackage";
    Icon errorIcon = new ImageIcon("./src/main/gui/resources/sad_cat.png");
    JRadioButtonMenuItem russianButton;
    JRadioButtonMenuItem englishButton;

    private JPanel mainPanel;
    private JButton deletePointButton;
    private JScrollPane tableScrollPane;
    private JTextField xValueField;
    private JTextField yValueField;
    private JTable pointsViewTable;
    private JButton addPointButton;
    private JLabel xPointLabel;
    private JLabel yPointLabel;
    private JButton calculateButton;
    private JTextField resultsField;
    private JLabel resultsLabel;

    private JMenuBar menuBar;

    private JMenu fileMenu;
    private JMenuItem createMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem exitMenuItem;

    private JMenu tabulateMenu;
    private JMenuItem tabulateMenuItem;

    private JMenu optionsMenu;
    private JMenuItem changeThemeMenuItem;
    private JMenuItem changeLangMenuItem;

    private JMenu helpMenu;
    private JMenuItem checkForUpdatesItem;
    private JMenuItem aboutMenuItem;

    private TabulatedFunctionHandler functionHandler;
    private Locale russianLocale;

    public MainWindow() {

        russianLocale = new Locale.Builder().setLanguage("ru").setRegion("RU").build();

        pointsViewTable.registerKeyboardAction(e -> onDelete(), KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        mainPanel.registerKeyboardAction(e -> onAdd(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        setMinimumSize(new Dimension(500, 550));
        setTitle("PriseTheFunctionAPP");
        setIconImage((new ImageIcon("./src/main/gui/resources/icon.png")).getImage());
        setContentPane(mainPanel);
        setResizable(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setupButtons();
        setupFields();
        setupTable();
        menuInit();
        setJMenuBar(menuBar);
        updateWindow();
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }

    private void menuInit() {
        menuBar = new JMenuBar();
        fileMenuInit(menuBar);
        tabulateMenuInit(menuBar);
        optionsMenuInit(menuBar);
        helpMenuInit(menuBar);
    }

    private void optionsMenuInit(JMenuBar parent) {
        optionsMenu = new JMenu();
        optionsMenu.setMnemonic('o');

        changeThemeMenuItem = new JMenuItem();
        changeThemeMenuItem.addActionListener(actionEvent -> new ChangeThemeFrame(this));

        changeLangMenuItem = new JMenu();

        ButtonGroup langs = new ButtonGroup();
        russianButton = new JRadioButtonMenuItem(setUpText("menu.options.changelang.russian"), false);
        russianButton.addActionListener(actionEvent -> {
            Locale.setDefault(russianLocale);
            ResourceBundle.clearCache();
            updateWindow();
        });
        englishButton = new JRadioButtonMenuItem(setUpText("menu.options.changelang.english"), true);
        englishButton.addActionListener(actionEvent -> {
            Locale.setDefault(Locale.US);
            ResourceBundle.clearCache();
            updateWindow();
        });
        langs.add(russianButton);
        langs.add(englishButton);
        changeLangMenuItem.add(russianButton);
        changeLangMenuItem.add(englishButton);

        optionsMenu.add(changeThemeMenuItem);
        optionsMenu.add(changeLangMenuItem);
        parent.add(optionsMenu);
    }

    private void helpMenuInit(JMenuBar parent) {
        helpMenu = new JMenu();
        helpMenu.setMnemonic('h');

        checkForUpdatesItem = new JMenuItem();
        checkForUpdatesItem.setEnabled(false);
        checkForUpdatesItem.addActionListener(actionEvent -> {
            //TODO
        });

        aboutMenuItem = new JMenuItem();
        aboutMenuItem.addActionListener(actionEvent -> {
            try {
                Desktop.getDesktop().open(new File("./aboutpage/index.html"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        helpMenu.add(checkForUpdatesItem);
        helpMenu.add(aboutMenuItem);
        parent.add(helpMenu);
    }

    private void tabulateMenuInit(JMenuBar parent) {
        tabulateMenu = new JMenu();
        tabulateMenu.setMnemonic('t');

        tabulateMenuItem = new JMenuItem();
        tabulateMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl T"));
        tabulateMenuItem.addActionListener(actionEvent -> {
            if (pointsViewTable.getRowCount() == 0) {
                String text = ResourceBundle.getBundle(PATH_TO_RESOURCES).getString("empty.table");
                JOptionPane.showMessageDialog(this, text, "Warning", JOptionPane.WARNING_MESSAGE, errorIcon);
            } else {
                // Сначала Y, потом Х. Неожиданно. Не правда ли?
                double left = (double) pointsViewTable.getModel().getValueAt(0, 0);
                double right = (double) pointsViewTable.getModel().getValueAt(
                        pointsViewTable.getModel().getRowCount() - 1, 0
                );
                FunctionParamsDialog dialog = new FunctionParamsDialog(left, right);
                Object[] params = dialog.showDialog();
                if ((int) params[0] == 1) {
                    try {
                        functionHandler.tabulateFunction((double) params[1], (double) params[2], (int) params[3]);
                    } catch (InappropriateFunctionPointException e) {
                        e.printStackTrace();
                    }
                }
                updateTable();
            }
        });

        tabulateMenu.add(tabulateMenuItem);
        parent.add(tabulateMenu);
    }

    private void fileMenuInit(JMenuBar parent) {
        fileMenu = new JMenu();
        fileMenu.setMnemonic('f');

        createMenuItem = new JMenuItem();
        createMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        createMenuItem.addActionListener(actionEvent -> {
            CreateCustomFunction function = new CreateCustomFunction();
            functionHandler = function.showDialog();
            if (functionHandler != null) {
                updateTable();
            }
        });

        openMenuItem = new JMenuItem();
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        openMenuItem.addActionListener(actionEvent -> {
            saveCurrentCondition();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./saves"));
            fileChooser.showOpenDialog(this);
            fileChooser.setMultiSelectionEnabled(false);
            File file = fileChooser.getSelectedFile();
            if (file != null) {
                try {
                    functionHandler = new TabulatedFunctionHandler(file.getName());
                    updateTable();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        saveMenuItem = new JMenuItem();
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        saveMenuItem.addActionListener(actionEvent -> {
            saveCurrentCondition();
        });

        saveAsMenuItem = new JMenuItem();
        saveAsMenuItem.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./saves"));
            fileChooser.showSaveDialog(this);
            try {
                if (fileChooser.getSelectedFile() != null) {
                    functionHandler.saveFunctionAs(fileChooser.getSelectedFile().getName());
                }
            } catch (IOException ex) {
                String msg = "Can't save current function";
                JOptionPane.showMessageDialog(this, ex.getMessage() + msg);
            }
        });

        exitMenuItem = new JMenuItem();
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
        exitMenuItem.addActionListener(actionEvent -> onExit());

        fileMenu.add(createMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(exitMenuItem);
        parent.add(fileMenu);
    }

    private void setupFields() {
        xValueField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkForNoneDigits(e, xValueField);
            }
        });

        yValueField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkForNoneDigits(e, yValueField);
            }
        });

        resultsField.setColumns(10);
    }

    private void onExit() {
        JFrame frame = new JFrame();

        JPanel picture = new JPanel();
        JLabel imageLabel = new JLabel(new ImageIcon("./src/main/gui/resources/exit.jpg"));
        picture.add(imageLabel);

        frame.add(picture);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

        String msg = setUpText("save.or.notsave");

        int answer = JOptionPane.showConfirmDialog(this, msg,
                "Before you're leaving", JOptionPane.YES_NO_CANCEL_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            saveCurrentCondition();
            this.dispose();
            frame.dispose();
            System.exit(1);
        } else if (answer == JOptionPane.NO_OPTION) {
            this.dispose();
            frame.dispose();
            System.exit(1);
        } else {
            frame.dispose();
        }
    }

    private void onAdd() {
        if (xValueField.getText().isEmpty() || yValueField.getText().isEmpty()) {
            //Nothing
            return;
        }
        double x = Double.parseDouble(xValueField.getText());
        double y = Double.parseDouble(yValueField.getText());

        xValueField.setText("");
        yValueField.setText("");

        FunctionTableModel tableModel = (FunctionTableModel) pointsViewTable.getModel();
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; ++i) {
            if ((double) tableModel.getValueAt(i, 0) > x) {
                tableModel.insertRow(i, new Object[]{x, y});
                return;
            }
        }
        tableModel.addRow(new Object[]{x, y});
    }

    private void onDelete() {
        int[] indexes = pointsViewTable.getSelectedRows();
        if (indexes.length == 0) {
            return;
        }
        for (int i = indexes.length - 1; i >= 0; --i) {
            ((DefaultTableModel) pointsViewTable.getModel()).removeRow(indexes[i]);
        }
    }

    private void onCalc() {
//        String msg = ResourceBundle.getBundle(PATH_TO_RESOURCES).getString("enter.step");
        if (pointsViewTable.getModel().getRowCount() > 1) {
            Double result = new CalculateDialog(pointsViewTable.getModel(), functionHandler.getFunction()).showDialog();
            if (result != null) {
                resultsField.setText(String.valueOf(result));
            }
        }
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

    private void setupButtons() {
        addPointButton.addActionListener(e -> onAdd());
        deletePointButton.addActionListener(e -> onDelete());
        calculateButton.addActionListener(e -> onCalc());
    }

    private void setupTable() {
        FunctionTableModel table = new FunctionTableModel(this);
        pointsViewTable.setModel(table);
    }

    private void updateTable() {
        FunctionTableModel model = (FunctionTableModel) pointsViewTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        int count = functionHandler.getPointsCount();
        for (int i = 0; i < count; ++i) {
            double x = functionHandler.getPointX(i);
            double y = functionHandler.getPointY(i);
            model.addRow(new Object[]{x, y});
        }
    }

    private boolean isEmptyTable() {
        return pointsViewTable.getRowCount() == 0;
    }

    private void saveCurrentCondition() {
        if (!isEmptyTable()) {
            try {
                if (functionHandler.isFilenameAssigned()) {
                    functionHandler.saveFunction();
                } else {
                    String msg = "Enter a filename";
                    String answer = JOptionPane.showInputDialog(this,
                            msg, "Warning!", JOptionPane.WARNING_MESSAGE);
                    functionHandler.saveFunctionAs(answer);
                }
            } catch (IOException ex) {
                JOptionPane.showInputDialog(this, ex.getMessage(), "Warning!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private String setUpText(String key) {
        return ResourceBundle.getBundle(PATH_TO_RESOURCES).getString(key);
    }

    private void updateWindow() {
        fileMenu.setText(setUpText("menu.file"));
        createMenuItem.setText(setUpText("menu.file.new"));
        openMenuItem.setText(setUpText("menu.file.open"));
        saveMenuItem.setText(setUpText("menu.file.save"));
        saveAsMenuItem.setText(setUpText("menu.file.saveas"));
        exitMenuItem.setText(setUpText("menu.file.exit"));

        tabulateMenuItem.setText(setUpText("menu.tabulate.changeparams"));
        tabulateMenu.setText(setUpText("menu.tabulate"));

        optionsMenu.setText(setUpText("menu.options"));
        changeLangMenuItem.setText(setUpText("menu.options.changelang"));
        changeThemeMenuItem.setText(setUpText("menu.options.changetheme"));

        helpMenu.setText(setUpText("menu.help"));
        aboutMenuItem.setText(setUpText("menu.help.about"));
        checkForUpdatesItem.setText(setUpText("menu.help.checkupdate"));

        russianButton.setText(setUpText("menu.options.changelang.russian"));
        englishButton.setText(setUpText("menu.options.changelang.english"));

        xPointLabel.setText(setUpText("new.point.x"));
        yPointLabel.setText(setUpText("new.point.y"));
        addPointButton.setText(setUpText("add.point.btn"));
        deletePointButton.setText(setUpText("delete.point.btn"));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
