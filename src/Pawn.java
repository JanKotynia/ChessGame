import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class Pawn extends Figure {
    Pawn(boolean s,boolean sp) {
        side = s;
        startPosition = sp;
        FigureType = 'p';
        if (s) {
            InputStream imageStream = Main.class.getResourceAsStream("/chess_f/pion_w.png");
            if (imageStream == null) {
                System.err.println("Nie znaleziono pliku: " + "/chess_f/pion_w.png");
            } else {
                try {
                    image = ImageIO.read(imageStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            InputStream imageStream = Main.class.getResourceAsStream("/chess_f/pion_b.png");
            if (imageStream == null) {
                System.err.println("Nie znaleziono pliku: " + "/chess_f/pion_b.png");
            } else {
                try {
                    image = ImageIO.read(imageStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}