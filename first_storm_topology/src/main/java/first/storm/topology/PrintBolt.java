package first.storm.topology;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintBolt extends BaseRichBolt {
    private HashMap<Integer, Integer> numsq = null;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        numsq = new HashMap<>();
    }

    @Override
    public void execute(Tuple tuple) {
        Integer num = new Integer(tuple.getIntegerByField("number"));
        Integer res = new Integer(tuple.getIntegerByField("numbersquare"));
        numsq.put(num, res);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public void cleanup() {
        System.out.println("--- Results by Program ---");
        List<Integer> keys = new ArrayList<Integer>();
        keys.addAll(numsq.keySet());
        Collections.sort(keys);
        for (Integer key : keys) {
            System.out.println(key + " : " + numsq.get(key));
        }
        System.out.println("--------------------------");
    }
}
