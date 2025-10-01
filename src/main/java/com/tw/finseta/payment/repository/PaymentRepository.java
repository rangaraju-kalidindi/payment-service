package com.tw.finseta.payment.repository;

import com.tw.finseta.payment.dao.PaymentDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Spring Data repository interface for managing PaymentDAO entities.
 *
 * <p>Provides data access and query capabilities for payments, including
 * filtering by currency and amount criteria.</p>
 *
 * @author Ranga Raju
 */
public interface PaymentRepository extends JpaRepository<PaymentDAO, Long> {

    @Query("SELECT p FROM PaymentDAO p WHERE " +
            "(:currencies IS NULL OR p.currency IN :currencies) AND " +
            "(:minAmount IS NULL OR p.amount >= :minAmount)")
    List<PaymentDAO> findByCurrencyInAndAmountGreaterThanEqual(
            @Param("currencies") List<String> currencies,
            @Param("minAmount") BigDecimal minAmount);
}