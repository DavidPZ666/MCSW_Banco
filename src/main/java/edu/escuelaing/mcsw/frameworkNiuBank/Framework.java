package edu.escuelaing.mcsw.frameworkNiuBank;

import com.sun.deploy.net.HttpResponse;
import edu.escuelaing.mcsw.HttpServerNiuBank.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Framework implements FrameworkNiuBank{

    private static Framework _intance = new Framework();
    private int httPort = 36000;
    Map<String, BiFunction<String, HttpResponse, JSONObject > >functionMap = new HashMap<>();
    HttpServer httpServer = new HttpServer();

    public static Framework getInstance() {
        return _intance;
    }

    public void get(String route, BiFunction<String, HttpResponse, JSONObject> biFunction){
        functionMap.put(route, biFunction);
    }

    public void startServer() throws IOException {
        httpServer.startServer(httPort);
    }

    public void port(int serverPort){
        this.httPort = serverPort;
    }

    @Override
    public JSONObject handle(String path, String req, HttpResponse res){

        if(functionMap.containsKey(path)){
            return functionMap.get(path).apply(req,res);
        }else if (path.contains("get")){
            System.out.println("base de datos");
        }return null;

    }

    private String validErrorHttpHeader() {
        return "HTTP/1.1 404 Not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Error 404</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>Error 404</h1>\n"
                + "</body>\n"
                + "</html>\n";
    }






}
