package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.XMLFormatter;

 class Logger {
	private static Logger instance;

	 static Logger getInstance() {
		if (Logger.instance == null) {
			Logger.instance = new Logger();
		}
		return Logger.instance;
	}

	java.util.logging.Logger log;

	private FileHandler fh;

	@SuppressWarnings("ResultOfMethodCallIgnored")
	private Logger() {
		final File f = new File("log.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (final IOException e) {
				System.out.println("Failed to create log file.");
				e.printStackTrace();
			}
		}
		try {
			fh = new FileHandler("log.txt", true);
		} catch (SecurityException | IOException e) {
			System.out.println("Failed to handle log file.");
			e.printStackTrace();
		} // Appends to log.txt file.
		log = java.util.logging.Logger.getLogger("MainLog");
		log.setUseParentHandlers(false);
		log.setLevel(Level.INFO);
		log.addHandler(fh);
		fh.setFormatter(new XMLFormatter());
		
	}

}