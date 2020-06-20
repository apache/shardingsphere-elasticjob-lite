/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.elasticjob.lite.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.lite.util.concurrent.ElasticJobExecutorService;

/**
 * Job event bus.
 */
@Slf4j
public final class JobEventBus {
    
    private final JobEventConfiguration jobEventConfig;
    
    private final ElasticJobExecutorService elasticJobExecutorService;
    
    private final EventBus eventBus;
    
    private boolean isRegistered;
    
    public JobEventBus() {
        jobEventConfig = null;
        elasticJobExecutorService = null;
        eventBus = null;
    }
    
    public JobEventBus(final JobEventConfiguration jobEventConfig) {
        this.jobEventConfig = jobEventConfig;
        elasticJobExecutorService = new ElasticJobExecutorService("job-event", Runtime.getRuntime().availableProcessors() * 2);
        eventBus = new AsyncEventBus(elasticJobExecutorService.createExecutorService());
        register();
    }
    
    private void register() {
        try {
            eventBus.register(jobEventConfig.createJobEventListener());
            isRegistered = true;
        } catch (final JobEventListenerConfigurationException ex) {
            log.error("Elastic job: create JobEventListener failure, error is: ", ex);
        }
    }
    
    /**
     * Post event.
     *
     * @param event job event
     */
    public void post(final JobEvent event) {
        if (isRegistered && !elasticJobExecutorService.isShutdown()) {
            eventBus.post(event);
        }
    }
}
