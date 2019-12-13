package net.mcorp.common.debugger;

import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;

public class DebuggerTrace{

	public final JFrame frame;
	public final JPanel panel;
	
	public final JTextArea message;
	public final PrintStream printStream = new PrintStream(new OutputStream() {

		@Override
		public void write(int arg0) throws IOException {
			message.append(((char)arg0)+"");
		}
		
	});
	
	public final Throwable throwable;
	
	public DebuggerTrace(Throwable throwable) {
		if(GraphicsEnvironment.isHeadless())
			throw new HeadlessException("[CRITICAL] @ DebuggerTrace(Throwable)\n  - Could not generate Trace, Environment is headless...");
		this.throwable = throwable;
		if(throwable == null)
			throw new NullPointerException("[CRITICAL] @ DebuggerTrace(Throwable)\n - Could not generate Trace, throwable parameter was set to null object.");
		
		int width = 480;
		int height = 240;
		
		this.frame = new JFrame();
		this.frame.setSize(width,height);
		this.frame.setResizable(false);
		this.frame.setTitle("Debug Trace");
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.panel = new JPanel(null);
		
		this.message = new JTextArea();
		this.message.setEditable(false);
		this.throwable.printStackTrace(printStream);
		JScrollPane scroll_message = new JScrollPane(this.message);
		scroll_message.setBounds(0, 0, width, height-50);
		scroll_message.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.panel.add(scroll_message);
		
		JPanel controls = new JPanel(null);
		controls.setBounds(0,height-50,width,20);
		this.panel.add(controls);
		
		JButton btn_throw = new JButton("Log in Console");
		btn_throw.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					throw throwable;
				}catch(Throwable t) {
					t.printStackTrace();
				}
			}
			
		});
		btn_throw.setBounds(5,0,width/2-10,20);
		controls.add(btn_throw);
		
		JButton btn_dump = new JButton("Dump to File");
		btn_dump.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int user_action = fileChooser.showSaveDialog(null);
				if(user_action == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						FileWriter fw = new FileWriter(file);
						throwable.printStackTrace(new PrintStream(new OutputStream() {

							@Override
							public void write(int arg0) throws IOException {
								fw.write(arg0);
							}
							
						}));
						fw.flush();
						fw.close();
					}catch(Exception e1) {
					}
				}else {
					
				}
				
			}
			
		});
		btn_dump.setBounds(width/2-5,0,width/2-10,20);
		controls.add(btn_dump);
		
		this.frame.add(panel);
		this.frame.setVisible(true);
	}
	
}
