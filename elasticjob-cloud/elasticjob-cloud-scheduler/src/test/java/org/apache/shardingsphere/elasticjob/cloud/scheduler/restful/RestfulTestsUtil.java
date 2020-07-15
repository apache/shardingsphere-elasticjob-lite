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

package org.apache.shardingsphere.elasticjob.cloud.scheduler.restful;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.io.ByteArrayBuffer;

import javax.ws.rs.core.MediaType;

public class RestfulTestsUtil {

    /**
     * Send request.
     * @param url the url
     * @param method the method name
     * @return http status code
     * @throws Exception exception when error
     */
    public static int sentRequest(final String url, final String method) throws Exception {
        HttpClient httpClient = new HttpClient();
        try {
            httpClient.start();
            ContentExchange contentExchange = new ContentExchange();
            contentExchange.setMethod(method);
            contentExchange.setURL(url);
            httpClient.send(contentExchange);
            contentExchange.waitForDone();
            return contentExchange.getResponseStatus();
        } finally {
            httpClient.stop();
        }
    }

    /**
     * Send request.
     * @param url the url
     * @param method the method name
     * @param content the content
     * @return the http status code
     * @throws Exception exception when error
     */
    public static int sentRequest(final String url, final String method, final String content) throws Exception {
        HttpClient httpClient = new HttpClient();
        try {
            httpClient.start();
            ContentExchange contentExchange = new ContentExchange();
            contentExchange.setMethod(method);
            contentExchange.setRequestContentType(MediaType.APPLICATION_JSON);
            contentExchange.setRequestContent(new ByteArrayBuffer(content.getBytes("UTF-8")));
            httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
            contentExchange.setURL(url);
            httpClient.send(contentExchange);
            contentExchange.waitForDone();
            return contentExchange.getResponseStatus();
        } finally {
            httpClient.stop();
        }
    }

    /**
     * Send get request.
     * @param url the url
     * @return the http response
     * @throws Exception exception when error
     */
    public static String sentGetRequest(final String url) throws Exception {
        HttpClient httpClient = new HttpClient();
        try {
            httpClient.start();
            ContentExchange contentExchange = new ContentExchange();
            contentExchange.setMethod("GET");
            contentExchange.setRequestContentType(MediaType.APPLICATION_JSON);
            httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
            contentExchange.setURL(url);
            httpClient.send(contentExchange);
            contentExchange.waitForDone();
            return contentExchange.getResponseContent();
        } finally {
            httpClient.stop();
        }
    }
}
