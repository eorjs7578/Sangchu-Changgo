package com.d108.project.interfaces.api;


import com.d108.project.cache.redisEmail.dto.EmailAuthCheckDto;
import com.d108.project.cache.redisToken.dto.TokenResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyByMemberIdResponseDto;
import com.d108.project.domain.map.dto.PolygonDto;
import com.d108.project.domain.member.dto.MemberLoginDto;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import com.d108.project.domain.member.dto.MemberResponseDto;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/map")
public interface MapApi {
    @GetMapping("/gu")
    ResponseEntity<List<PolygonDto>> getGuPolygon();

    @GetMapping("/dong")
    ResponseEntity<List<PolygonDto>> getDongPolygon(@RequestParam Long code);

    @GetMapping("/area")
    ResponseEntity<List<PolygonDto>> getAreaPolygon(@RequestParam Long code);
}
