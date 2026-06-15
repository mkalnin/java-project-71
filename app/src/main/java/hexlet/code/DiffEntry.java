package hexlet.code;

public class DiffEntry {
    private final DiffStatus status;
    private final Object oldValue;
    private final Object newValue;

    public DiffEntry(DiffStatus status, Object oldValue, Object newValue) {
        this.status = status;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public DiffStatus getStatus() {
        return status;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }
}