1. Gitlab 소스 클론 이후 빌드 및 배포할 수 있도록 정리한 문서
    
## INFRA
- hadoop-3.3.1
- spark-3.5.2
- Docker version 24.0.7, build 24.0.7-0ubuntu2~20.04.1
- mariadb from 11.5.2-MariaDB, client 15.2 for debian-linux-gnu (x86_64) using EditLine wrapper
- redis-cli 7.4.0
- Jenkins 2.479
- ELK Stack 8.8
- Intellij IDEA latest
- VScode latest

    
## BE
    
### build.gradle
```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.3.2'
    id 'io.spring.dependency-management' version '1.1.6'
    id 'jacoco'
    id "org.sonarqube" version "5.0.0.4638"
}

group = 'com.d108.'
version = '0.0.1-SNAPSHOT'

ext.jacocoDir = layout.buildDirectory.dir("customJacocoReportDir").get().asFile.toString() as String

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter'
    implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'

    runtimeOnly 'org.mariadb.jdbc:mariadb-java-client'

    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'

    // lombok
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    implementation 'org.projectlombok:lombok'
    testCompileOnly 'org.projectlombok:lombok:'

    implementation 'jakarta.persistence:jakarta.persistence-api:3.1.0'

    //swagger
    implementation group: 'org.springdoc', name: 'springdoc-openapi-starter-webmvc-ui', version: '2.6.0'

    // queryDSL
    implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
    annotationProcessor "com.querydsl:querydsl-apt:5.0.0:jakarta"
    annotationProcessor "jakarta.annotation:jakarta.annotation-api"
    annotationProcessor "jakarta.persistence:jakarta.persistence-api"

    // junit (test tool)
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'

    // jwt
    implementation 'io.jsonwebtoken:jjwt-api:0.11.2'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.2'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.2'

    // mariadb
    runtimeOnly 'org.mariadb.jdbc:mariadb-java-client'

    // kafka
    implementation 'org.springframework.kafka:spring-kafka:3.2.2'

    // mysql
    runtimeOnly 'com.mysql:mysql-connector-j'

    // redis
    implementation 'org.springframework.boot:spring-boot-starter-data-redis'

    // mail
    implementation 'org.springframework.boot:spring-boot-starter-mail'

    //jsonObject
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.15.0'
    implementation 'org.json:json:20211205'

    // Hibernate Spatial
    implementation 'org.hibernate.orm:hibernate-spatial:6.0.0.Final' // 버전 확인 후 최신으로 변경

    // JTS (Java Topology Suite)
    implementation 'org.locationtech.jts:jts-core:1.18.2'

    implementation 'org.springdoc:springdoc-openapi-ui:1.7.0'
}

tasks.named('test') {
    useJUnitPlatform()
}

task copyYML(type: Copy) {
    copy {
        from './submodule'
        include "*.yml"
        into 'src/main/resources'
    }
}

// 분리된 설정 파일 포함
apply {
    from 'querydsl.gradle'
}

// 스프링 부트 2.5 버전 이후, 프로젝트를 빌드할 때 .jar 파일이 두 개가 생성 -> 방지 -> 올바른 jar 파일 사용
jar {
    enabled = false
}
```

### build.gradle
```gradle
def querydslDir = "src/main/generated"

tasks.withType(JavaCompile) {
    options.getGeneratedSourceOutputDirectory().set(file(querydslDir))
}

sourceSets {
    main.java.srcDirs += [querydslDir]
}

clean {
    delete file(querydslDir)
}
```   

### applicationl.yml    
```yml
server:
    port: 8080

spring:
    application:
    name: server1

    datasource:
    url: jdbc:mariadb://15.165.236.50:3306/S11P21D108?serverTimezone=UTC&useUniCode=true&characterEncoding=utf8
    username: root
    password: enqnrhkwk108

    jpa:
    show-sql: true
    hibernate:
        ddl-auto: update
    properties:
        format-sql: true

    mail:
    host: smtp.gmail.com
    port: 587
    username: jinee9523@gmail.com
    password: tdkjpcgxzosuclcn

    data:
    redis:
        host: 15.165.236.50
        port: 6379
        password: enqnrhkwk108

    jwt:
    # oepnssl rand -base64 64
    salt: l9AJvRTCoQeVviADMtOpsRu4RqgqZvXKauLjn9503PpX1fMCXWfR5Rt10vC9xCniKDXtaxdaTw4ynTAl9NHUn7==
    # 코드 내부에서 1000ms 곱해서 사용
    access-token:
        expire-time: 864000  # 60sec * 60min * 24hour
    refresh-token:
        expire-time: 8640000  # 60sec * 60min * 24hour * 10day

    security:
    oauth2:
        client:
        registration:
            naver:
            client-id: GLp4B7DpCGdgXZBUMLo0
            client-secret: G8Kgf5K_nb
            client-authentication-method: client_secret_post
            authorization-grant-type: authorization_code
            redirect-uri: "{baseUrl}/api/{action}/oauth2/code/{registrationId}"
            scope:
                - name
                - email
                - nickname
            client-name: Naver

            kakao:
            client-id: e1a573202c3c0ee4f68757690df7b5ea
            client-secret: XYSU98qumCfXLQHJCzumg2xkF01DKVJO
            client-authentication-method: client_secret_post
            authorization-grant-type: authorization_code
            scope: # https://developers.kakao.com/docs/latest/ko/kakaologin/common#user-info
                - profile_nickname
            redirect-uri: "{baseUrl}/api/{action}/oauth2/code/{registrationId}"
            client-name: Kakao

        provider:
            naver:
            authorization-uri: https://nid.naver.com/oauth2.0/authorize
            token-uri: https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user-info-authentication-method: header
            user-name-attribute: response # Naver 응답 값 resultCode, message, response 중 response 지정

            kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-info-authentication-method: header
            user-name-attribute: id # Kakao 응답 값 id, connected_at, properties, kakao_account 중 id 지정

```
    
### DockerFile
```Dockerfile
# 베이스 이미지 설정
FROM openjdk:17

# 인자 설정 - JAR 파일의 경로를 유연하게 설정
ARG JAR_FILE=build/libs/*.jar

# 로컬에서 빌드된 JAR 파일을 컨테이너 안으로 복사
COPY ${JAR_FILE} app.jar

# 컨테이너 실행 시 JAR 파일 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
```
    
## FE
### package.json
```json
{
    "name": "front",
    "version": "0.0.0",
    "private": true,
    "type": "module",
    "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview"
    },
    "dependencies": {
    "axios": "^1.7.7",
    "bootstrap": "^5.3.3",
    "bootstrap-icons": "^1.11.3",
    "chart.js": "^4.4.4",
    "lucide-vue-next": "^0.446.0",
    "pinia": "^2.2.2",
    "pinia-plugin-persistedstate": "^4.0.2",
    "vue": "^3.4.29",
    "vue-chartjs": "^5.3.1",
    "vue-router": "^4.3.3"
    },
    "devDependencies": {
    "@vitejs/plugin-vue": "^5.0.5",
    "vite": "^5.3.1"
    }
}
```

### .env
```env
VITE_KAKAOMAP_KEY="8358e3ab9d8e5e3e1c10793a6c35dbbc"
VITE_VUE_API_URL="https://j11d108.p.ssafy.io"
VITE_VUE_SOCIAL_REDIRECT_URL=https://j11d108.p.ssafy.io/members/login/social/redirect
```

### nginx.conf

```bash
server {
    listen 80;  # 서버가 80번 포트에서 HTTP 요청을 수신하도록 설정 (HTTP의 기본 포트)
    server_name localhost;  # 서버 이름을 localhost로 설정, 즉 localhost로 들어오는 요청을 처리

    root /usr/share/nginx/html;  # 웹 페이지를 제공할 기본 디렉토리 설정 (정적 파일의 루트 경로)
    index index.html index.html;  # 기본적으로 제공할 파일 설정, index.html 또는 index.htm이 요청이 없을 때 자동으로 로드됨

    location / {  # '/'로 시작하는 모든 요청에 대한 설정
        try_files $uri $uri/ /index.html;  # 요청된 파일이 있으면 제공, 없으면 index.html을 반환 (SPA 라우팅을 위한 설정)
    }

    error_page 404 /index.html;  # 404 에러 발생 시 index.html로 리다이렉트 (클라이언트 사이드 라우팅을 위한 설정)

    location = /50x.html {  # 500대 오류(예: 500, 502, 503, 504) 발생 시 /50x.html 파일을 제공
        root /usr/share/nginx/html;  # 오류 페이지도 정적 파일의 루트 경로에서 제공
    }

    location ~ /\.ht {  # .ht로 시작하는 파일 요청에 대해 설정 (예: .htaccess 파일)
        deny all;  # .ht로 시작하는 파일은 접근을 모두 차단 (보안 설정)
    }
}
```

### Dockerfile

```Dockerfile
# 1. Node.js 이미지를 사용하여 Vue.js 프로젝트 빌드
FROM node:14 AS build-stage
WORKDIR /app

# package.json과 package-lock.json을 복사하고 npm install
COPY package*.json ./
RUN npm install

# EC2 서버의 .env 파일을 이미지에 복사
COPY .env .env

# 소스 코드 복사 후 빌드
COPY . .
RUN npm run build

# 2. Nginx 이미지를 사용하여 빌드된 정적 파일을 서빙
FROM nginx:alpine AS production-stage

# NGINX 설정 파일 복사
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

# 빌드된 파일을 Nginx의 HTML 경로로 복사
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Nginx가 80번 포트에서 실행됨
EXPOSE 80

# Nginx 실행
CMD ["nginx", "-g", "daemon off;"]

```

## DB
```
url: jdbc:mariadb://15.165.236.50:3306/S11P21D108?serverTimezone=UTC&useUniCode=true&characterEncoding=utf8
username: root
password: enqnrhkwk108
```
