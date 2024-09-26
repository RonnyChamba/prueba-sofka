package com.assessment.sofka.mscorepersona;

import com.assessment.sofka.mscorepersona.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MsCorePersonaApplicationTests {

    @Test
    void testCustomer() {

        Customer customer = new Customer();
        customer.setPersonId(1);
        customer.setFullName("Ronny");
        customer.setAge(25);
        customer.setAddress("Calle 123");
        customer.setPhone("123456");
        customer.setStatus("Active");

        // assert
        assertEquals(customer.getPersonId(), 1);
        assertEquals(customer.getFullName(), "Ronny");
        assertEquals(customer.getAge(), 25);
        assertEquals(customer.getAddress(), "Calle 123");
        assertEquals(customer.getPhone(), "123456");
        assertEquals(customer.getStatus(), "Active");

    }
}
