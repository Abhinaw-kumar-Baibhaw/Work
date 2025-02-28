package com.example.SocialMediaPost.service;

import com.example.SocialMediaPost.dto.PostDto;
import com.example.SocialMediaPost.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import java.util.List;


public interface PostService {

    ResponseEntity<PostDto> createPost(Post post) throws JsonProcessingException;

    ResponseEntity<PostDto> updatePost(Long postId, PostDto postDto);

    ResponseEntity<String> deletePost(Long postId);

    ResponseEntity<PostDto> getById(Long id);

    ResponseEntity<List<PostDto>> getAll();

    ResponseEntity<List<Post>> getTotalLikesOnPost(Long postId);

    ResponseEntity<List<Post>> getTotalCommentOnPost(Long postId);

    ResponseEntity<Post> getTotalLikesAndCommentOnPost(Long postId);

}
