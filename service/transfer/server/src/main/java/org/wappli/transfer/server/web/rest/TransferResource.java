package org.wappli.transfer.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.wappli.admin.api.rest.dto.entities.BankAccountDTO;
import org.wappli.admin.api.rest.interfaces.BankAccountWebInterface;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.server.web.util.HeaderUtil;
import org.wappli.common.server.web.util.ResponseUtil;
import org.wappli.common.server.web.util.URIUtil;
import org.wappli.transfer.api.rest.dto.input.DepositOrWithdrawDTO;
import org.wappli.transfer.api.rest.dto.input.AmountTransferDTO;
import org.wappli.transfer.api.rest.dto.output.CurrentAccountBalanceDTO;
import org.wappli.transfer.api.rest.dto.output.IdDTO;
import org.wappli.transfer.api.rest.interfaces.TransferInterface;
import org.wappli.transfer.server.domain.DepositOrWithdraw;
import org.wappli.transfer.server.service.DepositOrWithdrawService;
import org.wappli.transfer.server.service.mapper.DepositOrWithdrawMapper;

import java.util.List;

import static org.wappli.common.server.config.Constants.BE_API_V;

@RestController
public class TransferResource implements TransferInterface {
    private static final Logger log = LoggerFactory.getLogger(TransferResource.class);
    private static String entityUrl = "/depositOrWithdraw";

    private BankAccountWebInterface bankAccountRemote;
    private DepositOrWithdrawService service;
    private DepositOrWithdrawMapper mapper;

    public TransferResource(BankAccountWebInterface bankAccountRemote, DepositOrWithdrawService service, DepositOrWithdrawMapper mapper) {
        this.bankAccountRemote = bankAccountRemote;
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<IdDTO> depositOrWithdraw(DepositOrWithdrawDTO depositOrWithdrawDTO) {
        checkBankAccountExists(depositOrWithdrawDTO);

        DepositOrWithdraw entity = service.create(mapper.toEntity(depositOrWithdrawDTO));
        long id = entity.getId();

        return ResponseEntity.created(URIUtil.createURI(BE_API_V + entityUrl + "/" + id))
                .body(new IdDTO().id(id));
    }

    private void checkBankAccountExists(DepositOrWithdrawDTO depositOrWithdrawDTO) {
        ResponseEntity<EntityWithIdOutputDTO<BankAccountDTO>> bankAccountResponse = bankAccountRemote.get(depositOrWithdrawDTO.getBankAccountId());

        ResponseUtil.unwrap(log, bankAccountResponse);
    }

    @Override
    public ResponseEntity<IdDTO> transfer(AmountTransferDTO amountTransfer) {
        return null;
    }

    @Override
    public ResponseEntity<CurrentAccountBalanceDTO> getCurrentAccountBalance(Long accountId) {
        return null;
    }

    @Override
    public ResponseEntity<List<DepositOrWithdrawDTO>> getAllDepositOrWithdraws(Long accountId) {
        return null;
    }
}
