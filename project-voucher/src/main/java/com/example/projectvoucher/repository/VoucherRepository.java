package com.example.projectvoucher.repository;

import com.example.projectvoucher.entity.VoucherEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherEntity, Long> {

  Optional<VoucherEntity> findByVoucherCode(String voucherCode);
}
