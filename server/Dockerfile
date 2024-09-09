# 베이스 이미지 설정
FROM openjdk:17

# 인자 설정 - JAR_File (기본값 설정)
ARG JAR_FILE=build/libs/*.jar

# 로컬에서 빌드된 .jar 파일을 컨테이너 안으로 복사
COPY build/libs/server-0.0.1-SNAPSHOT.jar app.jar

# 컨테이너 실행 시 .jar 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
