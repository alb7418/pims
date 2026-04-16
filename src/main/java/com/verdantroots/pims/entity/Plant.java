package com.verdantroots.pims.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Common name is required.")
    @Size(max = 100, message = "Common name must be 100 characters or less.")
    @Column(nullable = false, length = 100)
    private String commonName;

    @Size(max = 150, message = "Scientific name must be 1550 characters or less.")
    @Column(length = 150)
    private String scientificName;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must be 50 characters or less.")
    @Column(nullable = false, length = 50)
    private String category;

    @NotBlank(message = "Supplier is required")
    @Size(max = 100, message = "Supplier must be 100 characters or less.")
    @Column(nullable = false, length = 100)
    private String supplier;

    @NotNull(message = "Quantity in stock is required.")
    @Min(value = 0, message = "Quantity in stock cannot be negative.")
    @Column(nullable = false)
    private Integer quantityInStock;

    @NotNull(message = "Reorder level is required.")
    @Min(value = 0, message = "Reorder level cannot be negative.")
    @Column(nullable = false)
    private Integer reorderLevel;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative.")
    @Digits(integer = 8, fraction = 2, message = "Price must be a valid amount.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Size(max = 100, message = "Location must be 100 characters or less.")
    @Column(length = 100)
    private String location;

    @Size(max = 50, message = "Sunlight needs must be 50 characters or less.")
    @Column(length = 50)
    private String sunlightNeeds;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    public Plant() {

    }

    @PrePersist
    @PreUpdate
    public void updateTimeStamp() {
        this.lastUpdated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName != null ? commonName.trim() : null;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName != null ? scientificName.trim() : null;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category != null ? category.trim() : null;
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

    public String getSunlightNeeds() {
        return sunlightNeeds;
    }

    public void setSunlightNeeds(String sunlightNeeds) {
        this.sunlightNeeds = sunlightNeeds != null ? sunlightNeeds.trim() : null;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

}
