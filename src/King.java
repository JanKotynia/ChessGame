import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class King extends Figure {
    King(boolean s) {
        side = s;
        FigureType = 'k';
        if (s) {
            InputStream imageStream = Main.class.getResourceAsStream("/chess_f/krol_w.png");
            if (imageStream == null) {
                System.err.println("Nie znaleziono pliku: " + "/chess_f/krol_w.png");
            } else {
                try {
                    image = ImageIO.read(imageStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            InputStream imageStream = Main.class.getResourceAsStream("/chess_f/krol_b.png");
            if (imageStream == null) {
                System.err.println("Nie znaleziono pliku: " + "/chess_f/krol_b.png");
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
