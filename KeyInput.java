
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

	// �L�[�R�[�h������ϐ�
	int KeyCode;

	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
		case 27:
			System.exit(0);
			break;
		}
	}

	public void keyReleased(KeyEvent event) {

	}

	public void keyTyped(KeyEvent event) {

	}

}