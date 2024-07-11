# spring boot 빌드하는 곳 jar를 만들 것임
# local 환경을 삭 다 가져오면 좋긴 한데 기존의 jdk가 깔려 있는 환경과 섞일 수 있으니 리눅스 내부에 워크 디렉터리 새로 만들고
# 여기 기준으로 동작하게끔 한다.
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace/app

# 경로 내부에다가 카피
# gradlew를 복사해서 현재 위치에 복사해 줘
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

# 도커 이미지 빌드 과정 속에서 이미지 빌드를 위해 내부적으로 어떤 명령을 수행해야 한다면
# RUN 명령어로 build 과정 속에서 필요한 실행은 RUN
# 컨테이너가 실행될 때 수행해야 되는 명령어는 ENTRYPOINT, CMD
RUN ./gradlew clean build

# build하게 되면 jar 경로는
# /workspace/app/build/libs/*-SNAPSHOT.jar



# jar 로 빌드를 하겠다.
FROM eclipse-temurin:17-jdk

# build stage 에서 해당 경로의 친구를 가져오겠다
COPY --from=build /workspace/app/build/libs/*-SNAPSHOT.jar app.jar
# 컨테이너 내부에는 entrypoint.sh로 복사를 해줘
# shell script 파일은 작성한다고 바로 실행가능하지 않음
# 해당 파일에 chmod +x 옵션을 줘야 한다.
COPY script/entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh

# java -jar app.jar
# exec form
# docker container 자체가 프로그램을 실행하는 것처럼 만들 때
# # 간접적으로 실행할 것이기 때문에 루트의 shell script 실행하도록 변경
ENTRYPOINT ["/entrypoint.sh"]

# 파라미터 받게 되면 그걸 사용하고 아니면 기본값을 사용하는 용도로 많이 쓴다.
#CMD ["app.jar"]
