package trident;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

public class HelloTrident extends BaseFunction {
    @Override
    public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
        System.out.println("========= HelloTrident execute");
        tridentCollector.emit(new Values("Hello" + tridentTuple.getString(0) + ". Welcome to Trident"));
    }
}
