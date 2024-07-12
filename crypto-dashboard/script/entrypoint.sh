#!/bin/sh

# entrypoint.sh을 실행할 때 전달된 커맨드 라인 명령 @
exec java ${JAVA_OPTS} -jar /app.jar ${@}