package com.packt.stormtraining;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class NumberSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private Integer i = 2;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        this.collector.emit(new Values(i));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        i += 2;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        // tuple field name
        outputFieldsDeclarer.declare(new Fields("numbers"));
    }
}
