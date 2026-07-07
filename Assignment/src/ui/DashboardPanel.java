package ui;
import javax.swing.JPanel;

public interface DashboardPanel{
    public String getName();
    public boolean isVisible();
    public JPanel getPanel();
}