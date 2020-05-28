package org.derivco.data;

import org.derivco.entity.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestProviderTest {

    @Test
    void getDroneRequests() {
        validateRequestFile(5937);
        validateRequestFile(6043);
    }

    private void validateRequestFile(long id) {
        RequestProvider requestProvider = new RequestProvider();
        List<Request> droneRequests = requestProvider.getDroneRequests(id);
        Assertions.assertNotNull(droneRequests);
        for (Request req : droneRequests) {
            Assertions.assertNotNull(req);
            Assertions.assertNotNull(req.getDroneId());
            Assertions.assertNotNull(req.getLatitude());
            Assertions.assertNotNull(req.getLongitude());
            Assertions.assertNotNull(req.getTime());
        }
    }
}