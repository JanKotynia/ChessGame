import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;

public class Board extends JPanel {
    private final Figure[][] board;
    private final int pixelSize = 100;
    private boolean firstTurn = true;
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
                if (!pick) {
                    System.out.println("wybrano figure");
                    coordinates = pick_figure(e.getX() / pixelSize, e.getY() / pixelSize);
                } else {
                    System.out.println("wybrana figura: " + coordinates.x + " / " + coordinates.y);
                    System.out.println("ruch ale nie wiadomo czy dobry");
                    move(coordinates.x, coordinates.y, e.getX() / pixelSize, e.getY() / pixelSize);

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
                if (board[x][y] != null) {
                    g.drawImage(board[x][y].image, x * pixelSize, y * pixelSize, pixelSize, pixelSize, this);
                }


            }
        }
    }


    private void fill_board(boolean side_choose) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (j == 1) {
                    Pawn p = new Pawn(!side_choose, true);
                    board[i][j] = p;
                }
                if (j == 6) {
                    Pawn p = new Pawn(side_choose, false);
                    board[i][j] = p;
                }
            }
        }

        Tower t = new Tower(!side_choose);
        board[0][0]= t;
        board[7][0]= t;
        Tower bt = new Tower(side_choose);
        board[0][7]= bt;
        board[7][7]= bt;

        Horse h = new Horse(!side_choose);
        board[1][0]= h;
        board[6][0]= h;
        Horse bh = new Horse(side_choose);
        board[1][7]= bh;
        board[6][7]= bh;

        Bishop b = new Bishop(!side_choose);
        board[2][0]= b;
        board[5][0]= b;
        Bishop bb = new Bishop(side_choose);
        board[2][7]= bb;
        board[5][7]= bb;

        King k = new King(!side_choose);
        King bk = new King(side_choose);
        if(!side_choose){
            board[4][0] = k;
            board[3][7] = bk;
        }
        else
        {
            board[3][0] = k;
            board[4][7] = bk;
        }
    }

    public Point pick_figure(int posx, int posy) {

        if (board[posx][posy] != null && firstTurn == board[posx][posy].side) {
            firstTurn = !firstTurn;
            pick = !pick;
            return new Point(posx, posy);

        } else {
            System.out.println("błąd wyboru figury");
            return new Point(-1, -1);
        }
    }

    public void move(int posx, int posy, int nposx, int nposy) {
        if (range(posx, posy, nposx, nposy)) {
            System.out.println("udany ruch");
            board[nposx][nposy] = board[posx][posy];
            board[posx][posy] = null;
            pick = !pick;
        } else {
            System.out.println("zly ruch");
        }

    }

    public boolean range(int x, int y, int nx, int ny) {

        switch (board[x][y].FigureType) {

            case 'p': {
                return pawnRange(x,y,nx,ny);
            }
                case 't': {
                    return towerRange(x,y,nx,ny);
                }
                    case 'h' : {
                        return horseRange(x,y,nx,ny);
                    }
                        case 'k' : {
                            return kingRange(x,y,nx,ny);
                        }
                            case 'b' : {
                                return false;
                                //return bishopRange(x,y,nx,ny);
                            }
                default:
                    return false;
        }
    }



    public boolean bishopRange(int x, int y, int nx, int ny)
{
    int step=1;
    int step2=-1;
    if(Math.abs(nx - x) == Math.abs(ny - y))
    {
        for (int i = x;i<nx;i+=step)
        {
            for (int j = y;j<ny;j+=step)
            {
                if(board[i][j]!=null && board[i][j].side == board[x][y].side)
                    return false;
            }
        }
        return true;
    }
    else
        return false;
}

    public boolean pawnRange(int x, int y, int nx, int ny)
    {
        if (board[x][y].startPosition) {
            if (y - ny == -1 && x == nx) {
                if(board[nx][ny] == null || board[nx][ny] != null && (board[nx][ny].side != board[x][y].side) )
                    return true;
            } else {
                return false;
            }
            //-1
        }
        else
        {
            if (y - ny == 1 && x == nx) {
                {
                    if(board[nx][ny] == null || board[nx][ny] != null && (board[nx][ny].side != board[x][y].side) )
                        return true;
                }
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    public boolean kingRange(int x, int y, int nx, int ny)
    {
        if (Math.abs(x - nx) <= 1 && Math.abs(y - ny) <= 1){
            return board[nx][ny] == null || (board[nx][ny] != null && (board[nx][ny].side != board[x][y].side));
        }
        else
            return false;
    }

    public boolean towerRange(int x, int y, int nx, int ny)
    {
        int step;
        if(x != nx && y != ny) {
            return false;
        }

        if(y == ny)
        {
            step = (x<nx) ? 1 : -1;
            for (int i = x+step ; i != nx ; i+=step)
            {
                if(board[i][y] != null)
                    return false;
            }
            if(board[nx][ny] == null || (board[nx][ny] != null && (board[nx][ny].side != board[x][y].side)) )
            {    return true; }
        }

        if(x == nx)
        {
            step = (y<ny) ? 1 : -1;
            for (int i = y+step ; i != ny ; i+=step)
            {
                if(board[x][i] != null)
                    return false;

            }
            if(board[nx][ny] == null || (board[nx][ny] != null && (board[nx][ny].side != board[x][y].side)) )
            {    return true; }
        }
        return false;
    }

    public boolean horseRange(int x, int y, int nx, int ny)
    {
        if ((Math.abs(x - nx) == 2 && Math.abs(y - ny) == 1) || (Math.abs(x - nx) == 1 && Math.abs(y - ny) == 2)){
            return board[nx][ny] == null || (board[nx][ny] != null && (board[nx][ny].side != board[x][y].side));
        }
        else
            return false;
    }
}