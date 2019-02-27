package banayaki.HotelLifeEmulator.gui.main;

import banayaki.HotelLifeEmulator.gui.helpers.ThreadsController;
import banayaki.HotelLifeEmulator.logic.entityes.HotelEntity;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

@Service("mainWindow")
public class MainWindow extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);
    private ThreadsController threadsController;

    @Autowired(required = true)
    public void setThreadsController(ThreadsController threadsController) {
        this.threadsController = threadsController;
    }

    public MainWindow() {
        setupViewSettings();
        logger.info("Interface settings was successfully installed");
    }

    private void setupViewSettings() {
        setTitle("Hotel Life Emulator");
        setResizable(true);
//        setIconImage();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(new Dimension(300, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void setupComponents(MainPanel mainPanel) {
        setContentPane(mainPanel.rootPanel);
        pack();
        setVisible(true);
        logger.info("Pack and display success");

        threadsController.startEndlessSpawn();
    }

    public static void main(String[] args) {
        logger.info("Launch the application");
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("META-INF/spring/app-context.xml");
        ctx.refresh();

        EventQueue.invokeLater(() -> {
            MainWindow mainWindow = ctx.getBean("mainWindow", MainWindow.class);
            MainPanel mainPanel = ctx.getBean("mainPanel", MainPanel.class);
            mainWindow.setupComponents(mainPanel);
        });


        HotelEntity.getInstance();
    }

}
