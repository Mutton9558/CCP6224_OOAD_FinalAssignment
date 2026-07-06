package Equipment;

import javax.swing.*;

public class temprunner{
  public static void main(String[] args){
      SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        CategoryDB categoryRepo = new CategoryDB();
        EquipmentDB equipmentRepo = new EquipmentDB();
        CategoryController categoryService = CategoryController.getInstance(categoryRepo);
        EquipmentController equipmentService = EquipmentController.getInstance(equipmentRepo, categoryService);
        
        core.SystemServices services = new core.SystemServices(categoryService, equipmentService);
        core.SystemFacade facade = new core.SystemFacade(services);
        
        // Instantiate your new JPanel
        EquipmentPanelUI listPanel = new EquipmentPanelUI(false, facade);

        // Highly recommended: Wrap it in a scroll pane so content can scroll 
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);

        frame.add(scrollPane);
        frame.setVisible(true);
    });
  }
}
