package com.eventbooking.dto.response;

import java.time.LocalDateTime;

public class VenueDto {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String description;
    private Integer capacity;
    private Boolean active;
    private LocalDateTime createdAt;

    public VenueDto() {}

    public VenueDto(Long id, String name, String address, String city, String description, Integer capacity, Boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.description = description;
        this.capacity = capacity;
        this.active = active;
        this.createdAt = createdAt;
    }

    public static VenueDtoBuilder builder() {
        return new VenueDtoBuilder();
    }

    public static class VenueDtoBuilder {
        private Long id;
        private String name;
        private String address;
        private String city;
        private String description;
        private Integer capacity;
        private Boolean active;
        private LocalDateTime createdAt;

        public VenueDtoBuilder id(Long id) { this.id = id; return this; }
        public VenueDtoBuilder name(String name) { this.name = name; return this; }
        public VenueDtoBuilder address(String address) { this.address = address; return this; }
        public VenueDtoBuilder city(String city) { this.city = city; return this; }
        public VenueDtoBuilder description(String description) { this.description = description; return this; }
        public VenueDtoBuilder capacity(Integer capacity) { this.capacity = capacity; return this; }
        public VenueDtoBuilder active(Boolean active) { this.active = active; return this; }
        public VenueDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public VenueDto build() {
            return new VenueDto(id, name, address, city, description, capacity, active, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
