package com.example.SocialMediaPost.serviceImplementation;

import com.example.SocialMediaPost.dto.Comment;
import com.example.SocialMediaPost.dto.PostDto;
import com.example.SocialMediaPost.dto.Users;
import com.example.SocialMediaPost.exceptions.ResourceNotFoundException;
import com.example.SocialMediaPost.model.Post;
import com.example.SocialMediaPost.repo.PostRepo;
import com.example.SocialMediaPost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private RestTemplate restTemplate;

    private PostDto convertToDto(Post post){
        return new PostDto(post.getId(), post.getContent(), post.getImageUrl(), post.getUserId(), post.getTimestampCreated(), post.getTimestampUpdated(),post.getUser(),post.getLikeCount(),post.getCommentCount());
    }

    @Override
    public ResponseEntity<PostDto> createPost(Post post) {
        Post save = postRepo.save(post);
        return new ResponseEntity<>(convertToDto(save), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PostDto> getById(Long id) {
        Optional<Post> post = postRepo.findById(id);
        if (post.isPresent()){
            return new ResponseEntity<>(convertToDto(post.get()),HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException("Post not found with id " + id);
        }
    }

    @Override
    public ResponseEntity<List<PostDto>> getAll() {
        List<Post> allPosts = postRepo.findAll();
        List<PostDto> collect = allPosts.stream().map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(collect,HttpStatus.OK);
    }


    @Override
    public ResponseEntity<List<Post>> getTotalLikesOnPost(Long postId) {
        List<Post> posts = postRepo.findByUserId(postId);
        for (Post post : posts) {
            String likeServiceUrl = "http://SOCIALMEDIALIKE/likes/count/" + post.getId();
            Long likeCount = restTemplate.getForObject(likeServiceUrl, Long.class);
            post.setLikeCount(likeCount);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK); // Return the list of posts with 200 OK status
    }

    @Override
    public ResponseEntity<List<Post>> getTotalCommentOnPost(Long postId) {
        List<Post> posts = postRepo.findByUserId(postId);
        for (Post comment : posts) {
            String commentServiceUrl = "http://SOCIALMEDIACOMMENT/comment/getById/" + comment.getUserId();
            Long commentCount = restTemplate.getForObject(commentServiceUrl, Long.class);
            comment.setCommentCount(commentCount);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK); // Return the list of posts with 200 OK status
    }

    public ResponseEntity<Post> getTotalLikesAndCommentOnPost(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        String commentServiceUrl = "http://SOCIALMEDIACOMMENT/comment/getById/" + postId;
        List<Comment> comments = restTemplate.exchange(commentServiceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Comment>>() {}).getBody();
        Long commentCount = (comments != null) ? (long) comments.size() : 0;
        post.setCommentCount(commentCount);

        String likeServiceUrl = "http://SOCIALMEDIALIKE/likes/count/" + postId;
        Long likeCount = restTemplate.getForObject(likeServiceUrl, Long.class);
        post.setLikeCount(likeCount);

        String userServiceUrl = "http://SOCIALMEDIAUSER/users/getById/" + post.getUserId();
        Users user = restTemplate.getForObject(userServiceUrl, Users.class);
        post.setUser(user);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }



}
