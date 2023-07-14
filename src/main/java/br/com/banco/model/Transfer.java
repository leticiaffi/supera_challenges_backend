package br.com.banco.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import br.com.banco.repositories.converters.TimestampConverter;

@Data
@Entity
@Table(name = "transferencia")
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data_transferencia")
    @Convert(converter = TimestampConverter.class)
    private Timestamp transferDate;

    @Column(name = "valor")
    private BigDecimal value;

    @Column(name = "tipo")
    private String type;

    @Column(name = "nome_operador_transacao")
    private String transactionOperatorName;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Account account;
}
