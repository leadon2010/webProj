package employee;

public class Employee {
	private String employeeId;
	private String firstName;
	private String lastName;
	private int salary;
	private String hireDate;
	private String email;
	private String jobId;

	public Employee() {
	}

	public Employee(String firstName, int salary, String email) {
		super();
		this.firstName = firstName;
		this.salary = salary;
		this.email = email;
	}

	public Employee(String lastName, String hireDate, String email, String jobId) {
		super();
		this.lastName = lastName;
		this.hireDate = hireDate;
		this.email = email;
		this.jobId = jobId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", salary=" + salary + ", hireDate=" + hireDate + ", email=" + email + ", jobId=" + jobId + "]";
	}

}
