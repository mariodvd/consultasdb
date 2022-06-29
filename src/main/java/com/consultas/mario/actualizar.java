package com.consultas.mario;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = "/actualizar", initParams = {@WebInitParam(name = "dbUrl", value = "jdbc:postgresql://localhost:5432/bootcamp_market"),
        @WebInitParam(name = "dbUser", value = "postgres"),
        @WebInitParam(name = "dbPassword", value = "postgres"),
})

public class actualizar extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(config.getInitParameter("dbUrl"), config.getInitParameter("dbUser"), config.getInitParameter("dbPassword"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            Statement stmt = connection.createStatement();
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            ResultSet rs = stmt.executeQuery("update cliente set nombre = '" + req.getParameter("nombre") + "', apellido = '" + req.getParameter("apellido") + "', nro_cedula = '" + req.getParameter("cedula") + "', telefono = '" + req.getParameter("telefono") + "' where id = " +req.getParameter("id")+";");
            out.println("<html>");
            out.println("<body>");
            while (rs.next()) {
                Integer id= rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String nro_cedula = rs.getString("nro_cedula");
                String telefono = rs.getString("telefono");

                
                out.println("<p>ID = " + id + "</p>");
                out.println("<p>NOMBRE = " + nombre + "</p>");
                out.println("<p>APELLIDO = " + apellido + "</p>");
                out.println("<p>NRO CEDULA = " + nro_cedula + "</p>");
                out.println("<p>TELEFONO = " + telefono + "</p>");

            }
            out.println("</body>");
            out.println("</html>");
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }


    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}