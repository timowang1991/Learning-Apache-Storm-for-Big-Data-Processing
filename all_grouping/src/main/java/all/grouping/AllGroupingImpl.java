package all.grouping;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class AllGroupingImpl {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Emit_Number", new NumberSpout());

        // "allGrouping" ensures every stream emitted will go to all the bolt instances (aka broadcasting to all the bolts)
        builder.setBolt("File_Write_Bolt", new FileWriterBolt(), 2).allGrouping("Emit_Number");

        Config config = new Config();
        config.setDebug(true);
        config.put("dirToWriter", "all_grouping/data_output/shuffle_output");
        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        LocalCluster cluster = new LocalCluster();

        try {
            cluster.submitTopology("All-Grouping-Topology", config, builder.createTopology());
            Thread.sleep(10000);
        } finally {
            cluster.shutdown();
        }
    }
}
