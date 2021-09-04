package trident;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.tuple.Fields;

public class HelloTridentTopology {
    public static void main(String[] args) {
        LocalDRPC drpc = new LocalDRPC();
        TridentTopology topology = new TridentTopology();
        topology.newDRPCStream("names", drpc)
            .each(new Fields("args"), new HelloTrident(), new Fields("greeting"));

        Config conf = new Config();
        conf.setDebug(true);

        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology("trident-topology", conf, topology.build());
        for (String word : new String[]{"Prashant", "Utkarsha"}) {
            System.out.println("Result for " + word + ": " + drpc.execute("names", word));
        }
        cluster.shutdown();
        drpc.shutdown();
    }
}
