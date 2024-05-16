package com.dayone.objectclasse;

public class Connection {
	
	private int connectionNumber;
	private String customerName;
	private String planName;
	private String connectionType;
	
	public Connection() {
		super();
	}

	public Connection(int connectionNumber, String customerName, String planName, String connectionType) {
		super();
		this.connectionNumber = connectionNumber;
		this.customerName = customerName;
		this.planName = planName;
		this.connectionType = connectionType;
	}

	public int getConnectionNumber() {
		return connectionNumber;
	}

	public void setConnectionNumber(int connectionNumber) {
		this.connectionNumber = connectionNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	@Override
	public String toString() {
		return "Connection [connectionNumber=" + connectionNumber + ", customerName=" + customerName + ", planName="
				+ planName + ", connectionType=" + connectionType + "]";
	}

}
