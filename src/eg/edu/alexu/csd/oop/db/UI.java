package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.Scanner;

import eg.edu.alexu.csd.oop.db.DBMSController;

public class UI {

	private static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		DBMSController executor = DBMSController.getInstance();
		while (true) {
			System.out.println("SQL >> ");
			String query = sc.nextLine();
			if (query.isEmpty()) {
				query = sc.nextLine();
	        }
			try {
				System.out.println(executor.invoke(query));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
