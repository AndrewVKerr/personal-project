package net.projectio.utils;

public class Debugger {
	
	public enum DebugLevel{
		None(0),
		Log(1),
		Warn(2),
		Fatal(3);
		public final int lvl;
		private DebugLevel(int lvl) {
			this.lvl = lvl;
		}
	}
	
	public static DebugLevel level = DebugLevel.Fatal;
	
	public static void println(String str) {
		if(level.lvl >= DebugLevel.Log.lvl) {
			System.out.println("[>=Log] "+str);
		}
	}
	
	public static void println(DebugLevel level, String str) {
		if(level.lvl <= Debugger.level.lvl) {
			System.out.println("["+level.toString()+"] "+str);
		}
	}
	
	public static void printException(Exception e) {
		if(level.lvl >= DebugLevel.Warn.lvl) {
			System.err.print("[>=Warn] ");
			e.printStackTrace();
		}
	}
	
	public static void printException(DebugLevel level, Exception e) {
		if(level.lvl <= Debugger.level.lvl) {
			System.err.println("["+level.toString()+"] ");
			e.printStackTrace();
		}
	}
	
}
