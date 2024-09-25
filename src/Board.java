import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Board extends JPanel {
    private final Figure[][] board;
    private final int pixelSize = 100;
    private boolean firstTurn=true;

    public Board(boolean side_choose) {
        int size = 800 / pixelSize;
        board = new Figure[size][size];
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.WHITE);
        fill_board(side_choose);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pick_figure(e.getX()/pixelSize,e.getY()/pixelSize);

                repaint();
            }

        });

        setFocusable(true);
        requestFocusInWindow();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if ((x % 2 == 1 && y % 2 == 0) || (x % 2 == 0 && y % 2 == 1)) {
                    g.setColor(new Color(0, 0, 0));
                    g.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
                }
                if (board[x][y]!=null) {
                    g.drawImage(board[x][y].image, x * pixelSize, y * pixelSize, pixelSize, pixelSize, this);
                }


            }
        }
    }


    private void fill_board(boolean side_choose) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (j == 1) {
                    Pawn p = new Pawn(!side_choose,true);
                    board[i][j] = p;
                }
                if (j == 6) {
                    Pawn p = new Pawn(side_choose,false);
                    board[i][j] = p;
                }
            }
        }
    }

    public void pick_figure(int posx, int posy)
    {

        if(board[posx][posy]!=null&&firstTurn == board[posx][posy].side)
        {
                System.out.println("wybrano figure, rane: " + board[posx][posy].range);
                firstTurn=!firstTurn;
        }
        else
        {

                System.out.println("Błąd!!!");
        }




    }
}