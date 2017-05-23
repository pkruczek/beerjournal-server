#!/bin/bash

deploy target directory
rsync -r --delete-after --quiet $TRAVIS_BUILD_DIR/target $SSH_USER@$SSH_HOST:deploy/server

#run server
ssh -t $SSH_USER@$SSH_HOST << EOF
    screen -X -S server-deploy quit
    sleep 30
    cd deploy/server/target/
	screen -L -dmS server-deploy java -jar -Dspring.profiles.active=prod bj-server-0.0.1-SNAPSHOT.jar
	curl -X POST -H 'Content-type: application/json' \
	--data '{"text":"New server version deployed. Check it out under SSH_HOST:8080/swagger-ui.html"}' \
 	$SLACK_WEBHOOK
	echo "Done"
	exit
EOF

