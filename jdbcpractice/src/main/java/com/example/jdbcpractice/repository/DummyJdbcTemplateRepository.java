package com.example.jdbcpractice.repository;

import com.example.jdbcpractice.entity.Dummy;
import lombok.val;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DummyJdbcTemplateRepository implements DummyRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate writeTransactionOperations;
    private final TransactionTemplate readTransactionOperations;

    public DummyJdbcTemplateRepository(JdbcTemplate jdbcTemplate,
                                       TransactionTemplate writeTransactionOperations,
                                       TransactionTemplate readTransactionOperations) {
        this.jdbcTemplate = jdbcTemplate;
        this.writeTransactionOperations = writeTransactionOperations;
        this.readTransactionOperations = readTransactionOperations;
    }


    /**
     * 트랜잭션 템플릿을 사용하는 방법.
     * write, read 트랜잭션 오퍼레이션을 사용한다.
     * 기존의 코드를 execute() 안의 람다로 넣으면 됨
     */
    @Override
    public Dummy save(Dummy dummy) {
        return writeTransactionOperations.execute(status -> {
            val sql = "INSERT INTO DUMMY VALUES (?, ?)";
            jdbcTemplate.update(sql, LocalDateTime.now().getNano(), dummy.getName());
            return dummy;
        });
    }

    @Override
    public Optional<Dummy> findByName(String name) {
        return readTransactionOperations.execute(status -> {
            val sql = "SELECT * FROM DUMMY WHERE NAME = ?";
            List<Dummy> dummies = jdbcTemplate.query(sql,
                    (ResultSet resultSet, int rowNum) -> {
                        Dummy dummy = new Dummy();
                        dummy.setName(resultSet.getString("NAME"));
                        dummy.setId(resultSet.getInt("ID"));
                        return dummy;
                    }, name);

            return Optional.ofNullable(dummies.get(0));
        });
    }

    /**
     * 트랜잭션 탬플릿을 사용하지 않고 어노테이션 사용법은 그냥 메서드 위에 붙이면 됨
     * 만약 검색만 하는 거면 @Transactional(readOnly = true) 붙이면 됨
     */
    @Override
    @Transactional
    public int updateNameById(int id, String name) {
        val sql = "UPDATE DUMMY SET NAME = ? WHERE ID = ?";

        int updatedRow = jdbcTemplate.update((Connection con) -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            return preparedStatement;
        });

        return updatedRow;
    }

    @Override
    @Transactional
    public int deletedById(int id) {
        val sql = "DELETE FROM DUMMY WHERE ID = ?";

        return jdbcTemplate.update((Connection con) -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement;
        });
    }
}
