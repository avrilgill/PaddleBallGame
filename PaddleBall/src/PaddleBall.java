import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class PaddleBall implements ActionListener, KeyListener {

	public static PaddleBall game;

	public int FRAME_WIDTH = 1000;
	public int FRAME_HEIGHT = 700;

	public GamePanel render;
	public Paddle Player1;
	public Paddle Player2;

	public boolean Computer = false;

	public boolean w, s, up, down;

	public Ball ball;
	int loopstoper = 0;

	public int GameProgress = 0;

	public PaddleBall() {
		Timer time = new Timer(20, this);
		JFrame f = new JFrame("Paddle Ball");
		render = new GamePanel();
		f.setSize(FRAME_WIDTH + 19, FRAME_HEIGHT + 40);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addKeyListener(this);
		f.add(render);
		start();
		time.start();
	}

	HashMap<String, Integer> records = new HashMap<>();

	// creating a path for writing a file
	String fileStr = "storedata.txt";
	Path path = Paths.get(fileStr);
	Charset charset = Charset.forName("UTF-8");

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		game = new PaddleBall();

	}

	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

		if (GameProgress == 0) // started menu
		{
			g.setColor(Color.BLUE);
			g.setFont(new Font("Arial", 1, 50));

			g.drawString("Paddle Ball", FRAME_WIDTH / 2 - 120, 200);

			g.setColor(Color.RED);
			g.setFont(new Font("Arial", 1, 25));
			g.drawString("Player 1 VS Player 2 - Press 1", 300, 350);
			g.drawString("Player VS Computer - Press 2", 300, 400);
			g.drawString("End the Match - Press 3", 300, 450);
			g.drawString("Instructions - Press 4", 300, 500);
			g.drawString("High Scores - Press 5", 300, 550);
			g.drawString("Exit - Press 6", 300, 600);
			
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", 1, 19));
			g.drawString("Made By - Avril Gill", 600, 650);
			g.drawString("Student ID - 300254970", 600, 670);
		}

		if (GameProgress == 1) // IN progress
		{
			Player1.render(g);
			Player2.render(g);
			ball.render(g);

			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 25));
			g.drawString(String.valueOf(Player1.score), 200, 50);
			g.drawString("SCORE", 450, 50);
			g.drawString(String.valueOf(Player2.score), 750, 50);

			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 25));
			g.drawString("Press 3 to End Match", 370, 670);
		}

		if (GameProgress == 2) // Ended
		{
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", 1, 25));

			String winner = "";
			if (Player1.score > Player2.score) {
				if (!Computer) {
					winner = "Player 1 Wins!";
				} else if (Computer) {
					winner = "Human Player Wins!";
				}
			} else if (Player1.score < Player2.score) {
				if (!Computer) {
					winner = "Player 2 Wins!";
				} else if (Computer) {
					winner = "Computer Wins!";
				}
			} else if (Player1.score == Player2.score) {
				winner = "It's a Draw!";
			}

			g.drawString("Result of the game: " + winner, FRAME_WIDTH / 2 - 160, 350);
			g.drawString("Press 1 for Main Menu!", FRAME_WIDTH / 2 - 160, 400);

		}

		if (GameProgress == 3) // Instructions
		{
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("INSTRUCTIONS", 200, 50);

			g.setColor(Color.RED);
			g.setFont(new Font("Arial", 1, 25));
			g.drawString("Player 1 Controls", 200, 150);

			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 25));
			g.drawString("Press W to move Up", 200, 200);
			g.drawString("Press S to move Down", 200, 250);

			g.setColor(Color.RED);
			g.setFont(new Font("Arial", 1, 25));
			g.drawString("Player 2 Controls", 200, 300);

			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 25));
			g.drawString("Press Up Arrow to move Up", 200, 350);
			g.drawString("Press Down Arrow to move Down", 200, 400);

			g.setColor(Color.RED);
			g.setFont(new Font("Arial", 1, 25));
			g.drawString("Press 1 for Main Menu", 200, 450);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (GameProgress == 4) {
			System.exit(0);
		}
		if (GameProgress == 1) {
			update();
		}
		render.repaint();

		if (GameProgress == 2 && loopstoper == 0) {
			String input = "";
			if (Computer) {
				input = "Computer";
				loopstoper = 1;
			} else if (Player1.score == Player2.score) {
				loopstoper = 1;
			}

			else {
				loopstoper = 1;
				if (input.length() == 0) {
					input = JOptionPane.showInputDialog(null, "Please Enter Correct Name:", "Input",
							JOptionPane.PLAIN_MESSAGE);
				} else {
					input = "No Name";

				}
				if (Player1.score > Player2.score) {
					records.put(input, Player1.score);
				} else if (Player1.score < Player2.score) {
					records.put(input, Player2.score);
				}

			}

		}

	}

	private void update() {
		// TODO Auto-generated method stub

		if (w) {
			Player1.move(true);
		}
		if (s) {
			Player1.move(false);
		}
		if (!Computer) {
			if (up) {
				Player2.move(true);
			}
			if (down) {
				Player2.move(false);
			}
		} else if (Computer) {

			// Computer Player
			if (Player2.y + Player2.PaddleHeight / 2 < ball.y) {
				Player2.move(false);
			}
			if (Player2.y + Player2.PaddleHeight / 2 > ball.y) {
				Player2.move(true);
			}
		}

		ball.update(Player1, Player2);
	}

	public void start() {
		Player1 = new Paddle(this, 1);
		Player2 = new Paddle(this, 2);
		ball = new Ball(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W) {
			w = true;
		} else if (id == KeyEvent.VK_S) {
			s = true;
		} else if (id == KeyEvent.VK_UP) {
			up = true;
		} else if (id == KeyEvent.VK_DOWN) {
			down = true;
		}
		if (id == KeyEvent.VK_2 && GameProgress == 0) {
			Computer = true;
			start();
			GameProgress = 1;
		} else if (id == KeyEvent.VK_1 && GameProgress == 0) {
			loopstoper = 0;
			Computer = false;
			start();
			GameProgress = 1;
		}

		else if (id == KeyEvent.VK_1 && GameProgress == 3) {
			Computer = false;
			loopstoper = 0;
			start();
			GameProgress = 0;
		} else if (id == KeyEvent.VK_3 && GameProgress == 1) {
			GameProgress = 2;
		} else if (id == KeyEvent.VK_1 && GameProgress == 2) {
			loopstoper = 0;
			Computer = false;
			start();
			GameProgress = 0;
		} else if (id == KeyEvent.VK_4 && GameProgress == 0) {
			GameProgress = 3;
		} // instructions
		else if (id == KeyEvent.VK_5 && GameProgress == 0) {
			Map<String, Integer> sortedMap = sortByComparator(records);

			try (BufferedWriter out = Files.newBufferedWriter(path, charset)) {
				for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
					out.write("The Player " + entry.getKey() + " scored " + entry.getValue() + " points.");
					out.newLine();

				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Highscores were successully stored in a file!");
		} else if (id == KeyEvent.VK_6 && GameProgress == 0) {
			GameProgress = 4; // exit
		}
	}

	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {

		// Convert Map to List
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// Storing sorted items into map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W) {
			w = false;
		} else if (id == KeyEvent.VK_S) {
			s = false;
		} else if (id == KeyEvent.VK_UP) {
			up = false;
		} else if (id == KeyEvent.VK_DOWN) {
			down = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
