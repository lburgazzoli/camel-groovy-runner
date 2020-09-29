@Grab(group = 'org.apache.camel', module = 'camel-timer', version='3.4.4')
@Grab(group = 'org.apache.camel', module = 'camel-http', version='3.4.4')
@Grab(group = 'org.apache.camel', module = 'camel-jackson', version='3.4.4')
@Grab(group = 'org.apache.camel', module = 'camel-timer', version='3.4.4')
@Grab(group = 'com.github.lburgazzoli', module = 'camel-groovy-runner', version='3.5.0-SNAPSHOT', changing=true)
@groovy.transform.BaseScript com.github.lburgazzoli.camel.groovy.runner.CamelScript c

configure {
    durationMaxMessages = 1
}

from('timer:tick')
    .to('https://api.chucknorris.io/jokes/random')
    .unmarshal().json()
    .log('${body[value]}')

