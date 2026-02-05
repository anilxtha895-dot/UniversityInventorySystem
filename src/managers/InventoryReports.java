package managers;

import java.util.ArrayList;

import models.Equipment;
import models.InventoryItem;
import models.StaffMember;

public class InventoryReports {

	private final ArrayList<InventoryItem> inventory;
	private final ArrayList<StaffMember> staffList;

	public InventoryReports(ArrayList<InventoryItem> inventory, ArrayList<StaffMember> staffList) {
		this.inventory = inventory;
		this.staffList = staffList;
	}

	// FOR LOOP
	public void generateInventoryReport() {
		System.out.println("\nInventory Report:");
		for (int i = 0; i < inventory.size(); i++) {
			System.out.println((i + 1) + ". " + inventory.get(i));
		}
		System.out.println("Total items: " + inventory.size());
	}

	// WHILE LOOP
	public void findExpiredWarranties() {
		System.out.println("\nExpired Warranties:");
		int i = 0;
		int found = 0;

		while (i < inventory.size()) {
			InventoryItem item = inventory.get(i);

			if (item instanceof Equipment) {
				Equipment eq = (Equipment) item;
				if (eq.getWarrantyMonths() <= 0) {
					found++;
					System.out.println(found + ". " + eq.getAssetId() + " - " + eq.getName());
				}
			}
			i++;
		}

		if (found == 0) {
			System.out.println("None found.");
		}
	}

	// ENHANCED FOR LOOP
	public void displayAssignmentsByDepartment() {
		System.out.println("\nAssignments by Department:");

		ArrayList<String> deptNames = new ArrayList<>();
		ArrayList<Integer> deptTotals = new ArrayList<>();

		for (StaffMember staff : staffList) {
			String dept = staff.getDepartment();
			int count = staff.getAssignedEquipmentCount();

			int index = -1;
			for (int i = 0; i < deptNames.size(); i++) {
				if (deptNames.get(i).equalsIgnoreCase(dept)) {
					index = i;
					break;
				}
			}

			if (index == -1) {
				deptNames.add(dept);
				deptTotals.add(count);
			} else {
				deptTotals.set(index, deptTotals.get(index) + count);
			}
		}

		for (int i = 0; i < deptNames.size(); i++) {
			System.out.println(deptNames.get(i) + ": " + deptTotals.get(i) + " item(s) assigned");
		}

		if (deptNames.isEmpty()) {
			System.out.println("No staff/assignments.");
		}
	}

	// NESTED LOOPS
	public void calculateUtilisationRate() {
		System.out.println("\nUtilisation Rate:");

		int totalEquipment = 0;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) instanceof Equipment) {
				totalEquipment++;
			}
		}

		int totalAssigned = 0;

		for (int s = 0; s < staffList.size(); s++) {
			StaffMember staff = staffList.get(s);
			Equipment[] assigned = staff.getAssignedEquipment();

			for (int e = 0; e < assigned.length; e++) {
				if (assigned[e] != null) {
					totalAssigned++;
				}
			}
		}

		if (totalEquipment == 0) {
			System.out.println("0% (no equipment)");
			return;
		}

		double rate = (totalAssigned * 100.0) / totalEquipment;
		System.out.printf("Assigned: %d/%d (%.2f%%)\n", totalAssigned, totalEquipment, rate);
	}

	// DO-WHILE LOOP
	public void generateMaintenanceSchedule() {
		System.out.println("\nMaintenance Schedule:");

		if (inventory.isEmpty()) {
			System.out.println("No inventory.");
			return;
		}

		int i = 0;
		int shown = 0;

		do {
			InventoryItem item = inventory.get(i);
			if (!item.isAvailable()) {
				shown++;
				System.out.println(shown + ". " + item.getName() + " (" + item.getItemType() + ")");
			}
			i++;
		} while (i < inventory.size());

		if (shown == 0) {
			System.out.println("Nothing needs maintenance right now.");
		}
	}
}
