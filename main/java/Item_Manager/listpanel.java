
package Item_Manager;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import Item_Manager.*;


public class listpanel implements ActionListener{
    
    public static JPanel f;
    JLabel l1, l2, l3, l4;
    connection c;

    public listpanel(){
        try {
            c = new connection();
            f = new JPanel();
            f.setLayout(new FlowLayout());
            f.setSize(600, 600);
            f.setVisible(true);
            
            l1 = new JLabel("Name");
            l2 = new JLabel("Price");
            l3 = new JLabel("Brand");
            l4 = new JLabel("Category");
            
            ResultSet result = c.selectALL();
            ResultSetMetaData md = result.getMetaData();
            int column_count = md.getColumnCount();
            String count = column_count+"";
            JTextField info = new JTextField(count);
            info.setEditable(false);
            System.out.println(column_count);
            
            while(result.next()){
                JPanel content_panel = new JPanel();
                content_panel.setLayout(new FlowLayout());
                //content_panel.setSize(600, 600);
                content_panel.setVisible(true);
            
                String id = result.getString(1);
                String name = result.getString(2);
                String category = result.getString(3);
                String price = result.getString(4);
                String brand = result.getString(5);
                String src1 = result.getString(6);
                String desc = result.getString(9);
                String quantity = result.getString(10);
                
                JLabel l1, l2, l3, l4, l5, l6/*, l7*/;
                l1 = new JLabel(name);
                l2 = new JLabel(category);
                l3 = new JLabel(price);
                l4 = new JLabel(brand);
                l5 = new JLabel(desc);
                l6 = new JLabel(quantity);
            }
        } catch (SQLException ex) {
            System.out.println("Error"+ex);
        }
        
    }

    public void actionPerformed(ActionEvent e) {
        
    }
    
}
