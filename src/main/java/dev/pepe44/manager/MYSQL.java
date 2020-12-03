package dev.pepe44.manager;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class MYSQL {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    ArrayList<String[]> result = new ArrayList<String[]>();


    private String host = "localhost";
    private String port = "3306";
    private String dbname = "essentialmode";
    private String user = "copnet";
    private String pass = "CO2Myf92DrNeql239$s$$ad.dawe";

    Connection conn = null;

    public MYSQL() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Fehler beim laden der MYSQL Treiber" + e);
            return;
        }
        try {
            System.out.println("Start Connection...");
            String url = "jdbc:mysql://"+host+":"+port+"/"+dbname+"?idleConnectionTestPeriod=10&idleConnectionTestPeriod=10&useUnicode=yes&autoReconnect=true";
            conn = DriverManager.getConnection(url, user, pass);

            System.out.println("*launch Statement");


        }catch (SQLException sqle) {
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
            sqle.printStackTrace();
        }

    }

    public int getData(String name) throws SQLException  {
        String sqlCMD =
                "SELECT timePlay from users WHERE name = ?";
        PreparedStatement stmt = conn.prepareStatement(sqlCMD);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int time = rs.getInt("timePlay");
            return time;
        }
        stmt.close();

       return -1;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}