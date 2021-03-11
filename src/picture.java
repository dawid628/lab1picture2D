import static java.lang.Math.random;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


class Surface extends JPanel implements ActionListener {


    private final Color colors[] = { Color.green, Color.black, Color.orange, Color.pink, Color.red, Color.yellow, Color.white, Color.magenta };

    private final double points[][] = { { 0, 185 }, { 75, 175 }, { 100, 110 }, { 125, 175 }, { 200, 185 }, { 150, 225 }, { 160, 290 }, { 100, 250 }, { 40, 290 }, { 50, 225 }, { 0, 185 } };


    private Ellipse2D.Float[] ellipses;

    private double esize[];

    private float estroke[];

    private double maxSize = 0;

    private final int NUMBER_OF_ELLIPSES = 30;

    private final int DELAY = 80;

    private final int INITIAL_DELAY = 50;

    private Timer timer;


    public Surface() {

        initSurface();

        initEllipses();

        initTimer();

    }

    private void initSurface() {

        setBackground(Color.gray);

        ellipses = new Ellipse2D.Float[NUMBER_OF_ELLIPSES];

        esize = new double[ellipses.length];

        estroke = new float[ellipses.length];

    }


    private void initEllipses() {

        int w = 500;
        int h = 500;

        maxSize = w / 10;

        for (int i = 0; i < ellipses.length; i++) {

            ellipses[i] = new Ellipse2D.Float();

            randomPosition(i, maxSize * random(), w, h);

        }

    }

    private void initTimer() {

        timer = new Timer(DELAY, this);

        timer.setInitialDelay(INITIAL_DELAY);

        timer.start();
    }


    private void randomPosition(int i, double size, int w, int h) {

        esize[i] = size;

        estroke[i] = 1.0f;

        double x = random() * (w - (maxSize / 2));

        double y = random() * (h - (maxSize / 2));

        ellipses[i].setFrame(x, y, size, size);

    }



    private void doStep(int w, int h) {

        for (int i = 0; i < ellipses.length; i++) {

            estroke[i] += 0.025f;

            esize[i]++;

            if (esize[i] > maxSize) {

                randomPosition(i, 1, w, h);

            } else {

                ellipses[i].setFrame(ellipses[i].getX(), ellipses[i].getY(), esize[i], esize[i]);
            }

        }

    }


    private void drawEllipses(Graphics2D g2d) {


        for (int i = 0; i < ellipses.length; i++) {

            g2d.setColor(colors[i % colors.length]);

            g2d.setStroke(new BasicStroke(estroke[i]));

            g2d.draw(ellipses[i]);

        }

    }


    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);


        g2d.setRenderingHints(rh);

        Dimension size = getSize();

        doStep(size.width, size.height);


        GeneralPath star = new GeneralPath();

        star.moveTo(points[0][0], points[0][1]);

        for (int k = 1; k < points.length; k++)
            star.lineTo(points[k][0], points[k][1]);

        star.closePath();

        g2d.setColor(Color.yellow);

        g2d.fill(star);


        drawEllipses(g2d);


        g2d.dispose();

    }


    @Override

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        doDrawing(g);
    }


    @Override

    public void actionPerformed(ActionEvent e) {

        repaint();
    }

}


public class picture extends JFrame {

    public picture() {

        initUI();

    }


    private void initUI() {

        add(new Surface());

        setTitle("2D PICTURE");

        setSize(700, 450);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {

        picture picture = new picture();
        picture.setVisible(true);

    }

}
