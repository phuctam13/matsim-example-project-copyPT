package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.network.io.NetworkWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NetworkModifier {

    public static void main(String[] args) {
        Path inputNetwork = Paths.get(args[0]);
        Path outputNetwork = Paths.get(args[1]);

        Network network = NetworkUtils.createNetwork();
        new MatsimNetworkReader(network).readFile(inputNetwork.toString());

        network.getLinks().get(Id.createLinkId("6")).setCapacity(120);

        new NetworkWriter(network).write(outputNetwork.toString());

    }
}
