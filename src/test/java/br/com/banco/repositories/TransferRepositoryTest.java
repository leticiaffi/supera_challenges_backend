package br.com.banco.repositories;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.banco.model.Transfer;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TransferRepositoryTest {

    @Autowired
    private TransferRepository repository;

    @Test
    void shouldListTransfers() {
        final List<Transfer> transfers = repository.findAll();

        assertNotNull(transfers);
        assertFalse(transfers.isEmpty());

        for (Transfer transfer : transfers) {
            assertNotNull(transfer.getId());
            assertNotNull(transfer.getTransferDate());
            assertNotNull(transfer.getValue());
            assertNotNull(transfer.getType());
            assertNotNull(transfer.getAccount());
        }
    }

    @Test
    void shouldReturnAllTransfersWhenParametersAreNull() {
        final List<Transfer> transfers = repository.findAllByTransferDateAndOperator(null, null, null);

        assertNotNull(transfers);
        assertFalse(transfers.isEmpty());
        assertEquals(6, transfers.size());
    }

    @Test
    void shouldReturnTransfersByOperator() {
        final List<Transfer> transfers = repository.findAllByTransferDateAndOperator(null, null, "Beltrano");

        assertNotNull(transfers);
        assertFalse(transfers.isEmpty());
        assertEquals(1, transfers.size());
    }

    @Test
    void shouldReturnTransfersByStartDate() {
        final List<Transfer> transfers = repository.findAllByTransferDateAndOperator(Timestamp.valueOf("2020-06-08 7:15:01"), null, null);

        assertNotNull(transfers);
        assertFalse(transfers.isEmpty());
        assertEquals(2, transfers.size());
    }

    @Test
    void shouldReturnTransfersByStartDateAndOperator() {
        final List<Transfer> transfers = repository.findAllByTransferDateAndOperator(Timestamp.valueOf("2020-06-08 7:15:01"), null, "Beltrano");

        assertNotNull(transfers);
        assertFalse(transfers.isEmpty());
        assertEquals(1, transfers.size());
    }

    @Test
    void shouldReturnTransfersByEndDate() {
        final List<Transfer> transfers = repository.findAllByTransferDateAndOperator(null, Timestamp.valueOf("2019-05-04 05:12:45"), null);

        assertNotNull(transfers);
        assertFalse(transfers.isEmpty());
        assertEquals(3, transfers.size());
    }

    @Test
    void shouldReturnTransfersByEndDateAndOperator() {
        final List<Transfer> transfers = repository.findAllByTransferDateAndOperator(null, Timestamp.valueOf("2022-04-01 12:12:04"), "Beltrano");

        assertNotNull(transfers);
        assertFalse(transfers.isEmpty());
        assertEquals(1, transfers.size());
    }

    @Test
    void shouldReturnTransfersByStartAndEndDate() {
        final List<Transfer> transfers = repository.findAllByTransferDateAndOperator(Timestamp.valueOf("2019-02-03 06:53:27"), Timestamp.valueOf("2019-08-07 05:12:45"), null);

        assertNotNull(transfers);
        assertFalse(transfers.isEmpty());
        assertEquals(3, transfers.size());
    }

    @Test
    void shouldReturnTransfersByOperatorAndStartAndEndDate() {
        final List<Transfer> transfers = repository.findAllByTransferDateAndOperator(Timestamp.valueOf("2019-02-03 06:53:27"), Timestamp.valueOf("2021-04-01 12:12:04"), "Ronnyscley");

        assertNotNull(transfers);
        assertFalse(transfers.isEmpty());
        assertEquals(1, transfers.size());
    }
}