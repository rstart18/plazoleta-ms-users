package co.com.bancolombia.api;

import co.com.bancolombia.api.rest.owner.OwnerApiRest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiRestTest {

    OwnerApiRest apiRest = new OwnerApiRest();

    @Test
    void apiRestTest() {
        var response = apiRest.commandName();
        assertEquals("", response);
    }
}
