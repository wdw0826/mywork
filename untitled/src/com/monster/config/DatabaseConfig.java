package com.monster.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/monster_hunter_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "william0826"; // <-- 改成你的 PostgreSQL 密碼

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}