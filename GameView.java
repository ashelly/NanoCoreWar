import javax.swing.*;
import java.awt.*;
/**
 * This class represents a single instruction line.
 * 
 * @author TheBestOne, Ilmari Karonen, ashelly
 * @version 3/18/15
 */
public class GameView extends JComponent{
    final static Color[] specialColors = new Color[]{
            new Color(0,0,0),
            new Color(190, 255, 152),
            Color.yellow,
            new Color(0, 93, 14),
            new Color(96, 92, 4),
            new Color(0, 93, 14),
            new Color(96, 92, 4),
            new Color(0, 93, 14),
            new Color(96, 92, 4)
    };

    final static Color playerOneColor = Color.green;
    final static Color playerTwoColor = Color.white;

    private final Game game;

    public final int[] coreData;
    public final JFrame frame;

    private int playerOneLocation;
    private int playerTwoLocation;
    private int time, turn;

    public int width = 128;
    public int height = 64;

    public GameView(Game game) {
        this.game = game;
        this.coreData = new int[game.coreSize];
        this.frame = new JFrame("Game");
        this.width = 128;
        this.height = game.coreSize/this.width;
        while (height*2 < width) {
            height*=2; width/=2;
        }
        /* make at least 1024 screen pix wide */
        int scale = 1;
        while (scale*width<=512) { scale *=2; }
        frame.add(this);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(width*scale, height*scale);
    }

    @Override
    public void paint(Graphics g) {
        int frameWidth = getSize().width;
        int frameHeight = getSize().height;

        int pixelWidth = frameWidth/width;
        int pixelHeight = frameHeight/height;

        for (int x = 0; x < width; x+= 1){
            for (int y = 0; y < height; y+= 1){
                int index = y*width+x;
                Color color = specialColors[coreData[index]];
                if (index == playerOneLocation){
                    color = playerOneColor;
                }
                if (index == playerTwoLocation){
                    color = playerTwoColor;
                }
                g.setColor(color);

                int px=x*pixelWidth;
                int py=y*pixelHeight;
                g.fillRect(px,py,pixelWidth, pixelHeight);
            }
        }
    }

    public void viewCore(Instruction[] core, int ploc, int xloc, int step){
        this.time = (step >> 1);
        this.turn = (step & 1);
        this.playerOneLocation = (turn == 0 ? ploc : xloc);
        this.playerTwoLocation = (turn == 0 ? xloc : ploc);

        repaint();
        try {
            Thread.sleep(1); // 1000 steps per second
        } catch (InterruptedException e) { }
    }
}
