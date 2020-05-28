package org.derivco.data;

import org.derivco.Simulator;
import org.derivco.entity.TubeStation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum TubeStationProvider {

    TUBE_STATION_PROVIDER;

    private List<TubeStation> tubeStations = null;

    private List<TubeStation> getTubeStationsList() {
        if (tubeStations == null) {
            return FileProvider.readInputFile(Simulator.getSystemProperty("tube_file"))
                    .stream()
                    .map(this::mapToStation)
                    .collect(Collectors.toList());
        }
        return tubeStations;
    }

    private TubeStation mapToStation(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }
        TubeStation station = null;
        try {
            String[] vals = line.split(",");
            if (vals.length >= 3) {
                station = new TubeStation(vals[0], Double.parseDouble(vals[1]),
                        Double.parseDouble(vals[2]));
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return station;
    }

    public Optional<TubeStation> getTubeStationInRange(double latitude, double longitude, double range) {
        return getTubeStationsList().stream()
                .filter(tubeStation -> {
                    double distance = getDistance(tubeStation.latitude, latitude,
                            tubeStation.longitude, longitude);
                    return distance <= range;
                }).findFirst();
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
     */
    private double getDistance(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }
}