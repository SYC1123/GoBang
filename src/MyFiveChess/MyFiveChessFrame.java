package MyFiveChess;

import javax.swing.JFrame;

import robot.IRobot;

public class MyFiveChessFrame extends JFrame
{
	public MyFiveChessFrame(IRobot robot)
	   {
	      add(new ImageComponent(robot));
	      pack();
	   }


}
