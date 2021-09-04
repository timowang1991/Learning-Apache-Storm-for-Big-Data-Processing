package custom.grouping;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.io.PrintWriter;
import java.util.Map;

public class FileWriterBolt extends BaseBasicBolt {

    private PrintWriter writer;

    @Override
    public void prepare(Map config, TopologyContext topologyContext) {
        String filename = "output" + "-" + topologyContext.getThisTaskId() + "-" + topologyContext.getThisComponentId() + ".txt";
        try {
            this.writer = new PrintWriter(config.get("dirToWriter").toString() + filename, "UTF-8");
        } catch (Exception e) {
            System.out.println("creating PrinterWriter error " + e.getMessage());
        }
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String str = tuple.getStringByField("integer") + "-" + tuple.getStringByField("bucket");
        basicOutputCollector.emit(new Values(str));
        writer.println(str);
        writer.flush();
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("field"));
    }
}
