package com.example.exresttemplate.controller;

import com.example.exresttemplate.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// MockMvc 사용하려면 이 어노테이션 달아야 함
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestController testController;

    // MockMvc 사용 때는 필요없음
//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestService testService;

    @Test
    void helloWorld() throws Exception {
        // 오토 와이어 잘 되는 지 확인
//        assertThat(testController).isNotNull();

        // hello world 받아올 수 있는 지 확인
//        String result = restTemplate.getForObject("http://localhost:" + port + "/api/v1/test", String.class);
//        assertThat(result).isEqualTo("Hello World!");

        when(testService.getTest()).thenReturn("Hello Mock");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello Mock"));
    }
}