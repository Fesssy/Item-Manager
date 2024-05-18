
package cj.program;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class Calculator{
    public static void main(String[] args) {
        new cal();
    }
}

class cal extends JFrame implements ActionListener{
    JTextField inp1, inp2;
    JButton add, sub, mul, div;
    JLabel ans;
    public cal() throws HeadlessException {
        
        setLayout(new FlowLayout());
        setSize(300, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        inp1 = new JTextField(20);
        inp1.setBackground(Color.WHITE);
        inp1.setBounds(10,10,10,25);
        
        inp2 = new JTextField(20);
        inp2.setBackground(Color.WHITE);
        inp2.setBounds(10,40,10,25);
        
        JPanel p = new JPanel(new GridLayout(2, 2, 10, 10));
        p.setSize(60, 60);
        
        add = new JButton("Add");
        add.setBorder(new BevelBorder(1));
        add.addActionListener(this);
        p.add(add);
        sub = new JButton("Subtract");
        sub.setBorder(new BevelBorder(1));
        sub.addActionListener(this);
        p.add(sub);
        mul = new JButton("Multiply");
        mul.setBorder(new BevelBorder(1));
        mul.addActionListener(this);
        p.add(mul);
        div = new JButton("Divide");
        div.setBorder(new BevelBorder(1));
        div.addActionListener(this);
        p.add(div);
        
        ans = new JLabel();
        ans.setBackground(Color.white);
        ans.setBounds(100,100,10,25);
        
        add(inp1);
        add(inp2);
        add(p);
        add(ans);
    }

    public void actionPerformed(ActionEvent e) {
        if(inp1.getText().isBlank() || inp2.getText().isBlank()){
            JOptionPane.showMessageDialog(this, "Fill all the inputs", "Alert", JOptionPane.ERROR_MESSAGE);
        }
        else{
            float a = Float.parseFloat(inp1.getText());
            float b = Float.parseFloat(inp2.getText());
            
            if(e.getActionCommand() == "Add"){
                float c = a + b;
                String answer = c+"";
                ans.setText(answer);
            }
            else if(e.getActionCommand() == "Subtract"){
                float c = a - b;
                String answer = c+"";
                ans.setText(answer);
            }
            else if(e.getActionCommand() == "Multiply"){
                float c = a * b;
                String answer = c+"";
                ans.setText(answer);
            }
            else if(e.getActionCommand() == "Divide"){
                float c = a / b;
                String answer = c+"";
                ans.setText(answer);
            }
        }
    }
    
}
