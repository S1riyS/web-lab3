#!/bin/bash

HELIOS_WILDFLY_PATH="~/web/lab2/wildfly-preview-34.0.0.Beta1"
LOCAL_WAR_PATH="$(pwd)/app/build/libs/app.war"
HELIOS_WAR_PATH="~/web/lab2"

if ./gradlew clean build; then
    echo "Copying WAR file..."
    scp $LOCAL_WAR_PATH itmo:$HELIOS_WAR_PATH
    echo "Done!"
    exit 0  
fi

# PqZs+7641