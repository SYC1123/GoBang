
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
	private static final int DEFAULT_WIDTH = 600;//��ΪͼƬ�Ĵ�СΪ600
  private static final int DEFAULT_HEIGHT = 600;//�����������������СҲΪ600
  int x;                                 //ԭͼ��û�����̵ģ���������Ҫ�Լ���
  int y;                                 //���̣�����Ҫȫ�ֱ�������ȡ��������
  int allchess[][] = new int[16][16];    //����һ����������ȡ��ǰ�������
  boolean isBlack = true;                //0����û���壬1�������ӣ�2��������
  boolean canPlay = true;//isBlack������˵����ǰӦ���Ǻ��ӻ��߰�������                
  private Image image;   //canPlay���������õ���Ϸ�������ܹ�������
  private IRobot iRobot; //�����洢���빹������iRobot
  private String string = "welcome to Gobang";  //ͼƬ�ϵ�ǰ����ʾ�ú�������

public ImageComponent(IRobot iRobot)
{
  this.iRobot = iRobot;    
  addMouseListener(this); //Ҫʵ�ֵ�������ͷ�����Ӧ�ͱ���ʵ��MouseListener�ӿ�
  image = new ImageIcon("background.jpg").getImage();
}                           //����ͼ�����������������

public void paint(Graphics g) //����paint����
{
 if (image == null) return;

 g.drawImage(image, 0, 0, null);
 
 g.setFont(new Font("����", 0, 16));
 g.setFont(new Font("Times New Roman", 0, 27));
 g.drawString(string, 185, 80);
 
 for (int i = 0; i < 16; i++) {//�������ڻ����̣��������̴�С15*15��
   g.drawLine(105, 140 + i*24, 465, 140 + i*24);
   g.drawLine(105 + i*24, 140, 105 + i*24, 500);
} 
 
 
 for (int i = 0; i < 16; i++) {
   for(int j = 0; j < 16; j++)
   {
       if(allchess[i][j] == 1) {//Ϊ1ʱ�Ǻ��ӣ�������Ҫ����fillOval������Բ
       int tempx = i * 24 + 6; 
       int tempy = j * 24 + 43;
       g.setColor(Color.BLACK);
       g.fillOval(tempx-7, tempy-7, 20, 20);
       }
       if (allchess[i][j] == 2) {//�����ӵ�ʱ����Ҫ��һ����ɫ��Բ������һ����ɫ�Ŀ�
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
public void mouseClicked(MouseEvent e) {//�������ʵ����Ϸ�����ұߵļ�������
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
//System.out.println("X" + e.getX());//�ڻ���֮ǰҪȷ�����꣬��Ҫ�������
//System.out.println("Y" + e.getY());
   
if (canPlay == true) { 
   x = e.getX();
   y = e.getY();
   if (x>105 && x<465 && y>140 && y<500) {
       float xxx = (float) 24.0;
       x = Math.round((x - 10)/xxx);//ʵ�������4��5��
       y = Math.round((y - 49)/xxx);
       if(allchess[x][y] == 0)
       {
           if (isBlack == true) {
               allchess[x][y] = 1;
               iRobot.retrieveGameBoard(allchess);//�û���������ʱ����Ҫ���������̵ľ��ƴ��ݹ�ȥ
               isBlack = false;
               string = "It's White";

               boolean winFlag = this.checkWin();//ÿ��һ����ʱ����Ҫ����Ƿ�ʤ��
               if (winFlag == true) {
                   JOptionPane.showMessageDialog(this, "Game over"+(allchess[x][y]==1 ? "Black" : "White") + "winned");
                   canPlay = false;
               }
               RobotAction();//����������
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

Pair pair = iRobot.getDeterminedPos();//�������ཫ����һ��pair�����
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



private boolean checkWin() {//��⵱ǰ�Ƿ����������ߵķ���������һ�£����������ʵ�ܼ򵥣�ֻҪ������ÿһ�����ӵ�ʱ�����Ƿ����������߾Ϳ���ȷ��һ������ʤ�������ǾͿ������Ϸ��֡��ȼ����ߺ����ߣ��ټ������б�ߡ�
   
boolean flag = false; //���õı�־��������������ʱ�ͷ���flag=false
int count = 1;        //������ǰ�ɼ�����������
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
//?????��?
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



