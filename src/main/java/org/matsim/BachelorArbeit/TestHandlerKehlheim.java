package org.matsim.BachelorArbeit;

import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;

public class TestHandlerKehlheim implements LinkEnterEventHandler {
    @Override
    public void handleEvent(LinkEnterEvent event) {
        System.out.println("Arrival event; time" + event.getTime()+" --- linkId:" + event.getLinkId() + "personId:" + event.getVehicleId() );

    }
}
