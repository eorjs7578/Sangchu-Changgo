# 1. Node.js 이미지를 사용하여 Vue.js 프로젝트 빌드
FROM node:14 AS build-stage
WORKDIR /app

# package.json과 package-lock.json을 복사하고 npm install
COPY package*.json ./
RUN npm install

# 소스 코드 복사 후 빌드
COPY . .
RUN npm run build

# 2. Nginx 이미지를 사용하여 빌드된 정적 파일을 서빙
FROM nginx:alpine AS production-stage

RUN pwd
RUN ls -a /home 
RUN ls 

# NGINX 설정 파일 복사
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 빌드된 파일을 Nginx의 HTML 경로로 복사
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Nginx가 80번 포트에서 실행됨
EXPOSE 80

# Nginx 실행
CMD ["nginx", "-g", "daemon off;"]
