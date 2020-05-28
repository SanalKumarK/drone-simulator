package org.derivco.data;

import org.derivco.Simulator;
import org.derivco.entity.Request;

import java.util.LinkedList;
import java.util.Queue;

public class RequestBuffer {
    private Queue<Request> requests = new LinkedList<>();;
    private int capacity = Integer.parseInt(Simulator.getSystemProperty("buffer_capacity"));

    public void addRequest(Request request) throws InterruptedException {
        synchronized (this) {
            while (requests.size() >= capacity) {
                wait();
            }
            System.out.println("Adding request " + request);
            requests.add(request);
            notify();
        }
    }

    public Request getRequest() throws InterruptedException {
        synchronized (this) {
            while (requests.size() == 0) {
                wait();
            }
            Request request = requests.poll();
            System.out.println("Removing request " + request);
            notify();
            return request;
        }
    }
}
