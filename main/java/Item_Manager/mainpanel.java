
package Item_Manager;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class mainpanel implements ActionListener{
    connection c;
    public static JPanel panel;
    JLabel l1, l2, l3, l4, l5, l6, lab1, lab2, lab3;
    JTextField tf1, tf2, tf3;
    JTextArea ta1;
    JButton btn1, btn2;
    JRadioButton rbtn1, rbtn2, rbtn3;
    ButtonGroup bg1;
    JComboBox cb1;
    JFileChooser filechooser;
    String img_src1, img_src2, img_src3;
    
    mainpanel(){
        new connection();
        img_src1 = img_src2 = img_src3 = null;
        
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setVisible(true);
        panel.setSize(400, 400);
        
        l1 = new JLabel("Item Name: ");
        panel.add(l1);
        tf1 = new JTextField("name", 30);
        panel.add(tf1);
        
        l2 = new JLabel("Item Price: ");
        panel.add(l2);
        tf2 = new JTextField("₨. ", 10);
        panel.add(tf2);
        
        l3 = new JLabel("Category: ");
        panel.add(l3);
        rbtn1 = new JRadioButton("Sneakers");
        rbtn2 = new JRadioButton("Ballet");
        rbtn3 = new JRadioButton("Flip Flop");
        bg1 = new ButtonGroup();
        bg1.add(rbtn1);
        bg1.add(rbtn2);
        bg1.add(rbtn3);
        panel.add(rbtn1);
        panel.add(rbtn2);
        panel.add(rbtn3);
        
        l4 = new JLabel("Brand: ");
        panel.add(l4);
        String[] Brands = {"Nike", "Adidas", "Bata"};
        cb1 = new JComboBox(Brands);
        panel.add(cb1);
        
        l5 = new JLabel("Description: ");
        panel.add(l5);
        ta1 = new JTextArea("Our item is .....", 5, 20);
        panel.add(ta1);
        
        l6 = new JLabel("Quantity: ");
        panel.add(l6);
        tf3 = new JTextField(30);
        panel.add(tf3);
        
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
               System.out.println("Fill all the blanks.");
           }
           else{
               String name = tf1.getText();
               String price = tf2.getText();
               String quantity = tf3.getText();
               String desc = ta1.getText();
               String category = bg1.getSelection().getActionCommand();
               String brand = cb1.getSelectedItem().toString();
               boolean s = c.add(name, category, price, brand, img_src1, img_src2, img_src3, desc, quantity);
               if (s){
                   JOptionPane.showMessageDialog(panel,"Successfully added.", "Message", JOptionPane.INFORMATION_MESSAGE);
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