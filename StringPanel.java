import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class StringPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int font_size;
	Font font1;
	Font font2;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int MAX_ROW;
	int MES_MAX_NUM;

	int[] vx;
	int[] strX;
	int[] strY;
	String[] message;
	boolean[] is_available;
	int displayTime;

	public StringPanel() {
		font_size = 60;
		displayTime = 420;// 単位はフレーム数
		font1 = new Font("MS明朝", Font.PLAIN, font_size);
		MAX_ROW = screenSize.height / font_size;
		MES_MAX_NUM = MAX_ROW;
		strX = new int[MES_MAX_NUM];
		strY = new int[MES_MAX_NUM];
		vx = new int[MES_MAX_NUM];
		message = new String[MES_MAX_NUM];
		is_available = new boolean[MAX_ROW];
		for (int i = 0; i < MES_MAX_NUM; i++) {
			message[i] = null;
			strX[i] = -10;
			strY[i] = -10;
			vx[i] = screenSize.width / displayTime;
		}
		for (int i = 0; i < MAX_ROW; i++) {
			is_available[i] = false;
		}
	}

	public void setMessage(String setText) {

		for (int i = 0; i < MES_MAX_NUM; i++) {
			if (message[i] != null && strX[i] < -font_size * message[i].length()) {// 文字列が次の文字列と重ならない程度に
				is_available[i] = true;
				message[i] = null;
			}
			if (message[i] == null) {
				strX[i] = screenSize.width;
				strY[i] = (i + 1) * font_size;
				message[i] = setText;
				vx[i] = (screenSize.width + font_size * message[i].length()) / displayTime;
				break;
			}
		}
	}

	public void drawShadowedText(Graphics g, String txt, int px, int py, Color fore, Color shadow) {
		FontMetrics fm = g.getFontMetrics();
		int y = py + fm.getAscent();
		Color bak = g.getColor();
		g.setColor(shadow);
		g.drawString(txt, px + 2, y);
		g.drawString(txt, px + 2, y + 2);
		g.drawString(txt, px, y + 2);
		g.setColor(fore);
		g.drawString(txt, px, y);
		g.setColor(bak);
	}

	@Override
	public void paint(Graphics g) {
		g.setFont(font1);
		g.setColor(new Color(0, 0, 0));
		for (int i = 0; i < MES_MAX_NUM; i++) {
			if (message[i] != null) {
				drawShadowedText(g, message[i], strX[i], strY[i], Color.white, Color.black);
				strX[i] -= 2 * vx[i];
			}
		}
	}

}