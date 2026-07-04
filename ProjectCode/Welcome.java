//Welcome.java
package ProjectCode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Welcome implements ActionListener {

	private JFrame window = new JFrame("Welcome");
	private JLabel label1 = new JLabel("Enter your name:");
	private JButton enter;
	private JButton instructions;
	private JLabel label2;
	private GradientPanel c;

	private JTextField textField;

	public Welcome() {

		c = new GradientPanel();
		c.setLayout(null);
		window.setContentPane(c);

		// Title
		label2 = new JLabel("BRICK BREAKER", SwingConstants.CENTER);
		label2.setForeground(Color.WHITE);
		label2.setFont(new Font("Segoe UI", Font.BOLD, 30));
		label2.setBounds(50, 40, 400, 45);
		c.add(label2);

		JLabel subtitle = new JLabel("Smash every brick. Beat your score.", SwingConstants.CENTER);
		subtitle.setForeground(new Color(230, 230, 230));
		subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		subtitle.setBounds(50, 85, 400, 20);
		c.add(subtitle);

		// Name label
		label1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		label1.setForeground(Color.WHITE);
		label1.setBounds(70, 170, 150, 30);
		c.add(label1);

		// Name field
		textField = new JTextField();
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textField.setBackground(new Color(255, 255, 255, 230));
		textField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(90, 90, 140), 1, true),
				BorderFactory.createEmptyBorder(6, 10, 6, 10)));
		textField.setBounds(220, 165, 200, 38);
		c.add(textField);

		// Play button
		enter = createStyledButton("PLAY", new Color(76, 175, 80));
		enter.setBounds(125, 250, 250, 50);
		c.add(enter);

		// Instructions button
		instructions = createStyledButton("INSTRUCTIONS", new Color(66, 133, 244));
		instructions.setBounds(125, 320, 250, 50);
		c.add(instructions);

		enter.addActionListener(this);
		instructions.addActionListener(this);

		window.setBounds(400, 100, 500, 500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
	}

	// Rounded, flat-style button
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

	// Panel with a soft diagonal gradient background
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

		if (e.getSource() == enter) {

			if (textField.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter your name", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				JFrame frame = new JFrame();
				Gameplay gamePlay = new Gameplay();
				frame.setBounds(10, 10, 700, 600);
				frame.setTitle("Brick Breaker Game");
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(gamePlay);
				Player.setName(textField.getText());
				window.dispose();
			}
		}

		if (e.getSource() == instructions) {
			new Instruction();
			window.dispose();
		}
	}
}
