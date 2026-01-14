package pl.antoni.contacts;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.antoni.contacts.dto.ContactCreateRequest;
import pl.antoni.contacts.dto.ContactResponse;
import pl.antoni.contacts.dto.ContactUpdateRequest;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository repo;

    public ContactService(ContactRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<ContactResponse> list() {
        return repo.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(this::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ContactResponse get(Long id) {
        Contact c = repo.findById(id).orElseThrow(() -> new ContactNotFoundException(id));
        return toResponse(c);
    }

    @Transactional
    public ContactResponse create(ContactCreateRequest req) {
        if (repo.existsByEmail(req.getEmail())) {
            throw new DuplicateEmailException(req.getEmail());
        }

        Contact c = new Contact()
                .setName(req.getName())
                .setEmail(req.getEmail())
                .setPhone(req.getPhone());

        Contact saved = repo.save(c);
        return toResponse(saved);
    }

    @Transactional
    public ContactResponse update(Long id, ContactUpdateRequest req) {
        Contact c = repo.findById(id).orElseThrow(() -> new ContactNotFoundException(id));

        if (!c.getEmail().equalsIgnoreCase(req.getEmail()) && repo.existsByEmail(req.getEmail())) {
            throw new DuplicateEmailException(req.getEmail());
        }

        c.setName(req.getName())
                .setEmail(req.getEmail())
                .setPhone(req.getPhone());

        return toResponse(repo.save(c));
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ContactNotFoundException(id);
        }
        repo.deleteById(id);
    }

    private ContactResponse toResponse(Contact c) {
        return new ContactResponse()
                .setId(c.getId())
                .setName(c.getName())
                .setEmail(c.getEmail())
                .setPhone(c.getPhone())
                .setCreatedAt(c.getCreatedAt())
                .setUpdatedAt(c.getUpdatedAt());
    }
}
