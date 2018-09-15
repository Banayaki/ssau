package gui;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Класс позволяющий переключить тему на какую-нибудь другую стоковую.
 *
 * @author banayaki
 * @see java.awt.Frame
 */
public class ChangeThemeFrame extends JFrame {

    /** Панель с кнопками переключения тем, ничего лишнего нам не надо */
    private JPanel buttonPanel;

    /**
     * Конструктор создающий окно с выбором темы
     *
     * @param frame - родительское окно
     */
    public ChangeThemeFrame(JFrame frame) {
        buttonPanel = new JPanel();

        /*Получаем информацию о доступных темах и создаем кнопки для переключения*/
        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : infos) {
            makeButton(info.getName(), info.getClassName(), frame);
        }

        /*Если окно теряет фокус, то оно закрывается, а зачем ему работать?)*/
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                dispose();
            }
        });
        add(buttonPanel);
        pack();
        setResizable(false);
        setTitle("Themes");
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Метод создающий кнопки, а именно кнопки переключающие темы
     *
     * @param name - название кнопки
     * @param plafName - название темы
     * @param frame - родительское окно (нужно ведь сменить его тему)
     */
    private void makeButton(String name, final String plafName, JFrame frame) {
        JButton button = new JButton(name);
        buttonPanel.add(button);

        button.addActionListener(e -> {
            try {
                UIManager.setLookAndFeel(plafName);
                SwingUtilities.updateComponentTreeUI(frame);
                SwingUtilities.updateComponentTreeUI(ChangeThemeFrame.this);
                pack();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
