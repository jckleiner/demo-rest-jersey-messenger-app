#!/bin/bash
ECLIPSE_DEPLOY_PATH=C:/Users/Can/Desktop/develop/eclipse-workspace/demo-rest-jersey-messenger-app/src/main/webapp
ANGULAR_PROJECT_PATH=C:/Users/Can/Desktop/develop/messengerFrontend
ANGULAR_DIST_PATH=./dist/messengerFrontend

cd $ANGULAR_PROJECT_PATH
ng build --prod --base-href ./
STATUS=$?
if [ $STATUS -eq 0 ]; then
  echo "*** Frontend build SUCCESSFUL ***"
  echo "Copying files to: "  $ECLIPSE_DEPLOY_PATH

  # Removes only files
  rm -f $ECLIPSE_DEPLOY_PATH/*
  cp $ANGULAR_DIST_PATH/* $ECLIPSE_DEPLOY_PATH
  if [[ "$?" -ne 0 ]] ; then
    echo 'Copy failure'; exit $rc
  else
    echo "Copy success"
  fi
else
  echo "*** Frontend build FAILED ***"
fi

