package org.n52.sps.sensor.config;

import org.n52.sps.sensor.SensorPlugin;
import org.n52.sps.sensor.SensorTaskService;
import org.n52.sps.sensor.model.SensorConfiguration;
import org.n52.sps.service.InternalServiceException;
import org.n52.sps.util.nodb.InMemorySensorTaskRepository;

public class SmlConfigActuatorTest {

    private InMemorySensorTaskRepository sensorTaskRepository;
    
    private SensorPlugin plugin;

    public void setUp() throws InternalServiceException {
        sensorTaskRepository = new InMemorySensorTaskRepository();
        SensorTaskService taskService = new SensorTaskService(sensorTaskRepository);
        SensorConfiguration configuration = new SensorConfiguration();
        plugin = new SmlConfigActuator(taskService, configuration);
    }
}
