package com.verdantroots.pims.entity;

import java.time.LocalDateTime;

public class InventoryRow {

    private String location;
    private String type;
    private String name;
    private Integer quantity;
    private Integer reorderLevel;
    private LocalDateTime lastUpdated;

    public InventoryRow(String location, String type, String name,
                        Integer quantity, Integer reorderLevel,
                        LocalDateTime lastUpdated) {
        this.location = location;
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.lastUpdated = lastUpdated;
    }

    public String getLocation() { return location; }
    public String getType() { return type; }
    public String getName() { return name; }
    public Integer getQuantity() { return quantity; }
    public Integer getReorderLevel() { return reorderLevel; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }

    public boolean isLowStock() {
        return quantity != null && reorderLevel != null && quantity <= reorderLevel;
    }
}