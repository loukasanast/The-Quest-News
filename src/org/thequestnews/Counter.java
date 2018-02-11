package org.thequestnews;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Counter  extends HttpServlet{
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();

        int pst_id = Integer.parseInt(req.getParameter("id"));

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thequest" ,"root", "*****");
            Statement pst_stmt  = conn.createStatement();

            String pst_sql = "UPDATE tbl_posts SET views=views+1 WHERE id=" + pst_id;
            pst_stmt.execute(pst_sql);
        }catch (Exception e)
        {
            out.println("Got an exception! ");
            out.println(e.getMessage());
        }
    }
}
