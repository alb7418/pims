package com.verdantroots.pims.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "local_goods")
public class LocalGood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Item name is required.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Category is required.")
    @Column(nullable = false)
    private String category;

    @NotBlank(message = "Local vendor is required.")
    @Column(nullable = false)
    private String localVendor;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero.")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "Quantity in stock is required.")
    @Min(value = 0, message = "Quantity cannot be negative.")
    @Column(nullable = false)
    private Integer quantityInStock;

    @NotNull(message = "Reorder level is required.")
    @Min(value = 0, message = "Reorder level cannot be negative.")
    @Column(nullable = false)
    private Integer reorderLevel;

    @NotBlank(message = "Location is required.")
    @Column(nullable = false)
    private String location;

    private String description;

    @Column(nullable = false)
    private boolean perishable;

    private LocalDate expirationDate;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    public LocalGood() {}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category != null ? category.trim() : null;
    }

    public String getLocalVendor() {
        return localVendor;
    }

    public void setLocalVendor(String localVendor) {
        this.localVendor = localVendor != null ? localVendor.trim() : null;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location != null ? location.trim() : null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }

    public boolean isPerishable() {
        return perishable;
    }

    public void setPerishable(boolean perishable) {
        this.perishable = perishable;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
}