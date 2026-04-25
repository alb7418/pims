package com.verdantroots.pims;

import com.verdantroots.pims.entity.InventoryRow;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryRowTest {

    @Test
    void lowStock_whenQuantityEqualsReorderLevel() {
        InventoryRow row = new InventoryRow(
                "Aisle 1",
                "Supply",
                "Soil",
                5,
                5,
                LocalDateTime.now()
        );

        assertTrue(row.isLowStock());
    }

    @Test
    void notLowStock_whenQuantityAboveReorderLevel() {
        InventoryRow row = new InventoryRow(
                "Aisle 2",
                "Plant",
                "Fern",
                10,
                5,
                LocalDateTime.now()
        );

        assertFalse(row.isLowStock());
    }
}