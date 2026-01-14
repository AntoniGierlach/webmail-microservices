package pl.antoni.sync;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sync")
public class SyncController {

    private final SyncOrchestrationService service;

    public SyncController(SyncOrchestrationService service) {
        this.service = service;
    }

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SyncJob request() {
        return service.requestSync();
    }

    @GetMapping("/jobs")
    public List<SyncJob> jobs() {
        return service.listJobs();
    }

    @GetMapping("/jobs/{id}")
    public SyncJob job(@PathVariable Long id) {
        return service.getJob(id);
    }
}
