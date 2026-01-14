package pl.antoni.contacts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContactCreateRequest {

    @NotBlank
    @Size(max = 120)
    private String name;

    @NotBlank
    @Email
    @Size(max = 254)
    private String email;

    @Size(max = 40)
    private String phone;

    public String getName() {
        return name;
    }

    public ContactCreateRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactCreateRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ContactCreateRequest setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
