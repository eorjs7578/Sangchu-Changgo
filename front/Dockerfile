# Node.js 이미지를 사용하여 Vue.js 프로젝트 빌드
FROM node:14 AS build-stage
WORKDIR /app

# 패키지 파일 복사 후 npm install 실행
COPY package*.json ./
RUN npm install

# 프로젝트 파일 복사 후 빌드
COPY . .
RUN npm run build

# Nginx 이미지를 사용하여 빌드된 파일 서빙
FROM nginx:alpine AS production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Nginx 설정 복사 (필요에 따라 사용자 설정 적용)
COPY ./nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
