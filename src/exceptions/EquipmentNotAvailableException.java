package exceptions;

public class EquipmentNotAvailableException extends InventoryException {

    public EquipmentNotAvailableException() {
        super();
    }

    public EquipmentNotAvailableException(String message) {
        super(message);
    }
}
