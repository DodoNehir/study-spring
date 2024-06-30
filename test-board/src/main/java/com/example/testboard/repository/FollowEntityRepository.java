package com.example.testboard.repository;

import com.example.testboard.model.entity.FollowEntity;
import com.example.testboard.model.entity.PostEntity;
import com.example.testboard.model.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowEntityRepository extends JpaRepository<FollowEntity, Long> {

  // java객체로 정의된 이름을 사용해야 한다.
  List<FollowEntity> findByFollower(UserEntity follower);

  List<FollowEntity> findByFollowing(UserEntity following);

  Optional<FollowEntity> findByFollowerAndFollowing(UserEntity follower, UserEntity following);
}
