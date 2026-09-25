package ro.florinradu.concurrentjava.chapter03.section01;

// Book section: 3.1 From Anonymous Classes to Lambda Expressions
// Adapted into a standalone runnable example.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnonymousVsLambdaExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Anonymous Vs Lambda");
        JButton buttonAnon = new JButton("Anonymous");
        JButton buttonLambda = new JButton("Lambda");

        // ActionListener implemented with anonymous class
        buttonAnon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("The button was clicked! (Anonymous class)");
            }
        });

        // ActionListener implemented with lambda expression
        buttonLambda.addActionListener(e -> System.out.println("The button was clicked! (Lambda Expression)"));

        frame.add(buttonAnon);
        frame.add(buttonLambda);
        frame.setLayout(new FlowLayout());
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }
}
