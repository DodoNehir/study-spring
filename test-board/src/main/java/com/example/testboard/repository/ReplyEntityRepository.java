package com.example.testboard.repository;

import com.example.testboard.model.entity.PostEntity;
import com.example.testboard.model.entity.ReplyEntity;
import com.example.testboard.model.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyEntityRepository extends JpaRepository<ReplyEntity, Long> {

  // java객체로 정의된 이름을 사용해야 한다.
  List<ReplyEntity> findByUser(UserEntity userEntity);


  List<ReplyEntity> findByPost(PostEntity postEntity);
}
