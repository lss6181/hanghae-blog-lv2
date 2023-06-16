package com.sparta.blog.service;

import com.sparta.blog.dto.PostRequestDto;
import com.sparta.blog.dto.PostResponseDto;
import com.sparta.blog.entity.Post;
import com.sparta.blog.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 블로그 글 작성 POST 요청 수행 메서드
    public PostResponseDto createPost(PostRequestDto requestDto) {
        // 요청받은 dto를 entity객체로 생성해주기
        Post post = new Post(requestDto);

        // DB에 저장
        Post savePost = postRepository.save(post);

        // 반환해주기 위해 entity객체를 가지고 responseDto 객체로 생성
        PostResponseDto postResponseDto = new PostResponseDto(post);

        return postResponseDto;
    }

    // 전체 게시글 조회 GET 요청 수행 메서드
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    // 게시글 수정 PUT 요청 수행 메서드
    public Long updatePost(Long id, PostRequestDto requestDto) {
        // 해당 메모 DB에서 조회 및 매칭
        Post post = findPost(id);

        // 수정요청 들어온 값 중 password가 불일치 시 수정 불가능
        if (requestDto.getPassword() != post.getPassword()) {
            new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        } else {
            post.update(requestDto);
        }

        return id;
    }


    // DB에서 조회 매칭 해주는 메서드
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다.")
        );
    }
}
