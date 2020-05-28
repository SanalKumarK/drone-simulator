package org.derivco.entity;

import java.util.Date;

public class Report {
    public final long droneId;
    public final Date time;
    public final long speed;
    public final Traffic traffic;

    public Report(long droneId, Date time, long speed, Traffic traffic) {
        this.droneId = droneId;
        this.time = time;
        this.speed = speed;
        this.traffic = traffic;
    }

    @Override
    public String toString() {
        return "Traffic Report {" +
                "droneId=" + droneId +
                ", time=" + time +
                ", speed=" + speed +
                ", traffic=" + traffic +'\'' +
                '}';
    }
}
