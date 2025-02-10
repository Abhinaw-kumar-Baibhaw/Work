package com.example.SocialMediaPost.controller;


import com.example.SocialMediaPost.dto.PostDto;
import com.example.SocialMediaPost.model.Post;
import com.example.SocialMediaPost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody Post post){
       return postService.createPost(post);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable("id") Long id){
       return postService.getById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PostDto>> getAll(){
       return postService.getAll();
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
        ResponseEntity<Post> response = postService.getTotalLikesAndCommentOnPost(postId);
        return response;
    }






}
