package org.wappli.transfer.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.wappli.transfer.server.domain.AccountBalance;
import org.wappli.transfer.server.domain.AmountTransfer;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long>, JpaSpecificationExecutor<AccountBalance> {
}
