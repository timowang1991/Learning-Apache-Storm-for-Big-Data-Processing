package shuffle.grouping;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class ShuffleGroupingImpl {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Emit_Number", new NumberSpout());

        builder.setBolt("File_Write_Bolt", new FileWriterBolt(), 3).shuffleGrouping("Emit_Number");

        Config config = new Config();
        config.setDebug(true);
        config.put("dirToWriter", "shuffle_grouping/data_output/shuffle_output");
        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        LocalCluster cluster = new LocalCluster();

        try {
            cluster.submitTopology("Shuffle-Grouping-Topology", config, builder.createTopology());
            Thread.sleep(10000);
        } finally {
            cluster.shutdown();
        }
    }
}
