import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class Bishop extends Figure {
    Bishop(boolean s) {
        side = s;
        FigureType = 'b';
        if (s) {
            InputStream imageStream = Main.class.getResourceAsStream("/chess_f/rojfel_w.png");
            if (imageStream == null) {
                System.err.println("Nie znaleziono pliku: " + "/chess_f/rojfel_w.png");
            } else {
                try {
                    image = ImageIO.read(imageStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            InputStream imageStream = Main.class.getResourceAsStream("/chess_f/rojfel_b.png");
            if (imageStream == null) {
                System.err.println("Nie znaleziono pliku: " + "/chess_f/rojfel_b.png");
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