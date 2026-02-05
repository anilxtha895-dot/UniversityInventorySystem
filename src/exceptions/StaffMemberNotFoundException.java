package exceptions;

public class StaffMemberNotFoundException extends InventoryException {

    public StaffMemberNotFoundException() {
        super();
    }

    public StaffMemberNotFoundException(String message) {
        super(message);
    }
}
