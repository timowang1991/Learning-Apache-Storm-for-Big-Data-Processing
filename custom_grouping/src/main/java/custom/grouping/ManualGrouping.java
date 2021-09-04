package custom.grouping;

import org.apache.storm.generated.GlobalStreamId;
import org.apache.storm.grouping.CustomStreamGrouping;
import org.apache.storm.task.WorkerTopologyContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ManualGrouping implements CustomStreamGrouping, Serializable {

    private List<Integer> targetTasks;

    // We need the targetTask id based on how much bolt we instantiate
    @Override
    public void prepare(WorkerTopologyContext workerTopologyContext, GlobalStreamId globalStreamId, List<Integer> targetTasks) {
        System.out.println("========== targetTasks " + targetTasks);
        this.targetTasks = targetTasks;
    }

    // Grouping Logic
    // Takes the tuple from a task
    // Informs which TaskID the tuple has to be sent to
    @Override
    public List<Integer> chooseTasks(int taskId, List<Object> values) {
        System.out.println("============ values " + values);
        List<Integer> boltIds = new ArrayList<>();

        Integer boltNum = Integer.parseInt(values.get(1).toString()) % targetTasks.size();

        boltIds.add(targetTasks.get(boltNum));

        return boltIds;
    }
}
