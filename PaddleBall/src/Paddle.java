import java.awt.Color;
import java.awt.Graphics;

public class Paddle {

	public int PaddleID;

	public int x;
	public int y;
	public int PaddleWidth = 20;
	public int PaddleHeight = 200;

	public int score;

	public Paddle(PaddleBall game, int PaddleID) {
		this.PaddleID = PaddleID;

		if (PaddleID == 1) {
			this.x = 0;
		} else if (PaddleID == 2) {
			this.x = game.FRAME_WIDTH - PaddleWidth;

		}

		this.y = game.FRAME_HEIGHT / 2 - this.PaddleHeight / 2;
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, PaddleWidth, PaddleHeight);
	}

	public void move(boolean up) {
		int speed = 15;

		if (up) {
			if (y - speed > 0) {
				y -= speed;
			} else {
				y = 0;
			}
		} else {
			if (y + PaddleHeight + speed < PaddleBall.game.FRAME_HEIGHT) {
				y += speed;
			} else {
				y = PaddleBall.game.FRAME_HEIGHT - PaddleHeight;
			}
		}
	}

}
