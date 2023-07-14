package br.com.banco.controllers;


import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TransferControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnTransferData() throws Exception {
        mockMvc.perform(get("/transfer")
                       .contentType("application/json"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.*", hasSize(6)))
               .andExpect(jsonPath("$[4].id", is(5)))
               .andExpect(jsonPath("$[4].transferDate", is("2020-06-08T07:15:01.000+00:00")))
               .andExpect(jsonPath("$[4].value", is(3241.23)))
               .andExpect(jsonPath("$[4].type", is("TRANSFERENCIA")))
               .andExpect(jsonPath("$[4].transactionOperatorName", is("Beltrano")))
               .andExpect(jsonPath("$[4].account.id", is(1)))
               .andExpect(jsonPath("$[4].account.responsibleName", is("Fulano")))
               .andExpect(jsonPath("$[1].transactionOperatorName", is(emptyOrNullString())));
    }

    @Test
    void shouldReturnAllTransfers() throws Exception {
        mockMvc.perform(get("/transfer")
                       .contentType("application/json"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.*", hasSize(6)));
    }

    @Test
    void shouldReturnTransfersByStartDate() throws Exception {
        mockMvc.perform(get("/transfer")
                       .contentType("application/json")
                       .param("startDate", "2019-08-07T08:12:45.000+03:00"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    void shouldReturnTransfersByStartDateAndOperator() throws Exception {
        mockMvc.perform(get("/transfer")
                       .contentType("application/json")
                       .param("startDate", "2019-08-07T08:12:45.000+03:00")
                       .param("operator", "Ronnyscley"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    void shouldReturnTransfersByEndDate() throws Exception {
        mockMvc.perform(get("/transfer")
                       .contentType("application/json")
                       .param("endDate", "2019-08-07T08:12:45.000+03:00"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.*", hasSize(4)));
    }

    @Test
    void shouldReturnTransfersByEndDateAndOperator() throws Exception {
        mockMvc.perform(get("/transfer")
                       .contentType("application/json")
                       .param("endDate", "2024-08-07T05:12:45.000+00:00")
                       .param("operator", "Ronnyscley"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    void shouldReturnTransfersByStartAndEndDate() throws Exception {
        mockMvc.perform(get("/transfer")
                       .contentType("application/json")
                       .param("startDate", "2019-01-01T12:00:00.000+03:00")
                       .param("endDate", "2019-05-04T08:12:45.000+03:00"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    void shouldReturnTransfersByOperator() throws Exception {
        mockMvc.perform(get("/transfer")
                       .contentType("application/json")
                       .param("operator", "Beltrano"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    void shouldReturnTransfersByOperatorAndStartAndEndDate() throws Exception {
        mockMvc.perform(get("/transfer")
                       .contentType("application/json")
                       .param("startDate", "2019-08-07T08:12:45.000+03:00")
                       .param("endDate", "2024-08-07T05:12:45.000+03:00")
                       .param("operator", "Beltrano"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.*", hasSize(1)));
    }
}