package Equipment;

import javax.swing.*;

public class temprunner{
  public static void main(String[] args){
      SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        // Instantiate your new JPanel
        RentedEquipmentUI listPanel = new RentedEquipmentUI();

        // Highly recommended: Wrap it in a scroll pane so content can scroll 
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);

        frame.add(scrollPane);
        frame.setVisible(true);
    });
  }
}
