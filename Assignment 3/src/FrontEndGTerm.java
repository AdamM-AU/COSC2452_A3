import java.awt.Font; // GTerm dosn't give us this or is it mentioned in the documentation... Required for SetFont

public class FrontEndGTerm {
	private BackEnd backEnd; // private: for this class only
	private GTerm gtMain;
	private GTerm gtSub;
	
	private String appName;
	private String appFont;
	private int appFontSize;
	
	private int gtMainX;
	private int mainTableIndex; 
	private String mainTableColumns;
	
	private int subTableIndex;
	private String subTableColumns;
	
	private int[] inputIndexs; // Array of Index's for input fields
	private String[] inputData; // Array of Data from Index Fields
	private Data[] dataStorage;  // Storage all all data, Multidimensional array
	private int processCounter;
	private int gtMainResetCounter;
	private int counter; // Counter used for dataStorage array rebuilds, table refreshes
	
	public FrontEndGTerm(BackEnd backEnd) {
		this.backEnd = backEnd;

		// Defaults for GUI
		this.appName = "Student Grade Calculator 2.0";
		this.appFont = "SANS_SERIF"; // Set Default Font
		this.appFontSize = 12; // Set Default Font Size
		
	   /* 
	    * Here i have pushed the GTerm code for each window in to their own method
	    * in an attempt to make the source code cleaner and easier to read
	    */
		this.guiMain();
		this.guiSub();
		this.refreshSubTable(); // Initialize table contents 
	}
	
	/*
	 * guiMain() - Contains all the code used to create the gtMain GTerm Window
	 */
	public void guiMain() {
		this.gtMainX = 320; // gtMain Window Width
		
		// Build Our GTerm Window
		this.gtMain = new GTerm(this.gtMainX,500); // Main Application Window
		this.gtMain.setBackgroundColor(244,241,222); // Set Background Color of Main Window
		this.gtMain.setTitle(this.appName); // Set Application Window Title
		
		this.mainTableColumns = "Grade\tGrade Name\tMark Range (%)";
		
		// Window Contents
		this.gtMain.setXY(5, 0); //center text
		this.gtMain.addImageIcon("logo.png");
		this.gtMain.setXY(10,18); // Return X to 0 and adjust Y position, GTERM needs an getY/setY & getX/setX
		this.gtMain.println(""); // Blank line
		
		this.inputIndexs = new int[8]; // Create array size of 8 (keys: 0 - 7)
		
		// Student Name
		this.GTLine(this.gtMain,new Object[]{"BOLD", 12}); // Custom Method > see GTLine()
		this.gtMain.print("First Name:\t");
		this.GTLine(this.gtMain,null); // Custom Method > see GTLine()
		this.inputIndexs[0] = this.gtMain.addTextField("Jane",150);
		
		this.GTLine(this.gtMain,new Object[]{"BOLD", 12, 1}); // Custom Method > see GTLine()
		this.gtMain.print("Last Name:\t");
		this.GTLine(this.gtMain,null); // Custom Method > see GTLine()
		this.inputIndexs[1] = this.gtMain.addTextField("Smith",150);		
		
		// Tutorial effort submissions - Questions
		this.GTLine(this.gtMain,new Object[]{"BOLD", 15});
		this.gtMain.print("\n\nTutorial effort submissions:\n");
		
		this.GTLine(this.gtMain, new Object[]{"BOLD", 12});
		this.gtMain.print("Received\t");
		this.GTLine(gtMain,null);
		this.inputIndexs[2] = this.gtMain.addTextField("0",50);
		
		this.GTLine(this.gtMain, new Object[]{"BOLD", 12});
		this.gtMain.print("\tout of\t");
		this.GTLine(gtMain,null);
		this.inputIndexs[3] = this.gtMain.addTextField("35",50);
		this.GTLine(this.gtMain, new Object[]{"BOLD", 12});
		this.gtMain.print("\tmarks.\t");
		this.GTLine(gtMain,null);
		
		// Assignment - Questions
		this.GTLine(this.gtMain,new Object[]{"BOLD", 15});
		this.gtMain.print("\n\nAssignments:\n");
		
		this.GTLine(this.gtMain, new Object[]{"BOLD", 12});
		this.gtMain.print("Received\t");
		this.GTLine(gtMain,null);
		this.inputIndexs[4] = this.gtMain.addTextField("0",50);
		
		this.GTLine(this.gtMain, new Object[]{"BOLD", 12});
		this.gtMain.print("\tout of\t");
		this.GTLine(gtMain,null);
		this.inputIndexs[5] = this.gtMain.addTextField("35",50);
		this.GTLine(this.gtMain, new Object[]{"BOLD", 12});
		this.gtMain.print("\tmarks.\t");
		this.GTLine(gtMain,null);		
		
		// Exam - Questions
		this.GTLine(this.gtMain,new Object[]{"BOLD", 15});
		this.gtMain.print("\n\nExam:\n");
		
		this.GTLine(this.gtMain, new Object[]{"BOLD", 12});
		this.gtMain.print("Received\t");
		this.GTLine(gtMain,null);
		this.inputIndexs[6] = this.gtMain.addTextField("0",50);
		
		this.GTLine(this.gtMain, new Object[]{"BOLD", 12});
		this.gtMain.print("\tout of\t");
		this.GTLine(gtMain,null);
		this.inputIndexs[7] = this.gtMain.addTextField("35",50);
		this.GTLine(this.gtMain, new Object[]{"BOLD", 12});
		this.gtMain.print("\tmarks.\t");
		this.GTLine(gtMain,null);
		this.gtMain.println("\n\n"); // Empty line x2
		
		// Window Buttons
		this.gtMain.setFontColor(0,100,0); // Green Button Text
		this.gtMain.addButton("Calculate", this, "addData"); // Process user input
		this.gtMain.print(" "); //Add Whitespace between buttons
		this.gtMain.setFontColor(100,0,0); // Red Button Text
		this.gtMain.addButton("Reset", this, "resetGTMain"); // Reset input form
		this.gtMain.setFontColor(0,0,0); // Green Button Text
		this.gtMain.println("\n\n\n\n"); // Empty Line x5
		
		// Grading Table - Contains Static Data
		this.GTLine(this.gtMain, new Object[]{"BOLD-ITALIC", 12});
		this.gtMain.println("Grading Table");
		this.gtMain.setFont(this.appFont,Font.PLAIN,12); // Set Font: SANS_SERIF, Set PLAIN, Size 12
		this.mainTableIndex = this.gtMain.addTable(290, 120, this.mainTableColumns); // Create Table and Column Names
		this.gtMain.addRowToTable(this.mainTableIndex,"HD\tHigh Distinction\t80 - 100"); 
		this.gtMain.addRowToTable(this.mainTableIndex,"DI\tDistinction\t70 - 79");
		this.gtMain.addRowToTable(this.mainTableIndex,"CR\tCredit\t60 - 69");
		this.gtMain.addRowToTable(this.mainTableIndex,"PA\tPass\t50 - 59");
		this.gtMain.addRowToTable(this.mainTableIndex,"NN\tFail\t0 - 49");
	}

	/*
	 * guiSub() - Contains all the code used to create the gtSub GTerm Window
	 */
	public void guiSub() {
		this.gtSub = new GTerm(900,400); // Processed Data Window (SubWindow) 
		this.gtSub.setBackgroundColor(244,241,222); // Set Background Color of Sub Window

		this.subTableColumns = "Student\tTutorial effort submissions\tAssignments\tExam\tOveral Mark\tGRADE";
		
		// Build our GTerm Window
		this.gtSub.setTitle(this.appName + " - Results"); // Set Application Window Title
		this.subTableIndex = this.gtSub.addTable(894, 295, this.subTableColumns); // Create Table and Column Names
		
		/* gtSub - Contents */
		this.gtSub.println(""); // Empty Row
		this.gtSub.println(""); // Empty Row
		this.gtSub.print(" "); //Add Whitespace between buttons
		this.gtSub.addButton("Modify Row", this, "modifySubTableRow"); // Modify currently selected row
		this.gtSub.print(" "); //Add Whitespace between buttons
		this.gtSub.addButton("Delete Row", this, "deleteButtonAction"); // Delete currently selected row
		this.gtSub.print(" "); //Add Whitespace between buttons
		this.gtSub.setFontColor(100,0,0); // Red Button Text
		this.gtSub.addButton("Empty Table", this, "emptySubTable"); // Empty entire table contents
		this.gtSub.setFontColor(0,0,0); // Black Text
		this.gtSub.print(" "); //Add Whitespace between buttons
		this.gtSub.println(""); // Empty Row
		this.gtSub.println(""); // Empty Row
		this.gtSub.print(" "); //Add Whitespace between buttons
		this.gtSub.addButton("Import CSV", this, "guiImportCSV"); // Empty entire table contents
		this.gtSub.print(" "); //Add Whitespace between buttons
		this.gtSub.addButton("Export CSV", this, "guiExportCSV"); // Empty entire table contents
		this.gtSub.print(" "); //Add Whitespace between buttons
		this.gtSub.setFontColor(0,100,0); // Green Button Text
		this.gtSub.addButton("  Refresh  ", this, "refreshSubTable"); // Empty entire table contents
		this.gtSub.print(" "); //Add Whitespace between buttons
		this.gtSub.setFontColor(100,0,0); // Red Button Text
		this.gtSub.addButton(" EXIT ", this, "guiExit"); // Empty entire table contents
		this.gtSub.setFontColor(0,0,0); // Black Text
		
		/* END gtSub - Contents */
	}
	
   /* The Idea of this method was to save the repetitiveness of creating new lines + font style and size changes
	* to avoid having multiple lines and accomplish the task with a single method call
	* 
	* Example:
	* this.GTLine(TARGET GTERM,new Object[]{STYLE, "FONT SIZE", NEW LINES});
	* 
	* Example 2:
	* All Object[] params are optional
	* this.GTLine(TARGET GTERM,new Object[]{STYLE, "FONT SIZE"});
	* this.GTLine(TARGET GTERM,new Object[]{STYLE});
	* this.GTLine(TARGET GTERM,null);
	*/
	public void GTLine(GTerm target, Object[] params) {
		// Defaults
		int linecount = 0;
		int newlines = 0;
		String style = "PLAIN";
		int size = this.appFontSize;
		
		// Unpack Arguments - Work out what we have
		if (params != null && params.length > 0 ) {
			if (params[0] != null) {
				style = (String) params[0];
			}
			if (params.length > 1 && params[1] != null) {
				size = (int) params[1];
			}
			if (params.length > 2 && params[2] != null) {
				newlines = (int) params[2];
			}			
		}
		
		// Change Settings based off the arguments
		if (style == "BOLD") {
			// Set Font: SANS_SERIF, Set BOLD
			target.setFont(this.appFont,Font.BOLD,size); 
		} else if (style == "ITALIC") {
			// Set Font: SANS_SERIF, Set ITALIC
			target.setFont(this.appFont,Font.ITALIC,size);
		} else if (style == "BOLD-ITALIC") {
			//Set Font: SANS_SERIF, Set BOLD + ITALIC
			this.gtMain.setFont(this.appFont,Font.BOLD | Font.ITALIC,12);
		} else {
			// Set Font: SANS_SERIF, Set PLAIN, Size 12
			target.setFont(this.appFont,Font.PLAIN,size);	
		}
		
		while (newlines > linecount) {
			target.println("");
			linecount++;
		}
	}
	
	/* 
	 * Button Methods - Start Here
	 */
	
	/*
	 * Add Data - Button Action
	 * Simple Button Action to process user inputs and pass them to the backEnd for validation.
     * backEnd will return an Object Array with two fields a boolean, and a string.
	 * boolean: true (OK), false (error)
	 * String: message for user
	 *
	 * If input is valid we run refreshSubTable() and then resetGTMain() otherwise we display the
	 * error message
	 */
	 
	public void addData() {
		this.processCounter = 0; // Start counter at 0
		this.inputData = new String[this.inputIndexs.length]; // Set Array Size to length of inputIndex array
		
		while (processCounter < this.inputIndexs.length) {
			// Fetch Data from Input Fields 
			this.inputData[this.processCounter] = this.gtMain.getTextFromEntry(this.inputIndexs[this.processCounter]).toString();
			processCounter++; // Increment Counter
		}
		Object[] inputValidation = this.backEnd.processInputs(this.inputData);
			
		boolean valid = (Boolean) inputValidation[0];
		
		if (valid) {
			this.refreshSubTable(); // Refresh the gtSub Table
			this.resetGTMain(); // Reset Main Window fields
			this.gtMain.showMessageDialog((String) inputValidation[1]); // Show Validation Message
		} else {
			// Inputs were processed, but failed validation
			this.gtMain.showErrorDialog((String) inputValidation[1]); // Show Validation Message
		}
	}
	
	/*
	 * Refresh Sub Table
	 * this method empties the gtSub Window Table and populates it with data from
	 * the multidimensional array
	 *
	 * Added new method to backend for shorting the decimal places for printing
     * 
	 * Alternative: MutliThread Application and have a refreshTable thread on a clock counter
	 * so we no longer need to call for a refresh, great for multiuser app
	 * 
	 */
	public void refreshSubTable() {
		// Wipe current table rows
		this.gtSub.clearRowsOfTable(this.subTableIndex);
		
		// Fetch Data from backend
		dataStorage = this.backEnd.getInputs();
		
		// Dump the data arrays provided to table
		this.counter=0;
		while (this.dataStorage.length > this.counter) {
			Data currentOBJ = this.dataStorage[this.counter];
			this.gtSub.addRowToTable(this.subTableIndex, 
								  currentOBJ.getLastName() + ", " + currentOBJ.getFirstName() + "\t" + 
								  this.backEnd.printableDouble(currentOBJ.getTutorialPercent()) + "\t" + 
								  this.backEnd.printableDouble(currentOBJ.getAssignmentsPercent()) + "\t" + 
								  this.backEnd.printableDouble(currentOBJ.getExamPercent()) + "\t" + 
								  this.backEnd.printableDouble(currentOBJ.getOverallTotal()) + "\t" +
								  currentOBJ.getOverallMark());
			this.counter++;
		}
	}	
	
	/*
	 * Reset GT Main
	 * Reset Input Fields on gtMain
	 */
	public void resetGTMain() {
		this.gtMainResetCounter = 0;
		while (this.gtMainResetCounter < this.inputIndexs.length) {
			if (this.gtMainResetCounter == 0 || this.gtMainResetCounter == 1) {
				this.gtMain.setTextInEntry(this.inputIndexs[this.gtMainResetCounter], ""); // Reset FirstName or LastName Filed to Empty
			} else {
				this.gtMain.setTextInEntry(this.inputIndexs[this.gtMainResetCounter], "0"); // Reset Mark Fields to 0
			} 
			this.gtMainResetCounter++;
		}
	
	}
	
	/*
	 *  Delete Sub Table Row - Button Action
	 */
	 public void deleteButtonAction() {
	 	int selectedRow = this.gtSub.getIndexOfSelectedRowFromTable(this.subTableIndex); // Get index of the selected row
	 	this.backEnd.deleteData(selectedRow);
	 	this.refreshSubTable();
	 }

	 /*
	 * Modify gtSub Table Row
	 * Simple Method to copy data from dataStorage array into the inputFields on gtMain
	 * then we delete the data from the dataStorage Array and rebuild table
	 * Alternative: Would love to hear some suggestions on this
	 */
	public void modifySubTableRow() {
		int selectedRow = this.gtSub.getIndexOfSelectedRowFromTable(this.subTableIndex); // Get index of the selected row
		Data currentOBJ = this.dataStorage[selectedRow];
		
		this.resetGTMain(); // Reset gtMain, for a clean slate
		
		// Unpack data into gtMain input fields
		this.gtMain.setTextInEntry(0, currentOBJ.getFirstName()); // First Name
		this.gtMain.setTextInEntry(1, currentOBJ.getLastName()); // Last Name
		this.gtMain.setTextInEntry(2, String.valueOf(currentOBJ.getTutorialScore())); // Tutorial Efforts Submission - Score
		this.gtMain.setTextInEntry(3, String.valueOf(currentOBJ.getTutorialTotal())); // Tutorial Efforts Submission - Total
		this.gtMain.setTextInEntry(4, String.valueOf(currentOBJ.getAssignmentsScore())); // Assignments - Score
		this.gtMain.setTextInEntry(5, String.valueOf(currentOBJ.getAssingmentsTotal())); // Assignments - Total
		this.gtMain.setTextInEntry(6, String.valueOf(currentOBJ.getExamScore())); // Exam - Score
		this.gtMain.setTextInEntry(7, String.valueOf(currentOBJ.getExamTotal())); // Exam - Total
		
		// Delete Select Row From dataStorage & Refresh Table i none hit
		this.backEnd.deleteData(selectedRow);
	}
	
	/*
	 * Empty Sub Table - Button Action
	 * Simple Button Action to clear table and empty the multidimensional dataStorage Array
	 * Alternative: not use GTerm
	 */
	public void emptySubTable() {
		// Delete all records in backEnd
		this.backEnd.wipeData();
		
		// refresh table
		this.refreshSubTable(); 
	}
	
	 /*
	  * Import CSV - Button Action
	  */
	public void guiImportCSV() {
		String filepath = this.gtSub.getFilePath(); // Open File Dialog and store selected files path
		this.backEnd.loadCSV(filepath);
		this.refreshSubTable(); // Refresh the table
	}
	
	 /*
	  * Export CSV - Button Action
	  */
	public void guiExportCSV() {
		String filepath = this.gtSub.getFilePath(); // Open File Dialog and store selected files path
		this.backEnd.writeCSV(filepath);
	}
	
	// Terminates the Application
	public void guiExit() {
		System.exit(0); // Terminate the app, with status 0 successful termination	
	}
	
	/* 
	 * Button Methods - End Here
	 */	
}
