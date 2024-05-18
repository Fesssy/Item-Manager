
package Item_Manager;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class mainframe {
    
    public static uservalidate u;
    JFrame frame;
    mainpanel tab1;
    listpanel tab2;
    
    public mainframe() {
        frame = new JFrame("Item Manager");
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane tp = new JTabbedPane();
        tp.setBounds(20, 20, 500, 500);
        frame.add(tp, BorderLayout.CENTER);
        
        new listpanel();
        new mainpanel();
        
        JScrollPane scrollablelist = new JScrollPane(listpanel.f);  
        scrollablelist.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scrollablelist.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        tp.add("New Item",mainpanel.panel);
        tp.add("Items List",frame.getContentPane().add(scrollablelist));
        
        JButton refreshbtn = new JButton("Refresh");
        refreshbtn.setBackground(Color.white);
        refreshbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tp.remove(1);
                new listpanel();
                JScrollPane scrollablelist = new JScrollPane(listpanel.f);  
                scrollablelist.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
                scrollablelist.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                tp.add("Items List",frame.getContentPane().add(scrollablelist));
            }
        });
        frame.add(refreshbtn, BorderLayout.SOUTH);
        
    }
    public static void main(String[] args) {
        u = new uservalidate();
        u.dispose();
        
    }
}

//user validation
class uservalidate extends JFrame{
    public uservalidate(){
        setSize(800, 400);
        setVisible(true);
        
        JPanel p = new JPanel(new GridLayout(3, 1));
        setSize(400, 200);
        
        JTextField tf1, tf2;
        tf1 = new JTextField("Username:  Admin");
        tf1.setEditable(false);
        tf2 = new JPasswordField(10);
        JButton btn = new JButton("Log In");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(tf2.getText().matches("abc123")){
                    JOptionPane.showMessageDialog(null, "Verified", "", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    new mainframe();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Password is wrong", "", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            }
        });
        p.add(tf1);
        p.add(tf2);
        p.add(btn);
        add(p);
        try {
            TimeUnit.MINUTES.sleep(5);
        } catch (Exception e) {
        }
    }
}

//Connections
class connection {
    
    Connection con;
    String q = null;
    Statement st;
    PreparedStatement pst;
    ResultSet rs;
    
    public connection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/shoesy", "root", "");
            System.out.println("Success!");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public int add(String name, String category, int price, String brand, String img_src1, String img_src2, String img_src3, String desc, int quantity){
        int successful = 0;
        q = "insert into `product`(`name`, `category`, `price`, `brand`, `img1`, `img2`, `img3`, `desc`, `quantity`) values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            pst = con.prepareStatement(q);
            pst.setString(1, name);
            pst.setString(2, category);
            pst.setInt(3, price);
            pst.setString(4, brand);
            pst.setString(5, img_src1);
            pst.setString(6, img_src2);
            pst.setString(7, img_src3);
            pst.setString(8, desc);
            pst.setInt(9, quantity);
            successful = pst.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return successful;
    }
    
    public int delete(int id){
        int res = 0;
        q = "delete from product where id = " + id + ";";
        int ans = JOptionPane.showConfirmDialog(listpanel.f, "Do you want to delete it?", "Confirmation!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (ans == JOptionPane.YES_OPTION) {} else { return -1; }
        try {
            st = con.createStatement();
            res = st.executeUpdate(q);
            return res;
        } catch (Exception e) {
            System.out.println("Error"+ e);
        }
        return res;
    }
    
    public ResultSet selectALL(){
        q = "select * from product;";
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

//listpanel
class listpanel implements ActionListener{
    
    public static JPanel f;
    JLabel l1, l2, l3, l4;
    connection c;

    public listpanel(){
        try {
            c = new connection();
            f = new JPanel();
            f.setLayout(new BoxLayout(f, BoxLayout.Y_AXIS));
            f.setAutoscrolls(true); 
            
            ResultSet result = c.selectALL();
            
            while(result.next()){
                JPanel content_panel = new JPanel();
                content_panel.setLayout(new BorderLayout());
                content_panel.setSize(30, 30);
                content_panel.setBackground(Color.white);
                
                f.add(content_panel);
            
                int id = result.getInt(1);
                /*String name = result.getString(2);
                String category = result.getString(3);
                String price = result.getString(4);
                String brand = result.getString(5);
                String src1 = result.getString(6);
                String desc = result.getString(9);
                String quantity = result.getString(10);*/
                
                
                JLabel l, l1, l2, l3, l4, l5, l6;
                ImageIcon img = new ImageIcon("C:\\xampp\\htdocs\\CA2_Project\\shoe_site\\public\\images\\"+result.getString(6));
                //Image thumbnail = img.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
                
                l = new JLabel(img);
                l1 = new JLabel("Name: "+result.getString(2));
                l2 = new JLabel("Category: " + result.getString(3));
                l3 = new JLabel("Price: " + result.getString(4));
                l4 = new JLabel("Brand: "+ result.getString(5));
                l5 = new JLabel("Description: " + result.getString(9));
                l6 = new JLabel("Quantity: " + result.getString(10));
                
                JButton delButton = new JButton("Delete");
                delButton.setBackground(Color.red);
                delButton.setForeground(Color.white);
                delButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int res = c.delete(id);
                        if(res>0){
                            JOptionPane.showMessageDialog(f, "Successfully Deleted", "System", JOptionPane.INFORMATION_MESSAGE);
                            ResultSet result = c.selectALL();
                        }
                        else if(res == 0){
                            JOptionPane.showMessageDialog(f, "Error while Deleting", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } 
                });
                
                // Image in left
                content_panel.add(l, BorderLayout.EAST);
                //content_panel.add(Box.createHorizontalStrut(10));
                content_panel.add(l);
                
                //Name & Category above
                JPanel up = new JPanel(new FlowLayout(FlowLayout.CENTER));
                //content_panel.add(Box.createHorizontalStrut(10));
                up.add(l1);
                up.add(l2);
                content_panel.add(up, BorderLayout.NORTH);
                
                //Price & Brand below
                JPanel below = new JPanel(new FlowLayout(FlowLayout.CENTER));
                //content_panel.add(Box.createHorizontalStrut(10));
                below.add(l3);
                below.add(l4);
                content_panel.add(below, BorderLayout.SOUTH);
                
                //Button in right
                content_panel.add(delButton, BorderLayout.WEST);
                
                /*
                content_panel.add(l3);
                content_panel.add(Box.createHorizontalStrut(10));
                content_panel.add(l4);
                content_panel.add(Box.createHorizontalStrut(10));
                content_panel.add(l5);
                content_panel.add(Box.createHorizontalStrut(10));
                content_panel.add(l6);*/
                
                content_panel.add(Box.createVerticalStrut(30));
                
            }
        } catch (SQLException ex) {
            System.out.println("Error"+ex);
        }
        
    }
    
    public String count(ResultSet result){
        String count = null;
        try {
            ResultSetMetaData md = result.getMetaData();
            int column_count = md.getColumnCount();
            count = column_count+"";
        } catch (SQLException ex) {
            Logger.getLogger(listpanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public void actionPerformed(ActionEvent e) {
        
    }
    
}

//mainpanel
class mainpanel implements ActionListener{
    connection c;
    public static JPanel panel;
    JLabel l1, l2, l3, l4, l5, l6, lab1, lab2, lab3;
    JTextField tf1, tf2, tf3;
    JTextArea ta1;
    JButton btn1, btn2;
    JRadioButton rbtn1, rbtn2, rbtn3;
    ButtonGroup bg1;
    JComboBox <String> cb1;
    JFileChooser filechooser;
    String img_src1, img_src2, img_src3;
    
     public void Reset(){
        lab1.setText(null);
        lab2.setText(null);
        lab3.setText(null);
        tf1.setText(null);
        tf2.setText(null);
        tf3.setText(null);
        ta1.setText(null);
        img_src1 = null;
        img_src2= null;
        img_src3 = null;
    }
     
    mainpanel(){
        c = new connection();
        img_src1 = img_src2 = img_src3 = null;
        
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //panel.setAlignmentX(0f);
        
        JPanel p1, p2, p3, p4, p5;
        
        p1 = new JPanel();
        
        
        l1 = new JLabel("Item Name: ");
        //panel.add(l1);
        p1.add(l1);
        tf1 = new JTextField("name", 10);
        //panel.add(tf1);
        p1.add(tf1);
        panel.add(p1);
        
        p2 = new JPanel();
        
        l2 = new JLabel("Item Price: ");
        //panel.add(l2);
        tf2 = new JTextField(10);
        //panel.add(tf2);
        p2.add(l2);
        p2.add(tf2);
        panel.add(p2);
        
        
        l3 = new JLabel("Category: ");
        panel.add(l3);
        rbtn1 = new JRadioButton("Sneakers");
        rbtn1.setActionCommand("Sneakers");
        rbtn2 = new JRadioButton("Ballet");
        rbtn1.setActionCommand("Ballet");
        rbtn3 = new JRadioButton("Flip Flop");
        rbtn1.setActionCommand("Flip Flop");
        bg1 = new ButtonGroup();
        bg1.add(rbtn1);
        bg1.add(rbtn2);
        bg1.add(rbtn3);
        panel.add(rbtn1);
        panel.add(rbtn2);
        panel.add(rbtn3);
        
        p3 = new JPanel();
        
        l4 = new JLabel("Brand: ");
        //panel.add(l4);
        String[] Brands = {"Nike", "Adidas", "Bata"};
        cb1 = new JComboBox <String> (Brands);
        //panel.add(cb1);
        p3.add(l4);
        p3.add(cb1);
        panel.add(p3);
        
        p4=new JPanel();
        
        l5 = new JLabel("Description: ");
        //panel.add(l5);
        ta1 = new JTextArea("Our item is .....", 5, 20);
        //panel.add(ta1);
        p4.add(l5);
        p4.add(ta1);
        panel.add(p4);
        
        p5 = new JPanel();
        
        l6 = new JLabel("Quantity: ");
        //panel.add(l6);
        tf3 = new JTextField(30);
        //panel.add(tf3);
        p5.add(l6);
        p5.add(tf3);
        panel.add(p5);
        
        filechooser = new JFileChooser("C:\\xampp\\htdocs\\CA2_Project\\shoe_site\\public\\images\\");
        btn1 = new JButton("Attach File");
        panel.add(btn1);
        btn1.addActionListener(this);
        
        lab1 = new JLabel();
        lab2 = new JLabel();
        lab3 = new JLabel();
        panel.add(lab1);
        panel.add(lab2);
        panel.add(lab3);
        
        btn2 = new JButton("Add");
        panel.add(btn2);
        btn2.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn1){
           FileNameExtensionFilter filter = new FileNameExtensionFilter("All img", "jpg", "jpeg", "png", "webp");
           filechooser.addChoosableFileFilter(filter);
           filechooser.setAcceptAllFileFilterUsed(false);
           int response = filechooser.showOpenDialog(null);
           
           if(response == JFileChooser.APPROVE_OPTION){
               File src = filechooser.getSelectedFile(); 
               if(img_src1 == null){
                   img_src1 = relative(src);
                   lab1.setText(img_src1);
               }
               else if(img_src2 == null){
                   img_src2 = relative(src);
                   lab2.setText(img_src2);
               }
               else if(img_src3 == null){
                   img_src3 = relative(src);
                   lab3.setText(img_src3);
               }
           }
       }
        
       if(e.getSource() == btn2){
           if (tf1.getText().isBlank() || tf2.getText().isBlank() || tf3.getText().isBlank() || ta1.getText().isBlank() || bg1.getSelection()==null || cb1.getSelectedIndex()<0 ){
               JOptionPane.showMessageDialog(panel, "Fill all the blanks", "Alert", JOptionPane.WARNING_MESSAGE);
           }
           else{
               String name = tf1.getText();
               int price = Integer.parseInt(tf2.getText());
               int quantity = Integer.parseInt(tf3.getText());
               String desc = ta1.getText();
               String category = bg1.getSelection().getActionCommand();
               String brand = cb1.getSelectedItem().toString();
               int s = c.add(name, category, price, brand, img_src1, img_src2, img_src3, desc, quantity);
               if (s == 1){
                   JOptionPane.showMessageDialog(panel,"Successfully added.", "Message", JOptionPane.INFORMATION_MESSAGE);
                   Reset();
               }
               else{
                   JOptionPane.showMessageDialog(panel, "Error while adding!", "Error", JOptionPane.ERROR_MESSAGE);
               }
           }
       }
    }
    
    public String relative(File img_src){
        String path = img_src.getAbsolutePath();
        String base = "C:/xampp/htdocs/CA2_Project/shoe_site/public/images";
        String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
        return relative;
    }
}