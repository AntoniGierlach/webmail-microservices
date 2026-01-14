package pl.antoni.contacts;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.antoni.contacts.dto.ContactCreateRequest;
import pl.antoni.contacts.dto.ContactResponse;
import pl.antoni.contacts.dto.ContactUpdateRequest;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public List<ContactResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public ContactResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactResponse create(@Valid @RequestBody ContactCreateRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public ContactResponse update(@PathVariable Long id, @Valid @RequestBody ContactUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
