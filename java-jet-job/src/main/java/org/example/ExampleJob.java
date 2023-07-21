package org.example;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.test.TestSources;
import com.hazelcast.jet.python.PythonServiceConfig;

import static com.hazelcast.jet.python.PythonTransforms.mapUsingPython;

public class ExampleJob {
    public static void main(String[] args) {
        PythonServiceConfig config = new PythonServiceConfig()
                .setHandlerModule("emreyigit/user-code:latest") // docker image
                .setHandlerFunction("transform_list"); // user function

        Pipeline pipeline = Pipeline.create();
        pipeline.readFrom(TestSources.itemStream(10, ExampleJob::GeneratorFn))
                .withoutTimestamps()
                .apply(mapUsingPython(config))
                .setLocalParallelism(1)
                .writeTo(Sinks.logger());

        JobConfig cfg = new JobConfig()
                .setName("python-pipeline")
                .addClass(ExampleJob.class);

        HazelcastInstance hz = Hazelcast.bootstrappedInstance();
        hz.getJet().newJob(pipeline, cfg);
    }
    public static String GeneratorFn(long ts, long seq)
    {
        return String.valueOf(seq);
    }
}