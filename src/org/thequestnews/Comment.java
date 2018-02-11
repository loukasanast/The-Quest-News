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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Comment extends HttpServlet{
    public void requestHandler(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();
        String cmt_postid = req.getParameter("id");
        String cmt_body = req.getParameter("body");

        Calendar cal = Calendar.getInstance();

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thequest" ,"root", "*****");
            Statement cmt_stmt  = conn.createStatement();

            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String cmt_dateFormatted = DATE_FORMAT.format(cal.getTime());

            String cmt_sql = "INSERT INTO tbl_comments (postid, body, date) VALUES ('" + cmt_postid + "', '" + cmt_body + "', '" + cmt_dateFormatted + "')";
            cmt_stmt.execute(cmt_sql);

            cmt_stmt.close();
            conn.close();

            out.println("{\"status\": \"OK\"}");
        }
        catch (Exception e)
        {
            out.println("{\"status\": \"ERROR\", \"message\": \"" + e.getMessage() + "\"}");
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        requestHandler(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        requestHandler(req, res);
    }
}
