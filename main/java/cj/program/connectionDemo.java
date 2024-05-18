package cj.program;

import java.sql.*;


public class connectionDemo {
    public static void main(String args[]){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "abc");
            Statement st = con.createStatement();
            
            String q = "select * from student where name='Imran';";
            ResultSet rs = st.executeQuery(q);
            while(rs.next()){
                String nm = rs.getString(1);
                String ct = rs.getString(2);
                System.out.println("Name: "+nm+ " City: "+ct);
            }
            
            System.out.println("Success");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
