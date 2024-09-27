import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class Horse extends Figure{
    Horse(boolean s) {
        side = s;
        FigureType = 'h';
        if (s) {
            InputStream imageStream = Main.class.getResourceAsStream("/chess_f/kon_w.png");
            if (imageStream == null) {
                System.err.println("Nie znaleziono pliku: " + "/chess_f/kon_w.png");
            } else {
                try {
                    image = ImageIO.read(imageStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            InputStream imageStream = Main.class.getResourceAsStream("/chess_f/kon_b.png");
            if (imageStream == null) {
                System.err.println("Nie znaleziono pliku: " + "/chess_f/kon_b.png");
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
