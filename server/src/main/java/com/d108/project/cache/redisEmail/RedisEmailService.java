package com.d108.project.cache.redisEmail;

public interface RedisEmailService {

    /**
     * 레디스에 저장된 인증번호와 입력된 인증번호를 비교
     * <br>
     * 이메일 인증 버튼 누르면 시작될 함수
     *
     * @param email    가입하려는 이메일
     * @param authCode 보내준 인증번호
     * @return 인증 성공/실패 여부 반환
     */
    Boolean checkAuthCode(String email, String authCode);

    /**
     * 이메일을 입력하고 요청이 들어오면
     * <br>
     * 인증번호를 생성하고 저장 후 해당 이메일에 메일을 보냄
     * @param email     요청 받은 이메일
     * @throws Exception 메시지가 안보내지는 경우의 예외처리
     */
    void sendEmail(String email) throws Exception;
}
