public class Application {
	private BackEnd backEnd;
	private FrontEndGTerm uiGT;
	private FrontEndConsole uiConsole;
	
	public Application(String database, String userInterface) {
		this.backEnd = new BackEnd(database);
		
		if (userInterface.equals("gterm")) {
			this.uiGT = new FrontEndGTerm(this.backEnd);
		} else if (userInterface.equals("console")) {
			this.uiConsole = new FrontEndConsole(this.backEnd);
		} else {
			this.uiGT = new FrontEndGTerm(this.backEnd);
			this.uiConsole = new FrontEndConsole(this.backEnd);			
		}
	}

	public static void main(String[] args) {
		String database;
		String userInterface;
		
		/* Runtime Arguments */
		// Database Selection, if none supplied set default
		if (args != null && args.length > 0) {
			database = args[0];
		} else {
			database = "database.csv";
		}
		
		// User Interface
		// Options are gterm, console, both
		// invalid options will always spawn both user interfaces
		if (args != null && args.length > 1) {
			userInterface = args[1];
		} else {
			userInterface = "both";
		}
		
		Application app = new Application(database, userInterface);
	}
}