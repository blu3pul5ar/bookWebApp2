/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.nap.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nicholas
 */
public class MySqlDBStrategy implements DBStrategy{
    private Connection conn;

    @Override
    public void openConnection(String driverClass,String url,String username,String password) throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url,username,password);
    }
    @Override
    public void closeConnection() throws SQLException{
        conn.close();
    }
    /**
     * make sure you open and close connection when using this method
     * future optimizations may include change the return type to array
     * @param tableName
     * @param maxRecords -limits records found to first maxRecords or if maxRecords is 0 then no limit.
     * @return 
     */
    public List<Map<String,Object>> findAllRecords(String tableName, int maxRecords) throws SQLException{
        String sql; 
        if(maxRecords < 1){
        sql = "Select * from " + tableName;
        }
        else{
            sql = "Select * from " + tableName + " limit " + maxRecords;
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        List<Map<String,Object>> records = new ArrayList<>();
        
        while(rs.next()){
            Map<String,Object> record = new HashMap<>();
            for(int colNo = 1; colNo <= columnCount; colNo++){
                Object colData = rs.getObject(colNo);
                String colName = rsmd.getColumnName(colNo);
                record.put(colName,colData);
                
            }
            records.add(record);
        }
        return records;
    }
     @Override
    public int deleteRecordbyPrimaryKey(String tableName, String primarykeyName, Object primaryKeyValue) throws SQLException {
        int numDeleted = 0;
        PreparedStatement statement = null;

        
            final String sql = "Delete FROM " + tableName + " WHERE " + primarykeyName + " = ?";

            statement = conn.prepareStatement(sql);

            if (primarykeyName != null) {
                if (primaryKeyValue instanceof String) {
                    statement.setString(1, (String) primaryKeyValue);
                } else {
                    statement.setObject(1, primaryKeyValue);
                }

            }
            numDeleted = statement.executeUpdate();
        return numDeleted;
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBStrategy db = new MySqlDBStrategy();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        List<Map<String,Object>> rawData = db.findAllRecords("author",0);
        db.closeConnection();
        System.out.println(rawData);
        
    }
}
              
  