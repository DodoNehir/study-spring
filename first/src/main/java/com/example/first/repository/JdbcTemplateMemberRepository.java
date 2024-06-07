package com.example.first.repository;

import com.example.first.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class JdbcTemplateMemberRepository implements MemberRepository {

    // JdbcTemplate
    private final JdbcTemplate jdbcTemplate;

    // JdbcTemplate 은 주입 못 받고 datasource를 주입받아서 만들 수 있음
    // 생성자가 딱 하나 있으면 @Autowired 생략할 수 있음
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Member save(Member member) {
        // sql 에 SQL 작성하고, update로 추가
        // id 값이 시간따라 달라질텐데 원하는 아이디는 좀 아닐 걸..
        String sql = "INSERT INTO MEMBER VALUES (?, ?)";
        jdbcTemplate.update(sql, LocalDateTime.now().getNano(), member.getName());
        return member;

        // SimpleJdbcInsert 사용. id 값을 자동 만들 수 있다.
//        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
//        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("name", member.getName());
//
//        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
//        member.setId(key.longValue());
//        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT * FROM MEMBER WHERE id = ?";
        List<Member> result = jdbcTemplate.query(sql, memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "SELECT * FROM MEMBER WHERE name = ?";
        List<Member> result = jdbcTemplate.query(sql, memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM MEMBER", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
