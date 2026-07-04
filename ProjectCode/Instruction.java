//Instruction.java

package ProjectCode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Instruction implements ActionListener {

	private JButton back;
	private JFrame fr = new JFrame("Rules of Game");
	private GradientPanel cr;
	private JLabel label1, label2, label3, label4, label5;

	public Instruction() {

		cr = new GradientPanel();
		cr.setLayout(null);
		fr.setContentPane(cr);

		// Title
		label1 = new JLabel("HOW TO PLAY", SwingConstants.CENTER);
		label1.setForeground(Color.WHITE);
		label1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		label1.setBounds(50, 40, 400, 40);
		cr.add(label1);

		// Rule 1
		label2 = new JLabel("<html>&larr; Press LEFT arrow key to move the paddle left</html>");
		label2.setForeground(new Color(230, 230, 230));
		label2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		label2.setBounds(60, 130, 380, 40);
		cr.add(label2);

		// Rule 2
		label3 = new JLabel("<html>&rarr; Press RIGHT arrow key to move the paddle right</html>");
		label3.setForeground(new Color(230, 230, 230));
		label3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		label3.setBounds(60, 175, 380, 40);
		cr.add(label3);

		// Rule 3
		label4 = new JLabel("<html>If the ball falls past the bottom of the screen, you lose.</html>");
		label4.setForeground(new Color(230, 230, 230));
		label4.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		label4.setBounds(60, 220, 380, 50);
		cr.add(label4);

		// Rule 4
		label5 = new JLabel("<html>Break every brick to win the game!</html>");
		label5.setForeground(new Color(230, 230, 230));
		label5.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		label5.setBounds(60, 275, 380, 40);
		cr.add(label5);

		// Back button, styled like Welcome screen buttons
		back = createStyledButton("BACK", new Color(66, 133, 244));
		back.setBounds(175, 370, 150, 48);
		cr.add(back);

		back.addActionListener(this);

		fr.setBounds(400, 100, 500, 500);
		fr.setResizable(false);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
	}

	// Same rounded, flat-style button used in Welcome
	private JButton createStyledButton(String text, Color bgColor) {
		JButton button = new JButton(text) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
				super.paintComponent(g2);
				g2.dispose();
			}
		};
		button.setFont(new Font("Segoe UI", Font.BOLD, 16));
		button.setForeground(Color.WHITE);
		button.setBackground(bgColor);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return button;
	}

	// Same gradient background as Welcome (kept as its own local class here
	// since Welcome's GradientPanel is private to that file)
	private static class GradientPanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			GradientPaint gp = new GradientPaint(
					0, 0, new Color(94, 53, 177),
					getWidth(), getHeight(), new Color(30, 30, 60));
			g2.setPaint(gp);
			g2.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			new Welcome();
			fr.dispose();
		}
	}
}
