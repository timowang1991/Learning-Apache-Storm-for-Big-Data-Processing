package mfa.trident;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.tuple.Fields;

public class AggregateTrident {
    public static void main(String[] args) {
        LocalDRPC drpc = new LocalDRPC();
        TridentTopology topology = new TridentTopology();
        topology.newDRPCStream("simple", drpc)
            .map(new MapTridentExample())
            .groupBy(new Fields("args"))
            .aggregate(new Count(), new Fields("count"));

        Config config = new Config();
        config.setDebug(true);

        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("trident-topology", config, topology.build());

        for (String word : new String[] { "Hello", "Hello", "welcome" }) {
            System.out.println("Result for " + word + ": " + drpc.execute("simple", word));
        }
        localCluster.shutdown();
        drpc.shutdown();
    }
}
