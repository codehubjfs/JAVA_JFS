package com.hms.driver;

import java.util.Scanner;

public class PatientTester {

	public static void main(String[] args) {
		
		Scanner scan = null;
		
		try {
			scan = new Scanner(System.in);
			System.out.println("Enter patient details(Id,Name,mobilenumber):");
			Patient patient = new Patient(scan.nextInt(),scan.next(),scan.nextLong());
			System.out.println(patient);
		}finally {
			scan.close();
		}
	}
}
