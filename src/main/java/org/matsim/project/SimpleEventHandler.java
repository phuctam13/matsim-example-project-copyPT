package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.population.Person;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SimpleEventHandler implements PersonDepartureEventHandler, PersonArrivalEventHandler/*, LinkEnterEventHandler*/ {

    Map<Id<Person>, Double> departureTimeByPersonMap= new HashMap<>();

    Id linkId6 = Id.createLinkId(6);
    int[] agentsPerHour = new int[23];
    double time =0;

    


    @Override
    public void handleEvent(PersonDepartureEvent event) {
        System.out.println("Departure event; time" + event.getTime() +" --- linkId:" + event.getLinkId() + "personId:" + event.getPersonId() );
       //departureTimeByPersonMap.put(event.getPersonId(), event.getTime());
    }

    @Override
    public void handleEvent(PersonArrivalEvent event) {
        System.out.println("Arrival event; time" + event.getTime()+" --- linkId:" + event.getLinkId() + "personId:" + event.getPersonId() );
        //System.out.println("Travel time:" + (event.getTime() -  departureTimeByPersonMap.get(event.getPersonId())));

    }

/*
    @Override
    public void handleEvent(LinkEnterEvent event) {
            if (event.getLinkId().equals(linkId6)) {
                time = event.getTime()/3600;
                agentsPerHour[(int) Math.floor(time)] += 1;
            }
        System.out.println(Arrays.toString(agentsPerHour));
    }

 */
}
