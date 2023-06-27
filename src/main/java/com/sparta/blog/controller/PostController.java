package com.sparta.blog.controller;

import com.sparta.blog.dto.PostRequestDto;
import com.sparta.blog.dto.PostResponseDto;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(requestDto, userDetails.getUser());
    }

    // 전체 게시글 목록 조회 API
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPosts(userDetails.getUser());
    }

    // 선택된 게시글 조회 API
    @GetMapping("/posts/{id}")
    public PostResponseDto getPostsBySelected(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.getPostsBySelected(id, userDetails.getUser());
    }

    // 게시글 수정 API
    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        return postService.updatePost(id, requestDto, userDetails.getUser());
    }

    // 게시글 삭제 API
    @DeleteMapping("/posts/{id}")
    public boolean deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getUser());
    }

}
