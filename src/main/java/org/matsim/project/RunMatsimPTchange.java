package org.matsim.project;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.pt.transitSchedule.api.*;
import org.matsim.pt.utils.TransitScheduleValidator;
import org.matsim.vehicles.Vehicle;
import org.matsim.vehicles.VehicleType;
import org.matsim.vehicles.VehiclesFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import java.util.logging.Logger;


public class RunMatsimPTchange {
    private final static Logger LOG = Logger.getLogger(RunMatsim.class);

    public static void main( String[] args ){


        Config config = ConfigUtils.loadConfig( "scenarios/pt-tutorial/0.config.xml" );
        config.controler().setOverwriteFileSetting( OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists );

        Scenario scenario = ScenarioUtils.loadScenario( config );

        TransitSchedule transitSchedule =  scenario.getTransitSchedule();
        TransitLine blueLine = transitSchedule.getTransitLines().get(Id.create("Blue Line", TransitLine.class));

        TransitRoute route1to3 = blueLine.getRoutes().get(Id.create("1to3", TransitRoute.class));

        TransitScheduleFactory tsf = transitSchedule.getFactory();

        Departure departure =tsf.createDeparture(Id.create("newDep", Departure.class), (8*60. + 45)*60.);

        route1to3.addDeparture(departure);

        TransitScheduleValidator.ValidationResult result = TransitScheduleValidator.validateAll(transitSchedule, scenario.getNetwork());
        for(String error: result.getErrors()){
            LOG.warn(error);
        }
        for(String warnings: result.getErrors()){
            LOG.warn(warnings);
        }
        for(TransitScheduleValidator.ValidationResult.ValidationIssue issue: result.getIssues()){
            LOG.warn(issue.getMessage());
        }

        VehiclesFactory vf = scenario.getVehicles().getFactory();
        Id<Vehicle> vehId = Id.createVehicleId("tr_3");

        VehicleType smallTrain = scenario.getTransitVehicles().getVehicleTypes().get(Id.create("1", VehicleType.class));

        Vehicle vehicle = vf.createVehicle(vehId, smallTrain);

        scenario.getTransitVehicles().addVehicle(vehicle);

        departure.setVehicleId(vehId);

        TransitScheduleWriter tsw = new TransitScheduleWriter((transitSchedule));
        tsw.writeFile("scenarios/pt-tutorial/ptchange.xml");

        Controler controler = new Controler( scenario );
        controler.run();
    }

}
/*
        TransitLine transitLine =  scenario.getTransitSchedule().getTransitLines().get("Blue Line");
        TransitRoute transitRoute = transitLine.getRoutes().get("1to3");
        List<Departure> depature = new ArrayList<>();
        //System.out.println(scenario.getTransitSchedule().getTransitLines().get("Blue Line").toString());

        for(Departure dep1 : transitRoute.getDepartures()){

        }

        for(Map.Entry element : transitRoute.entrySet())
        */