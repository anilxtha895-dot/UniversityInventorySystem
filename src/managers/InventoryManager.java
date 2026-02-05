package managers;

import java.util.ArrayList;

import exceptions.AssignmentLimitExceededException;
import exceptions.EquipmentNotAvailableException;
import exceptions.StaffMemberNotFoundException;
import models.Equipment;
import models.InventoryItem;
import models.StaffMember;

public class InventoryManager {

    private final ArrayList<InventoryItem> inventory;
    private final ArrayList<StaffMember> staffList;

    // constructor (receive the same lists from main)
    public InventoryManager(ArrayList<InventoryItem> inventory, ArrayList<StaffMember> staffList) {
        this.inventory = inventory;
        this.staffList = staffList;
    }

    // =========================================================
    // Task 4 - assignEquipment (ID-based, for your MENU)
    // =========================================================
    public void assignEquipment(int staffId, String assetId)
            throws StaffMemberNotFoundException, EquipmentNotAvailableException, AssignmentLimitExceededException {

        StaffMember staff = findStaffById(staffId);
        Equipment equipment = findEquipmentByAssetId(assetId);

        // delegate to the REQUIRED signature method
        assignEquipment(staff, equipment);
    }

    // =========================================================
    // Task 4 - REQUIRED SIGNATURE (StaffMember, Equipment)
    // =========================================================
    public void assignEquipment(StaffMember staff, Equipment equipment)
            throws EquipmentNotAvailableException, AssignmentLimitExceededException {

        validateAssignment(staff, equipment);

        staff.addAssignedEquipment(equipment);
        equipment.setAvailable(false);
    }

    // =========================================================
    // Task 4 - returnEquipment (ID-based, for your MENU)
    // =========================================================
    public void returnEquipment(int staffId, String assetId) throws StaffMemberNotFoundException {

        StaffMember staff = findStaffById(staffId);

        // delegate to the REQUIRED signature method
        returnEquipment(staff, assetId);
    }

    // =========================================================
    // Task 4 - REQUIRED SIGNATURE (StaffMember, String assetId)
    // =========================================================
    public void returnEquipment(StaffMember staff, String assetId) {

        boolean removed = staff.removeAssignedEquipment(assetId);

        if (!removed) {
            System.out.println("Staff does not have equipment " + assetId);
            return;
        }

        Equipment eq = findEquipmentByAssetId(assetId);
        if (eq != null) {
            eq.setAvailable(true);
        }
    }

    // =========================================================
    // Task 4 - searchEquipment overload #1 (name)
    // =========================================================
    public void searchEquipment(String name) {
        System.out.println("Search results:");

        for (int i = 0; i < inventory.size(); i++) {
            InventoryItem item = inventory.get(i);

            String itemName = item.getName();
            if (itemName != null && itemName.toLowerCase().contains(name.toLowerCase())) {
                System.out.println(item);
            }
        }
    }

    // =========================================================
    // Task 4 - searchEquipment overload #2 (category, availableOnly)
    // category here uses item.getItemType() e.g. "Equipment", "Furniture", "LabEquipment"
    // =========================================================
    public void searchEquipment(String category, boolean availableOnly) {
        System.out.println("Search results:");

        for (int i = 0; i < inventory.size(); i++) {
            InventoryItem item = inventory.get(i);

            String itemType = item.getItemType();
            if (itemType != null && itemType.equalsIgnoreCase(category)) {

                if (!availableOnly || item.isAvailable()) {
                    System.out.println(item);
                }
            }
        }
    }

    // =========================================================
    // Task 4 - searchEquipment overload #3 (warranty range)
    // =========================================================
    public void searchEquipment(int minWarranty, int maxWarranty) {
        System.out.println("Search results:");

        for (int i = 0; i < inventory.size(); i++) {
            InventoryItem item = inventory.get(i);

            if (item instanceof Equipment) {
                Equipment eq = (Equipment) item;

                int w = eq.getWarrantyMonths();
                if (w >= minWarranty && w <= maxWarranty) {
                    System.out.println(eq);
                }
            }
        }
    }

    // =========================================================
    // Task 4 - validateAssignment (NESTED if–else REQUIRED)
    // =========================================================
    public void validateAssignment(StaffMember staff, Equipment equipment)
            throws EquipmentNotAvailableException, AssignmentLimitExceededException {

        if (staff != null) {
            if (equipment != null) {
                if (equipment.isAvailable()) {
                    if (staff.getAssignedEquipmentCount() < 5) {
                        return; // valid
                    } else {
                        throw new AssignmentLimitExceededException("Staff already has 5 assigned equipment.");
                    }
                } else {
                    throw new EquipmentNotAvailableException(
                            "Equipment " + equipment.getAssetId() + " is not available.");
                }
            } else {
                throw new EquipmentNotAvailableException("Equipment not found (null).");
            }
        } else {
            // Not part of your custom exception list, so keep as runtime validation
            throw new IllegalArgumentException("Staff is null.");
        }
    }

    // =========================================================
    // Task 4 - calculateMaintenanceFee (IF / SWITCH REQUIRED)
    // =========================================================
    public double calculateMaintenanceFee(Equipment equipment, int daysOverdue) {

        double fee;

        if (daysOverdue <= 0) {
            fee = 0.0;
        } else if (daysOverdue <= 5) {
            fee = daysOverdue * 5.0;
        } else {
            fee = daysOverdue * 10.0;
        }

        return fee;
    }

    // =========================================================
    // helper methods
    // =========================================================
    private StaffMember findStaffById(int staffId) throws StaffMemberNotFoundException {
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getStaffId() == staffId) {
                return staffList.get(i);
            }
        }
        throw new StaffMemberNotFoundException("Staff not found: " + staffId);
    }

    private Equipment findEquipmentByAssetId(String assetId) {
        for (int i = 0; i < inventory.size(); i++) {
            InventoryItem item = inventory.get(i);

            if (item instanceof Equipment) {
                Equipment eq = (Equipment) item;

                String id = eq.getAssetId();
                if (id != null && id.equalsIgnoreCase(assetId)) {
                    return eq;
                }
            }
        }
        return null;
    }
}
