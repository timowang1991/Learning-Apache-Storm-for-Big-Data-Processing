package mfa.trident;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentTopology;

public class FilterTridentTopology {
    public static void main(String[] args) {
        LocalDRPC drpc = new LocalDRPC();
        TridentTopology topology = new TridentTopology();
        topology.newDRPCStream("names", drpc)
            .filter(new FilterTrident());

        Config conf = new Config();
        conf.setDebug(true);

        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("trident-topology", conf, topology.build());

        for (String word : new String[] { "Hello", "Utkarsha", "Prashant" }) {
            System.out.println("Result for " + word + ": " + drpc.execute("names", word));
        }

        localCluster.shutdown();
        drpc.shutdown();
    }
}
