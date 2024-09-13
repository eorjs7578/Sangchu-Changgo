package com.d108.project.config;

public class LogConfiguration {
    public static void configureLog4j() {
        // 환경 변수 LOG_CONFIG를 통해 log4j 설정 파일 경로를 설정
        String logConfigFile = System.getenv("LOG_CONFIG");

        // log4j 설정 파일을 시스템 속성으로 지정
        if (logConfigFile != null) {
            System.setProperty("log4j.configurationFile", logConfigFile);
        } else {
            System.err.println("LOG_CONFIG 환경 변수가 설정되지 않았습니다.");
            throw new IllegalStateException("LOG_CONFIG 환경 변수가 설정되지 않았습니다.");
        }
    }
}
