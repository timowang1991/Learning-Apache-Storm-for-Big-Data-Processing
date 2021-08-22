package first.storm.topology;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;

public class SquareStormTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("Number-Spout", new NumberSpout());
        builder.setBolt("Square-Bolt", new SquareBolt()).shuffleGrouping("Number-Spout");
        builder.setBolt("Print-Bolt", new PrintBolt()).shuffleGrouping("Square-Bolt");

        Config config = new Config();

        // LocalCluster helps deploy topology on the fly instead of daemon processes
//        LocalCluster cluster = new LocalCluster();

        StormSubmitter cluster = new StormSubmitter();

        try {
            cluster.submitTopology("first_storm_topology", config, builder.createTopology());
        } catch (Exception e) {
            System.out.println("exception occurred when submitting topology: " + e.getMessage());
        }

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//
//        }
//
//        cluster.killTopology("square-storm-topology");
//        cluster.shutdown();
    }
}
