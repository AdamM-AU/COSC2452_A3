public class Data {
	private String firstName; 			// Student First Name
	private String lastName; 			// Student Last Name
	
	private double tutorialScore; 		// Tutorial Efforts Submission - Score
	private int tutorialTotal; 			// Tutorial Efforts Submission - Total
	private double tutorialPercent;		// Tutorial Efforts Submission - Percentage Calculated
	
	private double assignmentsScore; 	// Assignments - Score
	private int assingmentsTotal; 		// Assignments - Total
	private double assignmentsPercent; 	// Assignments - Percentage Calculated
	
	private double examScore; 			// Exams - Score
	private int examTotal; 				// Exams - Total
	private double examPercent; 		// Exams - Percentage Calculated
	
	private double overallTotal; 		// Overall Total - Percentage Calculated
	private String overallMark; 		// Overall Mark - Matched Data
	
	/*
	 * Our custom data class... Called "Data"... so very creative :)
	 */
	public Data(String firstName, String lastName, double tutorialScore, int tutorialTotal, double tutorialPercent,
				double assignmentsScore, int assignmentsTotal, double assignmentsPercent, double examScore, 
				int examTotal, double examPercent, double overallTotal, String overallMark) {

		// Set the input data
		this.setFirstName(firstName);
		this.setLastName(lastName);
		
		this.setTutorialScore(tutorialScore);
		this.setTutorialTotal(tutorialTotal);
		this.setTutorialPercent(tutorialPercent);
		
		this.setAssignmentsScore(assignmentsScore);
		this.setAssingmentsTotal(assignmentsTotal);
		this.setAssignmentsPercent(assignmentsPercent);
		
		this.setExamScore(examScore);
		this.setExamTotal(examTotal);
		this.setExamPercent(examPercent);
		
		this.setOverallTotal(overallTotal);
		this.setOverallMark(overallMark);
	}


	/* 
	 * Generated Setters and Getters
	 * 
	 * Generated Setters
	 * All Setters are private, as no external classes should be setting fields individually
	 */
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	private void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	private void setTutorialScore(double tutorialScore) {
		this.tutorialScore = tutorialScore;
	}

	private void setTutorialTotal(int tutorialTotal) {
		this.tutorialTotal = tutorialTotal;
	}

	private void setTutorialPercent(double tutorialPercent) {
		this.tutorialPercent = tutorialPercent;
	}

	private void setAssignmentsScore(double assignmentsScore) {
		this.assignmentsScore = assignmentsScore;
	}

	private void setAssingmentsTotal(int assingmentsTotal) {
		this.assingmentsTotal = assingmentsTotal;
	}

	private void setAssignmentsPercent(double assignmentsPercent) {
		this.assignmentsPercent = assignmentsPercent;
	}

	private void setExamScore(double examScore) {
		this.examScore = examScore;
	}

	private void setExamTotal(int examTotal) {
		this.examTotal = examTotal;
	}

	private void setExamPercent(double examPercent) {
		this.examPercent = examPercent;
	}

	private void setOverallTotal(double overallTotal) {
		this.overallTotal = overallTotal;
	}

	private void setOverallMark(String overallMark) {
		this.overallMark = overallMark;
	}
	
	/* Generated Getters
	 * All getters are public, as we want external classes to be able to fetch fields
	 */
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public double getTutorialScore() {
		return tutorialScore;
	}

	public int getTutorialTotal() {
		return tutorialTotal;
	}

	public double getTutorialPercent() {
		return tutorialPercent;
	}

	public double getAssignmentsScore() {
		return assignmentsScore;
	}

	public int getAssingmentsTotal() {
		return assingmentsTotal;
	}

	public double getAssignmentsPercent() {
		return assignmentsPercent;
	}

	public double getExamScore() {
		return examScore;
	}

	public int getExamTotal() {
		return examTotal;
	}

	public double getExamPercent() {
		return examPercent;
	}

	public double getOverallTotal() {
		return overallTotal;
	}

	public String getOverallMark() {
		return overallMark;
	}	
}
