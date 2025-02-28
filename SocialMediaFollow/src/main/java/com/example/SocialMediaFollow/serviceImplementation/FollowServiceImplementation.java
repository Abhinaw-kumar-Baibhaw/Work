package com.example.SocialMediaFollow.serviceImplementation;

import com.example.SocialMediaFollow.dto.FollowDto;
import com.example.SocialMediaFollow.exception.FollowersNotFoundException;
import com.example.SocialMediaFollow.model.Follow;
import com.example.SocialMediaFollow.model.Users;
import com.example.SocialMediaFollow.repo.FollowRepo;
import com.example.SocialMediaFollow.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowServiceImplementation implements FollowService {

    @Autowired
    private FollowRepo followRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate<String , String> kafkaTemplate;

    private FollowDto convertToDto(Follow follow) {
        List<Follow> follows = followRepo.findByFollowedIdOrderByCreatedAtDesc(follow.getFollowedId());
        int followerCount = 0;
        if (!follows.isEmpty()) {
            Follow latestFollow = follows.get(0);
            followerCount = latestFollow.getFollowerCount();
        }
        follow.setFollowerCount(followerCount);
        String followerServiceUrl = "http://SOCIALMEDIAUSER/users/getById/" + follow.getFollowedId();
        Users follower = restTemplate.getForObject(followerServiceUrl, Users.class);
        follow.setUsers(follower);
        return new FollowDto(follow.getId(), follow.getFollowerId(), follow.getFollowedId(),
                follow.getCreatedAt(), follow.getUsers(), follow.getFollowerCount());
    }



    private List<FollowDto> convertToDtoList(List<Follow> follows) {
        return follows.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public ResponseEntity<FollowDto> follow(Follow follow) {
        Follow existingFollow = followRepo.findByFollowerIdAndFollowedId(follow.getFollowerId(), follow.getFollowedId());
        if (existingFollow != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        follow.setCreatedAt(LocalDateTime.now());
        List<Follow> follows = followRepo.findByFollowedIdOrderByCreatedAtDesc(follow.getFollowedId());
        int followerCount = 0;
        if (!follows.isEmpty()) {
            Follow latestFollow = follows.get(0);
            followerCount = latestFollow.getFollowerCount();
        }
        follow.setFollowerCount(followerCount + 1);
        Follow savedFollow = followRepo.save(follow);
        String message = follow.getFollowerId() +" followed you";
        kafkaTemplate.send("follow-topic",message);
        FollowDto followDto = convertToDto(savedFollow);
        return new ResponseEntity<>(followDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<FollowDto> unfollow(Long followerId, Long followedId) {
        Follow existingFollow = followRepo.findByFollowerIdAndFollowedId(followerId, followedId);
        if (existingFollow == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        followRepo.delete(existingFollow);
        List<Follow> followers = followRepo.findByFollowedId(followedId);
        int updatedFollowerCount = followers.size();
        for (Follow follow : followers) {
            follow.setFollowerCount(updatedFollowerCount);
            followRepo.save(follow);
        }
        FollowDto followDto = convertToDto(existingFollow);
        return new ResponseEntity<>(followDto, HttpStatus.OK);
    }

    public ResponseEntity<List<FollowDto>> getFollowers(Long followedId) {
        List<Follow> followers = followRepo.findByFollowedId(followedId);
        if(followers.isEmpty()){
            throw new FollowersNotFoundException("No followers found for user with ID "+ followedId);
        }
        List<FollowDto> followerDtos = convertToDtoList(followers);
        return new ResponseEntity<>(followerDtos, HttpStatus.OK);
    }

    public ResponseEntity<List<FollowDto>> getFollowing(Long followerId) {
        List<Follow> following = followRepo.findByFollowerId(followerId);
        List<FollowDto> followingDtos = convertToDtoList(following);
        return new ResponseEntity<>(followingDtos, HttpStatus.OK);
    }
}
