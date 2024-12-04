package view.panel;

import core.GlobalResources;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AnimatedGifPanel extends JPanel {
    private final List<Image> frames = new ArrayList<>();
    private int currentFrame = 0;
    private Timer timer;

    public AnimatedGifPanel() {
        loadFrames();
        initializeTimer();
    }

    private void loadFrames() {
        // Carica le GIF utilizzando le costanti definite in GlobalResources
        frames.add(new ImageIcon(GlobalResources.plantHappyState).getImage());
        frames.add(new ImageIcon(GlobalResources.plantNormalState).getImage());
        frames.add(new ImageIcon(GlobalResources.plantSadState).getImage());
    }

    private void initializeTimer() {
        int delay = 1000; // intervallo in millisecondi (1 secondo tra i frame)
        timer = new Timer(delay, e -> {
            currentFrame = (currentFrame + 1) % frames.size();
            repaint(); // Richiama il metodo paintComponent
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(GlobalResources.COLOR_PANNA);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.drawImage(frames.get(currentFrame), GlobalResources.folderXOffset, GlobalResources.folderYOffset, this);
    }
}