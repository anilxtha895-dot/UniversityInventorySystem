package models;

public class StaffMember {

	private int staffId;
	private String name;
	private String email;
	private String department;

	private Equipment[] assignedEquipment;
	private int assignedCount;

	// Basic constructor (department defaults to "General")
	public StaffMember(int staffId, String name, String email) {
		this(staffId, name, email, "General");
	}

	// Overloaded constructor (allows department)
	public StaffMember(int staffId, String name, String email, String department) {
		this.staffId = staffId;
		this.name = name;
		this.email = email;
		this.department = (department == null || department.trim().isEmpty()) ? "General" : department.trim();

		this.assignedEquipment = new Equipment[5];
		this.assignedCount = 0;
	}

	public int getStaffId() {
		return staffId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = (department == null || department.trim().isEmpty()) ? "General" : department.trim();
	}

	public boolean addAssignedEquipment(Equipment equipment) {
		if (equipment == null) return false;
		if (assignedCount >= 5) return false;

		assignedEquipment[assignedCount] = equipment;
		assignedCount++;
		return true;
	}

	public boolean removeAssignedEquipment(String assetId) {
		if (assetId == null) return false;

		for (int i = 0; i < assignedEquipment.length; i++) {
			Equipment eq = assignedEquipment[i];
			if (eq != null && eq.getAssetId().equalsIgnoreCase(assetId)) {

				// shift left
				for (int j = i; j < assignedEquipment.length - 1; j++) {
					assignedEquipment[j] = assignedEquipment[j + 1];
				}
				assignedEquipment[assignedEquipment.length - 1] = null;

				assignedCount--;
				return true;
			}
		}
		return false;
	}

	public int getAssignedEquipmentCount() {
		return assignedCount;
	}

	public Equipment[] getAssignedEquipment() {
		return assignedEquipment;
	}

	@Override
	public String toString() {
		return "StaffMember{staffId=" + staffId +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", department='" + department + '\'' +
				", assignedCount=" + assignedCount + '}';
	}
}
