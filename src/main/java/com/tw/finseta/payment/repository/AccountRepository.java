package com.tw.finseta.payment.repository;

import com.tw.finseta.payment.dao.AccountDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository interface for managing AccountDAO entities.
 *
 * <p>Provides data access methods for querying accounts by account number and sort code.</p>
 *
 * @author Ranga Raju
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountDAO, Long> {
    Optional<AccountDAO> findByAccountNumberAndSortCode(String accountNumber, String sortCode);
}
