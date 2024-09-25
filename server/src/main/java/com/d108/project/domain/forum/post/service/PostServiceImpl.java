package com.d108.project.domain.forum.post.service;

import com.d108.project.cache.redis.RedisUtil;
import com.d108.project.domain.forum.board.entity.Board;
import com.d108.project.domain.forum.post.entity.Post;
import com.d108.project.domain.forum.post.repository.PostRepository;
import com.d108.project.domain.forum.board.repository.BoardRepository;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;
import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final RedisUtil redisUtil;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private static final String REDIS_PREFIX = "post:viewCount:";

    // 글 작성
    @Override
    public Long createPost(Member member, PostCreateDto postCreateDto) {
        Board board = boardRepository.findById(postCreateDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다."));

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
                .map(post -> {
                    PostResponseDto postResponseDto = PostResponseDto.from(post);
                    postResponseDto.setView(getViewCountById(post.getId()));
                    return postResponseDto;
                })
                .collect(Collectors.toList());
    }

    // 단일 글 조회
    @Override
    public PostResponseDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 번호 입니다."));

        // 조회수 올리고
        Long view = incrementViewCountById(postId);

        // 조회
        PostResponseDto postResponseDto = PostResponseDto.from(post);
        postResponseDto.setView(view);

        return postResponseDto;
    }

    // 글 수정
    @Override
    public void updatePostById(Member member, Long postId, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 번호 입니다."));

        if (!member.getId().equals(post.getMember().getId())) {
            throw new AccessDeniedException("본인의 글만 수정할 수 있습니다.");
        }

        post.setTitle(postUpdateDto.getTitle());
        post.setContent(postUpdateDto.getContent());

        postRepository.save(post);
    }

    // 글 삭제
    @Override
    public void deletePostById(Member member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        if (!member.getId().equals(post.getMember().getId())) {

            throw new AccessDeniedException("본인의 글만 삭제하실 수 있습니다.");
        }

        // 레디스에 저장된 값 삭제하고
        redisUtil.deleteData(REDIS_PREFIX + postId);

        // DB에서도 삭제
        postRepository.deleteById(postId);
    }

    // 상권 ID 기반으로 게시글 반환
    @Override
    public List<PostResponseDto> getAllPostsByAreaId(Long areaId) {
        List<Post> posts = postRepository.findAllByAreaId(areaId);

        return posts.stream()
                .map(post -> {
                    PostResponseDto postResponseDto = PostResponseDto.from(post);
                    postResponseDto.setView(getViewCountById(post.getId()));
                    return postResponseDto;
                })
                .collect(Collectors.toList());
    }

    // 프랜차이즈 ID 기반으로 게시글 반환
    @Override
    public List<PostResponseDto> getAllPostsByFranchiseId(Long franchiseId) {
        List<Post> posts = postRepository.findAllByFranchiseId(franchiseId);

        return posts.stream()
                .map(post -> {
                    PostResponseDto postResponseDto = PostResponseDto.from(post);
                    postResponseDto.setView(getViewCountById(post.getId()));
                    return postResponseDto;
                })
                .collect(Collectors.toList());
    }



    // 조회수 관련 메서드

    // redis-DB 싱크
    @Override
    @Transactional
    public void syncCountToDatabase(Long postId) {
        String redisKey = REDIS_PREFIX + postId;

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 글입니다."));

        post.setView(getViewCountById(postId));
        postRepository.save(post);

        redisUtil.deleteData(redisKey);
    }
    // 전체 글 Id 저장
    @Override
    public List<Long> getAllPostIds() {
        return postRepository.findAllPostIds();
    }
    // 조회수 증가
    private Long incrementViewCountById(Long postId) {
        String redisKey = REDIS_PREFIX + postId;

        return redisUtil.incrementView(redisKey, RedisUtil.REDIS_VIEW_EXPIRE);
    }

    // 조회수 조회
    private Long getViewCountById(Long postId) {
        String redisKey = REDIS_PREFIX + postId;
        String viewCountStr = redisUtil.getData(redisKey);
        // 레디스에 저장된 viewCount가 없는 경우 DB에서 가져옴
        if (viewCountStr != null) {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 글입니다."));
            // DB에서 가져온 데이터를 문자열로 바꾼 후, 다시 레디스에 저장함
            viewCountStr = post.getView().toString();
            redisUtil.setDataExpire(redisKey, viewCountStr, RedisUtil.REDIS_VIEW_EXPIRE);
        }
        // DB에도 없는 경우.. 가 있는지는 모르겠는데 그 경우 0으로
        return (viewCountStr==null) ? 0:Long.parseLong(viewCountStr);
    }
}
