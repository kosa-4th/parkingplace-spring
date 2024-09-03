package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

}
