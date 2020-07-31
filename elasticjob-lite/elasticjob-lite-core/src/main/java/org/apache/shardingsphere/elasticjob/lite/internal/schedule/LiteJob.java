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

package org.apache.shardingsphere.elasticjob.lite.internal.schedule;

import lombok.Setter;
import org.apache.shardingsphere.elasticjob.api.ElasticJob;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.executor.ElasticJobExecutor;
import org.apache.shardingsphere.elasticjob.executor.JobFacade;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Lite job class.
 */
@Setter
public final class LiteJob implements Job {
    
    private ElasticJob elasticJob;
    
    private String elasticJobType;
    
    private JobConfiguration jobConfig;
    
    private JobFacade jobFacade;
    
    @Override
    public void execute(final JobExecutionContext context) {
        createExecutor().execute();
    }
    
    private ElasticJobExecutor createExecutor() {
        return null == elasticJob ? new ElasticJobExecutor(elasticJobType, jobConfig, jobFacade) : new ElasticJobExecutor(elasticJob, jobConfig, jobFacade);
    }
}
