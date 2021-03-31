import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball {

	public int x, y, width = 25, height = 25;

	public int motionX, motionY;

	public Random random;

	private PaddleBall game;

	public int amountOfHits;

	public Ball(PaddleBall game) {
		this.game = game;

		this.random = new Random();

		spawn();
	}

	public void update(Paddle paddle1, Paddle paddle2) {
		int speed = 5;

		this.x += motionX * speed;
		this.y += motionY * speed;

		if (this.y + height - motionY > game.FRAME_HEIGHT || this.y + motionY < 0) {
			if (this.motionY < 0) {
				this.y = 0;
				this.motionY = random.nextInt(4);

				if (motionY == 0) {
					motionY = 1;
				}
			} else {
				this.motionY = -random.nextInt(4);
				this.y = game.FRAME_HEIGHT - height;

				if (motionY == 0) {
					motionY = -1;
				}
			}
		}

		if (checkCollision(paddle1) == 1) {
			this.motionX = 1 + (amountOfHits / 5);
			this.motionY = -2 + random.nextInt(4);

			if (motionY == 0) {
				motionY = 1;
			}

			amountOfHits++;
		} else if (checkCollision(paddle2) == 1) {
			this.motionX = -1 - (amountOfHits / 5);
			this.motionY = -2 + random.nextInt(4);

			if (motionY == 0) {
				motionY = 1;
			}

			amountOfHits++;
		}

		if (checkCollision(paddle1) == 2) {
			paddle2.score++;
			spawn();
		} else if (checkCollision(paddle2) == 2) {
			paddle1.score++;
			spawn();
		}
	}

	public void spawn() {
		this.amountOfHits = 0;
		this.x = game.FRAME_WIDTH / 2 - this.width / 2;
		this.y = game.FRAME_HEIGHT / 2 - this.height / 2;

		this.motionY = -2 + random.nextInt(4);

		if (motionY == 0) {
			motionY = 1;
		}

		if (random.nextBoolean()) {
			motionX = 1;
		} else {
			motionX = -1;
		}
	}

	public int checkCollision(Paddle paddle) {
		if (this.x < paddle.x + paddle.PaddleWidth && this.x + width > paddle.x
				&& this.y < paddle.y + paddle.PaddleHeight && this.y + height > paddle.y) {
			return 1; // bounce
		} else if ((paddle.x > x && paddle.PaddleID == 1) || (paddle.x < x - width && paddle.PaddleID == 2)) {
			return 2; // score
		}

		return 0; // nothing
	}

	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(x, y, width, height);
	}

}
