package main;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class main {
    public static void main(String args[]) throws IOException {
        BufferedImage img = null;
        File f = null;
// wczytaj obraz
        try {
            f = new File("img/pobrane.jpg");
            img = ImageIO.read(f);
            //allWhite(img);
            imgNegative(img);
        } catch (IOException e) {
            System.out.println(e);
        }
// pobieramy szerokość i wysokość obrazów
        int width = img.getWidth();
        int height = img.getHeight();
        // pobieramy środkowy piksel
        int p = img.getRGB(width / 2, height / 2);
// Odczytujemy wartosci kanalow przesuwajac o odpowiednia liczbe bitow w prawo, tak aby
// kanal znalazł się na bitach 7-0, następnie zerujemy pozostałe bity używając bitowego AND z maską 0xFF.

        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

// Ustawiamy wartosci poszczegolnych kanalow na przykładowe liczby


        a = 255;
        r = 100;
        g = 150;
        b = 200;


// TODO: ustaw ponownie wartości kanałów dla zmiennej

        img.setRGB(width / 2, height / 2, p);

// zapis obrazu
        try {
            f = new File("img/modified.png");
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void allWhite(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int white = (255 << 8) | (255 << 16) | (255<< 24) | 255;
        for(int i=0; i < width; i++){
            for(int j=0; j < height; j++){
                img.setRGB(i, j, white);
            }
        }

    }

    public static void imgNegative(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                int p = img.getRGB(i, j);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                int negative = 255-b | (255-g << 8) | (255-r << 16) | (255-a << 24);

                img.setRGB(i ,j ,negative);
            }
        }
    }

    public static void imgNegative2(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Color sample = new Color(img.getRGB(i,j));
                int a = sample.getAlpha();
                int r = sample.getRed();
                int g = sample.getGreen();
                int b = sample.getBlue();
                Color negative = new Color(255-r, 255-g, 255-b, 255-a);
                img.setRGB(i, j, negative.getRGB());

            }
        }
    }
}