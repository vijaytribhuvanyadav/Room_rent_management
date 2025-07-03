package com.room;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class RoomServlet
 */
@WebServlet("/RoomServlet")
public class RoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public RoomServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		int roomNo = Integer.parseInt(request.getParameter("roomNo"));
		String date = request.getParameter("date");
		String month = request.getParameter("month");
		float amount = Float.parseFloat(request.getParameter("amount"));
		String bye = request.getParameter("bye");
		String table = getTableName(roomNo);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/roomdb", "root", "Vijay1999@");

			String sql = "INSERT INTO " + table + " (date, month, amount, bye_method) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, date);
			stmt.setString(2, month);
			stmt.setFloat(3, amount);
			stmt.setString(4, bye);

			int rows = stmt.executeUpdate();
			if (rows > 0) {
				out.println("<h2 style='color:green;'>Data inserted into " + table + ".</h2>");
			} else {
				out.println("<h2 style='color:red;'>Insertion failed.</h2>");
			}

			conn.close();
		} catch (Exception e) {
			out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
		}

		out.println("<br><a href='index.html'>← Back to Home</a>");
	}

	private String getTableName(int roomNo) {
		return switch (roomNo) {
		case 1 -> "room1_data";
		case 2 -> "room2_data";
		case 3 -> "room3_data";
		case 4 -> "room4_data";
		case 5 -> "room5_data";
		case 6 -> "room6_data";
		case 7 -> "shanti_sagar_data";
		case 8 -> "shop_data";
		default -> "unknown";
		};
	}

}
