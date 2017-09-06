/*
 * 2017 04 01
 * 배구 게임
 */

package ballgame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameUI extends JFrame {
	private Ball ball;

	class Ball extends Canvas {
		private Graphics gbuff;
		private Image buff;

		// 공의 x,y 좌표
		private int ballX;
		private int ballY;

		// 사용자의 x 좌표
		private int userX = 350;

		// 컴퓨터의 x 좌표
		private int comX = 0;

		// 공의 위, 아래, 왼쪽, 오른쪽 방향을 판단하기 위한 값
		private final static int UP = 1;
		private final static int DOWN = -1;
		private final static int LEFT = -1;
		private final static int RIGHT = 1;

		// 공의 진행 방향을 인식하기 위한 값
		private int yDirection = DOWN;
		private int xDirection = RIGHT;

		// 컴퓨터 바의 진행 방향
		private int comDirection = RIGHT;

		// 공의 속도
		public static final int GRAVITY = 1;

		// 생성자 
		public Ball() {
			this.setBackground(Color.white);
			this.setLocation(80, 0);
			this.setSize(720, 500);

			// 공의 초기 방향 및 위치 지정 메서드 호출
			initBall();

			this.addMouseMotionListener(new MouseAdapter() {

				// 마우스가 움직일 때
				@Override
				public void mouseMoved(MouseEvent e) {
					// 마우스의 위치에 따라 사용자 바 이동
					if (e.getX() - 50 >= getWidth() / 2 && e.getX() + 50 < getWidth()) {
						userX = e.getX() - 50;
					}
				}

			});

		}

		// 공의 위치와 방향 지정 
		public void initBall() {
			yDirection = (int) (Math.random() * 10) % 2 == 0 ? 1 : -1;
			xDirection = (int) (Math.random() * 10) % 2 == 0 ? 1 : -1;
			ballX = (int) (Math.random() * 1000);
			ballY = (int) (Math.random() * 100);
		}

		// UI 그리기
		@Override
		public void paint(Graphics g) {
			// 공이 떨어지면
			if (ballY == getHeight()) {
				// 왼쪽 네트에 떨어지면 우측 승, 오른쪽 네트에 떨어지면 좌측 승 메시지 띄우기
				JOptionPane.showMessageDialog(this, (ballX + 50) < (getWidth() / 2) ? "RIGHT WIN!" : "LEFT WIN!");
				// 공 다시 그리기
				initBall();
			}
			
			if (gbuff == null) {
				buff = createImage(this.getWidth(), this.getHeight());
				gbuff = buff.getGraphics();
			}
			gbuff.clearRect(0, 0, getWidth(), getHeight());

			// 공 그리기
			gbuff.fillOval(ballX, ballY, 50, 50);

			// 네트 그리기
			gbuff.fillRect(getWidth() / 2 - 5, getHeight() / 2 + 40, 10, getHeight() / 2);

			// 사용자 바 그리기
			gbuff.fillRect(userX, getHeight() - 10, 100, 10);

			// 컴퓨터 바 그리기
			gbuff.fillRect(comXoperate(), getHeight() - 10, 100, 10);

			// 공의 방향이 아래일 경우
			if (yDirection == DOWN) {
				downBall();
			}
			// 공의 방향이 위일 경우
			else if (yDirection == UP) {
				upBall();
			}

			g.drawImage(buff, 0, 0, this);

			// 다시 그리기
			repaint();
		}

		@Override
		public void update(Graphics g) {
			// 속도 조절을 위한 쓰레드 실행
			try {
				Thread.sleep(2);
			} catch (Exception e) {
			}
			paint(g);
		}

		// 컴퓨터 바 움직임
		public int comXoperate() {
			// 네트에 닿으면 방향 왼쪽으로 세팅
			if (comX == getWidth() / 2 - 100) {
				comDirection = LEFT;
			}
			// 왼쪽 벽에 닿으면 방향 오른족으로 세팅
			else if (comX == 0) {
				comDirection = RIGHT;
			}

			// 컴퓨터 바가 오른쪽 방향일 경우
			if (comDirection == RIGHT) {
				// x 좌표 1 증가
				return comX++;
			}
			// 컴퓨터 바가 왼쪽 방향일 경우
			else {
				// x 좌표 1 감소
				return comX--;
			}
		}

		// 위로 올라가는 방향의 공
		public void upBall() {
			// 공의 y 좌표가 0보다 클 경우
			if (ballY > 0) {
				// y 좌표 감소
				ballY--;
			}
			// 공의 y 좌표가 0일 경우
			if (ballY == 0) {
				// 방향을 아래로 지정
				yDirection = DOWN;
			}

			// 공의 방향을 지정해 주는 메서드 호출
			directionBall();
			// 공을 움직이는 메서드 호출
			moveBall();
		}

		// 아래로 내려가는 방향의 공
		public void downBall() {
			// 공의 y 좌표가 화면의 크기보다 작을 경우
			if (ballY + 50 < getHeight()) {
				// 공의 y 좌표 증가
				ballY++;
			}
			// 사용자 바와 컴퓨터 바에 부딪혔을 때
			else if (userX < ballX + 25 && userX + 100 > ballX + 25 && ballY + 50 == getHeight()
					|| comX < ballX + 25 && comX + 100 > ballX + 25 && ballY + 50 == getHeight()) {
				// 공의 방향 위로 지정
				yDirection = UP;
			}
			// 그렇지 않으면 공의 y 좌표 증가
			else {
				ballY++;
			}

			// 공의 방향 지정해주는 메서드 호출
			directionBall();
			// 공의 움직이는 메서드 호출
			moveBall();
		}

		// 공의 방향 지정 메서드
		public void directionBall() {

			// 공의 x 좌표가 0일 때
			if (ballX == 0) {
				// 공의 방향은 오른쪽
				xDirection = RIGHT;
			}
			// 공이 오른쪽에 부딪혔을 때
			else if (ballX + 50 == getWidth()) {
				// 공의 방향은 왼쪽
				xDirection = LEFT;
			}

			// 공의 방향이 오른쪽일 때
			if (xDirection == RIGHT) {
				// 공의 x 좌표가 네트에 부딪혔을 때
				if (ballX == (getWidth() / 2 - 55) && ballY > getHeight() / 2) {
					// 공의 방향은 왼쪽
					xDirection = LEFT;
				}
			}
			// 공의 방향이 왼쪽일 때
			else if (xDirection == LEFT) {
				// 공의 x 좌표가 네트에 부딪혔을 때
				if (ballX == (getWidth() / 2 + 5) && ballY > getHeight() / 2) {
					xDirection = RIGHT;
				}
			}

		}

		// 공의 움직임 메서드
		public void moveBall() {
			// 왼쪽 방향일 때
			if (xDirection == LEFT) {
				// 공의 x좌표 감소
				ballX--;
			// 오른쪽 방향일 때
			} else if (xDirection == RIGHT) {
				// 공의 x좌표 증가
				ballX++;
			}
		}

	}

	// 생성자
	public GameUI() {
		ball = new Ball();
		this.add(ball);
		this.setTitle("VOLLEY BALL");
		// 창 크기 재설정 금지
		this.setResizable(false);
		this.setVisible(true);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(1100, 750);
		this.setLocation(new Point((d.width / 2) - (this.getWidth() / 2), (d.height / 2) - (this.getHeight() / 2)));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new GameUI();
	}
}
