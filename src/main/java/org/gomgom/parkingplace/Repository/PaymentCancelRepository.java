package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.PaymentCancel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentCancelRepository extends JpaRepository<PaymentCancel, Long> {

}
