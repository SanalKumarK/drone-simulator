package org.derivco;

import org.derivco.data.FileProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class Simulator {

    private static Properties systemProperties;
    private Dispatcher dispatcher;
    public static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static Date START_TIME;

    public void startSimulator() {
        String[] drones = getSystemProperty("drones").split(",");

        Date shutTime = null;
        try {
            START_TIME = DATE_FORMATTER.parse(getSystemProperty("start_time"));
            shutTime = DATE_FORMATTER.parse(getSystemProperty("shut_time"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        initiateDispatcher();
        startDrones(drones);
        scheduleShutDown(drones, shutTime);
    }

    private void initiateDispatcher() {
        dispatcher = new Dispatcher();
    }

    private void scheduleShutDown(String[] ids, Date shutTime) {

        TimerTask shutDownTask = new TimerTask() {
            @Override
            public void run() {
                for (String id : ids) {
                    dispatcher.shutDownDrone(Long.valueOf(id));
                }
            }
        };

        new Timer().schedule(shutDownTask, Duration.between(START_TIME.toInstant(),
                shutTime.toInstant()).getSeconds() * 1000);
    }

    public void startDrones(String[] ids) {
        for (String id : ids) {
            dispatcher.startDrone(Long.valueOf(id), START_TIME.getTime());
        }
    }

    public static String getSystemProperty(String prop) {
        if (systemProperties == null) {
            systemProperties = FileProvider.loadProperties();
        }
        return String.valueOf(systemProperties.get(prop));
    }
}
