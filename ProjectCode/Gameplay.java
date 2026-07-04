//Gameplay.java
package ProjectCode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Gameplay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false; //so that the game doesn't start by default
	private int score = 0;
	private int totalBricks = 21;

	private Timer timer;
	private int delay = 3;

	private int playerX = 310; //starting position of paddle
	private int ballposX = 130; //starting position of ball from x axis
	private int ballposY = 350; //starting position of ball from y axis
	private int ballXdir = -1;
	private int ballYdir = -2;

	private MapGenerator map;

	public Gameplay() {

		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);

		timer.start(); //Starts the Timer,causing it to start sending action events to its listeners.
	}

	//to draw components
	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//gradient background instead of flat gray
		GradientPaint bg = new GradientPaint(0, 0, new Color(25, 25, 55), 0, 592, new Color(60, 40, 90));
		g2.setPaint(bg);
		g2.fillRect(0, 0, 692, 592);

		//drawing map
		map.draw(g2);

		//score panel
		g2.setColor(new Color(255, 255, 255, 30));
		g2.fillRoundRect(520, 10, 150, 40, 15, 15);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
		g2.drawString("Score: " + score, 535, 37);

		//the paddle — rounded with gradient shading
		GradientPaint paddleGradient = new GradientPaint(playerX, 550, new Color(255, 90, 90),
				playerX, 558, new Color(200, 30, 30));
		g2.setPaint(paddleGradient);
		g2.fillRoundRect(playerX, 550, 100, 8, 10, 10);

		//the ball — glow halo + gradient core
		g2.setColor(new Color(255, 255, 150, 90));
		g2.fillOval(ballposX - 3, ballposY - 3, 26, 26);
		GradientPaint ballGradient = new GradientPaint(ballposX, ballposY, Color.WHITE,
				ballposX + 20, ballposY + 20, new Color(255, 210, 0));
		g2.setPaint(ballGradient);
		g2.fillOval(ballposX, ballposY, 20, 20);

		//if the game hasn't started yet (fresh load or after a restart, before pressing Enter)
		if(!play && totalBricks > 0 && ballposY <= 600) {
			drawStartPanel(g2);
		}

		//if player breaks all the bricks
		if(totalBricks <=0)
		{
			play = false;
			ballXdir = 0;
			ballYdir = 0;

			drawEndPanel(g2, "YOU WON!", "Final Score: " + score, new Color(76, 175, 80));

			if(Player.getName() != null) {
				try {
					FileWriter fwrite = new FileWriter("Score.txt",true);
					String s = Player.getName()+"\t"+score+"\n";
					BufferedWriter bw = new BufferedWriter(fwrite);
					PrintWriter pw = new PrintWriter(bw);
					pw.println(s);
					pw.close();
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, "File cannot be created", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		//if the ball goes below the border
		if(ballposY>600) {

			play = false;
			ballXdir = 0;
			ballYdir = 0;

			drawEndPanel(g2, "GAME OVER", "Final Score: " + score, new Color(220, 60, 60));

			if(Player.getName() != null) {
				try {
					FileWriter fwrite = new FileWriter("Score.txt",true);
					String s = Player.getName()+"\t"+score+"\n";
					BufferedWriter bw = new BufferedWriter(fwrite);
					PrintWriter pw = new PrintWriter(bw);
					pw.println(s);
					pw.close();
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, "File cannot be created", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		g.dispose();
	}

	//styled overlay shown before the game starts
	private void drawStartPanel(Graphics2D g2) {
		int panelX = 110, panelY = 240, panelW = 470, panelH = 110;

		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRoundRect(panelX, panelY, panelW, panelH, 25, 25);
		g2.setColor(new Color(255, 221, 89));
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(panelX, panelY, panelW, panelH, 25, 25);

		String message = "PLEASE PRESS ENTER TO START THE GAME";
		g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
		FontMetrics fm = g2.getFontMetrics();
		int msgX = panelX + (panelW - fm.stringWidth(message)) / 2;
		g2.setColor(Color.WHITE);
		g2.drawString(message, msgX, panelY + 65);
	}

	//reusable styled overlay panel for win / game-over screens
	private void drawEndPanel(Graphics2D g2, String title, String subtitle, Color accent) {
		int panelX = 130, panelY = 220, panelW = 430, panelH = 160;

		g2.setColor(new Color(0, 0, 0, 160));
		g2.fillRoundRect(panelX, panelY, panelW, panelH, 25, 25);
		g2.setColor(accent);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(panelX, panelY, panelW, panelH, 25, 25);

		g2.setFont(new Font("Segoe UI", Font.BOLD, 32));
		FontMetrics fm1 = g2.getFontMetrics();
		int titleX = panelX + (panelW - fm1.stringWidth(title)) / 2;
		g2.setColor(Color.WHITE);
		g2.drawString(title, titleX, panelY + 60);

		g2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		FontMetrics fm2 = g2.getFontMetrics();
		int subX = panelX + (panelW - fm2.stringWidth(subtitle)) / 2;
		g2.drawString(subtitle, subX, panelY + 95);

		String restartMsg = "PRESS ENTER TO RESTART";
		g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
		FontMetrics fm3 = g2.getFontMetrics();
		int restartX = panelX + (panelW - fm3.stringWidth(restartMsg)) / 2;
		g2.setColor(accent);
		g2.drawString(restartMsg, restartX, panelY + 130);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();

		if(play) {

			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX,550,100,8))) {
				ballYdir = -ballYdir;
			}

			A: for(int i=0; i<map.map.length; i++) {
				for(int j=0; j<map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j*map.getBrickWidth() + 70;
						int brickY = i*map.getBrickHeight()+50;
						int brickWidth = map.getBrickWidth();
						int brickHeight = map.getBrickHeight();

						Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);


						if(ballRect.intersects(brickRect)) {
							map.setBrickvalue(0,i,j);
							totalBricks--;
							score +=5;

							//when the ball touches the brick from left or right, it changes the angle
							if(ballposX+19 <= brickRect.x || ballposX+1 >= brickRect.x + brickRect.width)
							{
								ballXdir =- ballXdir;
							}
							else
							{
								ballYdir = -ballYdir;
							}

							break A;
						}
					}

				}
			}


			ballposX += ballXdir;
			ballposY += ballYdir;

			//when the ball touches the border, it bounces back in the opposite direction
			//left border
			if(ballposX<0)
				ballXdir = -ballXdir;

			//upper border
			if(ballposY<0)
				ballYdir = -ballYdir;

			//right border
			if(ballposX>670)
				ballXdir = -ballXdir;

			repaint();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			if(playerX >=585)
				playerX = 585;
			else
				moveRight();
		}

		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			if(playerX < 10)
				playerX = 10;
			else
				moveLeft();
		}

		if(e.getKeyCode()== KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -2;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);

				repaint();

			}
		}
	}

	public void moveRight() {
		play = true;
		playerX+=20;
	}

	public void moveLeft() {
		play = true;
		playerX-=20;
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public static void main(String[] args) {
		new Gameplay();
		new Welcome();
	}
}
