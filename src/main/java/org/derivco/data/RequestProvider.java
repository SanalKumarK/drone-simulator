package org.derivco.data;

import org.derivco.Simulator;
import org.derivco.entity.Request;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class RequestProvider {
    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("\"yyyy-MM-dd HH:mm:ss\"");

    public List<Request> getDroneRequests(long droneId) {
        return FileProvider.readInputFile(droneId+".csv").stream()
                .map(this::mapToRequest)
                .collect(Collectors.toList());
    }

    private Request mapToRequest(String line) {
        if(line == null || line.isBlank()) {
            return null;
        }
        Request request = null;
        try{
            String[] vals = line.split(",");
            if(vals.length == 4) {
                request = new Request();
                request.setDroneId(Long.parseLong(vals[0]));
                request.setLatitude(Double.parseDouble(vals[1].replaceAll("\"","")));
                request.setLongitude(Double.parseDouble(vals[2].replaceAll("\"","")));
                //String date = vals[3].replaceAll("\"","");
                //System.out.println("Date Parsing : " + date);
                request.setTime(DATE_FORMATTER.parse(vals[3]));
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return request;
    }
}