package com.zproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SQLData {


    public static Iterator<Object[]> getData() throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root");

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select isbn, aisle from jsonData");
        List<Object[]> list = new ArrayList<>();
        while(rs.next()){
           String isbn = rs.getString("isbn");
           String aisle = rs.getString("aisle");
           list.add(new Object[] {isbn, aisle});
        }
        conn.close();
        statement.close();
        rs.close();
        return list.iterator();
    }
}