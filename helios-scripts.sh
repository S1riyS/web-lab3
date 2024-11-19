# App
~/web/lab2/wildfly-preview-34.0.0.Beta1/bin/jboss-cli.sh --connect --commands="deploy --force ~/web/lab2/app.war"

# Wildfly
~/web/lab2/wildfly-preview-34.0.0.Beta1/bin/standalone.sh -Djboss.http.port=24732
