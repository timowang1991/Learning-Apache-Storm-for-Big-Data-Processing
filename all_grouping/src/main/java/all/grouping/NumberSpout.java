package all.grouping;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class NumberSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private Integer i = 0;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        while (i <= 100) {
            Integer intBucket = this.i / 10;

            this.collector.emit(new Values(this.i.toString(), intBucket.toString()));
            this.i = this.i + 1;
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        // tuple field name
        outputFieldsDeclarer.declare(new Fields("integer", "bucket"));
    }
}
