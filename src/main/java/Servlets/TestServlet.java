package Servlets;

import DataSets.StudentsDataSet;
import Services.StudentService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "MyServlet",
        urlPatterns = {"/hello"}
)
public class TestServlet extends HttpServlet {

    private final StudentService service = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        try{
            Gson gson = new Gson();
            StudentsDataSet student = service.getCurUserByLogin("ymnyaga@yandex.ru");
            String json = gson.toJson(student);
            out.write(json.getBytes());
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        /*out.write("hello gays".getBytes());
        out.flush();
        out.close();*/
    }

}
