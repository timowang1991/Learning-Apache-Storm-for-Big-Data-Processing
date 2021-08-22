#!/bin/sh

# Kill old topology
echo "Killing old topology..."
docker exec -it nimbus storm kill square-storm-topology -w 1

# Wait for old topology to be cleaned up
echo "Wait for 10 second."
sleep 10

docker exec -it nimbus bash -c "storm jar \
    /src/target/learning-apache-storm-1.0-SNAPSHOT.jar \
    com.packt.stormtraining.SquareStormTopology"

# Wait for logs to be ready
echo "Wait for 15 seconds for logs to be ready"
sleep 15

echo "Tailing the newest log file"
docker exec -it supervisor \
    bash -c 'cd /logs/workers-artifacts/ ; tail -f $(ls -Art | tail -n 1)/6700/worker.log'