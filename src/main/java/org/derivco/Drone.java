package org.derivco;

import org.derivco.data.RequestBuffer;
import org.derivco.data.TubeStationProvider;
import org.derivco.entity.Report;
import org.derivco.entity.Request;
import org.derivco.entity.Traffic;
import org.derivco.entity.TubeStation;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class Drone extends Thread {
    private long droneId;
    private AtomicBoolean shutDown = new AtomicBoolean(false);
    private Date time;
    private RequestBuffer requestBuffer;
    private int MILLI = 1000;
    private int RANGE = 350;

    private TimerTask ticker = new TimerTask() {
        @Override
        public void run() {
            time.setTime(time.getTime()+MILLI);
        }
    };

    public Drone(long id, RequestBuffer requestBuffer, long time) {
        this.droneId = id;
        this.requestBuffer = requestBuffer;
        this.time = new Date(time);
        Timer droneTimer = new Timer();
        droneTimer.schedule(ticker, 0, 1000);
    }

    public RequestBuffer getRequestBuffer() {
        return requestBuffer;
    }

    @Override
    public void run() {
        while (!shutDown.get()) {
            try {
                Request request = requestBuffer.getRequest();
                processRequest(request.getLatitude(), request.getLongitude(), request.getTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void processRequest(double lat, double lon, Date destinationTime) {
        printMessage(String.format("Moving to coordinate(%f, %f)", lat, lon));

        //Flying time to reach the destination.
        //TODO check why null is coming in the request.
        if(destinationTime != null) {
            long duration = Duration.between(time.toInstant(), destinationTime.toInstant()).getSeconds();
            if(duration > 0) {
                fly(duration);
            } else {
                System.out.println("Sleeping time is lesser than time:"+time+" " + duration);
            }
        }

        //System.out.printf("Drone reached the coordinate(%d, %d), ", droneId, lat, lon);
        //Check if a tube stations exist at the given location.
        Optional<TubeStation> tubeStationInRange = TubeStationProvider.TUBE_STATION_PROVIDER
                .getTubeStationInRange(lat, lon, RANGE);
        if (tubeStationInRange.isPresent()) {
            String station = tubeStationInRange.get().station;
            printMessage(String.format("Found a Tube Station at %s.", station));
            printMessage(String.format("%s %s",station, generateTrafficReport(destinationTime).toString()));
        } else {
            printMessage("No Tube Station found in the range.");
        }
    }

    private Report generateTrafficReport(Date targetTime) {
        return new Report(droneId, targetTime, 50, Traffic.getRandomValue());
    }

    private void fly(long secs) {
        try {
            //System.out.printf("Target to reach in %d secs\n", secs);
            Thread.sleep(secs*1000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public void shutDown() {
        System.out.printf("Shutting down the drone : %d\n", droneId);
        shutDown.set(true);
    }

    private void printMessage(String message) {
        System.out.printf("Drone %d : %s\n",droneId, message);
    }
}
