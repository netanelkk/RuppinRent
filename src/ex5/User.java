package ex5;

import java.io.Serializable;

/**
 * User object 
 * @author netanel & guy
 *
 */
public class User implements Serializable {
	
	/**
	 * id - user's israeli id number (9 chars)
	 * licenseYear - licence year of user
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String id;
	private String email;
	private String birthDate;
	private int licenseYear;
	private String password;
	
	public User(String firstName, String lastName, String id, String email, String birthDate, int licenseYear, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.email = email;
		this.birthDate = birthDate;
		this.licenseYear = licenseYear;
		this.password = password;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public int getLicenseYear() {
		return licenseYear;
	}

	public void setLicenseYear(int licenseYear) {
		this.licenseYear = licenseYear;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", id=" + id + ", email=" + email
				+ ", birthDate=" + birthDate + ", licenseYear=" + licenseYear + ", password=" + password + "]";
	}
	
	
	
	
}
