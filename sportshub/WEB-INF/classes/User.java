import java.io.Serializable;
public class User implements Serializable{
	private String userId;
	private String password;
	private String fname;
	private String lname;
	private String email;
	private String mno;
	private int role;

	public static final int CUSTOMER = 0;
	public static final int STORE_MANAGER = 1;
	// public static final int SALESMAN = 2;

	public User() {

	}

	public User(String userId, String password, String fname, String lname, String email, String mno, int role) {

		this.userId = userId;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.mno = mno;
		this.role = role;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMno() {
		return mno;
	}

	public void setMno(String mno) {
		this.mno = mno;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
}
