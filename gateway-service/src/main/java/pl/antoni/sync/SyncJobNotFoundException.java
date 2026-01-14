package pl.antoni.sync;

public class SyncJobNotFoundException extends RuntimeException {
    public SyncJobNotFoundException(Long id) {
        super("Sync job not found: id=" + id);
    }
}
