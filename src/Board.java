import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;

public class Board extends JPanel {
    private final Figure[][] board;
    private final int pixelSize = 100;
    private boolean firstTurn=true;
    private boolean pick = false;
    private Point coordinates;
    public Board(boolean side_choose) {
        int size = 800 / pixelSize;
        board = new Figure[size][size];
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.WHITE);
        fill_board(side_choose);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!pick)
                {
                    System.out.println("wybrano figure");
                    coordinates = pick_figure(e.getX() / pixelSize, e.getY() / pixelSize);
                }
                else
                {
                    System.out.println("wybrana figura: " + coordinates.x + " / " + coordinates.y);
                    System.out.println("ruch ale nie wiadomo czy dobry");
                    move(coordinates.x,coordinates.y,e.getX() / pixelSize, e.getY() / pixelSize);

                }

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

    public Point pick_figure(int posx, int posy)
    {

        if(board[posx][posy]!=null&&firstTurn == board[posx][posy].side)
        {
                firstTurn=!firstTurn;
                pick=!pick;
                return new Point(posx,posy);

        }
        else
        {
            System.out.println("błąd wyboru figury");
            return new Point(-1,-1);
        }
    }

    public void move(int posx, int posy, int nposx, int nposy)
    {
        if(range( posx, posy, nposx, nposy)) {
            System.out.println("udany ruch");
            board[nposx][nposy] = board[posx][posy];
            board[posx][posy]=null;
            pick = !pick;
        }
        else {
            System.out.println("zly ruch");
        }

    }

    public boolean range(int x,int y, int nx, int ny)
    {
        //if(board[x][y].FigureType=='p') {
            if (board[x][y].startPosition) {
                if (y - ny == -1 && x==nx) {
                    return true;
                } else {
                    return false;
                }
                //-1
            } else {
                if (y - ny == 1&& x==nx) {
                    return true;
                } else {
                    return false;
                }
            }

    }
}