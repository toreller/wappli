package org.wappli.transfer.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.wappli.transfer.server.domain.DepositOrWithdraw;

import java.util.List;

@Repository
public interface DepositOrWithdrawRepository extends JpaRepository<DepositOrWithdraw, Long>, JpaSpecificationExecutor<DepositOrWithdraw> {
    List<DepositOrWithdraw> findByBankAccountId(long bankAccountId);
}
