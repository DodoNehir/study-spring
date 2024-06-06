package com.example.simpledomain.repository;

import com.example.simpledomain.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    // 메모리니까 저장을 어딘가 하자. 여기선 Map
    private static Map<Long, Member> store = new HashMap<>();
    // sequence 는 키 값 생성을 위함.
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
//        for (Map.Entry<Long, Member> entry : store.entrySet()) {
//            if (entry.getValue().getId().equals(id)) {
//                return Optional.of(entry.getValue());
//            }
//        }
//        return Optional.empty();
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {

        return store.values().stream()
                .filter(m -> m.getName().equals(name))
                .findFirst();


//        for (Map.Entry<Long, Member> entry : store.entrySet()) {
//            if (entry.getValue().getName().equals(name)) {
//                return Optional.of(entry.getValue());
//            }
//        }
//        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
//        return store.values().stream().toList();
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
