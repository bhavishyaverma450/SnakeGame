import javax.swing.*;
import java.util.*;

public class Main extends JFrame {
    Main(){
        super("Snake Game");
        add(new Board());
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
