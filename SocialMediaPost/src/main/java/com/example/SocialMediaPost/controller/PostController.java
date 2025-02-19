package com.example.SocialMediaPost.controller;


import com.example.SocialMediaPost.dto.PostDto;
import com.example.SocialMediaPost.model.Post;
import com.example.SocialMediaPost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody Post post){
       PostDto createdPost = postService.createPost(post).getBody();
       return ResponseEntity.status(201).body(createdPost);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto postUpdateDto) {
       PostDto updatedPost = postService.updatePost(id, postUpdateDto).getBody();
       return updatedPost != null ? ResponseEntity.ok(updatedPost) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
        boolean isDeleted = postService.deletePost(id).hasBody();
        if (isDeleted) {
            return ResponseEntity.ok("Post deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Post not found"); // Not Found (404)
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable("id") Long id) {
        PostDto postDto = postService.getById(id).getBody();
        return postDto != null ? ResponseEntity.ok(postDto)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PostDto>> getAll() {
        List<PostDto> posts = postService.getAll().getBody();
        return posts.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(posts);
    }



    @GetMapping("/LikesOnPost/{postId}")
    public ResponseEntity<List<Post>> getLikesOnPost(@PathVariable Long postId) {
        ResponseEntity<List<Post>> response = postService.getTotalLikesOnPost(postId);
        if (response.getBody().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getBody());
        }
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/CommentOnPost/{postId}")
    public ResponseEntity<List<Post>> getCommentOnPost(@PathVariable("postId") Long postId) {
        ResponseEntity<List<Post>> response = postService.getTotalCommentOnPost(postId);
        if (response.getBody().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getBody());
        }
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/LikeAndComment/{postId}")
    public ResponseEntity<Post> getTotalLikesAndCommentOnPost(@PathVariable("postId") Long postId) {
        Post response = postService.getTotalLikesAndCommentOnPost(postId).getBody();
        return ResponseEntity.ok(response);
    }






}
