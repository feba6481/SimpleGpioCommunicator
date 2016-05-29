package writer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class WriterGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4662394111684018104L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WriterGUI frame = new WriterGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WriterGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		final JButton btnStart = new JButton("Start");

		contentPane.add(btnStart, BorderLayout.SOUTH);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		final JTextArea taMessage = new JTextArea();
		scrollPane.setViewportView(taMessage);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		final JSlider slSpeed = new JSlider();
		slSpeed.setMinimum(5);
		slSpeed.setMaximum(2000);
		panel.add(slSpeed, BorderLayout.CENTER);

		JLabel lblMin = new JLabel("5 ms");
		panel.add(lblMin, BorderLayout.WEST);

		JLabel lblMax = new JLabel("2000 ms");
		panel.add(lblMax, BorderLayout.EAST);

		final Writer w = new Writer(RaspiPin.GPIO_26, new Pin[] {
				RaspiPin.GPIO_27, RaspiPin.GPIO_28, RaspiPin.GPIO_29 });

		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taMessage.setEnabled(false);
				btnStart.setEnabled(false);
				slSpeed.setEnabled(false);
				// write
				try {
					System.out.println("parsing...");
					boolean[] parsed = parse(taMessage.getText());
					System.out.println();
					boolean b = w.write(parsed, slSpeed.getValue());
					System.out.println(b);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

				taMessage.setText("");
				taMessage.setEnabled(true);
				btnStart.setEnabled(true);
				slSpeed.setEnabled(true);
			}
		});
	}

	public boolean[] parse(String text) throws Exception {
		boolean[] message = new boolean[text.length()];
		for (int i = 0; i < text.length(); i++) {
			try {
				if (text.charAt(i) == '0' || text.charAt(i) == '1')
					message[i] = text.charAt(i) == '1';
				else
					throw new Exception("Enter 0 and 1 only.");
			} catch (Exception e) {
				throw e;
			}
		}
		return message;
	}

}
