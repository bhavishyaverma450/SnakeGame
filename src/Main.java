import javax.swing.*;
import java.util.*;

public class Main extends JFrame {
    Main(){
        super("Snake Game");
        add(new Board());
        pack();

        setSize(300,300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}