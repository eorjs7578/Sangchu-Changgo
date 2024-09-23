# 1. Node.js 이미지를 사용하여 Vue.js 프로젝트 빌드
FROM node:14 AS build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# 2. Nginx 이미지를 사용하여 빌드된 정적 파일 서빙
FROM nginx:alpine AS production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Nginx 설정
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]
