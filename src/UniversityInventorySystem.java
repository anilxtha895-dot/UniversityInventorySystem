import java.util.ArrayList;
import java.util.Scanner;

import exceptions.InventoryException;
import managers.InventoryManager;
import managers.InventoryReports;
import models.Equipment;
import models.InventoryItem;
import models.StaffMember;

public class UniversityInventorySystem {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		// Main storage lists (system database)
		ArrayList<InventoryItem> inventory = new ArrayList<>();
		ArrayList<StaffMember> staffList = new ArrayList<>();

		InventoryManager manager = new InventoryManager(inventory, staffList);
		InventoryReports reports = new InventoryReports(inventory, staffList);

		int choice = -1;

		do {
			System.out.println("\n===== UNIVERSITY INVENTORY SYSTEM =====");
			System.out.println("1. Add new equipment");
			System.out.println("2. Register new staff member");
			System.out.println("3. Assign equipment to staff");
			System.out.println("4. Return equipment");
			System.out.println("5. Search inventory");
			System.out.println("6. Generate reports");
			System.out.println("0. Exit");
			System.out.print("Enter choice: ");

			while (!sc.hasNextInt()) {
			    System.out.println("Please enter a number.");
			    sc.next();
			}
			int warranty = sc.nextInt();
			sc.nextLine();

			try {
				switch (choice) {

				case 1:
					// Add equipment (simple)
					System.out.print("Asset ID: ");
					String assetId = sc.nextLine();

					System.out.print("Name: ");
					String name = sc.nextLine();

					System.out.print("Brand: ");
					String brand = sc.nextLine();

					System.out.print("Category: ");
					String category = sc.nextLine();

					System.out.print("Warranty months: ");
					int warrantyMonths = sc.nextInt();
					sc.nextLine();

					Equipment eq = new Equipment("EQ" + assetId, name, true, assetId, brand, warrantyMonths, category);
					inventory.add(eq);
					System.out.println("Equipment added.");
					break;

				case 2:
					// Add staff
					System.out.print("Staff ID: ");
					int staffId = sc.nextInt();
					sc.nextLine();

					System.out.print("Name: ");
					String staffName = sc.nextLine();

					System.out.print("Email: ");
					String email = sc.nextLine();

					System.out.print("Department: ");
					String dept = sc.nextLine();

					staffList.add(new StaffMember(staffId, staffName, email, dept));
					System.out.println("Staff member registered.");
					break;

				case 3:
					// Assign equipment
					System.out.print("Enter Staff ID: ");
					int sid = sc.nextInt();
					sc.nextLine();

					System.out.print("Enter Equipment Asset ID: ");
					String aid = sc.nextLine();

					manager.assignEquipment(sid, aid);
					System.out.println("Assigned.");
					break;

				case 4:
					// Return equipment
					System.out.print("Enter Staff ID: ");
					int rsid = sc.nextInt();
					sc.nextLine();

					System.out.print("Enter Equipment Asset ID: ");
					String raid = sc.nextLine();

					manager.returnEquipment(rsid, raid);
					System.out.println("Returned.");
					break;

				case 5:
					// Search inventory
					System.out.print("Search by name: ");
					String criteria = sc.nextLine();
					manager.searchEquipment(criteria);
					break;

				case 6: {
					int reportChoice;

					do {
						System.out.println("\n===== REPORTS MENU =====");
						System.out.println("1. Inventory Report");
						System.out.println("2. Expired Warranties ");
						System.out.println("3. Assignments by Department");
						System.out.println("4. Utilisation Rate");
						System.out.println("5. Maintenance Schedule");
						System.out.println("0. Back to main menu");
						System.out.print("Choose an option: ");

						while (!sc.hasNextInt()) {
							System.out.println("Please enter a number.");
							sc.next();
						}
						reportChoice = sc.nextInt();
						sc.nextLine(); // consume newline

						switch (reportChoice) {
						case 1:
							reports.generateInventoryReport();
							break;
						case 2:
							reports.findExpiredWarranties();
							break;
						case 3:
							reports.displayAssignmentsByDepartment();
							break;
						case 4:
							reports.calculateUtilisationRate();
							break;
						case 5:
							reports.generateMaintenanceSchedule();
							break;
						case 0:
							System.out.println("Returning to main menu...");
							break;
						default:
							System.out.println("Invalid.");
						}

					} while (reportChoice != 0);

					break;
				}

				case 0:
					System.out.println("Exiting... Bye!");
					break;

				default:
					System.out.println("Invalid.");
				}

			} catch (InventoryException e) {
				System.out.println("ERROR: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("Unexpected Error: " + e.getMessage());
			}

		} while (choice != 0);

		sc.close();
	}
}
