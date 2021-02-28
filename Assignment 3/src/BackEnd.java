import java.util.Scanner;  // Import the Scanner class
import java.io.File; // Import the File Class
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class BackEnd {
	private Data[] data; // Data Array using Data Class
	
	private int arraySize; // Size of arrays
	private int counter; // How many time have we added content
	private String[] inputData; // Raw data input from GUI/Console
	
	private String database; // Variable to hold the CSV File path
	
	public BackEnd(String database) {
		// Initialize arrays
		this.arraySize = 0;
		this.data = new Data[this.arraySize];
		this.database = database; // Copy local variable to class global
		this.loadCSV(this.database); // This method handles CSV's
		
	}
	
	/*
	 * Process Inputs - From UI's
	 * Processing inputData array and validating it against validation criteria
	 * on failure we add to the object element 0 - boolean value false and element 1 a targeted error message
	 * on pass we add to the object element 0 - boolean value true and element 1 as success message
	 * We are using an Object array so we can have mixed data types
	 * 
	 * Alternative: We could use try and catch rather then a large if, else if, else statement
	 */
	public Object[] processInputs(String[] inputData) {
		this.inputData = inputData;
		Object[] response = new Object[2]; // Create a Object Array with then size of two  
		

		// Validate Input Fields
		// Validate First Name
		// Changed if else layout for easier reading
		if (inputData[0].isEmpty()) {
			response[0] = false; // Pass(true) or Fail(false) Validation 
			response[1] = "Input Validation Failed!\nError: \"First Name\" is empty!"; // Error Message
		}
		// Validate Last Name
		else if (inputData[1].isEmpty()) {
			response[0] = false; // Pass(true) or Fail(false) Validation 
			response[1] = "Input Validation Failed!\nError: \"Last Name\" is empty!"; // Error Message			
		}
		// Validate - Tutorial Effort Submissions
		else if (!this.inputData[2].matches("^[0-9]+$") && !this.inputData[2].matches("^\\d+\\.\\d+")) { // Check if input string is decimal or integer (REGEX)
			response[0] = false; // Pass(true) or Fail(false) Validation 
			response[1] = "Input Validation Failed!\nError: \"Tutorial effort submissions\" Received marks is not a numeric value!"; // Error Message
		}
		else if (!this.inputData[3].matches("^[0-9]+$") && !this.inputData[3].matches("^\\d+\\.\\d+")) { // Check if input string is decimal or integer (REGEX)
			response[0] = false; // Pass(true) or Fail(false) Validation 
			response[1] = "Input Validation Failed!\nError: \"Tutorial effort submissions\" Received marks is not a numeric value!"; // Error Message
		}
		else if (Double.parseDouble(inputData[2]) > Double.parseDouble(inputData[3])) {
			response[0] = false; // Pass(true) or Fail(false) Validation 
			response[1] = "Input Validation Failed!\nError: \"Tutorial effort submissions\" Received marks is higher than total marks!"; // Error Message		
		} 

		// Validate - Assignments
		else if (!this.inputData[4].matches("^[0-9]+$") && !this.inputData[4].matches("^\\d+\\.\\d+")) { // Check if input string is decimal or integer (REGEX)
			response[0] = false; // Pass(true) or Fail(false) Validation 
			response[1] = "Input Validation Failed!\nError: \"Assignments\" Received marks is not a numeric value!"; // Error Message
		}
		else if (!this.inputData[5].matches("^[0-9]+$") && !this.inputData[5].matches("^\\d+\\.\\d+")) { // Check if input string is decimal or integer (REGEX)
			response[0] = false; // Pass(true) or Fail(false) Validation 
			response[1] = "Input Validation Failed!\nError: \"Assignments\" Received marks is not a numeric value!"; // Error Message
		}
		else if (Double.parseDouble(inputData[4]) > Double.parseDouble(inputData[5])) {
			response[0] = false; // Pass(true) or Fail(false) Validation 
			response[1] = "Input Validation Failed!\nError: \"Assignments\" Received marks is higher than total marks!"; // Error Message		
		} 

		// Validate - Exam
		else if (!this.inputData[6].matches("^[0-9]+$") && !this.inputData[6].matches("^\\d+\\.\\d+")) { // Check if input string is decimal or integer (REGEX)
			response[0] = false; // Pass(true) or Fail(false) Validation 
			response[1] = "Input Validation Failed!\nError: \"Exam\" Received marks is not a numeric value!"; // Error Message
		}
		else if (!this.inputData[7].matches("^[0-9]+$") && !this.inputData[7].matches("^\\d+\\.\\d+")) { // Check if input string is decimal or integer (REGEX)
			response[0] = false; // Pass(true) or Fail(false) Validation 
			response[1] = "Input Validation Failed!\nError: \"Exam\" Received marks is not a numeric value!"; // Error Message
		}
		else if (Double.parseDouble(inputData[6]) > Double.parseDouble(inputData[7])) {
			response[0] = false; // Pass(true) or Fail(false) Validation 
			response[1] = "Input Validation Failed!\nError: \"Exam\" Received marks is higher than total marks!"; // Error Message		
		} 		
		else { 
			response[0] = true; // Pass(true) or Fail(false) Validation 
			response[1] = inputData[1] + ", " +inputData[0] + ": Successfully Added!"; // User Message
			
			this.growArray(); // Increase storage array size and set this.counter and increment this.arraySize
			// Add up total and score
			double tmpTotal = Double.parseDouble(this.inputData[3]) + Double.parseDouble(this.inputData[5]) + Double.parseDouble(this.inputData[7]); 
			double tmpScore = Double.parseDouble(this.inputData[2]) + Double.parseDouble(this.inputData[4]) + Double.parseDouble(this.inputData[6]);
			
			// Copy and type convert inputs[]
			this.data[this.counter] = new Data(inputData[0],
											   inputData[1],
											   Double.parseDouble(inputData[2]),
											   Integer.parseInt(inputData[3]),	
											   percentageCalc(new double[] {Double.parseDouble(this.inputData[2]), Double.parseDouble(this.inputData[3])}),
											   Double.parseDouble(inputData[4]),
											   Integer.parseInt(inputData[5]),
											   percentageCalc(new double[] {Double.parseDouble(this.inputData[4]), Double.parseDouble(this.inputData[5])}),
											   Double.parseDouble(inputData[6]),
											   Integer.parseInt(inputData[7]),
											   percentageCalc(new double[] {Double.parseDouble(this.inputData[6]), Double.parseDouble(this.inputData[7])}),
											   percentageCalc(new double[] { tmpScore, tmpTotal }),
											   finalGrade( percentageCalc(new double[] { tmpScore, tmpTotal }) ));
			// Save to database - Auto Save :)
			this.writeCSV(this.database);
		}
		return response;
	}		
	
	/* Method used by CSV import ONLY! */
	public void addInputs(String[] inputs) {
		this.growArray(); // Increase storage array size and set this.counter and increment this.arraySize
		// Copy and type convert inputs[]
		this.data[this.counter] = new Data(inputs[0],						// Student First Name
										   inputs[1],						// Student Last Name
										   Double.parseDouble(inputs[2]),	// Tutorial Efforts Submission - Score
										   Integer.parseInt(inputs[3]),		// Tutorial Efforts Submission - Total
										   Double.parseDouble(inputs[4]),	// Tutorial Efforts Submission - Percentage Calculated
										   Double.parseDouble(inputs[5]),	// Assignments - Score
										   Integer.parseInt(inputs[6]),		// Assignments - Total
										   Double.parseDouble(inputs[7]), 	// Assignments - Percentage Calculated
										   Double.parseDouble(inputs[8]),	// Exams - Score
										   Integer.parseInt(inputs[9]), 	// Exams - Total
										   Double.parseDouble(inputs[10]),	// Exams - Percentage Calculated
										   Double.parseDouble(inputs[11]),	// Overall Total - Percentage Calculated
										   inputs[12]);						// Overall Mark - Matched Data
	}
	
	/* Method to Grow the array in size */
	public void growArray() {
		this.arraySize = this.data.length; // Increment ArraySize
		
		// Temp Arrays
		Data[] temp_data = new Data[this.arraySize + 1]; // Temp Data array +1 in size of data array 
		
		this.counter = 0;
		while (this.counter < this.arraySize) {
			// Copy old arrays to temp arrays
			temp_data[this.counter] = this.data[this.counter]; // Copy old array to temp array
			this.counter++;
		}
		
		this.data = temp_data; // replace old array with new array
	}	
	
	/* This method handles CSV files containing data we wish to load into our data array
	 * I am using Scanner because the IIE doesn't specify and its honestly the most simplistic way,
	 * and as this unit is all about the basics it make sense Scanner would be preferred.
	 * 
	 * Alternatives: OpenCSV, Apache Commons CSV, etc are java class library dedicated to this task 
	 * and would be much more effective. 
	 */
	public void loadCSV(String filepath) {
		try {
			Scanner csv = new Scanner(new File(filepath)); // Read in file
			
			while (csv.hasNext()) { // Process the CSV line by line in a loop
				String csvRow = csv.next();
				String[] split = csvRow.split(",");
				this.addInputs(split);
				
			}
			csv.close(); // Close IO on filepath
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	 * As mentioned for importCSV external library would probably be prone to not incur errors and
	 * will better handle text with escape characters or special characters such as comma's.
	 * 
	 * but as per the requirements of this subject i chose the simple but effective method
	 */
	public void writeCSV(String filepath) {
		// If no filepath is given use the database variable 
		if (filepath == null || filepath.isEmpty()) {
			filepath = this.database;
		}
		
		try {
			FileWriter writeCsv = new FileWriter(filepath);
			
			int count = 0;
			
			// Loop the array and write lines to the buffer,
			// at the end of the line send \n to make a 
			// new line and flush the buffer to the file
			
			while (this.data.length > count) {
				//String,Char,INT,DOUBLE,BOOLEAN
				// writeCsv.append() Appending to existing line 
				
				writeCsv.append(this.data[count].getFirstName() + ","); 			// Student First Name
				writeCsv.append(this.data[count].getLastName() + ","); 				// Student Last Name
				
				writeCsv.append(this.data[count].getTutorialScore() + ","); 		// Tutorial Efforts Submission - Score
				writeCsv.append(this.data[count].getTutorialTotal() + ",");			// Tutorial Efforts Submission - Total
				writeCsv.append(this.data[count].getTutorialPercent() + ",");		// Tutorial Efforts Submission - Percentage Calculated
				
				writeCsv.append(this.data[count].getAssignmentsScore() + ",");		// Assignments - Score
				writeCsv.append(this.data[count].getAssingmentsTotal() + ",");		// Assignments - Total
				writeCsv.append(this.data[count].getAssignmentsPercent() + ",");	// Assignments - Percentage Calculated
				
				writeCsv.append(this.data[count].getExamScore() + ",");				// Exams - Score
				writeCsv.append(this.data[count].getExamTotal() + ",");				// Exams - Total
				writeCsv.append(this.data[count].getExamPercent() + ",");			// Exams - Percentage Calculated
				
				writeCsv.append(this.data[count].getOverallTotal() + ",");			// Overall Total - Percentage Calculated
				writeCsv.append(this.data[count].getOverallMark() + ",");			// Overall Mark - Matched Data
				
				writeCsv.append("\n"); // New Line/Row in file
				writeCsv.flush(); // Flush to file / Writer buffer to file.
				
				count++;
			}
			writeCsv.close(); // Final flush and close IO to file
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	// Return an Object array containing all input arrays 
	// this allows us to keep all array data types intact
	public Data[] getInputs() {
	
		return this.data; // Return Multidimensional array
	}
	
	/*
	 *  Delete Sub Table Row - Method
	 *  Note: Separated from deleteButtonAction so we can recycle this code
	 *
	 *  Here we are deleting the row from the first level of the multidimensional dataStoage array
	 *  Alternatively: if we were using an ArrayList we could simply remove an entry with arraylist.remove()
	 */
	public void deleteData(int selectedRow) {
		int newArraySize = this.data.length - 1; // New Array Size
		
		// Temp Arrays
		Data[] tempDataStorage = new Data[newArraySize];
		
		this.counter = 0;  // Loop Counter
		int newIndex = 0; // New Array Index Key 
		while (this.data.length > this.counter) {
			if (counter != selectedRow) {
				tempDataStorage[newIndex] = this.data[this.counter]; // Copy Data using new index
				newIndex++;
			}
			this.counter++; // Increment Loop Counter
	
		}
		// Overwrite dataStorage with contents of tempDataStorage
		this.data = tempDataStorage; // Copy tempArrayStorage over dataStorage
		
		// Save to database - Auto Save :)
		this.writeCSV(this.database);		
	}
	
	// Wipe's All Data not much more to say
	public void wipeData() {
		// Wipe all arrays
		this.data = new Data[0];
		
		// reset variables
		this.counter = 0;
		this.arraySize = 0;
	}

	/*
	 * Percentage Calculator
	 * Simple Math method to take two doubles in an array
	 * and calculate the percentage
	 * Alternative: None
	 */
	public double percentageCalc(double[] results) {
		double result;
		// results[0]; Score
		// results[1]; // Total 
		result = (results[0]/results[1]) * 100; // Calculate the Percentage from the ratio
		
		return result;
	}

	/*
	 * Final Grade
	 * Takes double input and returns the matching grade
	 * Alternative: None
	 */
	public String finalGrade(double total) {
		/*
		 * HD (High Distinction) = 80 - 100%
		 * DI (Distinction) = 70 - 79%
		 * CR (Credit) = 60 - 69%
		 * PA (Pass) = 50 - 59%
		 * NN (Fail) = 0 - 49%
		 */
		String result;
		if (total >= 80) {
			result = "HD";
		} else if ((total >= 70) && (total < 80)) {
			result = "DI";
		} else if ((total >= 60) && (total < 70)) {
			result = "CR";
		} else if ((total >= 50) && (total < 60)) {
			return "PA";
		} else {
			result = "NN";
		}
		return result;
	}
	/*
	 * Method converts a double (0.00000000) to a 2 decimal place printable string
	 * I have put it in the back end as both user interface require it.
	 * however we do not store this, we only store the full double to be more accurate
	 */
	public String printableDouble(double input) {
		String result;
		result = new java.text.DecimalFormat("0.00").format(input);
		
		return result;
	}	
}


