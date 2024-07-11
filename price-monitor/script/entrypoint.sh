#!/bin/sh

# java -jar 사이에 java 옵션을 넣으면 된다.
# 근데 한 두개가 아니라서 한꺼번에 통으로 받는 게 좋음
# 2. command line 도 한 두개가 아니니 얘도 통으로 받음
# entrypoint.sh을 실행할 때 전달된 커맨드 라인 명령 인자들을 대입해주는 @ 변수
exec java ${JAVA_OPTS} -jar /app.jar ${@}
