#!/usr/bin/env groovy

def call(body) {
    echo "Check status"

    (1..3).each {
        echo "Check Status Number: " + it
    }

    currentBuild.result = 'SUCCESS' //FAILURE to fail
    return this
}
