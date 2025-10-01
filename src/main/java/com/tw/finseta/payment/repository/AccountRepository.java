package com.tw.finseta.payment.repository;

import com.tw.finseta.payment.bo.AccountBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository interface for managing AccountBo entities.
 *
 * <p>Provides data access methods for querying accounts by account number and sort code.</p>
 *
 * @author Ranga Raju
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountBo, Long> {
    Optional<AccountBo> findByAccountNumberAndSortCode(String accountNumber, String sortCode);
}
