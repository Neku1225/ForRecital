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

		// ��ɍőO�ʂɕ\��
		// this.setAlwaysOnTop(true);

		// �w�i�𓧖����邢�͔������ɂ���ꍇ�A

		this.setUndecorated(true);
		this.setBackground(new Color(0, 0, 0, 0));

		// �E�B���h�E�͈͂������g�����Ă���
		// this.getRootPane().setBorder(new LineBorder(Color.black, 2));

		this.pack();
		this.setVisible(true);
		this.addKeyListener(new KeyInput());
	}

	public static void main(String[] args) {
		TransParentFrame frame = new TransParentFrame();
		String hashtag = JOptionPane.showInputDialog(frame, "�ǂ����������^�O��#�������ē���Ă�������");
		if (hashtag == null) {
			JOptionPane.showMessageDialog(frame, "�c�C�[�g�擾���ɃG���[���N���܂���");
			System.exit(ERROR);
		}
		// ��ɍőO�ʂɕ\��
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
