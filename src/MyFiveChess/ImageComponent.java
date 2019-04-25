
package MyFiveChess;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import robot.IRobot;
import robot.Pair;

public class ImageComponent extends JComponent implements MouseListener
{
	private static final int DEFAULT_WIDTH = 600;//因为图片的大小为600
  private static final int DEFAULT_HEIGHT = 600;//所以这里设置组件大小也为600
  int x;                                 //原图是没有棋盘的，这里我们要自己画
  int y;                                 //棋盘，故需要全局变量来获取棋盘坐标
  int allchess[][] = new int[16][16];    //构造一个数组来存取当前局势情况
  boolean isBlack = true;                //0代表没有棋，1代表黑子，2代表白子
  boolean canPlay = true;//isBlack是用来说明当前应该是黑子或者白子落棋                
  private Image image;   //canPlay是用来设置当游戏结束后不能够再落子
  private IRobot iRobot; //用来存储传入构造器的iRobot
  private String string = "welcome to Gobang";  //图片上当前会显示该黑子落棋

public ImageComponent(IRobot iRobot)
{
  this.iRobot = iRobot;    
  addMouseListener(this); //要实现当点击鼠标就发生响应就必须实现MouseListener接口
  image = new ImageIcon("background.jpg").getImage();
}                           //背景图在最下面会贴出来的

public void paint(Graphics g) //构造paint方法
{
 if (image == null) return;

 g.drawImage(image, 0, 0, null);
 
 g.setFont(new Font("宋体", 0, 16));
 g.setFont(new Font("Times New Roman", 0, 27));
 g.drawString(string, 185, 80);
 
 for (int i = 0; i < 16; i++) {//这里是在画棋盘（国际棋盘大小15*15）
   g.drawLine(105, 140 + i*24, 465, 140 + i*24);
   g.drawLine(105 + i*24, 140, 105 + i*24, 500);
} 
 
 
 for (int i = 0; i < 16; i++) {
   for(int j = 0; j < 16; j++)
   {
       if(allchess[i][j] == 1) {//为1时是黑子，这里需要调用fillOval方法画圆
       int tempx = i * 24 + 6; 
       int tempy = j * 24 + 43;
       g.setColor(Color.BLACK);
       g.fillOval(tempx-7, tempy-7, 20, 20);
       }
       if (allchess[i][j] == 2) {//当白子的时候需要画一个白色的圆再添加一个黑色的框
           int tempx = i * 24 + 6;
           int tempy = j * 24 + 43;
           g.setColor(Color.WHITE);
           g.fillOval(tempx-7, tempy-7, 20, 20);

       }
   }
   
}
}

public Dimension getPreferredSize() { return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); }


@Override
public void mouseClicked(MouseEvent e) {//下面就是实现游戏界面右边的几个功能
// TODO Auto-generated method stub
}


@Override
public void mouseReleased(MouseEvent e) {
// TODO Auto-generated method stub

}

@Override
public void mouseEntered(MouseEvent e) {
// TODO Auto-generated method stub

}

@Override
public void mouseExited(MouseEvent e) {
// TODO Auto-generated method stub

}
@Override
public void mousePressed(MouseEvent e) {
// TODO Auto-generated method stub
//System.out.println("X" + e.getX());//在划线之前要确认坐标，需要输出坐标
//System.out.println("Y" + e.getY());
   
if (canPlay == true) { 
   x = e.getX();
   y = e.getY();
   if (x>105 && x<465 && y>140 && y<500) {
       float xxx = (float) 24.0;
       x = Math.round((x - 10)/xxx);//实现坐标的4舍5入
       y = Math.round((y - 49)/xxx);
       if(allchess[x][y] == 0)
       {
           if (isBlack == true) {
               allchess[x][y] = 1;
               iRobot.retrieveGameBoard(allchess);//该机器人下棋时，需要将现在棋盘的局势传递过去
               isBlack = false;
               string = "It's White";

               boolean winFlag = this.checkWin();//每下一次棋时都需要检查是否胜出
               if (winFlag == true) {
                   JOptionPane.showMessageDialog(this, "Game over"+(allchess[x][y]==1 ? "Black" : "White") + "winned");
                   canPlay = false;
               }
               RobotAction();//机器人下棋
               this.repaint();
           }
       }
       else {
           JOptionPane.showMessageDialog(this, "Please play chess in the chessboard");
       }

   }
   
}
}

void RobotAction(){

Pair pair = iRobot.getDeterminedPos();//机器人类将返回一个pair类回来
x = pair.x;
y = pair.y;
allchess[x][y] = 2;
isBlack = true;
string = "It's Black";

boolean winFlag = this.checkWin();
if (winFlag == true) {
   JOptionPane.showMessageDialog(this, "Game over"+(allchess[x][y]==1 ? "Black" : "White") + " winned");
   canPlay = false;
}
}



private boolean checkWin() {//检测当前是否由五子连线的方法，简述一下，这个方法其实很简单，只要我们在每一次落子的时候检查是否由五子连线就可以确保一旦有人胜出，我们就可以马上发现。先检查横线和竖线，再检查左右斜线。
   
boolean flag = false; //设置的标志，当由五子连线时就返回flag=false
int count = 1;        //计数当前由几颗棋子相连
int color = allchess[x][y];
int i = 1;

while(((x+i)<16)&&color == allchess[x+i][y]) {
   count++;
   i++;
   }
i = 1;
while(((x-i)>=1)&&color == allchess[x-i][y]) {
   count++;
   i++;
   }
if(count>=5)
   {flag = true;}
//?????ж?
int count2 = 1;
int i2 = 1;
while(((y+i2)<16)&&color == allchess[x][y+i2]) {
   count2++;
   i2++;
   }
i = 1;
while(((y-i2)>=1)&&color == allchess[x][y-i2]) {
   count2++;
   i2++;
   }
if(count2>=5)
   {flag = true;}

int count3 = 1;
int i3 = 1;
while(((y-i3)>=1)&&((x+i3)<16)&&color == allchess[x+i3][y-i3]) {
   count3++;
   i3++;
   }
i = 1;
while(((x-i3)>=1)&&((y+i3)<16)&&color == allchess[x-i3][y+i3]) {
   count3++;
   i3++;
   }
if(count3>=5)
   {flag = true;}

int count4 = 1;
int i4 = 1;
while(((y-i4)>=1)&&((x-i4)>=1)&&color == allchess[x-i4][y-i4]) {
   count4++;
   i4++;
   }
i = 1;
while(((x+i4)<16)&&((y+i4)<16)&&color == allchess[x+i4][y+i4]) {
   count4++;
   i4++;
   }
if(count4>=5)
   {flag = true;}

return flag;

}

}




