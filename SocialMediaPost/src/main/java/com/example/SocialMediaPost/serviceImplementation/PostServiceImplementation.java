package com.example.SocialMediaPost.serviceImplementation;

import com.example.SocialMediaPost.dto.Comment;
import com.example.SocialMediaPost.dto.PostDto;
import com.example.SocialMediaPost.dto.Users;
import com.example.SocialMediaPost.exceptions.ResourceNotFoundException;
import com.example.SocialMediaPost.model.Post;
import com.example.SocialMediaPost.repo.PostRepo;
import com.example.SocialMediaPost.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PostServiceImplementation implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImplementation.class);

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private RestTemplate restTemplate;

    private PostDto convertToDto(Post post){
        return new PostDto(post.getId(), post.getContent(), post.getImageUrl(), post.getUserId(), post.getTimestampCreated(), post.getTimestampUpdated(),post.getUser(),post.getLikeCount(),post.getCommentCount());
    }

    @Override
    public ResponseEntity<PostDto> createPost(Post post) {
        logger.info("Creating post with user ID: {}", post.getUserId());
        Post savedPost = postRepo.save(post);
        logger.info("Post created successfully with ID: {}", savedPost.getId());
        return new ResponseEntity<>(convertToDto(savedPost), HttpStatus.CREATED);
    }

    @Override
    @CacheEvict(value = "post", key = "#postId")
    public ResponseEntity<PostDto> updatePost(Long postId, PostDto postDto) {
        logger.info("Updating post with ID: {}", postId);
        Optional<Post> existingPost = postRepo.findById(postId);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setContent(postDto.getContent());
            post.setImageUrl(postDto.getImageUrl());
            post.setTimestampUpdated(postDto.getTimestampUpdated());
            Post updatedPost = postRepo.save(post);
            PostDto updated = convertToDto(updatedPost);
            logger.info("Post updated successfully with ID: {}", postId);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        logger.error("Post not found with ID: {}", postId);
        throw new ResourceNotFoundException("Post not found with id: " + postId);
    }

    @Override
    @CacheEvict(value = "post", key = "#postId")
    public ResponseEntity<String> deletePost(Long postId) {
        Optional<Post> postOptional = postRepo.findById(postId);
        if (postOptional.isPresent()) {
            postRepo.deleteById(postId);  // Deleting the post from the database
            logger.info("Post with ID: {} has been deleted.", postId);
            return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
        } else {
            logger.error("Post not found with ID: {}", postId);
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Cacheable(value = "post", key = "#id", unless = "#result == null")
    public ResponseEntity<PostDto> getById(Long id) {
        logger.info("Fetching post with ID: {}", id);
        Optional<Post> post = postRepo.findById(id);
        if (post.isPresent()) {
            logger.info("Post found with ID: {}", id);
            return new ResponseEntity<>(convertToDto(post.get()), HttpStatus.OK);
        } else {
            logger.error("Post not found with ID: {}", id);
            throw new ResourceNotFoundException("Post not found with id " + id);
        }
    }

    @Override
    public ResponseEntity<List<PostDto>> getAll() {
        logger.info("Fetching all posts");
        List<Post> allPosts = postRepo.findAll();
        List<PostDto> postDtos = allPosts.stream().map(this::convertToDto).collect(Collectors.toList());
        logger.info("Fetched {} posts", postDtos.size());
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }


    @Override
    @Cacheable(value = "postLikes", key = "#postId1")
    public ResponseEntity<List<Post>> getTotalLikesOnPost(Long postId1) {
        logger.info("Fetching total likes for post with ID: {}", postId1);
        List<Post> posts = postRepo.findByUserId(postId1);
        for (Post post : posts) {
            String likeServiceUrl = "http://SOCIALMEDIALIKE/likes/count/" + postId1;
            Long likeCount = restTemplate.getForObject(likeServiceUrl, Long.class);
            post.setLikeCount(likeCount);
            logger.debug("Post ID: {} has {} likes", post.getId(), likeCount);
        }
        logger.info("Fetched total likes for {} posts", posts.size());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Override
    @Cacheable(value = "postComments", key = "#postId", unless = "#result.isEmpty()")
    public ResponseEntity<List<Post>> getTotalCommentOnPost(Long postId) {
        logger.info("Fetching total comments for post with ID: {}", postId);
        List<Post> posts = postRepo.findByUserId(postId);
        for (Post comment : posts) {
            String commentServiceUrl = "http://SOCIALMEDIACOMMENT/comment/getById/" + comment.getUserId();
            Long commentCount = restTemplate.getForObject(commentServiceUrl, Long.class);
            comment.setCommentCount(commentCount);
            logger.debug("Post ID: {} has {} comments", comment.getId(), commentCount);
        }
        logger.info("Fetched total comments for {} posts", posts.size());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Cacheable(value = "likesAndComments",key = "#postId")
    public ResponseEntity<Post> getTotalLikesAndCommentOnPost(Long postId) {
        logger.info("Fetching total likes and comments for post with ID: {}", postId);
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        String commentServiceUrl = "http://SOCIALMEDIACOMMENT/comment/getById/" + postId;
        List<Comment> comments = restTemplate.exchange(commentServiceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Comment>>() {}).getBody();
        Long commentCount = (comments != null) ? (long) comments.size() : 0;
        post.setCommentCount(commentCount);
        logger.debug("Post ID: {} has {} comments", postId, commentCount);

        String likeServiceUrl = "http://SOCIALMEDIALIKE/likes/count/" + postId;
        Long likeCount = restTemplate.getForObject(likeServiceUrl, Long.class);
        post.setLikeCount(likeCount);
        logger.debug("Post ID: {} has {} likes", postId, likeCount);

        String userServiceUrl = "http://SOCIALMEDIAUSER/users/getById/" + post.getUserId();
        Users user = restTemplate.getForObject(userServiceUrl, Users.class);
        post.setUser(user);

        logger.info("Fetched total likes and comments for post with ID: {}", postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
