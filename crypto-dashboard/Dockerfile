# jar 빌드하는 내용
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

# 도커 이미지 빌드 과정 속에서 이미지 빌드를 위해 내부적으로 필요한 실행은 RUN
# 컨테이너가 실행될 때 수행해야 되는 명령어는 ENTRYPOINT, CMD
RUN ./gradlew clean build

# build하게 되면 jar 경로는
# /workspace/app/build/libs/*-SNAPSHOT.jar


# 도커 이미지 빌드
FROM eclipse-temurin:17-jdk

# build stage 에서 해당 경로의 파일을 가져오겠다
COPY --from=build /workspace/app/build/libs/*-SNAPSHOT.jar app.jar

# shell script 파일은 작성 후 바로 실행할 수 없다
# 해당 파일에 chmod +x 옵션을 준다
COPY script/entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh

# 루트의 shell script 실행
ENTRYPOINT ["/entrypoint.sh"]