package cj.program;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class eventDemo implements ActionListener{
    eventDemo(){
        JFrame j = new JFrame();
        j.setVisible(true);
        j.setLayout(null);
        j.setSize(100, 100);
        
        JButton btn = new JButton("Click ME!");
        j.add(btn);
        btn.addActionListener(this);
    }
    public static void main(String args[]){
        new eventDemo();
    }
    public void actionPerformed(ActionEvent e){
        JDialog dialog = new JDialog();
        //dialog.d
    }
}
