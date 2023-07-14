package br.com.banco.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.banco.model.Account;
import br.com.banco.model.Transfer;
import br.com.banco.repositories.TransferRepository;

@ExtendWith(MockitoExtension.class)
class TransferControllerTest {

    private static final Timestamp TRANSFER_TIME = Timestamp.from(Instant.now());
    private static final Account ACCOUNT = new Account(1L, "Fulano");
    private static final Transfer TRANSFER = new Transfer(1L, TRANSFER_TIME, BigDecimal.TEN, "SAQUE", "Beltrano", ACCOUNT);
    private static final Date START_DATE = new GregorianCalendar(2022, Calendar.JANUARY, 1).getTime();
    private static final Date END_DATE = new GregorianCalendar(2024, Calendar.FEBRUARY, 25).getTime();
    private static final String OPERATOR = "Fulano";

    @Mock
    private TransferRepository repository;

    @InjectMocks
    private TransferController controller;

    @Test
    void shouldReturnTransferList() {
        when(repository.findAllByTransferDateAndOperator(new Timestamp(START_DATE.getTime()), new Timestamp(END_DATE.getTime()), OPERATOR))
                .thenReturn(Arrays.asList(TRANSFER));
        final List<Transfer> transfers = controller.getTransfers(START_DATE, END_DATE, OPERATOR);

        assertEquals(Arrays.asList(TRANSFER), transfers);
    }

    @Test
    void shouldNotSendStartDateWhenNull() {
        when(repository.findAllByTransferDateAndOperator(null, new Timestamp(END_DATE.getTime()), OPERATOR))
                .thenReturn(Arrays.asList(TRANSFER));
        final List<Transfer> transfers = controller.getTransfers(null, END_DATE, OPERATOR);

        assertEquals(Arrays.asList(TRANSFER), transfers);
    }

    @Test
    void shouldNotSendEndDateWhenNull() {
        when(repository.findAllByTransferDateAndOperator(new Timestamp(START_DATE.getTime()), null, OPERATOR))
                .thenReturn(Arrays.asList(TRANSFER));
        final List<Transfer> transfers = controller.getTransfers(START_DATE, null, OPERATOR);

        assertEquals(Arrays.asList(TRANSFER), transfers);
    }

    @Test
    void shouldNotSendOperatorWhenNull() {
        when(repository.findAllByTransferDateAndOperator(new Timestamp(START_DATE.getTime()), new Timestamp(END_DATE.getTime()), null))
                .thenReturn(Arrays.asList(TRANSFER));
        final List<Transfer> transfers = controller.getTransfers(START_DATE, END_DATE, null);

        assertEquals(Arrays.asList(TRANSFER), transfers);
    }
}