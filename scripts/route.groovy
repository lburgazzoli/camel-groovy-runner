@Grab(group = 'org.apache.camel', module = 'camel-log', version='3.1.0')
@Grab(group = 'org.apache.camel', module = 'camel-timer', version='3.1.0')
@Grab(group = 'com.github.lburgazzoli', module = 'camel-groovy-runner', version='3.1.0')
@groovy.transform.BaseScript com.github.lburgazzoli.camel.groovy.runner.CamelScript c

from("timer:start")
    .to("log:out")
