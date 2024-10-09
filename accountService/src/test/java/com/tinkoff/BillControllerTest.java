package com.tinkoff;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BillControllerTest {

    private static final String RESPONSE_TEMPLATE = "{\"id\":1,\"customerID\":1,\"sum\":2622.00}";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getBillByIdTest(){
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/bill/1", String.class))
                                                    .contains(RESPONSE_TEMPLATE);
    }

}
