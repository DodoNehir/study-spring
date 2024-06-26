package com.example.testboard.service;

import com.example.testboard.exception.follow.FollowAlreadyExistsException;
import com.example.testboard.exception.follow.FollowNotFoundException;
import com.example.testboard.exception.follow.InvalidFollowException;
import com.example.testboard.exception.post.PostNotFoundException;
import com.example.testboard.exception.user.UserAlreadyExistsException;
import com.example.testboard.exception.user.UserNotAllowedException;
import com.example.testboard.exception.user.UserNotFoundException;
import com.example.testboard.model.entity.FollowEntity;
import com.example.testboard.model.entity.LikeEntity;
import com.example.testboard.model.entity.PostEntity;
import com.example.testboard.model.entity.UserEntity;
import com.example.testboard.model.user.Follower;
import com.example.testboard.model.user.LikedUser;
import com.example.testboard.model.user.User;
import com.example.testboard.model.user.UserAuthenticationResponse;
import com.example.testboard.model.user.UserPatchRequestBody;
import com.example.testboard.repository.FollowEntityRepository;
import com.example.testboard.repository.LikeEntityRepository;
import com.example.testboard.repository.PostEntityRepository;
import com.example.testboard.repository.UserEntityRepository;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

  private UserEntityRepository userEntityRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private FollowEntityRepository followEntityRepository;
  private PostEntityRepository postEntityRepository;
  private LikeEntityRepository likeEntityRepository;
  private JwtService jwtService;

  public UserService(
      UserEntityRepository userEntityRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      FollowEntityRepository followEntityRepository,
      PostEntityRepository postEntityRepository,
      LikeEntityRepository likeEntityRepository,
      JwtService jwtService
  ) {
    this.userEntityRepository = userEntityRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.followEntityRepository = followEntityRepository;
    this.postEntityRepository = postEntityRepository;
    this.likeEntityRepository = likeEntityRepository;
    this.jwtService = jwtService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }

  public User signUp(String username, String password) {
    // 이미 있는 지 확인
    userEntityRepository
        .findByUsername(username)
        .ifPresent(user -> {
          throw new UserAlreadyExistsException();
        });

    // savedUserEntity: JPA가 관리하는 객체. ID값도 있을 것임
    UserEntity savedUserEntity = userEntityRepository.save(
        UserEntity.of(username, bCryptPasswordEncoder.encode(password)));

    return User.from(savedUserEntity);
  }

  public UserAuthenticationResponse authenticate(String username, String password) {
    // username 확인
    UserEntity userEntity = userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    // password 확인
    if (bCryptPasswordEncoder.matches(password, userEntity.getPassword())) {
      // jwt 생성
      String accessToken = jwtService.generateAccessToken(userEntity.getUsername());
      // UserAuthenticationResponse로 return
      return new UserAuthenticationResponse(accessToken);
    } else {
      throw new UserNotFoundException();
    }

  }

  public List<User> getUsers(String query, UserEntity currentUser) {
    List<UserEntity> userEntities;

    if (query != null && !query.isBlank()) {
      // 검색어로 user 검색
      userEntities = userEntityRepository.findByUsernameContaining(query);
    } else {
      // 모든 유저 검색
      userEntities = userEntityRepository.findAll();
    }

    return userEntities.stream()
        .map(userEntity -> getUserWithFollowingStatus(userEntity, currentUser))
        .toList();
  }

  public User getUser(String username, UserEntity currentUser) {
    UserEntity userEntity = userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    return getUserWithFollowingStatus(userEntity, currentUser);
  }

  private User getUserWithFollowingStatus(UserEntity userEntity, UserEntity currentUser) {
    boolean isFollowing =
        followEntityRepository.findByFollowerAndFollowing(currentUser, userEntity)
            .isPresent();
    return User.from(userEntity, isFollowing);
  }

  public User updateUser(String username, UserPatchRequestBody userPatchRequestBody,
      UserEntity currentUser) {

    UserEntity userEntity = userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    if (!userEntity.equals(currentUser)) {
      throw new UserNotAllowedException();
    }

    if (userPatchRequestBody != null) {
      userEntity.setDescription(userPatchRequestBody.description());
    }

    return User.from(userEntityRepository.save(userEntity));
  }

  @Transactional
  public User follow(String username, UserEntity currentUser) {
    UserEntity following = userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    if (following.equals(currentUser)) {
      throw new InvalidFollowException("A user cannot follow itself");
    }

    followEntityRepository.findByFollowerAndFollowing(following, currentUser)
        .ifPresent(followEntity -> {
          throw new FollowAlreadyExistsException(currentUser, following);
        });

    followEntityRepository.save(FollowEntity.of(currentUser, following));

    following.setFollowersCount(following.getFollowersCount() + 1);
    currentUser.setFollowingsCount(following.getFollowingsCount() + 1);
    userEntityRepository.saveAll(List.of(following, currentUser));

    return User.from(following, true);
  }

  @Transactional
  public User unfollow(String username, UserEntity currentUser) {
    UserEntity following = userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    if (following.equals(currentUser)) {
      throw new InvalidFollowException("A user cannot unfollow itself");
    }

    FollowEntity followEntity = followEntityRepository.findByFollowerAndFollowing(following,
            currentUser)
        .orElseThrow(FollowNotFoundException::new);
    followEntityRepository.delete(followEntity);

    following.setFollowingsCount(Math.max(0, following.getFollowingsCount() - 1));
    currentUser.setFollowersCount(Math.max(0, following.getFollowersCount() - 1));
    userEntityRepository.saveAll(List.of(following, currentUser));

    return User.from(following, false);
  }

  public List<Follower> getFollowers(String username, UserEntity currentUser) {
    // username 을 구독한 사람들의 리스트.
    // SELECT * from follow WHERE following = username
    UserEntity following = userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    List<FollowEntity> followers = followEntityRepository.findByFollowing(following);

    return followers.stream()
        .map(followEntity -> Follower.from(
            getUserWithFollowingStatus(followEntity.getFollower(), currentUser),
            followEntity.getCreatedDateTime()))
        .toList();
  }

  public List<User> getFollowings(String username, UserEntity currentUser) {
    // username 이 구독하고 있는 사람들의 리스트
    // SELECT * from follow WHERE follower = username
    UserEntity follower = userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    List<FollowEntity> followings = followEntityRepository.findByFollower(follower);

    return followings.stream()
        .map(followEntity -> getUserWithFollowingStatus(followEntity.getFollowing(), currentUser))
        .toList();
  }

  public List<LikedUser> getLikedUsersByPostId(Long postId, UserEntity currentUser) {
    PostEntity postEntity = postEntityRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException(postId));

    List<LikeEntity> likeEntities = likeEntityRepository.findByPost(postEntity);

    return likeEntities.stream()
        .map(
            likeEntity -> getLikedUserWithFollowingStatus(likeEntity, postEntity, currentUser))
        .toList();
  }

  private LikedUser getLikedUserWithFollowingStatus(LikeEntity likeEntity, PostEntity postEntity,
      UserEntity currentUser) {
    return LikedUser.from(
        getUserWithFollowingStatus(likeEntity.getUser(), currentUser),
        postEntity.getPostId(),
        likeEntity.getCreatedDateTime());
  }

  public List<LikedUser> getLikedUsersByUser(String username, UserEntity currentUser) {

    UserEntity userEntity = userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    List<PostEntity> postEntities = postEntityRepository.findByUser(userEntity);

    return postEntities.stream()
        .flatMap(
            postEntity -> likeEntityRepository.findByPost(postEntity).stream()
                .map(
                    likeEntity -> getLikedUserWithFollowingStatus(likeEntity, postEntity,
                        currentUser)
                ))
        .toList();
  }
}
