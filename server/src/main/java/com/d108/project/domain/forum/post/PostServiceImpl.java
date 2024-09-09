package com.d108.project.domain.forum.post;

import com.d108.project.domain.forum.board.domain.Board;
import com.d108.project.domain.forum.post.domain.Post;
import com.d108.project.domain.forum.post.dto.PostDeleteDto;
import com.d108.project.domain.forum.post.repository.PostRepository;
import com.d108.project.domain.forum.board.repository.BoardRepository;
import com.d108.project.domain.member.domain.Member;
import com.d108.project.domain.member.repository.MemberRepository;
import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;
import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import jakarta.persistence.Access;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    // 글 작성
    @Override
    public Integer createPost(PostCreateDto postCreateDto) {
        Board board = boardRepository.findById(postCreateDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다."));

        Member member = memberRepository.findById(postCreateDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Post post = Post.builder()
                .board(board)
                .member(member)
                .title(postCreateDto.getTitle())
                .content(postCreateDto.getContent())
                .build();

        postRepository.save(post);

        return post.getId();
    }

    // 전체 글 조회
    @Override
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(PostResponseDto::from)
                .collect(Collectors.toList());
    }

    // 단일 글 조회
    @Override
    public PostResponseDto getPostById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 번호 입니다."));
        return PostResponseDto.from(post);
    }

    // 글 수정
    @Override
    public void updatePostById(Integer postId, Integer memberId, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 번호 입니다."));

        Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!member.equals(post.getMember())) {
            throw new AccessDeniedException("본인의 글만 수정할 수 있습니다.");
        }

        post.setTitle(postUpdateDto.getTitle());
        post.setContent(postUpdateDto.getContent());

        postRepository.save(post);
    }
    
    // 글 삭제
    @Override
    public void deletePostById(Integer postId, Integer memberId) {
        Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!member.equals(post.getMember())) {
            throw new AccessDeniedException("본인의 글만 삭제하실 수 있습니다.");
        }

        postRepository.deleteById(postId);
    }
}
