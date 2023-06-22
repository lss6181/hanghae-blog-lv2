package com.sparta.blog.controller;

import com.sparta.blog.dto.PostRequestDto;
import com.sparta.blog.dto.PostResponseDto;
import com.sparta.blog.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성 API
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    // 전체 게시글 목록 조회 API
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    // 선택된 게시글 조회 API
    @GetMapping("/posts/{id}")
    public PostResponseDto getPostsBySelected(@PathVariable Long id){
        return postService.getPostsBySelected(id);
    }

    // 게시글 수정 API
    @PutMapping("/posts/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    // 게시글 삭제 API
    @DeleteMapping("/posts/{id}")
    public Long deletePost(@PathVariable Long id, @RequestParam String password) {
        return postService.deletePost(id, password);
    }

}
