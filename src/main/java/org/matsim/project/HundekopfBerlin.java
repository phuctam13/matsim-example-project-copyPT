package org.matsim.project;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.network.io.NetworkWriter;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.geotools.MGC;
import org.matsim.core.utils.gis.ShapeFileReader;
import org.opengis.feature.simple.SimpleFeature;


import java.util.*;

public class HundekopfBerlin {
    public static void main (String[] args){
        String networkInputFile = "../../public-svn/matsim/scenarios/countries/de/berlin/berlin-v5.5-10pct/input/berlin-v5.5-network.xxml.gz";
        String networkOutputFile = "network-modified.xml.gz";

        String areaShapeFile = "../../shared-svn/studies/countries/de/open_berlin_scenario/berlin_hundekopf.shp";

        Collection<SimpleFeature> features = (new ShapeFileReader()).readFileAndInitialize(areaShapeFile);



        Map<String, Geometry> zoneGeometries = new HashMap<>();
        for(SimpleFeature feature : features){
            zoneGeometries.put((String) feature.getAttribute("SCHLUSSEL"), (Geometry)feature.getDefaultGeometry());

        }

        Geometry areaGeometry = zoneGeometries.get(("hundekopf"));

        Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        MatsimNetworkReader reader = new MatsimNetworkReader(scenario.getNetwork());
        reader.readFile(networkInputFile);

        for(Link link : scenario.getNetwork().getLinks().values()){
            Set<String> allowedModesBefore = link.getAllowedModes();
            Set<String> allowedModesAfter = new HashSet<>();

            Point linkCenterAsPoint = MGC.xy2Point(link.getCoord().getX(),link.getCoord().getY());

            for (String mode :allowedModesBefore){
                if(mode.equals(TransportMode.car)){
                    allowedModesAfter.add("carInternal");
                    if(!areaGeometry.contains(linkCenterAsPoint)){
                        allowedModesAfter.add(TransportMode.car);
                    } else{
                        allowedModesAfter.add(mode);
                    }
                }
            }
            link.setAllowedModes(allowedModesAfter);
        }

        NetworkWriter writer = new NetworkWriter((scenario.getNetwork()));
        writer.write(networkOutputFile);
    }
}
