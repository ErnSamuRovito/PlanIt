package View;

import javax.swing.*;

public class StWindow
{
    private final int WINDOW_HEIGHT = 600;
    private final int WINDOW_WIDTH = 800;
    private final String WINDOW_TITLE = "Plan It";

    private static StWindow instance;
    private final JFrame frame;
    private JPanel currentPanel;

    private StWindow()
    {
        frame = new JFrame(WINDOW_TITLE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        currentPanel = new LoginPanel(this);
        setPanel(currentPanel);
    }

    public static synchronized StWindow getInstance()
    {
        if (instance == null)
        {
            instance = new StWindow();
        }
        return instance;
    }

    public void setPanel(JPanel panel)
    {
        frame.getContentPane().removeAll();
        currentPanel = panel;
        frame.add(currentPanel);
        updateWindow();
    }

    private void updateWindow()
    {
        frame.revalidate();
        frame.repaint();
    }
}
