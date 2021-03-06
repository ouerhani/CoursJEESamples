/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myCode;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nabil.ouerhani
 */
public class LogServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        PrintWriter out = response.getWriter();
        Connection conn = null;

        String usr = request.getParameter("user_name");
        String pass = request.getParameter("passwd");
        try {
            String userName = "root";
            String password = "mytest";
            String url = "jdbc:mysql://localhost:3306/users";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            try (PreparedStatement ps = conn.prepareStatement("select * from usr where username = ? and passwd = ?")) {
                ps.setString(1, usr);
                ps.setString(2, pass);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String code = rs.getString("username");
                        String nameVal = rs.getString("passwd");

                        out.println("<p> Succesfull Login");
                        out.println("<p>" + nameVal + "  ( " + code + " )<p>");

                    }
                }
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            out.println(e.toString());
        } finally {
            out.close();
            if (conn != null) {
                try {
                    conn.close();
                    out.println("Database connection terminated");
                } catch (Exception e) {
                }
            }

        }
    }
}