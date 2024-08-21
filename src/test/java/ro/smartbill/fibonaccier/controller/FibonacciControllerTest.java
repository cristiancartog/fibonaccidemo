package ro.smartbill.fibonaccier.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ro.smartbill.fibonaccier.service.FibonacciComputerService;
import ro.smartbill.fibonaccier.service.exception.UserNotFoundException;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FibonacciController.class)
class FibonacciControllerTest {

    private static final String USER1 = "USER1";

    @MockBean
    private FibonacciComputerService fiboService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGettingNextValueForUser() throws Exception {
        when(fiboService.next(USER1))
                .thenReturn(8);

        mockMvc.perform(
                        post("/fibo/next")
                                .accept(APPLICATION_JSON)
                                .param("user", USER1)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(8)));
    }

    @Test
    void testGettingNextValueForNullUser() throws Exception {
        mockMvc.perform(
                        post("/fibo/next")
                                .accept(APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testGettingNextValueForEmptyUser() throws Exception {
        when(fiboService.next(null)).thenThrow(new UserNotFoundException());

        mockMvc.perform(
                        post("/fibo/next")
                                .accept(APPLICATION_JSON)
                                .param("user", "")
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testGoingBackOneStep() throws Exception {
        mockMvc.perform(
                        post("/fibo/back")
                                .accept(APPLICATION_JSON)
                                .param("user", USER1)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testGoingBackOneStepForNullUser() throws Exception {
        mockMvc.perform(
                        post("/fibo/back")
                                .accept(APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testGoingBackOneStepForEmpty() throws Exception {
        mockMvc.perform(
                        post("/fibo/back")
                                .accept(APPLICATION_JSON)
                                .param("user", "")
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testGettingAllValuesForNullUser() throws Exception {
        mockMvc.perform(
                        get("/fibo/list").accept(APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testGettingAllValuesForEmptyUser() throws Exception {
        mockMvc.perform(
                        get("/fibo/list")
                                .accept(APPLICATION_JSON)
                                .param("user", "")
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testGettingAllValuesForUser() throws Exception {
        when(fiboService.values(USER1))
                .thenReturn(List.of(1, 1, 2, 3, 5));

        mockMvc.perform(
                        get("/fibo/list")
                                .accept(APPLICATION_JSON)
                                .param("user", USER1)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user", equalTo(USER1)))
                .andExpect(jsonPath("$.values", hasSize(5)))
                .andExpect(jsonPath("$.values[0]", equalTo(1)))
                .andExpect(jsonPath("$.values[1]", equalTo(1)))
                .andExpect(jsonPath("$.values[2]", equalTo(2)))
                .andExpect(jsonPath("$.values[3]", equalTo(3)))
                .andExpect(jsonPath("$.values[4]", equalTo(5)));
    }

}