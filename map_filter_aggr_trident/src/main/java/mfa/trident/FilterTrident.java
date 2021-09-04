package mfa.trident;

import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.tuple.TridentTuple;

public class FilterTrident extends BaseFilter {
    @Override
    public boolean isKeep(TridentTuple tridentTuple) {
        return tridentTuple.getString(0).length() > 5;
    }
}
