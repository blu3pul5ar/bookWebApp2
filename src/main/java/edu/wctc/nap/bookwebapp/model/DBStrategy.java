/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.nap.bookwebapp.model;

/**
 *
 * @author Nicholas
 */

    import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * The general contract for all JDBC database access strategy implementations.
 * Use of this interface is mandatory.
 * 
 * @author jlombardo
 */
public interface DBStrategy {
    
    public abstract void openConnection(String driverClass, String url, 
            String userName, String password) 
            throws ClassNotFoundException, SQLException;
    
    public abstract void closeConnection() throws SQLException;
    public abstract List<Map<String,Object>> findAllRecords(String tableName, int maxRecords) throws SQLException;
    public int deleteRecordbyPrimaryKey(String tableName, String primarykeyName, Object primaryKeyValue) throws SQLException;
}
