package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author 김경민
 * @Date 2024.09.11
 * 결제 관련 Repository
 * */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {



}
