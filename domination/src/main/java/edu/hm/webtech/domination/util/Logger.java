package edu.hm.webtech.domination.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logger logging all given input.
 * 
 * @author Marco Wolff
 * 
 */
public class Logger {

	/**
	 * Date formater for formatting the date.
	 */
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"[H:mm:ss]");
	/**
	 * Name of the class that uses the instance of {@link Logger}.
	 */
	private String className;

	/**
	 * Constructs a new logger for given class name.
	 * 
	 * @param className
	 *            name of the class which uses this {@link Logger} instance.
	 */
	public Logger(String className) {
		this.className = className;
	}

	/**
	 * Logs given message under {@link LogLevel#INFO}:
	 * 
	 * @param message
	 *            that will be logged.
	 */
	public void infoLog(String message) {
		System.out.println(getLogString(LogLevel.INFO, className, message));
	}

	/**
	 * Logs given message under {@link LogLevel#ERROR}:
	 * 
	 * @param message
	 *            that will be logged.
	 */
	public void errorLog(String message) {
		System.out.println(getLogString(LogLevel.ERROR, className, message));
	}

	/**
	 * Displays a log message with given {@link LogLevel}, class name and
	 * message.
	 * 
	 * @param logLevel
	 * @param className
	 * @param message
	 */
	public void log(LogLevel logLevel, String message) {
		System.out.println(getLogString(logLevel, className, message));
	}

	/**
	 * Returned the filled in log template for log messages.
	 * 
	 * @param logLevel
	 *            the specified {@link LogLevel}.
	 * @param className
	 *            the specified class name.
	 * @param message
	 *            the specified message.
	 * @return {@link String} containing the filled in log message template.
	 */
	private String getLogString(LogLevel logLevel, String className,
			String message) {
		return getDate() + " " + logLevel + " " + className + ": " + message;
	}

	/**
	 * @return current {@link Date} as formatted {@link String}.
	 */
	private String getDate() {
		return dateFormatter.format(new Date());
	}

	/**
	 * Describes the log level of the logger.
	 * 
	 * @author Marco Wolff
	 * 
	 */
	public enum LogLevel {
		INFO("[INFO]"), ERROR("[ERROR]");

		/**
		 * Sets the variable that will be returned if {@link #toString()} is
		 * called.
		 * 
		 * @param toString
		 *            the value that will be returned when {@link #toString()}
		 *            is called on this object.
		 */
		private LogLevel(String toString) {
			this.toString = toString;
		}

		private String toString;

		@Override
		public String toString() {
			return toString;
		}
	}

}
