package MyFiveChess;
import robot.*;
import java.awt.*;
import javax.swing.*;
public class Gobang {
	public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new MyFiveChessFrame(new StupidRobot());
            frame.setTitle("HLJU������С����");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}
