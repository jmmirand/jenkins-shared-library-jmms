#!/usr/bin/env groovy

def call(body) {
  Jenkins.instance.pluginManager.plugins.each{
    plugin ->
      println ("${plugin.getShortName()}:${plugin.getVersion()}")
}
