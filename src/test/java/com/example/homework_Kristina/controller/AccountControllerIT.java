package com.example.homework_Kristina.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCreateAccount() throws Exception {
        String json = """
        {
            "name": "Jane Mary",
            "phoneNr": "+37254872537"
        }
        """;

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Jane Mary"))
                .andExpect(jsonPath("$.phoneNr").value("+37254872537"));

    }

    @Test
    void shouldGetAccountById() throws Exception {
        String json = """
        {
            "name": "Jane Mary",
            "phoneNr": "+37254872573"
        }
        """;


        MvcResult addAccount = mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Jane Mary"))
                .andExpect(jsonPath("$.phoneNr").value("+37254872573"))
                .andReturn();

        String response = addAccount.getResponse().getContentAsString();
        Number id = JsonPath.read(response, "$.id");
        long accountId = id.longValue();

        mockMvc.perform(get("/accounts/" + accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jane Mary"))
                .andExpect(jsonPath("$.phoneNr").value("+37254872573"));
    }

    @Test
    void shouldUpdateAccountName() throws Exception {
        String json = """
        {
            "name": "Jane Mary",
            "phoneNr": "+37254872538"
        }
        """;


        MvcResult addAccount = mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Jane Mary"))
                .andExpect(jsonPath("$.phoneNr").value("+37254872538"))
                //.andExpect(jsonPath("$.phoneNr").isEmpty())
                .andReturn();

        String response = addAccount.getResponse().getContentAsString();
        Number id = JsonPath.read(response, "$.id");
        long accountId = id.longValue();

        String newJson = """
        {
            "name": "Paul",
            "phoneNr": "+37254872538"
        }
        """;

        mockMvc.perform(put("/accounts/" + accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId))
                .andExpect(jsonPath("$.name").value("Paul"))
                .andExpect(jsonPath("$.phoneNr").value("+37254872538"));
    }
    @Test
    void shouldUpdateAccountNameAndPhoneNr() throws Exception {
        String json = """
        {
            "name": "Jane Mary",
            "phoneNr": "+37254872539"
        }
        """;


        MvcResult addAccount = mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Jane Mary"))
                .andExpect(jsonPath("$.phoneNr").value("+37254872539"))
                .andReturn();

        String response = addAccount.getResponse().getContentAsString();
        Number id = JsonPath.read(response, "$.id");
        long accountId = id.longValue();

        String newJson = """
        {
            "name": "Paul"
        }
        """;

        mockMvc.perform(put("/accounts/" + accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId))
                .andExpect(jsonPath("$.name").value("Paul"))
                .andExpect(jsonPath("$.phoneNr").isEmpty());
    }

    @Test
    void shouldThrowExceptionOnNonExistingUpdateAccount() throws Exception {
        String json = """
        {
            "name": "Paul"
        }
        """;

        mockMvc.perform(put("/accounts/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account not found"));
    }

    @Test
    void deleteAccountById() throws Exception {
        String json = """
        {
            "name": "Jane Mary",
            "phoneNr": "+37254872540"
        }
        """;

        MvcResult addAccount = mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();

        String response = addAccount.getResponse().getContentAsString();
        Number id = JsonPath.read(response, "$.id");
        long accountId = id.longValue();

        mockMvc.perform(delete("/accounts/" + accountId))
                .andExpect(status().isOk());
    }
}