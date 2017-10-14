package Queen;

import Queen.EightQueen;

import javax.swing.*;
import java.awt.*;


public class PaintChess {
    JFrame frame = new JFrame("皇后棋盘");
    JLabel lab[][] = null;

    public PaintChess(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setLocation(200,200);
        frame.setVisible(true);
    }
    public void show(){
        frame.setLayout(new GridLayout(EightQueen.max,EightQueen.max));
        frame.setSize(EightQueen.max*20, EightQueen.max*20);
        lab=new JLabel[EightQueen.max][EightQueen.max];

        for(int i=0;i<EightQueen.max;i++){
            for(int j=0;j<EightQueen.max;j++){
                lab[i][j]=new JLabel();
                lab[i][j].setOpaque(true);//设置为透明，才能显示背景色
                if((i+j)%2==0){
                    lab[i][j].setBackground(Color.black);
                }
                else{
                    lab[i][j].setBackground(Color.white);
                }
                frame.add(lab[i][j]);
            }
        }
    }

}
