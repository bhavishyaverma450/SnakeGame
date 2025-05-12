import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import java.awt.*;
//import java.awt.resource.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    private int dots;
    private int dot_size=10;
    private final int random_position = 29;
    private int apple_x;
    private int apple_y;
    private Timer timer;

    private boolean inGame = true;

    private final int all_dots = 900;
    private final int[] x = new int[all_dots];
    private final int[] y = new int[all_dots];

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirecton = false;
    private boolean downDirection = false;

    private Image apple;
    private Image dot;
    private Image head;

    Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(300,300));

        loadImages();
        initGame();
    }

    public void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("apple.png"));
        apple = i1.getImage();

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("dot.png"));
        dot = i2.getImage();

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("head.png"));
        head = i3.getImage();
    }
    public void initGame(){
        dots=3;
        for(int i=0;i<dots;i++){
            y[i]=50;
            x[i] += 50 - i * dot_size;
        }
        locateApple();
        timer = new Timer(100,this);
        timer.start();
    }
    public void locateApple(){
        int r = (int )(Math.random()*random_position);
        apple_x = r*dot_size;
        r = (int )(Math.random()*random_position);
        apple_y = r*dot_size;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        draw(g);
    }
    public void draw(Graphics g){
        if(inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
    }
    public void gameOver(Graphics g){
        String msg = "Game Over!!!";
        Font font = new Font("San SERIF",Font.BOLD,20);
        FontMetrics metrices = getFontMetrics(font);
        g.setColor(Color.white);
        g.drawString(msg,((300-metrices.stringWidth(msg))/2),300/2);
    }
    public void move(){
        for(int i=dots;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDirection){
            x[0] -= dot_size;
        }
        if(rightDirection){
            x[0] += dot_size;
        }
        if(upDirecton){
            y[0] -= dot_size;
        }
        if(downDirection){
            y[0]+= dot_size;
        }
    }
    public void checkApple(){
        if((x[0]==apple_x) && (y[0]==apple_y)){
            dots++;
            locateApple();
        }
    }
    public void checkCollision(){
        for(int i=dots;i>0;i--){
            if((i>4) && ((x[0]==x[i])&&(y[0]==y[i]))) {
                inGame = false;
            }
        }
        if(y[0]>=300){
            inGame=false;
        }
        if(x[0]>=300){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(x[0]<0){
            inGame=false;
        }
        if(!inGame){
            timer.stop();
        }
    }
    public void actionPerformed(ActionEvent ae){
        if(inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection=true;
                upDirecton=false;
                downDirection=false;
            }
            if(key== KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirecton=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP && (!downDirection)){
                upDirecton=true;
                rightDirection=false;
                leftDirection=false;
            }
            if(key==KeyEvent.VK_DOWN && (!upDirecton)){
                downDirection=true;
                rightDirection=false;
                leftDirection=false;
            }
        }
    }
}
