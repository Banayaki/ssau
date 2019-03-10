package banayaki.HotelLifeEmulator.gui.main;

import banayaki.HotelLifeEmulator.gui.helpers.ThreadsController;
import banayaki.HotelLifeEmulator.logic.entityes.HotelEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

@Service("mainWindow")
public class MainWindow extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);
    private ThreadsController threadsController;

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        logger.info("Launch the application");
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("META-INF/spring/app-context.xml");
        ctx.refresh();

//        EventQueue.invokeLater(() -> {
            MainPanel mainPanel = ctx.getBean("mainPanel", MainPanel.class);
            MainWindow mainWindow = ctx.getBean("mainWindow", MainWindow.class);
            mainWindow.setupComponents(mainPanel);
            logger.info("Finish configurations");
//        });
    }

    public MainWindow() {
        setupViewSettings();
        logger.info("Interface settings was successfully installed");
    }

    private void setupViewSettings() {
        setTitle("Hotel Life Emulator");
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(new Dimension(300, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Autowired
    public void setThreadsController(ThreadsController threadsController) {
        this.threadsController = threadsController;
    }

    private void setupComponents(MainPanel mainPanel) {
        setContentPane(mainPanel.rootPanel);
        pack();
        setVisible(true);
        logger.info("Pack and display success");

        HotelEntity.getInstance();
        threadsController.startEndlessSpawn();
    }
}
