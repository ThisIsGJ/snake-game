import java.awt.Color;

import javax.swing.JFrame;


public class main {
	public static void main(String[] args){
		
		JFrame frame = new JFrame("Ã∞≥‘…ﬂ--π‹ø°");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		snackEat t = new snackEat();
		t.setBackground(Color.white);
		t.setFocusable(true);
		frame.add(t);
		frame.setSize(300,500);
		frame.setVisible(true);

	}
}
