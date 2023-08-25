package com.coffee.export.fullstack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class MainTest {
    /**
     * Method under test: {@link Main#greet(String)}
     */
    @Test
    void testGreet() {
        GreetResponse actualGreetResult = (new Main()).greet("Name");
        assertEquals("Hello Name", actualGreetResult.greet());
        assertEquals(2, actualGreetResult.languages().size());
        Person personResult = actualGreetResult.person();
        assertEquals(30000.0d, personResult.savings());
        assertEquals("Bond", personResult.lastName());
        assertFalse(personResult.isStudent());
        assertEquals("James", personResult.firstName());
    }

    /**
     * Method under test: {@link Main#greet(String)}
     */
    @Test
    void testGreet2() {
        GreetResponse actualGreetResult = (new Main()).greet(null);
        assertEquals("Hello", actualGreetResult.greet());
        assertEquals(2, actualGreetResult.languages().size());
        Person personResult = actualGreetResult.person();
        assertEquals(30000.0d, personResult.savings());
        assertEquals("Bond", personResult.lastName());
        assertFalse(personResult.isStudent());
        assertEquals("James", personResult.firstName());
    }

    /**
     * Method under test: {@link Main#greet(String)}
     */
    @Test
    void testGreet3() {
        GreetResponse actualGreetResult = (new Main()).greet("");
        assertEquals("Hello", actualGreetResult.greet());
        assertEquals(2, actualGreetResult.languages().size());
        Person personResult = actualGreetResult.person();
        assertEquals(30000.0d, personResult.savings());
        assertEquals("Bond", personResult.lastName());
        assertFalse(personResult.isStudent());
        assertEquals("James", personResult.firstName());
    }
}

