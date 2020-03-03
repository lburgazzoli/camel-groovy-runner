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

import org.apache.camel.builder.RouteBuilder
import org.apache.camel.main.Main

class MainExtension {
    static void addRoutesBuilder(Main self, Closure<?> callable) {
        self.addRoutesBuilder(new RouteBuilder() {
            @Override
            void configure() throws Exception {
                callable.delegate = this
                callable.resolveStrategy = Closure.DELEGATE_ONLY
                callable.call()
            }
        })
    }

    static void routes(Main self, Closure<?> callable) {
        addRoutesBuilder(self, callable)
    }

    static void properties(Main self, Map<String, String> properties) {
        properties.each { 
            k, v -> self.addInitialProperty(k, v)
        }
    }
}
