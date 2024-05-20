package com.hms.driver;

public class Patient {
	
	private int patientId;
	private String patientName;
	private long contactNumber;
	
	public Patient() {
		super();
	}

	public Patient(int patientId, String patientName, long contactNumber) {
		super();
		this.patientId = patientId;
		this.patientName = patientName;
		this.contactNumber = contactNumber;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", patientName=" + patientName + ", contactNumber=" + contactNumber
				+ "]";
	}
}
