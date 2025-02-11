package com.example.SocialMediaPost.service;

import com.example.SocialMediaPost.dto.Comment;
import com.example.SocialMediaPost.dto.PostDto;
import com.example.SocialMediaPost.dto.Users;
import com.example.SocialMediaPost.model.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PostService {

    ResponseEntity<PostDto> createPost(Post post);

    ResponseEntity<PostDto> updatePost(Long postId, PostDto postDto);

    ResponseEntity<String> deletePost(Long postId);

    ResponseEntity<PostDto> getById(Long id);

    ResponseEntity<List<PostDto>> getAll();

    ResponseEntity<List<Post>> getTotalLikesOnPost(Long postId);

    ResponseEntity<List<Post>> getTotalCommentOnPost(Long postId);

    ResponseEntity<Post> getTotalLikesAndCommentOnPost(Long postId);

}
