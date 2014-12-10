import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class snackEat extends JPanel implements KeyListener,ActionListener{
	
	int snack_x;
	int snack_y;
	int bound_x;
	int bound_y;
	int food_x;
	int food_y;
	int count;
	int the_number1, the_number2, the_number3;
	File file;
	Random randomGenerator;
	Timer time;
	int speed;	// the snack speed
	int dir_x, dir_y;
	String detail;
	ArrayList<Point> snack_List;
	boolean pause;
	int backDir;
	BufferedReader rank_read;
	BufferedWriter rank_write;
	List rank_list;
	
	snackEat (){
		
		snack_x = 0;
		snack_y = 0;
		bound_x = 700;
		bound_y = 700;
		food_x = 0;
		food_y = 0;
		speed = 100;
		dir_x = 20;
		dir_y = 0;
		count = 0; 
		backDir = 1;
		the_number1 = the_number2 = the_number3 = 0;
		pause = true;
		detail = "蛇长 : " + count;
		rank_list = new List(3);
		snack_List = new ArrayList<Point>();
		file = new File("src/rank.txt");
		
		snack_List.add(new Point(snack_x,snack_y));
		
		randomGenerator = new Random();
		randomFoodPostion();
		addKeyListener(this);
		
		loadRank();
		time = new Timer(speed,this);	// repaint the graph
	}
	
	public void paint(Graphics g)
	{
		
		super.paintComponent(g);
		
		g.setColor(Color.black);
		g.drawLine(0,400,300,400);
		
		for(int i = 0 ; i < snack_List.size(); i++){
			g.fillRect(snack_List.get(i).x, snack_List.get(i).y, 20, 20);
		}
		
		g.setColor(Color.blue);
		g.fillRect(food_x, food_y, 20, 20);
		
		String message = "使用方向键控制蛇， 使用 P 开始/暂停游戏. ";
		String player_rank = "第一名: " + the_number1 + "    第二名: " + the_number2 + 
				"    第三名: " + the_number3;
		
		detail = "蛇长 : " + count;
		g.drawString(message, 5 , 420);
		g.drawString(detail, 5, 440);
		g.drawString(player_rank, 5, 460);
		
	}
	
	private void randomFoodPostion(){
		food_x = randomGenerator.nextInt(14) * 20 ;
		food_y = randomGenerator.nextInt(19) * 20 ;
		for(int i = 0; i < snack_List.size(); i++ ){
			if(snack_List.get(i).x == food_x && snack_List.get(i).y == food_y){
				randomFoodPostion();
			}
		}
	}
	
	//test whether snack touched the food
	private boolean testTouch(){
		if(snack_x == food_x && snack_y == food_y)
			return true	;//touch
		else
			return false;  	// not touch
	}
	
	public void keyTyped(KeyEvent e) {
		//do nothing
    }
	
    @Override
    public void keyPressed(KeyEvent e) {
    	
    		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    			
    			if(backDir != 3 || snack_List.size() == 1){
	    			backDir = 1;
	    			dir_x = 20;
	    			dir_y = 0;
    			}
            }
       	 	else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
       	 		if(backDir != 1 || snack_List.size() == 1 ){
	       	 		backDir = 3;
	       	 		dir_x = -20;
	       	 		dir_y = 0;
       	 		}
       	 		
            }else if (e.getKeyCode() == KeyEvent.VK_UP) {
            	if(backDir != 2  || snack_List.size() == 1){
	            	backDir = 0;
	            	dir_x = 0;
	            	dir_y = -20;
            	}
            }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            	if(backDir != 0 || snack_List.size() == 1){
	            	backDir = 2;
	            	dir_x = 0;
	            	dir_y = 20;
            	}
            }
            else if(e.getKeyCode() == KeyEvent.VK_P){
            	if(!pause){
            		pause = true;
            		time.stop();
            	}
            	else{
            		pause = false;
            		time.start();
            	}
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // do nothing
    }

    private void loadRank(){
    	
    	try{
    		
    		String the_number;
    		FileReader fr = new FileReader(file.getAbsolutePath());
    		rank_read = new BufferedReader(fr);
    		the_number1 = Integer.parseInt(rank_read.readLine());
    		the_number2 = Integer.parseInt(rank_read.readLine());
    		the_number3 = Integer.parseInt(rank_read.readLine());
    		rank_read.close();
    	}catch(FileNotFoundException e){
    		System.err.println("cannot find rank.txt");
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void updateRank(){
    	try {
			FileWriter fw = new FileWriter(file.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(fw);
			String write = "";
			if(count > the_number1){
				write += Integer.toString(count) + "\n";
				write += Integer.toString(the_number1) + "\n";
				write += Integer.toString(the_number2);
			}
			else if(count > the_number2 && count < the_number1){
				write += Integer.toString(the_number1) + "\n";
				write += Integer.toString(count) + "\n";
				write += Integer.toString(the_number2);
			}
			else if(count > the_number3 && count < the_number2){
				write += Integer.toString(the_number1) + "\n";
				write += Integer.toString(the_number2) + "\n";
				write += Integer.toString(count);
			}else{
				write += Integer.toString(the_number1) + "\n";
				write += Integer.toString(the_number2) + "\n";
				write += Integer.toString(the_number3);
			}
			bw.write(write);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		
		snack_x += dir_x;
		snack_y += dir_y;
		
		addPoint();
		
		if(testFail()){
			dir_x = 20;
			dir_y = 0;
			snack_x = snack_y = 0;
			snack_List.clear();
			snack_List.add(new Point(0,0));
			speed = 100;
			updateRank();
			count = 0;
			loadRank();
			JOptionPane.showMessageDialog(null, "重新开始......");
			pause = true;
			time.stop();
	       // time.setDelay( speed);
	       // time.start();
		}
		
		if(testTouch()){
			detail = "蛇长 : " + ++count;
			//control speed of the snack
			/*
			speed -= 30;
			if(speed < 150){
				speed = 100;
			}*/
			time.stop();
	        time.setDelay( speed);
	        time.start();
			snack_List.add(new Point(food_x,food_y));
			randomFoodPostion();
		}
		repaint();
	}
	
	private boolean testFail(){
		
		if(snack_x < 0 || snack_x > 290 || snack_y < 0 || snack_y > 390){
			return true;
		}
		else if(killSelf())
		{
			return true;
		}
		else
			return false;
	}
	
	private boolean killSelf(){
		
		if(snack_List.size() != 1){
			for(int i = 0; i < snack_List.size()-1 ; i++ ){
				if(snack_List.get(i).x == snack_x &&
						snack_List.get(i).y == snack_y){
					return true;
				}
			}
		}
		return false;
	}
	
	private void addPoint(){
		snack_List.remove(0);
		snack_List.add(new Point(snack_x, snack_y));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}