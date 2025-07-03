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
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class ViewServlet
 */
@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		int roomNo = Integer.parseInt(request.getParameter("roomNo"));
		String table = getTableName(roomNo);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/roomdb", "root", "Vijay1999@");

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);

			out.println("<h2>Data for: " + table + "</h2>");
			out.println(
					"<table border='1' cellpadding='10'><tr><th>ID</th><th>Date</th><th>Month</th><th>Amount</th><th>Payment Method</th></tr>");

			while (rs.next()) {
				out.println("<tr><td>" + rs.getInt("id") + "</td><td>" + rs.getString("date") + "</td><td>"
						+ rs.getString("month") + "</td><td>" + rs.getFloat("amount") + "</td><td>"
						+ rs.getString("bye_method") + "</td></tr>");
			}

			out.println("</table>");
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
