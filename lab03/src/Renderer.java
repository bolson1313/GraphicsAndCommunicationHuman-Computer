import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Renderer {

    public enum LineAlgo { NAIVE, BRESENHAM, BRESENHAM_INT; }

    private BufferedImage render;

    private String filename;
    private LineAlgo lineAlgo = LineAlgo.NAIVE;

    public Renderer(String filename, int width, int height) {
        render = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.filename = filename;
    }

    public void drawPoint(int x, int y) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);
        render.setRGB(x, y, white);
    }

    public void drawLine(int x0, int y0, int x1, int y1, LineAlgo lineAlgo) {
        if(lineAlgo == LineAlgo.NAIVE) drawLineNaive(x0, y0, x1, y1);
        if(lineAlgo == LineAlgo.BRESENHAM) drawLineBresenham(x0, y0, x1, y1);
        if(lineAlgo == LineAlgo.BRESENHAM_INT) drawLineBresenhamInt(x0, y0, x1, y1);
    }

    public void drawLineNaive(int x0, int y0, int x1, int y1) {
        float dx = x1 - x0;
        float dy = y1 - y0;
        float steps = Math.max(Math.abs(dx), Math.abs(dy));
        float m = Math.abs(dy) / Math.abs(dx);


        float xi = dx / steps;
        float yi = dy / steps;

        float x = x0;
        float y = y0;
        for(int i = 0; i <= steps; i++){
            drawPoint(Math.round(x), Math.round(y));

            x += xi;
            y += yi;
        }
    }

    public void drawLineBresenham(int x0, int y0, int x1, int y1) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);

        int dx = x1-x0;
        int dy = y1-y0;
        float derr = Math.abs(dy/(float)(dx));
        float err = 0;

        int y = y0;

        for (int x=x0; x<=x1; x++) {
            render.setRGB(x, y, white);
            err += derr;
            if (err > 0.5) {
                y += (y1 > y0 ? 1 : -1);
                err -= 1;
            }
        }
    }

    public void drawLineBresenhamInt(int x0, int y0, int x1, int y1) {
        // TODO: zaimplementuj
    }

    public void save() throws IOException {
        File outputfile = new File(filename);
        render = Renderer.verticalFlip(render);
        ImageIO.write(render, "png", outputfile);
    }

    public void clear() {
        for (int x = 0; x < render.getWidth(); x++) {
            for (int y = 0; y < render.getHeight(); y++) {
                int black = 0 | (0 << 8) | (0 << 16) | (255 << 24);
                render.setRGB(x, y, black);
            }
        }
    }

    public static BufferedImage verticalFlip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage flippedImage = new BufferedImage(w, h, img.getColorModel().getTransparency());
        Graphics2D g = flippedImage.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return flippedImage;
    }

    public Vec3f barycentric(Vec2f A, Vec2f B, Vec2f C, Vec2f P) {
                Vec3f v1 = new Vec3f(A.x - B.x, A.x - C.x, P.x - A.x);

                Vec3f v2 = new Vec3f(A.y - B.y, A.y - C.y, P.y - A.y);

                Vec3f cross = iloczyn_wek(v1, v2);

                Vec2f uv = new Vec2f(cross.x / cross.z, cross.y / cross.z);

                Vec3f barycentric = new Vec3f(uv.x, uv.y, 1 - uv.x - uv.y);

        return barycentric;
    }

    public Vec3f iloczyn_wek(Vec3f v1, Vec3f v2){
        Vec3f iloczyn = new Vec3f(v1.y*v2.z - v1.z*v2.y, v1.z*v2.x - v1.x*v2.z, v1.x*v2.y - v1.y*v2.x);
        return iloczyn;
    }

    public void drawTriangle(Vec2f A, Vec2f B, Vec2f C, Vec3f color) {
        float minX = Math.min(Math.min(A.x, B.x), C.x);
        float maxX = Math.max(Math.max(A.x, B.x), C.x);
        float minY = Math.min(Math.min(A.y, B.y), C.y);
        float maxY = Math.max(Math.max(A.y, B.y), C.y);
        for(int i = (int) minX; i < maxX; i++) {
            for(int j = (int) minY; j < maxY; j++){
                Vec2f point = new Vec2f(i, j);
                Vec3f barycent = barycentric(A, B, C, point);
                if(barycent.x > 0 && barycent.y > 0 && barycent.z > 0 && barycent.x < 1 && barycent.y < 1 && barycent.z < 1) {
                    int col = 255 | ((int)color.x%255 << 8) | ((int)color.y%255 << 16) | ((int)color.z%255 << 24);
                    render.setRGB(i, j, col);
                }
            }
        }
    }
}