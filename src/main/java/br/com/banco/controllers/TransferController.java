package br.com.banco.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.model.Transfer;
import br.com.banco.repositories.TransferRepository;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransferRepository transferRepository;

    @GetMapping(produces = "application/json")
    public List<Transfer> getTransfers(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate,
                                       @RequestParam(required = false) String operator) {
        return transferRepository.findAllByTransferDateAndOperator(
                startDate != null ? new Timestamp(startDate.getTime()) : null,
                endDate != null ? new Timestamp(endDate.getTime()): null,
                operator
        );
    }

}
