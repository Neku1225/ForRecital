import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TransParentFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	StringPanel stringPanel = new StringPanel();

	public TransParentFrame() {
		this.setContentPane(stringPanel);
		this.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 常に最前面に表示
		// this.setAlwaysOnTop(true);

		// 背景を透明あるいは半透明にする場合、

		this.setUndecorated(true);
		this.setBackground(new Color(0, 0, 0, 0));

		// ウィンドウ範囲を示す枠をつけておく
		// this.getRootPane().setBorder(new LineBorder(Color.black, 2));

		this.pack();
		this.setVisible(true);
		this.addKeyListener(new KeyInput());
	}

	public static void main(String[] args) {
		TransParentFrame frame = new TransParentFrame();
		String hashtag = JOptionPane.showInputDialog(frame, "追いかけたいタグを#を除いて入れてください");
		if (hashtag == null) {
			JOptionPane.showMessageDialog(frame, "ツイート取得中にエラーが起きました");
			System.exit(ERROR);
		}
		// 常に最前面に表示
		frame.setAlwaysOnTop(true);
		new TwitterReader(frame.stringPanel, hashtag);
		while (true) {
			frame.stringPanel.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
	}
}
