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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowServiceImplementation implements FollowService {

    @Autowired
    private FollowRepo followRepo;

    @Autowired
    private RestTemplate restTemplate;

   private FollowDto convertToDto(Follow follow) {
        String followerServiceUrl = "http://SOCIALMEDIAUSER/users/getById/" + follow.getFollowerId();
        Users follower = restTemplate.getForObject(followerServiceUrl, Users.class);
        follow.setUsers(follower);
        return new FollowDto(follow.getId(), follow.getFollowerId(), follow.getFollowedId(),follow.getCreatedAt(), follow.getUsers());
    }


    private List<FollowDto> convertToDtoList(List<Follow> follows) {
        return follows.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<FollowDto> follow(Follow follow) {
        Follow savedFollow = followRepo.save(follow);
        FollowDto followDto = convertToDto(savedFollow);
        return new ResponseEntity<>(followDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<FollowDto> unfollow(String follower) {
        return null;
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
