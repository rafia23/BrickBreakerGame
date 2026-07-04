//MapGenerator.java

package ProjectCode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class MapGenerator {

	protected int map[][];
	private int brickWidth;
	private int brickHeight;

	// A palette so each row gets a distinct color
	private static final Color[] ROW_COLORS = {
			new Color(255, 99, 99),   // red
			new Color(255, 179, 71),  // orange
			new Color(255, 221, 89),  // yellow
			new Color(102, 205, 170), // teal
			new Color(100, 149, 237)  // blue
	};

	public int getBrickWidth() {
		return brickWidth;
	}

	public void setBrickWidth(int brickWidth) {
		this.brickWidth = brickWidth;
	}

	public int getBrickHeight() {
		return brickHeight;
	}

	public void setBrickHeight(int brickHeight) {
		this.brickHeight = brickHeight;
	}


	public MapGenerator(int row, int col)
	{
		map = new int[row][col];
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {

				map[i][j] = 1;
			}
		}

		brickWidth = 80;
		brickHeight = 50;

	}

	public void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				if(map[i][j] > 0) {
					int x = j*brickWidth+70;
					int y = i*brickHeight+50;

					Color base = ROW_COLORS[i % ROW_COLORS.length];
					Color light = base.brighter();
					Color dark = base.darker();

					// Gradient fill for a glossy brick look
					GradientPaint gp = new GradientPaint(x, y, light, x, y + brickHeight, dark);
					g.setPaint(gp);
					g.fillRoundRect(x, y, brickWidth, brickHeight, 12, 12);

					// Subtle border
					g.setStroke(new BasicStroke(2)); //for borders in the bricks
					g.setColor(dark.darker());
					g.drawRoundRect(x, y, brickWidth, brickHeight, 12, 12);

				}
			}
		}
	}

	public void setBrickvalue(int value, int row, int col) {
		map[row][col] = value;

	}
}
