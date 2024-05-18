
package Item_Manager;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Item_Manager.*;

public class connection {
    
    Connection con;
    String q = null;
    Statement st;
    PreparedStatement pst;
    ResultSet rs;
    
    public connection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/shoesy", "root", "");
         
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public boolean add(String name, String category, String price, String brand, String img_src1, String img_src2, String img_src3, String desc, String quantity){
        boolean successful = false;
        q = "insert into product values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            pst = con.prepareStatement(q);
            pst.setString(1, name);
            pst.setString(2, category);
            pst.setString(3, price);
            pst.setString(4, brand);
            pst.setString(5, img_src1);
            pst.setString(6, img_src2);
            pst.setString(7, img_src3);
            pst.setString(8, desc);
            pst.setString(8, quantity);
            successful = st.execute(q);
            
        } catch (SQLException ex) {
            System.out.println();
        }
        return successful;
    }
    
    public ResultSet selectALL(){
        String q = "select * from product;";
        try {
            st = con.createStatement();
            rs = st.executeQuery(q);
        } catch (Exception e) {
            System.out.println("Error"+ e);
        }
        return rs;
    }
    
    public ResultSet selectID(int id){
        q = "select * from student where id=?;";
        String Id = id+"";
        try {
            pst = con.prepareStatement(q);
            pst.setString(1, Id);
            rs = pst.executeQuery();
        } catch (Exception e) {
            System.out.println("error" + e);
        }
        return rs;
    }
    
    public ResultSet select(String category, String brand, int range, String sort){
        q = "select * from product where(";
        if (category == null){}else{q = q+"category ='"+category+"'";}
        if(brand == null){}else{
            if(category == null){q = q+"brand ='"+brand+"'";}
            else{q = q + " && brand ='"+brand+"'";}
        }
        if(range < 0){}else{
            if(brand != null){q = q+" &&";}
            else if(category != null){q = q+" &&";}
            
                if(range == 1){
                    q = q+" price<1000";
                }
                else if(range == 2){
                    q = q+" price>1000 && price<5000";
                }
                else{
                    q = q+ "price>5000";
                }
            
        }
        if(sort != null){
            if(range > 0){q = q+" &&";}
            else if(brand != null){q = q+" &&";}
            else if(category != null){q = q+" &&";}
            if (sort == "l"){
                q = q+"order by price asc";
            }
            else if(sort == "h"){
                q = q+"order by price desc";
            }
            q = q+" );";
        }
        
        try {
            st = con.createStatement();
            rs = pst.executeQuery(q);
        } catch (Exception e) {
            System.out.println("error" + e);
        }
        return rs;
    }
    
}
