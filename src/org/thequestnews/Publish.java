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

public class Publish extends HttpServlet{
    public void requestHandler(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();
        String pst_author = req.getParameter("name");
        String pst_category = req.getParameter("category");
        String pst_title = req.getParameter("title");
        String pst_post = req.getParameter("post").replaceAll("\n", "");
        String pst_image = req.getParameter("image");

        Calendar cal = Calendar.getInstance();

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thequest" ,"root", "*****");
            Statement pst_stmt  = conn.createStatement();

            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String pst_dateFormatted = DATE_FORMAT.format(cal.getTime());

            String cmt_sql = "INSERT INTO tbl_posts (title, body, author, date, image, category) VALUES ('" + pst_title + "', '" + pst_post + "', '" + pst_author + "', '" + pst_dateFormatted + "', '" + pst_image + "', '" + pst_category + "')";
            pst_stmt.execute(cmt_sql);

            pst_stmt.close();
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
