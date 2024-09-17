package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.Payment;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author 김경민
 * @Date 2024.09.11
 * 결제 관련 Repository
 * */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Payment p SET p.paymentConfirmed = :paymentConfirmed WHERE p.merchantUid = :merchantUid")
    int updatePaymentStatusByUuid(@Param("merchantUid") String merchantUid, @Param("paymentConfirmed") Bool paymentConfirmed);

    Payment findByMerchantUid(String merchantUid);

}
