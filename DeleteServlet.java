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
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		int roomNo = Integer.parseInt(request.getParameter("roomNo"));
		int id = Integer.parseInt(request.getParameter("id"));
		String table = getTableName(roomNo);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/roomdb", "root", "Vijay1999@");

			String sql = "DELETE FROM " + table + " WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);

			int rows = stmt.executeUpdate();
			if (rows > 0) {
				out.println("<h2 style='color:green;'>Deleted from " + table + ".</h2>");
			} else {
				out.println("<h2 style='color:red;'>Delete failed.</h2>");
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
