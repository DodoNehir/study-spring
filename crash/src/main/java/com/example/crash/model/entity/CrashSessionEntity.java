package com.example.crash.model.entity;

import com.example.crash.model.crashsession.CrashSessionCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Random;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "crashsession",
    indexes = {
        @Index(name = "crash_sessionid_idx", columnList = "sessionid", unique = true)
    })
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CrashSessionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long sessionId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String body;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private CrashSessionCategory category;

  @Column(nullable = false)
  private ZonedDateTime dateTime;

  @ManyToOne
  @JoinColumn(name = "speakerid")
  private SessionSpeakerEntity speaker;

  public static CrashSessionEntity of(String title, String body, CrashSessionCategory category,
      ZonedDateTime dateTime, SessionSpeakerEntity sessionSpeakerEntity) {
    CrashSessionEntity crashSessionEntity = new CrashSessionEntity();
    crashSessionEntity.setTitle(title);
    crashSessionEntity.setBody(body);
    crashSessionEntity.setCategory(category);
    crashSessionEntity.setDateTime(dateTime);
    crashSessionEntity.setSpeaker(sessionSpeakerEntity);
    return crashSessionEntity;
  }

}
