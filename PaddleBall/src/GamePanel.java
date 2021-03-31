import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	/**
	 * Created by Avril Gill
	 */
	private static final long serialVersionUID = -3505560995802716476L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		PaddleBall.game.render(g);
	}

}
