#!/bin/bash

LOCAL_WAR_PATH="$(pwd)/app/build/libs/app.war"
HELIOS_WAR_PATH="/home/studs/s413732/web/wildfly/standalone/deployments"

if ./gradlew clean build; then
    echo "Copying WAR file..."
    scp $LOCAL_WAR_PATH itmo:$HELIOS_WAR_PATH
    echo "Done!"
    exit 0  
fi

# PqZs+7641