package dev.pepe44.manager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLLite {

    private static Connection conni;

    public static void connect() {
        conni = null;

        try {
            File file = new File("Drembot.db");
            if (!file.exists()) {
                file.createNewFile();
            }

        }catch (SQLException | IOException e) {

        }



    }

}
