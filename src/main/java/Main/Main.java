package Main;

import java.io.File;
import java.util.Scanner;

import DataSets.StudentsDataSet;
import Services.StudentService;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Main {

//TODO@ Проверка загрузки
    public static void main(String[] args) throws Exception{
        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.

        tomcat.setPort(8080);

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);
        /*Scanner scan = new Scanner(System.in);
        String word = scan.nextLine();
        scan.close();
        if(word.equals("start")){
            System.out.println("werwrwer");
            StudentService service = new StudentService();
            StudentsDataSet student = service.getCurUserByLogin("ymnyaga@yandex.ru");
            System.out.println(student.getEmail());
        }*/
        tomcat.start();
        System.out.println("Server started");
        tomcat.getServer().await();
    }
}
