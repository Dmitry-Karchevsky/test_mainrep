package Servlets;

import DataSets.StudentsDataSet;
import DataSets.TeachersDataSet;
import Services.StudentService;
import Services.TeacherService;
import Services.UserService_Interface;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "RegServlet",
        urlPatterns = {"/registration"}
)
public class RegistrationServlet extends HttpServlet {

    private UserService_Interface service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String role = req.getParameter("who");
        Gson gson = new Gson();
        if(role.equals("student")){
            service = new StudentService();
            StudentsDataSet student = gson.fromJson(req.getParameter("data"), StudentsDataSet.class);
            try {
                service.addUser(student.getName(), student.getSurname(), student.getEmail(), student.getPassword(), student.getRegDate());
            }catch (Exception e){
                e.printStackTrace();
            }
            ServletOutputStream out = resp.getOutputStream();
            out.write(gson.toJson("OK").getBytes());
            out.flush();
            out.close();
        } else if(role.equals("teacher")){
            service = new TeacherService();
            TeachersDataSet teacher = gson.fromJson(req.getParameter("data"), TeachersDataSet.class);
            try {
                service.addUser(teacher.getName(), teacher.getSurname(), teacher.getEmail(), teacher.getPassword(), teacher.getRegDate());
            }catch (Exception e){
                e.printStackTrace();
            }
            ServletOutputStream out = resp.getOutputStream();
            out.write(gson.toJson("OK").getBytes());
            out.flush();
            out.close();
        } else{
            ServletOutputStream out = resp.getOutputStream();
            out.write(gson.toJson("BAD_REQUEST").getBytes());
            out.flush();
            out.close();
        }
    }
}