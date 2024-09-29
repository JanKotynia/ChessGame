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
                    System.out.println("ruch ale nie wiadomo czy dobry");
                    move(coordinates.x, coordinates.y, e.getX() / pixelSize, e.getY() / pixelSize);

                }

                repaint();
            }

        });

        setFocusable(true);
        requestFocusInWindow();
    }

    public Point pick_figure(int posx, int posy) {

        if (board[posx][posy] != null && firstTurn == board[posx][posy].side) {
            pick = !pick;
            return new Point(posx, posy);

        }
        else {
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
            firstTurn = !firstTurn;
        } else if(board[nposx][nposy] != null && board[nposx][nposy].side==board[posx][posy].side)
        {
            System.out.println("wybierano kolejna figure");
            coordinates = pick_figure(nposx, nposy);
            pick=true;
        }
        else
            System.out.println("zly ruch");


    }

    public boolean range(int x, int y, int nx, int ny) {

        return switch (board[x][y].FigureType) {
            case 'p' -> pawnRange(x, y, nx, ny);
            case 't' -> towerRange(x, y, nx, ny);
            case 'h' -> horseRange(x, y, nx, ny);
            case 'b' -> bishopRange(x, y, nx, ny);
            case 'q' -> queenRange(x, y, nx, ny);
            case 'k' -> kingRange(x, y, nx, ny);
            default -> false;
        };
    }


    public boolean pawnRange(int x, int y, int nx, int ny)
    {
        if(y == 1 || y == 6)
        {
            if (board[x][y].startPosition) {
                if (y - ny == -1 && x == nx ) {
                    return pawnDefaultRange(x,y,nx,ny);
                }
                else if(y - ny == -2 && x == nx )
                {
                 return board[x][y+1] == null &&  board[x][ny] == null;
                }
                else
                    return false;
            }
            else {
                if (y - ny == 1 && x == nx ) {
                    return pawnDefaultRange(x,y,nx,ny);
                }
                else if(y - ny == 2 && x == nx )
                {
                    return board[x][y-1] == null &&  board[x][ny] == null;
                }
                else
                    return false;
            }

        }
        else
            return pawnDefaultRange(x,y,nx,ny);
    }


    public boolean pawnDefaultRange(int x, int y, int nx, int ny)
    {
        if (board[x][y].startPosition) {
            if (y - ny == -1 && x == nx ) {
                return board[nx][ny] == null;
            }
            else return board[nx][ny] != null && board[nx][ny].side != board[x][y].side && (Math.abs(x - nx) == 1 && ny - y == 1);
            //-1
        }
        else {
            if (y - ny == 1 && x == nx ) {
                return board[nx][ny] == null;
            }
            else return board[nx][ny] != null && board[nx][ny].side != board[x][y].side && (Math.abs(x - nx) == 1 && ny - y == -1);
        }
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
            return board[nx][ny] == null || (board[nx][ny] != null && (board[nx][ny].side != board[x][y].side));
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


    public boolean bishopRange(int x, int y, int nx, int ny)
    {
        if(Math.abs(nx - x) != Math.abs(ny - y))
        {
           return false;
        }

            int step2 = (ny>y) ? 1 : -1;
            int step = (nx>x) ? 1 : -1;

            int Tempx=x+step;
            int Tempy=y+step2;
            while (Tempx!=nx && Tempy!=ny)
            {

                if(board[Tempx][Tempy]!=null)
                    return false;
                Tempx+=step;
                Tempy+=step2;
            }
        return board[nx][ny] == null || (board[nx][ny].side != board[x][y].side);

    }


    public boolean queenRange(int x, int y, int nx, int ny)
    {
        return (towerRange(x,y,nx,ny) || bishopRange(x,y,nx,ny));
    }


    public boolean kingRange(int x, int y, int nx, int ny)
    {
        if(castling(x,y,nx,ny))
            return true;
        else
            {
                if (Math.abs(x - nx) <= 1 && Math.abs(y - ny) <= 1) {
                    return board[nx][ny] == null || (board[nx][ny] != null && (board[nx][ny].side != board[x][y].side));
                } else
                    return false;
            }
    }

    public boolean castling(int x, int y, int nx, int ny)
    {
        if(nx == 2 && y==ny && (y == 0 || y == 7) && x==4)
        {
            for(int i =1;i<4;i++) {
                if (board[i][y] != null)
                    return false;
            }
            System.out.println("cast");
            board[3][y]=board[0][y];
            board[0][y]=null;
            return true;
        }
        else if(nx == 6 && y==ny && (y == 0 || y == 7) && x==4)
        {
            for(int i =5;i<7;i++) {
                if (board[i][y] != null)
                    return false;
            }
            System.out.println("cast");
            board[5][y]=board[7][y];
            board[7][y]=null;
            return true;
        }
        else
            return false;
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

        Queen q = new Queen(!side_choose);
        Queen bq = new Queen(side_choose);

        King k = new King(!side_choose);
        King bk = new King(side_choose);
        board[4][0] = k;
        board[4][7] = bk;
        board[3][0]=q;
        board[3][7]=bq;
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
}