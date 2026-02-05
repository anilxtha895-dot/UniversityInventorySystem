package exceptions;

public class AssignmentLimitExceededException extends InventoryException {

    public AssignmentLimitExceededException() {
        super();
    }

    public AssignmentLimitExceededException(String message) {
        super(message);
    }
}
