package org.derivco;

import org.derivco.entity.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DispatcherTest {

    private Dispatcher dispatcher = new Dispatcher();
    private long droneId = 7007;

    @Test
    void startDrone() {
        Drone drone = dispatcher.startDrone(droneId, System.currentTimeMillis());
        Assertions.assertNotNull(drone);
    }

    @Test
    void startDroneMultipeTimes() {
        Assertions.assertNotNull(dispatcher.startDrone(droneId, System.currentTimeMillis()));
        Assertions.assertNotNull(dispatcher.startDrone(droneId, System.currentTimeMillis()));
    }

    @Test
    void testDroneLifeCycle() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .parse("2011-03-22 07:47:50");
        dispatcher.startDrone(droneId, date.getTime());
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dispatcher.shutDownDrone(droneId);
    }

    /*@Test
    void sendRequest() {
        Drone drone = dispatcher.startDrone(droneId);
        Request request = createRequest(droneId, 51.476051, -0.100078,
                LocalDateTime.now().plusSeconds(1));
        dispatcher.sendRequest(drone, request);

        request = createRequest(droneId, 51.503071,-0.280303,
                LocalDateTime.now().plusSeconds(1));
        dispatcher.sendRequest(drone, request);

    }*/

    private Request createRequest(long droneId, double lat, double lon, Date time) {
        Request request = new Request();
        request.setDroneId(droneId);
        request.setLatitude(lat);
        request.setLongitude(lon);
        request.setTime(time);
        return request;
    }
}