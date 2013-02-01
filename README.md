Voldemort Service Provider
====================

Voldemort service provider for the data object spi.

This service provider obviously needs voldemort as a dependency. You can download the project from https://github.com/voldemort/voldemort/downloads. We used and tested version 0.90.1.
Build the jar (with ant), go to the "dist" folder and install it in maven via 

"mvn install:install-file -Dfile=./voldemort-0.96.jar -DgroupId=voldemort -DartifactId=voldemort -Dversion=0.96 -Dpackaging=jar"

and

"mvn install:install-file -Dfile=./voldemort-contrib-0.96.jar -DgroupId=voldemort -DartifactId=voldemort-contrib -Dversion=0.96 -Dpackaging=jar"

Now build the service provider via the usual 

"mvn clean install"

and place the jar in the classpath of the bpmn-engine.

You will also need a couple of voldemort specific config files. For convienience the build process of the voldemort service provider has created a config folder with a sample configuration ("target/config"). Copy the files found in that folder to the config directory of the bpmn engine.

Now you can run processes with data objects (like testprocess_throw_dataOutput_integrationstart.xml).
