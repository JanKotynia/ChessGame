import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class Queen extends Figure {
    Queen(boolean s) {
        side = s;
        FigureType = 'q';
        if (s) {
            InputStream imageStream = Main.class.getResourceAsStream("/chess_f/damka_w.png");
            if (imageStream == null) {
                System.err.println("Nie znaleziono pliku: " + "/chess_f/damka_w.png");
            } else {
                try {
                    image = ImageIO.read(imageStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            InputStream imageStream = Main.class.getResourceAsStream("/chess_f/damka_b.png");
            if (imageStream == null) {
                System.err.println("Nie znaleziono pliku: " + "/chess_f/damka_b.png");
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