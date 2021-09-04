package mfa.trident;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentTopology;

public class MapTridentTopology {
    public static void main(String[] args) {
        LocalDRPC drpc = new LocalDRPC();
        TridentTopology topology = new TridentTopology();
        topology.newDRPCStream("names", drpc)
            .map(new MapTridentExample());

        Config config = new Config();
        config.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("trident-topology", config, topology.build());

        for (String word : new String[] { "Prashant", "Utkarsha", "Prem" }) {
            System.out.println("Result for " + word + ": " + drpc.execute("names", word));
        }

        cluster.shutdown();
        drpc.shutdown();
    }
}
