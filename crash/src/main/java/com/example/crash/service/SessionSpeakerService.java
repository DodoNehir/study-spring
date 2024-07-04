package com.example.crash.service;

import com.example.crash.exception.sessionspeaker.SpeakerNotFoundException;
import com.example.crash.model.entity.SessionSpeakerEntity;
import com.example.crash.model.speaker.SessionSpeaker;
import com.example.crash.model.speaker.SessionSpeakerCreateRequestBody;
import com.example.crash.model.speaker.SessionSpeakerUpdateRequestBody;
import com.example.crash.repository.SessionSpeakerRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class SessionSpeakerService {

  private final SessionSpeakerRepository sessionSpeakerRepository;

  public SessionSpeakerService(SessionSpeakerRepository sessionSpeakerRepository) {
    this.sessionSpeakerRepository = sessionSpeakerRepository;
  }


  public List<SessionSpeaker> getSessionSpeakers() {
    return sessionSpeakerRepository.findAll().stream().map(SessionSpeaker::from).toList();

  }

  public SessionSpeaker getSessionSpeakerBySpeakerId(Long speakerId) {
    return SessionSpeaker.from(getSessionSpeakerEntityBySpeakerId(speakerId));
  }


  public SessionSpeakerEntity getSessionSpeakerEntityBySpeakerId(Long speakerId) {
    return sessionSpeakerRepository.findById(speakerId)
        .orElseThrow(() -> new SpeakerNotFoundException(speakerId));
  }

  public SessionSpeaker createSessionSpeaker(SessionSpeakerCreateRequestBody requestBody) {
    // 같은 회사에 같은 이름의 speaker 가 있을 수도 있으니 별도의 중복 체크는 없음.
    SessionSpeakerEntity sessionSpeakerEntity = SessionSpeakerEntity.of(requestBody.company(),
        requestBody.name(), requestBody.description());
    return SessionSpeaker.from(sessionSpeakerRepository.save(sessionSpeakerEntity));
  }

  public SessionSpeaker updateSessionSpeaker(Long speakerId,
      SessionSpeakerUpdateRequestBody requestBody) {
    // 이미 있었어야 수정하든 삭제하든 가능함
    SessionSpeakerEntity speakerEntity = getSessionSpeakerEntityBySpeakerId(speakerId);

    // 각 항목이 null 일 수도 있으므로 확인해서 수정해줌
    if (!ObjectUtils.isEmpty(requestBody.company())) {
      speakerEntity.setCompany(requestBody.company());
    }
    if (!ObjectUtils.isEmpty(requestBody.name())) {
      speakerEntity.setName(requestBody.name());
    }
    if (!ObjectUtils.isEmpty(requestBody.description())) {
      speakerEntity.setDescription(requestBody.description());
    }

    return SessionSpeaker.from(sessionSpeakerRepository.save(speakerEntity));
  }

  public void deleteSessionSpeaker(Long sessionSpeakerId) {
    SessionSpeakerEntity speakerEntity = getSessionSpeakerEntityBySpeakerId(sessionSpeakerId);
    sessionSpeakerRepository.delete(speakerEntity);
  }
}
