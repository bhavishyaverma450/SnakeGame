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
    protected Timer timer;

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
        timer = new Timer(140,this);
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
        g.drawImage(apple,apple_x,apple_y,this);
        for(int i=0;i<dots;i++){
            if(i==0){
                g.drawImage(head,x[i],y[i],this);
            }else{
                g.drawImage(dot,x[i],y[i],this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
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
    public void actionPerformed(ActionEvent ae){
        move();
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