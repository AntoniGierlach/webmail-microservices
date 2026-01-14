package pl.antoni.contacts.dto;

import java.time.Instant;

public class ContactResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Instant createdAt;
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public ContactResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ContactResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ContactResponse setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ContactResponse setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public ContactResponse setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
