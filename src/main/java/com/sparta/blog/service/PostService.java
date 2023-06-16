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
    // 전체 게시글 조회를 제목만 나오게 하고,
    // 제목을 선택하면 그 선택한 게시글의 내용이 나오게끔 변경을 해보고싶다.
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    // 게시글 수정 PUT 요청 수행 메서드
    @Transactional
    public Long updatePost(Long id, PostRequestDto requestDto) {
        // 해당 메모 DB에서 조회 및 매칭
        Post post = findPost(id);

        // 수정요청 들어온 값 중 password가 일치 할 시 수정적용
        if (requestDto.getPassword().equals(post.getPassword())) {
            post.update(requestDto);
            System.out.println("수정 성공 확인 메세지");
        } else {
            // 비밀번호 불일치시 반환을 어떻게 해줘야할지 모르겠어서 우선 null을 리턴해주는걸로 했다.
            System.out.println("비밀번호가 일치하지 않습니다.");
            return null;
        }
        return id;
    }

    // 게시글 삭제 DELETE 요청 수행 메서드
    public Long deletePost(Long id, String password) {
        Post post = findPost(id);


        // 받아온 password와 db에서 찾은 게시글의 password 일치여부 확인
        if (password.equals(post.getPassword())) {
            postRepository.delete(post);
            System.out.println("삭제 성공 확인 메세지");
        } else {
            // 비밀번호 불일치시 반환을 어떻게 해줘야할지 모르겠어서 우선 null을 리턴해주는걸로 했다.
            System.out.println("비밀번호가 일치하지 않습니다.");
            return null;
        }
        return id;
    }



    // DB에서 id값으로 조회하여 매칭 해주는 메서드
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다.")
        );
    }
}
