package dev.pepe44;

import dev.pepe44.manager.MYSQL;

import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {

        MYSQL mysql = new MYSQL();
        System.out.println(mysql.getData("Pepe44"));
        mysql.close();
    }
}
