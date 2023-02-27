package org.matsim.BachelorArbeit;

import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;

import java.util.HashMap;
import java.util.LinkedList;

public class TestEventHandlerMap implements LinkEnterEventHandler {


    public HashMap<String, LinkedList<String>> cars = new HashMap<>();

    int counter=0;

    @Override
    public void handleEvent(LinkEnterEvent event) {
        //test.add(2);
        if(!cars.containsKey(event.getVehicleId().toString())){

            LinkedList<String> newRouteFirst = new LinkedList<>();
            newRouteFirst.add(event.getLinkId().toString());
            cars.put(event.getVehicleId().toString(), newRouteFirst);

        }
        else{

            LinkedList<String> newRoute = cars.get(event.getVehicleId().toString());
            newRoute.add(event.getLinkId().toString());
            cars.put(event.getVehicleId().toString(), newRoute);

        }
    }

    public void returnText(){
        System.out.println(cars);
    }
}
