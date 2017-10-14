package Queen;

import javax.swing.*;
import java.awt.*;
public class EightQueen {
    public static int max;
    public static int count=0;
    public static int array[];
    ImageIcon image = new ImageIcon("C:\\Users\\dell\\Desktop\\queen.jpg");//JLabel的皇后图标
    static PaintChess p = null;//构造类
    static Thread thread = null;//主线程
    public void placeQueen( int n) throws InterruptedException {
       if(n==max){
           count++;
           PaintBoard();

       }
       else{
           for (int i = 0; i <max ; i++) {//循环换行尝试能不能放皇后
               array[n]=i;//代表第i行第n列放一个皇后
               if(issafe(n))
                   placeQueen(n+1);//安全才放下一个皇后，不安全则继续循环去找一个安全的位置
           }

       }
    }

   public boolean issafe(int n){
       for (int i = 0; i <n ; i++) {//不同列
           if(array[n]==array[i]||Math.abs(n-i)==Math.abs(array[n]-array[i]))
               return false;
       }
       return  true;
   }
    public void PaintBoard() throws InterruptedException{
       image.setImage(image.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        for(int i=0;i<max;i++){
            for(int j=0;j<max;j++){
                if(i==array[j]){ //如果可以摆放皇后
                    p.lab[i][j].setIcon(image);//就设置该位置图标为皇后图片
                }
            }
        }
        thread.sleep(1000);//整个任务的主线程停止一秒钟
        for(int i=0;i<max;i++){
            for(int j=0;j<max;j++){
                (p.lab[i][j]).setIcon(null);//清楚所有的皇后格子的Icon
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        thread=Thread.currentThread(); //主线程，正在执行的线程
         p = new PaintChess();//弹窗
        String str =JOptionPane.showInputDialog(p.frame,"输入皇后个数","input",JOptionPane.QUESTION_MESSAGE);
        max = Integer.parseInt(str);
        if(max<4)
            JOptionPane.showMessageDialog(null, "请输入的大于3的整数！");
        else{
            array= new int[max];//确定皇后个数
            p.show();
            EightQueen queen = new EightQueen();
            queen.placeQueen(0);
        }

    }

    }

