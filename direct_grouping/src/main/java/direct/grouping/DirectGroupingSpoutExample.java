package direct.grouping;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.List;
import java.util.Map;

public class DirectGroupingSpoutExample extends BaseRichSpout {
    private SpoutOutputCollector collector;

    private Integer i = 0;

    private List<Integer> boltIds;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        this.boltIds = topologyContext.getComponentTasks("File_Write_Bolt");
    }

    @Override
    public void nextTuple() {
        while(i <= 100) {
            Integer intBucket = this.i / 10;

            this.collector.emitDirect(boltIds.get(getBoltId(intBucket)), new Values(i.toString(), intBucket.toString()));
            this.i = this.i + 1;
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("integer", "bucket"));
    }

    private Integer getBoltId(Integer intBucket) {
        return intBucket % boltIds.size();
    }
}
