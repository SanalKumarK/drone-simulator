package org.derivco;

import org.derivco.entity.Request;
import org.derivco.entity.TubeStation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataProviderTest {

   /* DataProvider dataProvider = new DataProvider();

    @Test
    void getTubeStationsList() {
        List<TubeStation> tubeStationsList = dataProvider.getTubeStationsList();
        Assertions.assertNotNull(tubeStationsList);
        Assertions.assertFalse(tubeStationsList.isEmpty());
    }

    @Test
    void getDroneRequests() {
        verifyDroneRequests(5937);
        verifyDroneRequests(6043);
    }

    private void verifyDroneRequests(long id) {
        List<Request> droneRequests = dataProvider.getDroneRequests(id);
        Assertions.assertNotNull(droneRequests);
        Assertions.assertFalse(droneRequests.isEmpty());
    }

    @Test
    void isTubeStationExist() {
        double lat = 51.522254, lon = -0.018009;
        Assertions.assertTrue(dataProvider.isTubeStationExist(lat, lon));
    }*/
}