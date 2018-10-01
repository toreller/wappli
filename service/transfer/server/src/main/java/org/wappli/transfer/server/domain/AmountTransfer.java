package org.wappli.transfer.server.domain;

import org.wappli.transfer.api.rest.util.HasId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class AmountTransfer implements HasId, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SEQ_NAME = "seq_amount_transfer_id";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "from_amount_in_out_id")
    DepositOrWithdraw from;

    @OneToOne(optional = false)
    @JoinColumn(name = "to_amount_in_out_id")
    DepositOrWithdraw to;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public DepositOrWithdraw getFrom() {
        return from;
    }

    public void setFrom(DepositOrWithdraw from) {
        this.from = from;
    }

    public DepositOrWithdraw getTo() {
        return to;
    }

    public void setTo(DepositOrWithdraw to) {
        this.to = to;
    }
}
