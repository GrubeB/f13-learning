package pl.app.common.model.snapshot;

public interface SnapshotException {
    class NullCollectionException extends RuntimeException {
        public NullCollectionException() {
            super("Collection of Snapshots must not be null, to make new snapshot");
        }

        public NullCollectionException(String message) {
            super(message);
        }
    }
}
