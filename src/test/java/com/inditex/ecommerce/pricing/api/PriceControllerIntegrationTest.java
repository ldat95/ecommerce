package com.inditex.ecommerce.pricing.api;

import com.inditex.ecommerce.pricing.api.record.PriceTestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/database/schema.sql", "/database/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PriceControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/prices" ;
    }

    static Stream<PriceTestCase> provideDataForTestsCases(){
        return Stream.of(
                new PriceTestCase("35455", "1", "2020-06-14T10:00:00", "\"priceList\":1", "\"price\":35.50"),
                new PriceTestCase("35455", "1", "2020-06-14T16:00:00", "\"priceList\":2", "\"price\":25.45"),
                new PriceTestCase("35455", "1", "2020-06-14T21:00:00", "\"priceList\":1", "\"price\":35.50"),
                new PriceTestCase("35455", "1", "2020-06-15T10:00:00", "\"priceList\":3", "\"price\":30.50"),
                new PriceTestCase("35455", "1", "2020-06-16T21:00:00", "\"priceList\":4", "\"price\":38.95")
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForTestsCases")
    void testGetPrices(PriceTestCase priceTestCase) {
        String url = UriComponentsBuilder.fromUriString(getBaseUrl())
                .queryParam("productId", priceTestCase.productId())
                .queryParam("brandId", priceTestCase.brandId())
                .queryParam("applicationDate", priceTestCase.applicationDate())
                .toUriString();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(priceTestCase.expectedPriceList());
        assertThat(response.getBody()).contains(priceTestCase.expectedPrice());
    }

    @Test
    void testPriceNotFound() {
        String url = UriComponentsBuilder.fromUriString(getBaseUrl())
                .queryParam("productId", 2)
                .queryParam("brandId", 4)
                .queryParam("applicationDate", "2020-06-14T10:00:00")
                .toUriString();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testPriceGenericError() {
        String url = UriComponentsBuilder.fromUriString(getBaseUrl())
                .queryParam("productId", "testValue1")
                .queryParam("brandId", "testValue1")
                .queryParam("applicationDate", 2)
                .toUriString();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
