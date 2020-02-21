@Grab(group = 'org.apache.camel', module = 'camel-log', version='3.0.1')
@Grab(group = 'org.apache.camel', module = 'camel-timer', version='3.0.1')
@Grab(group = 'com.github.lburgazzoli', module = 'camel-groovy-runner', version='0.1.0-SNAPSHOT', changing=true)
@groovy.transform.BaseScript com.github.lburgazzoli.camel.groovy.runner.CamelScript c

from("timer:start")
    .to("log:out")