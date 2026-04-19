package com.verdantroots.pims.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.math.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplies")
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Item name is required.")
    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String supplier;

    @NotNull(message = "Quantity in stock is required.")
    @Column(nullable = false)
    private Integer quantityInStock;

    @NotNull(message = "Reorder level is required.")
    @Column(nullable = false)
    private Integer reorderLevel;

    @NotNull(message = "Price is required.")
    @Column(nullable = false)
    private BigDecimal price;

    private String location;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    public Supply() {}

    @PreUpdate
    @PrePersist
    public void updateTimestamp() {
        this.lastUpdated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName != null ? itemName.trim() : null;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier != null ? supplier.trim() : null;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location != null ? location.trim() : null;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

}
