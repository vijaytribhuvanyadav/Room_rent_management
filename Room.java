package com.room;

import java.sql.*;
import java.util.Scanner;

public class Room {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("\n--- Room Rent Data Store ---");
			System.out.println("1. Insert Data");
			System.out.println("2. Update Data");
			System.out.println("3. Delete Data");
			System.out.println("4. View All Data");
			System.out.println("5. Exit");
			System.out.print("Choose an option: ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				insertData(sc);
				break;
			case 2:
				updateData(sc);
				break;
			case 3:
				deleteData(sc);
				break;
			case 4:
				viewData(sc);
				break;
			case 5:
				System.out.println("Exiting...");
				sc.close();
				return;
			default:
				System.out.println("Invalid option.");
			}
		}
	}

	private static void insertData(Scanner sc) {
		int roomNo;
		String date, month, bye;
		float amount;

		roomNo = getRoomNo(sc);
		System.out.print("Enter Date: ");
		date = sc.next();
		System.out.print("Enter Month: ");
		month = sc.next();
		System.out.print("Enter Amount: ");
		amount = sc.nextFloat();
		System.out.print("Enter Payment Method (Gpay/PhonePe/Cash): ");
		bye = sc.next();

		String table = getTableName(roomNo);

		try (Connection conn = getConnection()) {
			String sql = "INSERT INTO " + table + " (date, month, amount, bye_method) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, date);
			stmt.setString(2, month);
			stmt.setFloat(3, amount);
			stmt.setString(4, bye);

			int rows = stmt.executeUpdate();
			System.out.println(rows > 0 ? "Inserted successfully into " + table : "Insert failed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void updateData(Scanner sc) {
		int roomNo, id;
		String date, month, bye;
		float amount;

		roomNo = getRoomNo(sc);
		System.out.print("Enter ID to update: ");
		id = sc.nextInt();
		System.out.print("Enter new Date: ");
		date = sc.next();
		System.out.print("Enter new Month: ");
		month = sc.next();
		System.out.print("Enter new Amount: ");
		amount = sc.nextFloat();
		System.out.print("Enter new Payment Method: ");
		bye = sc.next();

		String table = getTableName(roomNo);

		try (Connection conn = getConnection()) {
			String sql = "UPDATE " + table + " SET date=?, month=?, amount=?, bye_method=? WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, date);
			stmt.setString(2, month);
			stmt.setFloat(3, amount);
			stmt.setString(4, bye);
			stmt.setInt(5, id);

			int rows = stmt.executeUpdate();
			System.out.println(rows > 0 ? "Updated successfully in " + table : "Update failed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deleteData(Scanner sc) {
		int roomNo, id;

		roomNo = getRoomNo(sc);
		System.out.print("Enter ID to delete: ");
		id = sc.nextInt();

		String table = getTableName(roomNo);

		try (Connection conn = getConnection()) {
			String sql = "DELETE FROM " + table + " WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);

			int rows = stmt.executeUpdate();
			System.out.println(rows > 0 ? "Deleted successfully from " + table : "Delete failed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void viewData(Scanner sc) {
		int roomNo = getRoomNo(sc);
		String table = getTableName(roomNo);

		try (Connection conn = getConnection()) {
			String sql = "SELECT * FROM " + table;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			System.out.println("\nID | Date | Month | Amount | Payment Method");
			System.out.println("---------------------------------------------");
			while (rs.next()) {
				System.out.println(rs.getInt("id") + " | " + rs.getString("date") + " | " + rs.getString("month")
						+ " | " + rs.getFloat("amount") + " | " + rs.getString("bye_method"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int getRoomNo(Scanner sc) {
		System.out.println("\nSelect Room Number:");
		System.out.println("1.Room 1\n2.Room 2\n3.Room 3\n4.Room 4\n5.Room 5\n6.Room 6\n7.Shanti Sagar\n8.Shop");
		int roomNo = sc.nextInt();
		if (roomNo < 1 || roomNo > 8) {
			System.out.println("Invalid Room Number!");
			return getRoomNo(sc); // retry
		}
		return roomNo;
	}

	private static String getTableName(int roomNo) {
		switch (roomNo) {
		case 1:
			return "room1_data";
		case 2:
			return "room2_data";
		case 3:
			return "room3_data";
		case 4:
			return "room4_data";
		case 5:
			return "room5_data";
		case 6:
			return "room6_data";
		case 7:
			return "shanti_sagar_data";
		case 8:
			return "shop_data";
		default:
			return "unknown";
		}
	}

	private static Connection getConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/roomdb", "root", "Vijay1999@");
	}
}
