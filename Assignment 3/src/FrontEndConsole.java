/*
 * - Add time out counter to main screen to refresh tables
 * - Commands Import, Wipe, Refresh, New, Save, Restore, Edit, Delete, Sort
 * - Add confirmation for wipe, delete, restore
 * - Error printing 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;  // Import the Scanner class

public class FrontEndConsole {
	private BackEnd backEnd;
	private String appName; 		// Name of the app
	private String consoleLogoFile; // Filepath to logo file
	private String columns; 		// Table Columns
	private String tableBreak;		// Line Break
	private Scanner consoleScanner;
	private String consoleInput;
	
	private Data[] dataOutput; 	// Temp Storage Variable - (data from backEnd)
	private String[] dataInput; // Temp Storage Variable - (data that going to be added to backEnd)
	private int counter; 		// Loop Counter

	public FrontEndConsole(BackEnd backEnd) {
		// Default Options
		this.backEnd = backEnd;
		this.appName = "Student Grade Calculator 2.0 - Console Version"; // Eeew a console version
		this.consoleLogoFile = "consoleLogo.txt";
		this.columns = "| Student\t| Tutorials\t| Assignments\t| Exam\t| Overall Mark\t| GRADE |"; // Table Columns
		this.tableBreak = "---------------------------------------------------------------------------------";
		
		// Initialize some variables;
		this.consoleScanner = new Scanner(System.in);
		this.consoleInput = null;
		
		guiMain(); // Initialize the console 
	
	}

	public void guiMain() {
		System.out.println(this.appName);
		consoleLogo();
		consoleRefreshTable();
		
		while (this.consoleInput == null || !this.consoleInput.equals("exit") || 
			   this.consoleInput.equals("add")  || this.consoleInput.equals("edit")  ||
			   this.consoleInput.equals("refresh") || this.consoleInput.equals("empty") ||
			   this.consoleInput.equals("import") || this.consoleInput.equals("export")) {
			
			System.out.println("\nValid Commands: [ exit, add, edit, delete, refresh, empty, import, export ]"); // Print this then move to next line
			System.out.print("> ");
			this.consoleInput = this.consoleScanner.nextLine();
			
			if (this.consoleInput.equals("add")) {
				this.consoleAddRecord();
				//this.consoleRefreshTable();
			}

			if (this.consoleInput.equals("edit")) {
				this.consoleEditRecord();
			}

			if (this.consoleInput.equals("delete")) {
				this.consoleDeleteRecord();
			}
			
			if (this.consoleInput.equals("refresh")) {
				this.consoleRefreshTable();
			}
			
			if (this.consoleInput.equals("empty")) {
				this.backEnd.wipeData();
				this.consoleRefreshTable();
			}
			
			if (this.consoleInput.equals("import")) {
				this.consoleImportCSV();
				this.consoleRefreshTable();			
			}

			if (this.consoleInput.equals("export")) {
				this.consoleExportCSV();
			}
			
			if (this.consoleInput.equals("exit")) {
				System.out.println("Goodbye!");
				System.exit(0); // Terminate the app, with status 0 successful termination
			}
		}
	}
	
	/*
	 * Method for Editing Records, Cross between Add and Edit 
	 */
	private void consoleEditRecord() {
		System.out.println(" ");
		System.out.println("*** Edit Record ***");
		System.out.print("Record ID: ");
		this.consoleInput = this.consoleScanner.nextLine();
		
		if (!this.consoleInput.matches("^[0-9]+$")) {
			System.out.println("");
			System.out.println("Input Validation Failed!");
			System.out.println("Error: Non Numerical Value Entered!");
		} else {
			int tmpRecordID = Integer.parseInt(this.consoleInput);
			Data currentOBJ = this.dataOutput[tmpRecordID];
			
			System.out.println("SELECTED: [" + tmpRecordID + "] " + currentOBJ.getLastName() + ", " + currentOBJ.getFirstName());
			System.out.println("");
			
			this.dataInput = new String[8];  // Create empty array with size of 8
			
			System.out.println(" ");
			System.out.print("First Name [" + currentOBJ.getFirstName() + "]: ");
			this.dataInput[0] = this.consoleScanner.nextLine();
			// If user has not entered new data for field use pre-existing
			if (this.dataInput[0].isEmpty()) { 
				this.dataInput[0] = currentOBJ.getFirstName();	
			} 
			
			System.out.print("Last Name [" + currentOBJ.getLastName() + "]: ");
			this.dataInput[1] = this.consoleScanner.nextLine();
			// If user has not entered new data for field use pre-existing
			if (this.dataInput[1].isEmpty()) { 
				this.dataInput[1] = currentOBJ.getLastName();	
			}
			
			System.out.println("");
			System.out.println("Please Enter Results for [" + this.dataInput[1] + ", " + this.dataInput[0] + "]: ");
			
			System.out.println("- Tutorials -");
			System.out.print("Marks Given [" +  + currentOBJ.getTutorialScore() + "]: ");
			this.dataInput[2] = this.consoleScanner.nextLine();		
			System.out.print("Total Avaliable Marks [" + currentOBJ.getTutorialTotal() + "]: ");
			this.dataInput[3] = this.consoleScanner.nextLine();
			
			System.out.println(" ");
			System.out.println("- Assignments -");
			System.out.print("Marks Given [" + currentOBJ.getAssignmentsScore() + "]: ");
			this.dataInput[4] = this.consoleScanner.nextLine();		
			System.out.print("Total Avaliable Marks [" + currentOBJ.getAssingmentsTotal() + "]: ");
			this.dataInput[5] = this.consoleScanner.nextLine();
			
			System.out.println(" ");
			System.out.println("- Exam -");
			System.out.print("Marks Given [" + currentOBJ.getExamScore() + "]: ");
			this.dataInput[6] = this.consoleScanner.nextLine();		
			System.out.print("Total Avaliable Marks [" + currentOBJ.getExamTotal() + "]: ");
			this.dataInput[7] = this.consoleScanner.nextLine();
			
			this.counter = 0;
			
			// All other fields - If user has not entered new data for field use pre-existing 
			if (this.dataInput[2].isEmpty()) { this.dataInput[2] = Double.toString(currentOBJ.getTutorialScore()); }
			if (this.dataInput[3].isEmpty()) { this.dataInput[3] = String.valueOf(currentOBJ.getTutorialTotal()); }
			if (this.dataInput[4].isEmpty()) { this.dataInput[4] = Double.toString(currentOBJ.getAssignmentsScore());	}
			if (this.dataInput[5].isEmpty()) { this.dataInput[5] = String.valueOf(currentOBJ.getAssingmentsTotal());	}
			if (this.dataInput[6].isEmpty()) { this.dataInput[6] = Double.toString(currentOBJ.getExamScore()); }
			if (this.dataInput[7].isEmpty()) { this.dataInput[7] = String.valueOf(currentOBJ.getExamTotal()); }
			
			Object[] inputValidation = this.backEnd.processInputs(this.dataInput); // Validate & Submit Data, Await Response
			boolean valid = (Boolean) inputValidation[0];
			
			if (valid) {
				// We only delete the old record if all is validated
				this.backEnd.deleteData(tmpRecordID); // pass int to backend
				
				System.out.println(" ");
				System.out.println((String) inputValidation[1]); // Show Validation Message
				System.out.println(" ");
				this.consoleRefreshTable(); // Refresh the Table
			} else {
				// Inputs were processed, but failed validation
				System.out.println(" ");
				System.out.println((String) inputValidation[1]); // Show Validation Message
				System.out.println(" ");
			}	
		}		
		
	}

	/*
	 * Method for Deleting Records, Self Explanatory 
	 */
	private void consoleDeleteRecord() {
		System.out.println(" ");
		System.out.println("*** Delete Record ***");
		System.out.print("Record ID: ");
		this.consoleInput = this.consoleScanner.nextLine();
		
		if (!this.consoleInput.matches("^[0-9]+$")) {
			System.out.println("");
			System.out.println("Input Validation Failed!");
			System.out.println("Error: Non Numerical Value Entered!");
		} else {
			int tmpRecordID = Integer.parseInt(this.consoleInput);
			Data currentOBJ = this.dataOutput[tmpRecordID];
			
			System.out.println("DELETED: [" + tmpRecordID + "] " + currentOBJ.getLastName() + ", " + currentOBJ.getFirstName());
			System.out.println("");
						
			this.backEnd.deleteData(tmpRecordID); // pass int to backend
			this.consoleRefreshTable(); // Refresh table
		}
		
	}

	/*
	 * Method for Deleting Records, Self Explanatory 
	 */
	private void consoleAddRecord() {
		this.dataInput = new String[8];  // Create empty array with size of 8
		
		System.out.println(" ");
		System.out.println("*** Add Record ***");
		System.out.print("First Name: ");
		this.dataInput[0] = this.consoleScanner.nextLine();
		
		System.out.print("Last Name: ");
		this.dataInput[1] = this.consoleScanner.nextLine();
		
		System.out.println("");
		System.out.println("Please Enter Results for [" + this.dataInput[1] + ", " + this.dataInput[0] + "]: ");
		
		System.out.println("- Tutorials -");
		System.out.print("Marks Given: ");
		this.dataInput[2] = this.consoleScanner.nextLine();		
		System.out.print("Total Avaliable Marks: ");
		this.dataInput[3] = this.consoleScanner.nextLine();
		
		System.out.println(" ");
		System.out.println("- Assignments -");
		System.out.print("Marks Given: ");
		this.dataInput[4] = this.consoleScanner.nextLine();		
		System.out.print("Total Avaliable Marks: ");
		this.dataInput[5] = this.consoleScanner.nextLine();
		
		System.out.println(" ");
		System.out.println("- Exam -");
		System.out.print("Marks Given: ");
		this.dataInput[6] = this.consoleScanner.nextLine();		
		System.out.print("Total Avaliable Marks: ");
		this.dataInput[7] = this.consoleScanner.nextLine();
		
		Object[] inputValidation = this.backEnd.processInputs(this.dataInput); // Validate & Submit Data, Await Response
		boolean valid = (Boolean) inputValidation[0];
		
		if (valid) {
			System.out.println(" ");
			System.out.println((String) inputValidation[1]); // Show Validation Message
			System.out.println(" ");
			this.consoleRefreshTable(); // Refresh the Table
		} else {
			// Inputs were processed, but failed validation
			System.out.println(" ");
			System.out.println((String) inputValidation[1]); // Show Validation Message
			System.out.println(" ");
		}		
	}

	/* 
	 * New table builder using "System.out.format"
	 * Idea from: https://stackoverflow.com/questions/15215326/
	 * Documentation: https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax
	 * Added new method to backend for shorting the decimal places for printing
	 */
	public void consoleRefreshTable() {
		// Fetch Data from backend
		dataOutput = this.backEnd.getInputs();
		
		String leftAlignFormat = "| %-3s | %-18s | %-10s | %-11s | %-6s | %-12s | %-5s |%n";
		
		System.out.format("+-----+--------------------+------------+-------------+--------+--------------+-------+%n");
		System.out.format("| ID  | Student Name       | Tutorials  | Assignments | Exam   | Overall Mark | GRADE |%n");
		System.out.format("|-----|--------------------|------------|-------------|--------|--------------|-------|%n");

		// Dump the data arrays provided to table
		this.counter=0;
		while (this.dataOutput.length > this.counter) {
			Data currentOBJ = this.dataOutput[this.counter];
			System.out.format(leftAlignFormat,
							  this.counter,
							  currentOBJ.getLastName() + ", " + currentOBJ.getFirstName(), 
							  this.backEnd.printableDouble(currentOBJ.getTutorialPercent()),
							  this.backEnd.printableDouble(currentOBJ.getAssignmentsPercent()), 
							  this.backEnd.printableDouble(currentOBJ.getExamPercent()), 
							  this.backEnd.printableDouble(currentOBJ.getOverallTotal()),
							  currentOBJ.getOverallMark());
							   							   
			this.counter++;
		}		
		
		System.out.format("+-----+--------------------+------------+-------------+--------+--------------+-------+%n");		
	}

	// Import a CSV File
	public void consoleImportCSV() {
		System.out.println("Please Enter Filepath to CSV: [usertest.csv]");
		this.consoleInput = consoleScanner.nextLine();
		while (this.consoleInput.isEmpty()) {
			System.out.println("Error: Please Enter Filepath to CSV: [usertest.csv]");
			this.consoleInput = consoleScanner.nextLine();
		}
		this.backEnd.loadCSV(this.consoleInput);
	}

	// Export a CSV File
	// Could easily merge both import and export into a single method, accepting a parameter
	public void consoleExportCSV() {
		System.out.println("Please Enter Filepath to CSV: [usertest.csv]");
		this.consoleInput = consoleScanner.nextLine();
		while (this.consoleInput.isEmpty()) {
			System.out.println("Error: Please Enter Filepath to CSV: [usertest.csv]");
			this.consoleInput = consoleScanner.nextLine();
		}
		this.backEnd.writeCSV(this.consoleInput);
	}	

	/* consoleLogo() - Simple method to open a read a file to console
	 * in this case it a ascii char logo file.
	 */

	private void consoleLogo() {
		Scanner input = null;
		try {
			input = new Scanner(new File(this.consoleLogoFile), "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (input.hasNextLine()) {
   			System.out.println(input.nextLine());
		}
	}

}
