package org.thequestnews;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import java.util.Iterator;

public class Upload extends HttpServlet{
    public void requestHandler(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();

        File file;
        int maxFileSize = 5000 * 1024;
        int maxMemSize = 5000 * 1024;
        String filePath = "c:/Users/Administrator/Desktop/Tomcat/webapps/thequestnews/img/images";
        if(req.getContentType() != null){
            String contentType = req.getContentType();
            if((contentType.indexOf("multipart/form-data") >= 0)){
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(maxMemSize);
                factory.setRepository(new File("c:\\temp"));

                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(maxFileSize);

                try{
                    List fileItems = upload.parseRequest(req);
                    Iterator i = fileItems.iterator();
                    while(i.hasNext())
                    {
                        FileItem fi = (FileItem)i.next();
                        if(!fi.isFormField ())  {
                            String fieldName = fi.getFieldName();
                            String fileName = fi.getName();
                            boolean isInMemory = fi.isInMemory();
                            long sizeInBytes = fi.getSize();
                            file = new File(filePath + fileName) ;
                            fi.write(file) ;
                        }
                    }

                    out.println("{\"status\": \"OK\"}");
                }catch(Exception e) {
                    out.println("{\"status\": \"ERROR\", \"message\": \"" + e.getMessage() + "\"}");
                }

            }
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
