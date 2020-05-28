package org.derivco;

import org.derivco.data.RequestBuffer;
import org.derivco.data.RequestProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Dispatcher {
    private Map<Long, Drone> droneMap = new HashMap<>();

    public Drone startDrone(long id, long time) {
        if (droneMap.get(id) == null) {
            RequestBuffer requestBuffer = new RequestBuffer();
            Drone drone = new Drone(id, requestBuffer, time);
            drone.setName("Drone-"+id);
            droneMap.put(id, drone);
            drone.start();
            simulateDrone(id, drone);
            return drone;
        }
        return droneMap.get(id);
    }

    public boolean shutDownDrone(long id) {
        Drone drone;
        if ((drone = droneMap.get(id)) != null) {
            try {
                drone.shutDown();
                droneMap.remove(id);
                drone.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private void simulateDrone(long id, Drone drone) {
        CompletableFuture.supplyAsync(() -> {
            new RequestProvider().getDroneRequests(id).forEach(request -> {
                try {
                    drone.getRequestBuffer().addRequest(request);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            return CompletableFuture.completedFuture(null);
        });
    }
}