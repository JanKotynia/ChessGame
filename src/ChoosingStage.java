import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

    public class ChoosingStage extends JPanel {
        private final boolean[][] board;
        private final int pixelSize = 400;

        ChoosingStage()
        {
            int size = 800 / pixelSize;
            board = new boolean[size][size];
            setPreferredSize(new Dimension(800, 800));
            setBackground(Color.WHITE);
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    JFrame frame = new JFrame("GameStart");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.add(new Board(board[e.getX() / pixelSize][e.getY() / pixelSize]));
                    frame.pack();
                    frame.setVisible(true);
                    Window window = SwingUtilities.getWindowAncestor(ChoosingStage.this);
                    window.setVisible(false);
                }

            });

            setFocusable(true);
            requestFocusInWindow();
        }


        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);


            g.setColor(new Color(0, 0, 0));
            board[1][0]=true;
            board[1][1]=true;
            g.fillRect(0 * pixelSize, 0 * pixelSize, pixelSize, pixelSize);
            g.fillRect(0 * pixelSize, 1 * pixelSize, pixelSize, pixelSize);
        }
    }

