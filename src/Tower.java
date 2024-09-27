import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class Tower extends Figure{
    Tower(boolean s) {
        side = s;
        FigureType = 't';
        if (s) {
            InputStream imageStream = Main.class.getResourceAsStream("/chess_f/wieza_w.png");
            if (imageStream == null) {
                System.err.println("Nie znaleziono pliku: " + "/chess_f/wieza_w.png");
            } else {
                try {
                    image = ImageIO.read(imageStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            InputStream imageStream = Main.class.getResourceAsStream("/chess_f/wieza_b.png");
            if (imageStream == null) {
                System.err.println("Nie znaleziono pliku: " + "/chess_f/wieza_b.png");
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
