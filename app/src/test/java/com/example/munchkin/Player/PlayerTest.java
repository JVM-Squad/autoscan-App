package com.example.munchkin.Player;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlayerTest {

    @Test
    protected void testDefaultConstructor() {
        Player player = new Player();
        assertNull(player.getName(), "Name should be null for default constructor");
    }

    @Test
    protected void testParameterizedConstructor() {
        String testName = "Username";
        Player player = new Player(testName);
        assertEquals(testName, player.getName(), "Name should match the one set in the constructor");
    }
}
