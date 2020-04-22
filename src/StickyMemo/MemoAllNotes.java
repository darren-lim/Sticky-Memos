package StickyMemo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MemoAllNotes implements ActionListener {

	public static MemoAllNotes allNotes;

	private JFrame frame;
	private JPanel listPanel;

	private CardLayout layout;

	private HashMap<String, Memo> buttonMap = new HashMap<>();
	private HashMap<String, Integer> titleMap = new HashMap<>();

	public MemoAllNotes() {
		frame = new JFrame("Memos All Notes");
		layout = new CardLayout(5, 5);
		frame.setSize(300, 400);
		frame.setLayout(layout);
		frame.setBackground(Color.WHITE);
		frame.setPreferredSize(new Dimension(300, 400));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// for editor IDE
		// File imgfile = new File("src/Image/stickymemo64.png");
				
		// for deployment
		File imgfile = new File("bin/Images/stickymemo64.png");
		
		String imgPath = imgfile.getAbsolutePath();
		ImageIcon img = new ImageIcon(imgPath);
		frame.setIconImage(img.getImage());
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				allNotes = null;
			}
		});

		repaint();
	}

	public void actionPerformed(ActionEvent e) {
		String title = e.getActionCommand();
		Memo post = buttonMap.get(title);
		post.bringBack();
	}

	public void bringBack() {
		frame.setVisible(true);
		frame.toFront();
	}

	public void repaint() {
		frame.getContentPane().removeAll();
		listPanel = new JPanel(new GridLayout(0, 1));
		
		addButtons();
		
		JScrollPane listScroller = new JScrollPane(listPanel);
		listScroller.setViewportView(listPanel);
		listScroller.setBorder(BorderFactory.createEmptyBorder());
		listScroller.getVerticalScrollBar().setUnitIncrement(16);
		listScroller.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		listPanel.setPreferredSize(listPanel.getPreferredSize());

		listScroller.setBackground(Color.white);
		listPanel.setBackground(Color.white);

		frame.add(listScroller, BorderLayout.CENTER);
		// frame.setVisible(false);
		frame.setVisible(true);
	}

	public void addButtons() {
		buttonMap.clear();
		titleMap.clear();
		for (int i = 0; i < StickyMemoMain.MemoArr.size(); i++) {
			JPanel panel = new JPanel(new BorderLayout());
			//panel.setSize(new Dimension(100, 60));
			panel.setPreferredSize(new Dimension(100, 60));
			panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 0));
			
			String title = StickyMemoMain.MemoArr.get(i).gettitle();
			if (titleMap.get(title) != null) {
				int num = titleMap.get(title) + 1;
				titleMap.replace(title, num);
				title = title + "(" + Integer.toString(num) + ")";
			}
			else {
				titleMap.put(title, 0);
			}
			
			JButton button = new JButton(title);
			buttonMap.put(title, StickyMemoMain.MemoArr.get(i));
			button.addActionListener(this);
			button.setMaximumSize(new Dimension(Integer.MAX_VALUE,
					button.getMinimumSize().height));
			
			button.setFont(new Font("Arial", Font.BOLD, 24));
			button.setBorder(BorderFactory.createRaisedSoftBevelBorder());
			button.setBackground(StickyMemoMain.MemoArr.get(i).getcolor());
			
			panel.add(button);
			listPanel.add(panel);
		}
	}

}
