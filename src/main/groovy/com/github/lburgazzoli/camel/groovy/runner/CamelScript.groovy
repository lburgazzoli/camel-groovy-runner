/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.lburgazzoli.camel.groovy.runner

import org.apache.camel.builder.EndpointConsumerBuilder
import org.apache.camel.builder.endpoint.EndpointRouteBuilder
import org.apache.camel.k.loader.groovy.dsl.BeansConfiguration
import org.apache.camel.k.loader.groovy.dsl.CamelConfiguration
import org.apache.camel.k.loader.groovy.dsl.RestConfiguration
import org.apache.camel.main.Main
import org.apache.camel.model.InterceptDefinition
import org.apache.camel.model.InterceptFromDefinition
import org.apache.camel.model.InterceptSendToEndpointDefinition
import org.apache.camel.model.OnCompletionDefinition
import org.apache.camel.model.OnExceptionDefinition
import org.apache.camel.model.RouteDefinition
import org.apache.camel.model.rest.RestDefinition

abstract class CamelScript extends Script {
    Main main
    EndpointRouteBuilder builder

    def beans(@DelegatesTo(BeansConfiguration) Closure<?> callable) {
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        callable.delegate = new BeansConfiguration(main.camelContext)
        callable.call()
    }

    def camel(@DelegatesTo(CamelConfiguration) Closure<?> callable) {
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        callable.delegate = new CamelConfiguration(main.camelContext)
        callable.call()
    }

    def rest(@DelegatesTo(RestConfiguration) Closure<?> callable) {
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        callable.delegate = new RestConfiguration(builder)
        callable.call()
    }

    RestDefinition rest() {
        return builder.rest()
    }

    RestConfiguration restConfiguration() {
        builder.restConfiguration();
    }

    RestDefinition rest(String path) {
        return builder.rest(path)
    }

    RouteDefinition from(String endpoint) {
        return builder.from(endpoint)
    }

    RouteDefinition from(EndpointConsumerBuilder endpoint) {
        return builder.from(endpoint)
    }

    OnExceptionDefinition onException(Class<? extends Throwable> exception) {
        return builder.onException(exception)
    }

    OnCompletionDefinition onCompletion() {
        return builder.onCompletion()
    }

    InterceptDefinition intercept() {
        return builder.intercept()
    }

    InterceptFromDefinition interceptFrom() {
        return builder.interceptFrom()
    }

    InterceptFromDefinition interceptFrom(String uri) {
        return builder.interceptFrom(uri)
    }

    InterceptSendToEndpointDefinition interceptSendToEndpoint(String uri) {
        return builder.interceptSendToEndpoint(uri)
    }

    abstract def runScript()

    @Override
    def run() {
        main = new Main()

        builder = new EndpointRouteBuilder() {
            @Override
            void configure() throws Exception {
                runScript()
            }
        }

        main.addRoutesBuilder(builder)
        main.run()
    }
}
