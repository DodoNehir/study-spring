package com.example.crash.model.entity;

import com.example.crash.model.user.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(
    name = "sessionspeaker",
    indexes = {
        @Index(name = "speaker_speakerid_idx", columnList = "speakerid", unique = true)
    })
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class SessionSpeakerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long speakerId;

  @Column(nullable = false)
  private String company;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private String profile; // 프로필 사진


  public static SessionSpeakerEntity of(String company, String name, String description) {
    SessionSpeakerEntity speaker = new SessionSpeakerEntity();
    speaker.setCompany(company);
    speaker.setName(name);
    speaker.setDescription(description);
    speaker.setProfile(
        "https://dev-jayce.github.io/public/profile/" + (new Random().nextInt(100) + 1) + ".png"
    );
    return speaker;
  }

}
