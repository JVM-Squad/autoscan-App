package com.example.munchkin.DTO;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntityDTOTest {

    @Test
    public void testEntityDTOAttributes() {
        EntityDTO entity = new EntityDTO("Entity", 3, 420);
        assertEquals("Entity", entity.getName());
        assertEquals(3, entity.getZone());
        assertEquals(420, entity.getId());

        entity.setName("New Entity");
        assertEquals("New Entity", entity.getName());

        entity.setZone(2);
        assertEquals(2, entity.getZone());

        entity.setId(69);
        assertEquals(69, entity.getId());
    }

    @Test
    public void testDefaultConstructor() {
        EntityDTO entity = new EntityDTO();
        assertNull(entity.getName());
        assertEquals(0, entity.getZone());
        assertEquals(0, entity.getId());
    }
}
