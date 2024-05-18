
package cj.program;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class ActionListenerDemo{
    public static void main(String args[]){
        new Action();
    }
}

class Action implements ActionListener{
    String a,b;
    JTextField t1;
    JTextField t2;

    public Action() {
        JFrame f = new JFrame("Student data");
        
        JLabel l1 = new JLabel("Name");
        t1 = new JTextField(10);
        JLabel l2 = new JLabel("City");
        t2 = new JTextField(10);
        
        JButton b1 = new JButton("Submit");
        b1.addActionListener(this);
        
        f.setLayout(new FlowLayout());
        
        f.add(l1);
        f.add(t1);
        f.add(l2);
        f.add(t2);
        f.add(b1);
        
        f.setSize(400,400);
        f.setVisible(true);
        
    }
    public void actionPerformed(ActionEvent e){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "abc");
            String q ="insert into student(Name, City) values(?,?);";
            PreparedStatement ps = con.prepareStatement(q);
            a = t1.getText();
            b = t2.getText();
            ps.setString(1,a);
            ps.setString(2,b);
            ps.executeUpdate();
            System.out.println("Success");
        }
        catch(SQLException ae){
            System.out.println(ae);
        }
        catch(ClassNotFoundException ae){
            System.out.println(ae);
        }
    }

}

