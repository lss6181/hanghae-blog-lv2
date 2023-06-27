package com.sparta.blog.service;

import com.sparta.blog.dto.PostRequestDto;
import com.sparta.blog.dto.PostResponseDto;
import com.sparta.blog.entity.Post;
import com.sparta.blog.entity.User;
import com.sparta.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 블로그 글 작성 POST 요청 수행 메서드
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        // 요청받은 dto를 entity객체로 생성 + DB에 저장
        Post post = postRepository.save(new Post(requestDto, user));
        // responseDto 반환
        return new PostResponseDto(post);
    }

    // 전체 게시글 목록 조회 메서드
    @Transactional(readOnly = true) // 지연로딩위해
    public List<PostResponseDto> getPosts(User user) {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    // 선택된 게시글 조회 GET 요청 수행 메서드
    public PostResponseDto getPostsBySelected(Long id, User user) {
        Post post = findPost(id);
        return new PostResponseDto(post);
    }

    // 게시글 수정 PUT 요청 수행 메서드
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) throws Exception {
        // 해당 메모 DB에서 조회 및 매칭
        Post post = findPost(id);

        // 해당 사용자가 작성한 게시글인지 체크 후 수정 적용
        if (user.getUsername().equals(post.getUser().getUsername())) {
            post.update(requestDto);
            System.out.println("수정 성공 확인 메세지");

        } else {
            throw new IllegalArgumentException("수정 권한이 없는 게시글 입니다.");
        }
        return new PostResponseDto(post);
    }

    // 게시글 삭제 DELETE 요청 수행 메서드
    @Transactional
    public Boolean deletePost(Long id, User user) {
        Post post = findPost(id);
        boolean successDelete = true;

        // // 해당 사용자가 작성한 게시글인지 체크 후 삭제 적용
        if (!user.getUsername().equals(post.getUser().getUsername())) {
            throw new IllegalArgumentException("삭제 권한이 없는 게시글 입니다.");
        } else {
            postRepository.delete(post);
            System.out.println("삭제 성공 확인 메세지");
        }
        return successDelete;
    }

    // DB에서 id값으로 조회하여 매칭 해주는 메서드
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다.")
        );
    }
}
