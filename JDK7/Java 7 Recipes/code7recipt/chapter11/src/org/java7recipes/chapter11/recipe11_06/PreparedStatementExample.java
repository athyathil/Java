
package org.java7recipes.chapter11.recipe11_06;

import org.java7recipes.chapter11.recipe11_01.CreateConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Recipe 11-6:  Using PreparedStatements
 * @author juneau
 */
public class PreparedStatementExample {
    
    public static Connection conn = null;

    public static void main(String[] args) {
        try {
            CreateConnection createConn = new CreateConnection();
            conn = createConn.getConnection();
            queryDbRecipe("11-1");
            insertRecord(
                    "11-6",
                    "Simplifying and Adding Security with Prepared Statements",
                    "Working with Prepared Statements",
                    "Recipe Text");
            queryDbRecipe("11-6");
            deleteRecord("11-6");
        } catch (java.sql.SQLException ex) {
            System.out.println(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
    
    private static void queryDbRecipe(String recipeNumber){
        String sql = "SELECT ID, RECIPE_NUM, NAME, DESCRIPTION " +
                     "FROM RECIPES " +
                     "WHERE RECIPE_NUM = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, recipeNumber);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(2) + ": " + rs.getString(3) + 
                                " - " + rs.getString(4));
            }           
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (pstmt != null){
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    }
    
    private static void insertRecord(String recipeNumber,
                              String title,
                              String description,
                              String text){
        String sql = "INSERT INTO RECIPES VALUES(" +
                     "RECIPES_SEQ.NEXTVAL, ?,?,?,?)";
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, recipeNumber);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, text);
            pstmt.executeUpdate();
            System.out.println("Record successfully inserted.");
        } catch (SQLException ex){
            ex.printStackTrace();
        } finally {
            if (pstmt != null){
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    }
    
    private static void deleteRecord(String recipeNumber){
        String sql = "DELETE FROM RECIPES WHERE " +
                     "RECIPE_NUM = ?";
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, recipeNumber);
            pstmt.executeUpdate();
            System.out.println("Recipe " + recipeNumber + " successfully deleted.");
        } catch (SQLException ex){
            ex.printStackTrace();
        } finally {
            if (pstmt != null){
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    }
    
}
