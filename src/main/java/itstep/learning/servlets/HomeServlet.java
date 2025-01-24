package itstep.learning.servlets;

import com.google.gson.Gson;
import itstep.learning.rest.RestResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message;
        try {
            DriverManager.registerDriver(
                    new com.mysql.cj.jdbc.Driver()
            );
            String connectionString = "jdbc:mysql://localhost:3308/java221";
            Connection connection = DriverManager.getConnection(
                    connectionString,
                    "user221",
                    "pass221"
            );
            String sql = "SELECT CURRENT_TIMESTAMP";
            /*
            Д.З. Відобразити результати запиту "SHOW DATABASES",
            передавши їх рядком через кому у відповідь HomeServlet
             */
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( sql );
            resultSet.next();
            message = resultSet.getString( 1 );   // !!! JDBC відлік з 1
        }
        catch( SQLException ex ) {
            message = ex.getMessage();
        }

        resp.setContentType( "application/json" );
        resp.getWriter().print(
                gson.toJson(
                        new RestResponse()
                                .setStatus( 200 )
                                .setMessage( message )
                )
        );
    }
}

/*
Д.З. Реалізувати @WebServlet("/time"), перехід на яку
буде видавати мітку серверного системного часу
** видавати у двох форматах: timestamp (long) / ISO
 */