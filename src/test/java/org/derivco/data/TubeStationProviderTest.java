package org.derivco.data;

import org.derivco.entity.TubeStation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class TubeStationProviderTest {
    @Test
    void getTubeStationInRange() {
        double lat = 51.522254, lon = -0.018009;
        Optional<TubeStation> tubeStationInRange = TubeStationProvider.TUBE_STATION_PROVIDER
                .getTubeStationInRange(lat, lon, 100);
        Assertions.assertTrue(tubeStationInRange.isPresent());
        Assertions.assertNotNull(tubeStationInRange.get().station);
    }

    @Test
    void getTubeStationNotInRange() {
        double lat = 51.523254, lon = -0.018109;
        Optional<TubeStation> tubeStationInRange = TubeStationProvider.TUBE_STATION_PROVIDER
                .getTubeStationInRange(lat, lon, 0);
        Assertions.assertFalse(tubeStationInRange.isPresent());
    }
}