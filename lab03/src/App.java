import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    String version = "0.02";

    public static void main(String[] args) {

        Renderer mainRenderer = new Renderer(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        mainRenderer.clear();
        //mainRenderer.drawLine(0, 0, 150, 150, Renderer.LineAlgo.valueOf(args[3]));
        mainRenderer.drawTriangle(new Vec2f(0, 0), new Vec2f(100, 0), new Vec2f(50, 100), new Vec3f(173, 133, 115));
        try {
            mainRenderer.save();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getVersion() {
        return this.version;
    }
}