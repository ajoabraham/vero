/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.db;




import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ajoabraham
 */
public class DumpDBProperties {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MSSQLServerDB ds = new MSSQLServerDB();
        ds.setUsername("stuser")
                .setPassword("sourcetable")
                .setDatabaseName("Northwind")
                .setHostName("sqlserver11.cokqqhqwkadj.us-west-2.rds.amazonaws.com");

        try (Connection conn = ds.connect()) {
            DatabaseMetaData md = conn.getMetaData();
            Class cl = DatabaseMetaData.class;            
            
            for (Method method : cl.getMethods()){
                if(method.getName().startsWith("get")){
                    if(method.getReturnType().getCanonicalName().equalsIgnoreCase("java.sql.ResultSet")){
                        if(method.getParameterTypes().length==0){
                            printResultSet(method.getName(), (ResultSet) method.invoke(md));
                        }else{
                            Object[] ag = new Object[method.getParameterTypes().length];
                            for(int i=0;i<method.getParameterTypes().length;i++){
                                ag[i] = (Object)null;
                            }
                            try{
                                printResultSet(method.getName(), (ResultSet) method.invoke(md,ag));
                        
                            }catch(Exception e){
                                //ignore
                            }                           
                        }
                    }else if(method.getReturnType().getCanonicalName().equalsIgnoreCase("java.lang.String")){
                        int len =method.getParameterTypes().length;
                        if(len ==0){
                            System.out.println(method.getName()+":\n");
                            System.out.println((String) method.invoke(md));
                        }else{
                            Object[] ag = new Object[method.getParameterTypes().length];
                            for(int i=0;i<method.getParameterTypes().length;i++){
                                ag[i] = (Object)null;
                            }
                            System.out.println(method.getName()+" ARGS:\n");
                            try{
                                System.out.println((String) method.invoke(md,ag));
                            }catch(Exception e){
                                System.out.println("could not build args");
                            }
                            
                        }
                        
                        
                    }
                }          
            }
                      
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(DumpDBProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void printResultSet(String name,ResultSet rs) throws SQLException {
        System.out.println(name+":\n");
        StringBuffer col = new StringBuffer();
        for (int j=1;j<rs.getMetaData().getColumnCount()+1;j++){
            if (j>0){
                    col.append(" ");
                }
                col.append(rs.getMetaData().getColumnName(j));
        }
        System.out.println(col);
        while(rs.next()){
            StringBuffer s = new StringBuffer();
            
            for(int i=1;i<rs.getMetaData().getColumnCount()+1;i++){
                if (i>0){
                    s.append(" ");
                }
                s.append(rs.getString(i));
            }
            System.out.println(s);
        }
    }
    
}
