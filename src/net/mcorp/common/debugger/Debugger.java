package net.mcorp.common.debugger;

import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.SwingConstants;

public final class Debugger {
	
	public static enum DebugLevel{
		/**
		 * These messages are fatal to the thread they reside in.
		 */
		FATAL(System.err),
		/**
		 * These messages are critical to the object they reside in.
		 */
		CRITICAL(System.err),
		/**
		 * These messages are important but not gonna prevent something from working.
		 */
		WARN(System.err),
		/**
		 * These messages are notes left in the program.
		 */
		INFO(System.out,false),
		/**
		 * These messages depict things that still need to be done.
		 */
		TODO(System.out,false),
		/**
		 * These messages are used as debug/testing info.
		 */
		DEBUG(System.out),
		/**
		 * This is a special flag, when added to {@linkplain MCorpDebugger#currentlyShownLevels} it will display the exact class, method, and file line of the
		 * every message as a means to locate and delete them when not necessary. This should not be added unless needed.
		 */
		_LOCATE(System.out,false);
		
		public final OutputStream out;
		public boolean allowOUT = true;
		private DebugLevel(OutputStream out) {
			this.out = out;
		}
		private DebugLevel(OutputStream out,boolean allowOUT) {
			this.out = out;
			this.allowOUT = allowOUT;
		}
	}
	
	public static final Debugger instance = new Debugger();
	
	public final ArrayList<DebugLevel> currentlyEnabledDebugLevels = new ArrayList<DebugLevel>();
	public final ArrayList<DebugLevel> currentlyDisabledDebugLevels = new ArrayList<DebugLevel>();
	
	public final JFrame debuggerFrame;
	public final JList<Throwable> jlist_throwables;
	
	public final JList<DebugLevel> disabledLevels;
	public final JList<DebugLevel> enabledLevels;
	
	private Debugger() {
		if(GraphicsEnvironment.isHeadless()) {
			debuggerFrame = null;
			jlist_throwables = null;
			disabledLevels = null;
			enabledLevels = null;
		}else {
			debuggerFrame = new JFrame();
			debuggerFrame.setSize(720,480);
			debuggerFrame.setTitle("MCorpDebugger");
			debuggerFrame.setResizable(false);
			debuggerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //TODO: SWITCH TO DO NOTHING
			
			int row_width = debuggerFrame.getWidth()/3;
			int col_height = debuggerFrame.getHeight()-24;
			
			JPanel panel = new JPanel(null);
			
			JPanel controls = new JPanel(null);
			controls.setBounds(0, 0, row_width, col_height);
			
			int controls_row_width = row_width/2-5;
			
			controls.add(this.createLabel(5, 5, controls_row_width, 20, "Disabled"));
			
			disabledLevels = new JList<DebugLevel>(); 
			JScrollPane scrollDisabled = new JScrollPane(disabledLevels);
			scrollDisabled.setBounds(5, 25, controls_row_width, 150);
			
			controls.add(this.createLabel(controls_row_width+5, 5, controls_row_width, 20, "Enabled"));
			
			enabledLevels = new JList<DebugLevel>();			
			JScrollPane scrollEnabled = new JScrollPane(enabledLevels);
			scrollEnabled.setBounds(controls_row_width+5, 25, controls_row_width, 150);
			
			JButton btn_enable = new JButton("->");
			btn_enable.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					DebugLevel level = disabledLevels.getSelectedValue();
					if(level == null)
						return;
					currentlyEnabledDebugLevels.add(level);
					currentlyDisabledDebugLevels.remove(level);
					
					//Recalculate the enable/disabled lists.
					
					ListModel<DebugLevel> enabled_debug = disabledLevels.getModel();
					ArrayList<DebugLevel> debug_levels = new ArrayList<DebugLevel>();
					for(int i = 0; i < enabled_debug.getSize(); i++) {
						DebugLevel level2 = enabled_debug.getElementAt(i);
						if(level2 == null || level2 == level)
							continue;
						debug_levels.add(level2);
					}
					disabledLevels.setListData(debug_levels.toArray(new DebugLevel[] {}));
					
					ListModel<DebugLevel> disabled_debug = enabledLevels.getModel();
					debug_levels = new ArrayList<DebugLevel>();
					for(int i = 0; i < disabled_debug.getSize(); i++) {
						DebugLevel level2 = disabled_debug.getElementAt(i);
						if(level2 == null)
							continue;
						debug_levels.add(level2);
					}
					debug_levels.add(level);
					enabledLevels.setListData(debug_levels.toArray(new DebugLevel[] {}));
					Debugger.println(DebugLevel.DEBUG, "Testing the moving of items. ENABLED");
				}
				
			});
			btn_enable.setBounds(15, 175, controls_row_width-20, 20);
			controls.add(btn_enable);
			
			JButton btn_disable = new JButton("<-");
			btn_disable.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					DebugLevel level = enabledLevels.getSelectedValue();
					if(level == null)
						return;
					currentlyEnabledDebugLevels.remove(level);
					currentlyDisabledDebugLevels.add(level);
					
					//Recalculate the enable/disabled lists.
					
					ListModel<DebugLevel> enabled_debug = enabledLevels.getModel();
					ArrayList<DebugLevel> debug_levels = new ArrayList<DebugLevel>();
					for(int i = 0; i < enabled_debug.getSize(); i++) {
						DebugLevel level2 = enabled_debug.getElementAt(i);
						if(level2 == null || level2 == level)
							continue;
						debug_levels.add(level2);
					}
					enabledLevels.setListData(debug_levels.toArray(new DebugLevel[] {}));
					
					ListModel<DebugLevel> disabled_debug = disabledLevels.getModel();
					debug_levels = new ArrayList<DebugLevel>();
					for(int i = 0; i < disabled_debug.getSize(); i++) {
						DebugLevel level2 = disabled_debug.getElementAt(i);
						if(level2 == null)
							continue;
						debug_levels.add(level2);
					}
					debug_levels.add(level);
					disabledLevels.setListData(debug_levels.toArray(new DebugLevel[] {}));
					Debugger.println(DebugLevel.DEBUG, "Testing the moving of items. DISABLED");
				}
				
			});
			btn_disable.setBounds(controls_row_width+15, 175, controls_row_width-20, 20);
			controls.add(btn_disable);
			
			JButton btn_clear = new JButton("Clear");
			btn_clear.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Debugger.this.jlist_throwables.setListData(new Throwable[] {});
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();
					addEntryThrowable("[CLEARED] @ "+dtf.format(now)+"\n");
				}
				
			});
			btn_clear.setBounds(15, 197, row_width-30, 20);
			controls.add(btn_clear);
			
			JButton btn_dump_to_file = new JButton("Dump to file");
			btn_dump_to_file.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					int user_action = fileChooser.showSaveDialog(null);
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();
					if(user_action == JFileChooser.APPROVE_OPTION) {
						addEntryThrowable("[APPROVED DUMP] @ "+dtf.format(now)+"\n");
						File file = fileChooser.getSelectedFile();
						try {
							FileWriter fw = new FileWriter(file);
							ListModel<Throwable> throwables = Debugger.this.jlist_throwables.getModel();
							for(int index = 0; index < throwables.getSize(); index++) {
								fw.write(throwables.getElementAt(index).toString());
							}
							//TODO:
							//fw.write(Debugger.this.debuggerTextArea.getText());
							fw.flush();
							fw.close();
						}catch(Exception e1) {
							addEntryThrowable("[DUMP EXCEPTION] @ "+dtf.format(now)+"\n  - "+e1.getLocalizedMessage()+"\n");
						}
					}else {
						addEntryThrowable("[CANCELED DUMP] @ "+dtf.format(now)+"\n");
					}
					
				}
				
			});
			btn_dump_to_file.setBounds(15, 220, row_width-30, 20);
			controls.add(btn_dump_to_file);
			
			JButton btn_trace = new JButton("Trace Exception");
			btn_trace.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Throwable throwable = jlist_throwables.getSelectedValue();
					if(throwable == null)
						return;
					new DebuggerTrace(throwable);
				}
				
			});
			btn_trace.setBounds(15, 243, row_width-30, 20);
			controls.add(btn_trace);
			
			controls.add(scrollDisabled);
			controls.add(scrollEnabled);
			panel.add(controls);
			
			jlist_throwables = new JList<Throwable>();
			JScrollPane scroll = new JScrollPane(jlist_throwables);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setBounds(row_width+3, 3, row_width*2-10, col_height-10);
			panel.add(scroll);
			
			debuggerFrame.add(panel);
		}
	}
	
	public static void startup() {
		Debugger.instance.startupCALL(DebugLevel.values(),new DebugLevel[] { DebugLevel._LOCATE });
	}
	
	private boolean started = false;
	public boolean started() { return started; };
	
	/**
	 * Setup and start the debugger, by not calling this method or the global startup method will result
	 * in no debug information and no debugger window.
	 * @param active - {@linkplain DebugLevel}[] - An array of DebugLevel's that will be enabled immediately.
	 * @param deactive - {@linkplain DebugLevel}[] - An array of DebugLevel's that will be disabled immediately.
	 * @apiNote When setting the active list, adding the _LOCATE value to such a list will result in no data.
	 */
	public void startupCALL(DebugLevel[] active, DebugLevel[] deactive) {
		for(DebugLevel level : active) {
			if(level == DebugLevel._LOCATE)
				continue;
			currentlyEnabledDebugLevels.add(level);
		}
		for(DebugLevel level : deactive) {
			if(currentlyEnabledDebugLevels.contains(level))
				continue;
			currentlyDisabledDebugLevels.add(level);
		}
		this.enabledLevels.setListData(currentlyEnabledDebugLevels.toArray(new DebugLevel[] {}));
		this.disabledLevels.setListData(currentlyDisabledDebugLevels.toArray(new DebugLevel[] {}));
		debuggerFrame.setVisible(true);
	}

	public Throwable addEntryThrowable(Throwable t) {
		ArrayList<Throwable> throwables = new ArrayList<Throwable>();
		throwables.add(t);
		ListModel<Throwable> throwables_ = this.jlist_throwables.getModel();
		for(int index = 0; index < throwables_.getSize(); index++) {
			Throwable throwable = throwables_.getElementAt(index);
			if(throwable != null)
				throwables.add(throwable);
		}
		this.jlist_throwables.setListData(throwables.toArray(new Throwable[] {}));
		return t;
	}
	
	private Throwable addEntryThrowable(String t) {
		return addEntryThrowable(new Exception(t) {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return this.getLocalizedMessage();
			}
		});
	}
	
	private JLabel createLabel(int x, int y, int width, int height, String string) {
		JLabel label = new JLabel(string,SwingConstants.CENTER);
		label.setLocation(x, y);
		label.setSize(width, height);
		return label;
	}
	
	public static void println(DebugLevel level, String message) {
		instance.println(3, level, message);
	}
	
	/**
	 * 
	 * @param level - {@linkplain DebugLevel} - The debug level at which this object should be logged at.
	 * @param throwable - {@linkplain Throwable} - The throwable object that should be debugged out.
	 * @return {@linkplain Throwable} - A new throwable object that encapsulates the provided throwable object or the original throwable object (SEE APINOTES)
	 * @apiNote If the method {@linkplain #startupCALL(DebugLevel[], DebugLevel[])} or {@linkplain Debugger#startup()} has not been called then this method will return the orignal throwable object.
	 */
	public static Throwable printThrowable(DebugLevel level, Throwable throwable) {
		if(instance.started == false)
			return throwable;
		Throwable t = instance.println(3, level, "Suppressed: "+throwable.getLocalizedMessage());
		t.addSuppressed(throwable);
		return t;
	}
	
	public Throwable println(int stackTraceIndex, DebugLevel level, String message) {
		if(instance.started == false)
			return null;
		if(level == DebugLevel._LOCATE)
			throw new RuntimeException("[WARN] @ MCorpDebugger.println(DebugLevel,String)\n  - Cannot use _LOCATE as a DebugLevel. _LOCATE's purpose is as a flag for "
					+ "tracing messages that cannot be found, and as such cannot be used to debug the info.");
		StackTraceElement ste = Thread.currentThread().getStackTrace()[stackTraceIndex];
		if(this.currentlyEnabledDebugLevels.contains(level)) {
			String className = ste.getClassName();
			try {
				Class<?> class_ = Class.forName(className);
				if(class_ != null) {
					className = class_.getSimpleName();
				}
			}catch(Exception e) {} //No big deal if exception is thrown just use stacktrace elements classname.
			String trace = "["+level.toString()+"] @ "+className+"."+ste.getMethodName()+"(...)\n  - "+message;
			OutputStream out = DebugLevel._LOCATE.out;
			if(out instanceof PrintStream) {
				if(out == System.out || out == System.err) {
					if(level.allowOUT) {
						((PrintStream)out).println(trace);	
					}
				}else {
					((PrintStream)out).println(trace);
				}
				return addEntryThrowable(trace+"\n");
			}else {
				try {
					out.write(trace.getBytes());
					out.flush();
					return addEntryThrowable(trace+"\n");
				}catch(Exception e) {
					System.out.println("--[MCORPDEBUGGER:FAILED_OUTPUT_WRITE]--");
					System.out.println(trace);
					return addEntryThrowable("--[MCORPDEBUGGER:FAILED_OUTPUT_WRITE]--\n"+trace+"\n");
				}
			}
		}else {
			/**
			 * Display class name, method name, and line number if _LOCATE is set.
			 */
			if(this.currentlyEnabledDebugLevels.contains(DebugLevel._LOCATE)) {
				String trace = "["+level.name()+"_LOCATE] @ File: "+ste.getFileName()+", Class: "+ste.getClassName()+", Method: "+ste.getMethodName()+", Line#: "+ste.getLineNumber()+"\n  - "+message;
				OutputStream out = level.out;
				if(out instanceof PrintStream) {
					if(out == System.out || out == System.err) {
						if(level.allowOUT) {
							((PrintStream)out).println(trace);	
						}
					}else {
						((PrintStream)out).println(trace);
					}
					return addEntryThrowable(trace+"\n");
				}else {
					try {
						out.write(trace.getBytes());
						out.flush();
						return addEntryThrowable(trace+"\n");
					}catch(Exception e) {
						System.out.println("--[MCORPDEBUGGER:FAILED_OUTPUT_WRITE]--");
						System.out.println(trace);
						return addEntryThrowable("--[MCORPDEBUGGER:FAILED_OUTPUT_WRITE]--\n"+trace+"\n");
					}
				}
			}
		}
		return null;
	}
	
}
