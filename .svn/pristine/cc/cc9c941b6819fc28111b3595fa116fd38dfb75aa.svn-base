import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WebFrame {
	
	private Semaphore semaphore;
	private JFrame mainFrame;
	private boolean readyState;
	private JLabel numberOfThreadsCompleted;
	private JLabel numberOfThreadsRunning;
	private JLabel elapsedTime;
	private JButton stopThreads;
	private JButton fetchSingle;
	private JButton fetchConcurrent;
	private JTextField threadCount;
	private boolean concurrent;
	private DefaultTableModel model;
	private Launcher launcher;
	private int runningThreads;
	private int completedThreads;
	
	public WebFrame(String fileName) {
		mainFrame = new JFrame("Web Loader");
		readyState = true;
		concurrent = false;
		runningThreads = 0;
		completedThreads = 0;
		mainFrame.setLayout(new BorderLayout());		
		model = new DefaultTableModel(new String[] { "url", "status"}, 0);
		JTable table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		FileLoader loader = new FileLoader(model);
		if(fileName != null) {
			loader.loadURLs(fileName);
		} else {
			loader.loadURLs("urls.txt");
		}
		table.setEnabled(false);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(600,300));
		mainFrame.add(scrollpane, BorderLayout.CENTER);
		
		JLabel title = new JLabel("Web Loader");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Serif", Font.PLAIN, 28));
		mainFrame.add(title, BorderLayout.NORTH);
		
		stopThreads = new JButton("STOP!");
		stopThreads.setEnabled(false);
		stopThreads.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				launcher.stopThreads();
				updateGUI();
			}
		});
		JLabel numberOfThreadsCompletedLbl = new JLabel("Number of Threads Completed:  ");
		numberOfThreadsCompleted = new JLabel("0");
		JLabel numberOfThreadsRunningLbl = new JLabel("Number of Threads Running:  ");
		numberOfThreadsRunning = new JLabel("0");
		JLabel elapsedTimeLbl = new JLabel("Elapsed time:  ");
		elapsedTime = new JLabel("0 s");
		
		fetchSingle = new JButton("Fetch");
		fetchSingle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				concurrent = false;
				updateGUI();
				launcher = new Launcher();
				launcher.start();
			}
		});
		fetchSingle.setName("Fetch");
		fetchConcurrent = new JButton("Concurrent Fetch");
		threadCount = new JTextField("2", 5);
		fetchConcurrent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				concurrent = true;
				updateGUI();
				launcher = new Launcher();
				launcher.start();
			}
		});
		
		JPanel fetchPanel = new JPanel();
		fetchPanel.setLayout(new GridLayout(5, 2));
		fetchPanel.add(numberOfThreadsRunningLbl);
		fetchPanel.add(numberOfThreadsRunning);
		fetchPanel.add(numberOfThreadsCompletedLbl);
		fetchPanel.add(numberOfThreadsCompleted);
		fetchPanel.add(elapsedTimeLbl);
		fetchPanel.add(elapsedTime);
		fetchPanel.add(stopThreads);
		fetchPanel.add(fetchSingle);
		fetchPanel.add(fetchConcurrent);
		fetchPanel.add(threadCount);
		mainFrame.add(fetchPanel, BorderLayout.SOUTH);
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	public void updateGUI() {
		readyState=!readyState;
		if (readyState) {
			stopThreads.setEnabled(false);
			threadCount.setEditable(true);
			fetchSingle.setEnabled(true);
			fetchConcurrent.setEnabled(true);
		} else {
			stopThreads.setEnabled(true);
			threadCount.setEditable(false);
			fetchSingle.setEnabled(false);
			fetchConcurrent.setEnabled(false);
		}
	}	
	
	public void resetGUI() {
		numberOfThreadsRunning.setText("1");
		numberOfThreadsCompleted.setText("0");
		runningThreads = 0;
		completedThreads = 0;
		elapsedTime.setText("0 s");
	}
	
	public synchronized void increaseThreads() {
		runningThreads++;
		numberOfThreadsRunning.setText(""+runningThreads);
	}
	
	public synchronized void decrementThreads() {
		runningThreads--;
		completedThreads++;
		numberOfThreadsRunning.setText(""+runningThreads);
		numberOfThreadsCompleted.setText(""+completedThreads);
		semaphore.release();	
	}
	
	class Launcher extends Thread {
		
		ArrayList<WebWorker> workers;
		boolean shouldStop;
		
		private Launcher() {
			shouldStop = false;
		}
		
		@Override
		public void run() {
			resetGUI();
			workers = new ArrayList<>();
			int index = 0;
			long startTime = System.currentTimeMillis();
			runningThreads = 1;
			if(concurrent) {
				semaphore = new Semaphore(Integer.valueOf(threadCount.getText()));
			} else {
				semaphore = new Semaphore(1);
			}
			for (int i = 0; i < model.getRowCount(); i++) {
				try {
					semaphore.acquire();
					increaseThreads();
					workers.add(new WebWorker((String) model.getValueAt(i, 0), WebFrame.this));
					workers.get(index).start();
					index++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			decrementThreads();
			int threadsRunning = Integer.valueOf(numberOfThreadsRunning.getText());
			while (threadsRunning != 0) {
				threadsRunning = Integer.valueOf(numberOfThreadsRunning.getText());
			}
			long difference = (System.currentTimeMillis() - startTime) / 1000;
			elapsedTime.setText(String.valueOf(difference) + " s");
			updateGUI();
		}
		
		public void stopThreads() {
			for (WebWorker worker : workers) {
				worker.stop();
			}
			this.stop();
			resetGUI();
		}
	}
}
