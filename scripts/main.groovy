@Grab(group = 'org.apache.camel', module = 'camel-log', version='3.1.0')
@Grab(group = 'org.apache.camel', module = 'camel-timer', version='3.1.0')
@Grab(group = 'org.apache.camel', module = 'camel-main', version='3.1.0')
@Grab(group = 'com.github.lburgazzoli', module = 'camel-groovy-runner', version='3.1.0')


def main = new org.apache.camel.main.Main()
main.properties [
    'message', 'test'
]
main.routes {
    from('timer:tick')
        .log('{{message}}')
}

main.run()
