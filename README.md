Voldemort Service Provider
====================

Voldemort service provider for the data object spi.

This service provider obviously needs voldemort as a dependency. You can download the project from https://github.com/voldemort/voldemort/downloads. We used and tested version 0.90.1.
Build the jar and install it in maven via 

"mvn install:install-file -Dfile=./voldemort-0.90.1.jar -DgroupId=voldemort -DartifactId=voldemort -Dversion=0.90.1 -Dpackaging=jar"

Now build the service provider via the usual 

"mvn clean install"

and place the jar in the services folder of the bpmn-engine.
