package org.thequestnews;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Index extends HttpServlet{
    public void requestHandler(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException{
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();
        String category = req.getParameter("category");

        out.println("{\n\"news\": [\n");

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thequest" ,"root", "*****");
            Statement pst_stmt  = conn.createStatement();

            String pst_sql = "SELECT * FROM tbl_posts WHERE category='" + category + "' ORDER BY id DESC";
            ResultSet pst_rs = pst_stmt.executeQuery(pst_sql);

            while (pst_rs.next())
            {
                int pst_id = pst_rs.getInt("id");
                String pst_title = pst_rs.getString("title");
                String pst_body = pst_rs.getString("body");
                String pst_author = pst_rs.getString("author");
                Date pst_date = new Date();
                pst_date.setTime(pst_rs.getTimestamp("date").getTime());
                String pst_image = pst_rs.getString("image");
                int pst_views = pst_rs.getInt("views");

                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd-MM-yyyy HH:mm", new Locale("en"));
                String pst_dateFormatted = DATE_FORMAT.format(pst_date);

                out.println("{\n\"id\": " + pst_id + ",\n\"title\": \"" + pst_title + "\",\n\"body\": \"" + pst_body + "\",\n\"author\": \"" + pst_author + "\",\n\"date\": \"" + pst_dateFormatted + "\",\n\"image\": \"" + pst_image + "\",\n\"views\": " + pst_views + ",\n\"comments\":\n[");

                Statement cmt_stmt  = conn.createStatement();
                String cmt_sql = "SELECT * FROM tbl_comments WHERE postid='" + pst_id + "'";
                ResultSet cmt_rs = cmt_stmt.executeQuery(cmt_sql);

                while(cmt_rs.next()){
                    int cmt_id = cmt_rs.getInt("id");
                    String cmt_body = cmt_rs.getString("body");
                    Date cmt_date = new Date();
                    cmt_date.setTime(cmt_rs.getTimestamp("date").getTime());

                    DATE_FORMAT = new SimpleDateFormat("EEE, dd-MM-yyyy HH:mm", new Locale("en"));
                    String cmt_dateFormatted = DATE_FORMAT.format(cmt_date);

                    out.println("{\n\"id\": " + cmt_id + ",\n\"body\": \"" + cmt_body + "\",\n\"date\": \"" + cmt_dateFormatted + "\"\n}");

                    if(!cmt_rs.isLast()){
                        out.println(",");
                    }
                }

                out.println("]");
                out.println("}");

                cmt_stmt.close();

                if(!pst_rs.isLast()){
                    out.println(",");
                }
            }

            pst_stmt.close();
            conn.close();
        }
        catch (Exception e)
        {
            out.println("Got an exception! ");
            out.println(e.getMessage());
        }

        out.println("]\n}");
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