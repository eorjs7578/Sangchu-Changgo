// package com.d108.project.interfaces.controller;

// import com.d108.project.domain.member.service.MemberService;
// import com.d108.project.domain.member.dto.MemberLoginDto;
// import com.d108.project.domain.member.dto.MemberRegisterDto;
// import com.d108.project.domain.member.dto.MemberResponseDto;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.http.ResponseEntity;

// import java.util.Arrays;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;

// // TODO: 로그인 반환값 토큰으로 바꿔서 이것도 고쳐야함
// @DisplayName("회원 관련 기능 테스트")
// class MemberControllerTest {

//     @InjectMocks
//     private MemberController memberController;

//     @Mock
//     private MemberService memberService;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     @DisplayName("회원가입 성공 테스트")
//     void 회원가입_성공() {
//         // given
//         MemberRegisterDto 회원가입요청 = new MemberRegisterDto("sihyun","user123", "password123");
//         MemberResponseDto 회원가입응답 = new MemberResponseDto(1L, "user123", "sihyun", "test@test.com");

//         회원가입_서비스_모의(회원가입응답);

//         // when
//         ResponseEntity<MemberResponseDto> 회원가입결과 = memberController.registerMember(회원가입요청);

//         // then
//         회원가입_검증(회원가입결과, 회원가입응답);
//     }

//     private void 회원가입_서비스_모의(MemberResponseDto 회원가입응답) {
//         when(memberService.loginMember(any(MemberLoginDto.class))).thenReturn(회원가입응답);
//     }

//     private void 회원가입_검증(ResponseEntity<MemberResponseDto> 회원가입결과, MemberResponseDto 예상응답) {
//         assertEquals(예상응답, 회원가입결과.getBody());
//     }

//     @Test
//     @DisplayName("로그인 성공 테스트")
//     void 로그인_성공() {
//         // given
//         MemberLoginDto 로그인요청 = new MemberLoginDto("user123", "password123");
//         MemberResponseDto 로그인응답 = new MemberResponseDto(1L, "user123", "sihyun");

//         로그인_서비스_모의(로그인요청, 로그인응답);

//         // when
//         ResponseEntity<MemberResponseDto> 로그인결과 = memberController.loginMember(로그인요청);

//         // then
//         로그인_검증(로그인결과, 로그인응답);
//     }

//     private void 로그인_서비스_모의(MemberLoginDto 로그인요청, MemberResponseDto 로그인응답) {
//         when(memberService.loginMember(로그인요청)).thenReturn(로그인응답);
//     }

//     private void 로그인_검증(ResponseEntity<MemberResponseDto> 로그인결과, MemberResponseDto 예상응답) {
//         assertEquals(예상응답, 로그인결과.getBody());
//     }

//     @Test
//     @DisplayName("모든 회원 조회 성공 테스트")
//     void 모든회원조회_성공() {
//         // given
//         MemberResponseDto 회원1 = new MemberResponseDto(1L, "user1", "email1@example.com");
//         MemberResponseDto 회원2 = new MemberResponseDto(2L, "user2", "email2@example.com");
//         List<MemberResponseDto> 회원목록 = Arrays.asList(회원1, 회원2);

//         모든회원조회_서비스_모의(회원목록);

//         // when
//         ResponseEntity<List<MemberResponseDto>> 회원목록결과 = memberController.getAllMembers();

//         // then
//         모든회원조회_검증(회원목록결과, 회원목록);
//     }

//     private void 모든회원조회_서비스_모의(List<MemberResponseDto> 회원목록) {
//         when(memberService.getAllMember()).thenReturn(회원목록);
//     }

//     private void 모든회원조회_검증(ResponseEntity<List<MemberResponseDto>> 회원목록결과, List<MemberResponseDto> 예상응답) {
//         assertEquals(예상응답, 회원목록결과.getBody());
//     }
// }
