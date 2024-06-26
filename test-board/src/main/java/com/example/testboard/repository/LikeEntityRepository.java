package com.example.testboard.repository;

import com.example.testboard.model.entity.LikeEntity;
import com.example.testboard.model.entity.PostEntity;
import com.example.testboard.model.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Long> {

  // java객체로 정의된 이름을 사용해야 한다.
  List<LikeEntity> findByUser(UserEntity userEntity);


  List<LikeEntity> findByPost(PostEntity postEntity);

  Optional<LikeEntity> findByUserAndPost(UserEntity userEntity, PostEntity postEntity);
}
