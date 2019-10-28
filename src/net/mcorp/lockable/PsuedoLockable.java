package net.mcorp.lockable;

public abstract class PsuedoLockable {
	
	/**
	 * This exception is thrown under three different circumstances:
	 * <ul>
	 * 	<li>A attempt was made to <b>write</b> to a locked variable.</li>
	 * 	<li>A attempt was made to <b>read</b> from a locked variable.</li>
	 * 	<li>A attempt was made to <b>generate</b> a new {@linkplain LockController} object when one already existed.</li>
	 * </ul>
	 * @author Andrew Kerr
	 */
	public static final class LockedVariableException extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = 3290603145482897338L;
		
		private LockedVariableException(String message) {
			super(message);
		}
		
		private LockedVariableException(String location, String flag) {
			super("["+location+":"+flag.toUpperCase().replace(' ', '_')+"] Some code attempted to read/write to/from a Variable that was under the protection of a PsuedoLockable object.");
		}
		
	}
	
	/**
	 * A instance of this class is stored within all {@linkplain PsuedoLockable} objects, its simple yet effective purpose is to control the lock
	 * of the object its contained within. Without this object you cannot edit any of the variables that are under its protection: If there is an
	 * attempt to edit a variable under this objects lock then a {@linkplain LockedVariableException} will be raised.
	 * @author Andrew Kerr
	 */
	public static final class LockController{
		
		/**
		 * This variable controls the writing to variables under the protection of this {@linkplain LockController}.<br>
		 * Attempting to write to a variable through a <b><i><u>PROTECTED</u></i></b> set method whilst this variable is set to TRUE
		 * will result in a RuntimeException called {@linkplain LockedVariableException}.<br>
		 * @apiNote
		 * 		PROTECTED - A set method that calls {@linkplain PsuedoLockable#write()} before making a change to the variable.<br>
		 * 		It should also be noted that the variable in question should be a private variable so that it cannot be interacted with.
		 */
		private boolean write_lock = false;
		
		/**
		 * This method will return {@linkplain LockController#write_lock}.
		 * @return {@linkplain Boolean} - True/False
		 */
		public synchronized boolean write_lock() { return this.write_lock; };
		
		/**
		 * This method will change the state of {@linkplain LockController#write_lock}.
		 * @param state - {@linkplain Boolean} - See write_lock for details on what this does.
		 */
		public synchronized void write_lock(boolean state) { this.write_lock = state; };
		
		/**
		 * This variable controls the reading of variables under the protection of this {@linkplain LockController}.
		 * Attempting to read from a variable through a <b><i><u>PROTECTED</u></i></b> get method whilst this variable is set to TRUE
		 * will result in a RuntimeException called {@linkplain LockedVariableException}.<br>
		 * @apiNote
		 * 		PROTECTED - A get method that calls {@linkplain PsuedoLockable#read()} before returning the variable.<br>
		 * 		It should also be noted that the variable in question should be a private variable so that it cannot be interacted with.
		 */
		private boolean read_lock = false;
		
		/**
		 * This method will return {@linkplain LockController#read_lock}.
		 * @return {@linkplain Boolean} - True/False
		 */
		public synchronized boolean read_lock() { return this.read_lock; };
		
		/**
		 * This method will change the state of {@linkplain LockController#read_lock}.
		 * @param state - {@linkplain Boolean} - See read_lock for details on what this does.
		 */
		public synchronized void read_lock(boolean state) { this.read_lock = state; };
		
		private LockController() {}; //Prevent this object from being able to be generated outside of this file.
		
	}
	
	/**
	 * This class is a template for variables that will be protected by a lock object.
	 * @author Andrew Kerr
	 *
	 * @param <P> - The class of the object that will be protected.
	 */
	public static abstract class LockableVariable<P>{
		
		private PsuedoLockable lock;
		
		/**
		 * Creates a new LockeableVariable and assignees it a lock object.
		 * @param lock
		 */
		public LockableVariable(PsuedoLockable lock) {
			this.lock = lock;
		}
		
		/**
		 * This method is only called internally and should therefore be set to private in any extending classes as to not leak this method to
		 * unsecured objects. 
		 */
		protected abstract P getCall();
		
		/**
		 * This method will retrieve the object contained within this object.
		 * @return An object that is contained within this object.
		 */
		public synchronized P get() {
			lock.read(Thread.currentThread().getStackTrace()[2]);
			return this.getCall();
		}
		
		/**
		 * This method is only called internally and should therefore be set to private in any extending classes as to not leak this method to
		 * unsecured objects. 
		 */
		protected abstract void setCall(P object);
		
		/**
		 * This method will retrieve the object contained within this object.
		 * @param P - A object that should be set as the value of the internal variable.
		 */
		public synchronized void set(P object) {
			lock.write(Thread.currentThread().getStackTrace()[2]);
			this.setCall(object);
		}
		
	}
	
	/**
	 * This objects lock controller, defaults to null.
	 */
	private LockController controller = null;
	
	/**
	 * This method checks if a internal variable contains a null value or a {@linkplain LockController} object.
	 * @return {@linkplain Boolean} - True if not null value, False if null value.
	 */
	public synchronized final boolean doesControllerExist() {
		return (controller != null);
	}
	
	/**
	 * Returns the internal state of the read variable contained within the {@linkplain LockController} object or false if the controller doesn't exist.
	 * @return {@linkplain Boolean} - True if reading is locked.
	 */
	public synchronized final boolean isReadingLocked() {
		if(doesControllerExist())
			return false;
		return controller.read_lock;
	}
	
	/**
	 * Returns the internal state of the write variable contained within the {@linkplain LockController} object or false if the controller doesn't exist.
	 * @return {@linkplain Boolean} - True if writing is locked.
	 */
	public synchronized final boolean isWritingLocked() {
		if(doesControllerExist())
			return false;
		return controller.write_lock;
	}
	
	/**
	 * This method will execute {@linkplain PsuedoLockable#isWritingLocked()}, if that returns true then a {@linkplain LockedVariableException} will be thrown.
	 * @param caller - {@linkplain StackTraceElement} - The caller of this method. ( Thread.currentThread().getStackTrace[1] )
	 */
	public synchronized final void write(StackTraceElement caller) {
		if(this.isWritingLocked()) {
			throw new LockedVariableException(caller.getClassName()+"."+caller.getMethodName(),"Writing locked");
		}
	}
	
	/**
	 * This method will execute {@linkplain PsuedoLockable#isReadingLocked()}, if that returns true then a {@linkplain LockedVariableException} will be thrown.
	 * @param caller - {@linkplain StackTraceElement} - The caller of this method. ( Thread.currentThread().getStackTrace[1] )
	 */
	public synchronized final void read(StackTraceElement caller) {
		if(this.isReadingLocked()) {
			throw new LockedVariableException(caller.getClassName()+"."+caller.getMethodName(),"Reading locked");
		}
	}
	
	/**
	 * This method will generate a new {@linkplain LockController} object and will return it.
	 * @apiNote This method can only be ran once, any subsequent executions will result in a {@linkplain LockedVariableException} being thrown.
	 * @return {@linkplain LockController} - The controller for this objects lock.
	 */
	public synchronized final LockController generateLock() {
		if(this.controller == null) {
			LockController lc = new LockController();
			this.controller = lc;
			return lc;
		}else {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			StackTraceElement e = stackTraceElements[2];
			throw new LockedVariableException("["+e.getClassName()+"."+e.getMethodName()+":CONTROLLER_EXISTS] This object has already generated a Lock controller.");
		}
	}
	
}
