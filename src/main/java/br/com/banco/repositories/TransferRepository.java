package br.com.banco.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.banco.model.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query("select t from Transfer t " +
            "WHERE (:operator is null or t.transactionOperatorName = :operator) " +
            "AND (:startDate is null or t.transferDate >= :startDate) " +
            "AND (:endDate is null or t.transferDate <= :endDate)")
    List<Transfer> findAllByTransferDateAndOperator(@Param("startDate") Timestamp startDate,
                                                    @Param("endDate") Timestamp endDate,
                                                    @Param("operator") String operator);

}
