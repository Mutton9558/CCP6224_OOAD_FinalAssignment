//package User;
//
//import javax.swing.*;
//import java.awt.*;
///**
// *
// * @author User
// */
//public class temprunner {
//    public static void main(String[] args){
//        JFrame test = new JFrame("Test");
//        test.setSize(800, 600);
//        JPanel testPanel = new JPanel();
//        testPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//        JButton testBtn = new JButton("Test");
//        
//        Window parent = SwingUtilities.getWindowAncestor(testBtn);
//        testBtn.addActionListener(e -> {
//            JDialog testDialog = new RegisterUserDialog(parent);
//            testDialog.setVisible(true);
//        });
//        
//        testPanel.add(testBtn);
//        test.add(testPanel);
//        
//        test.setVisible(true);
//        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//}
