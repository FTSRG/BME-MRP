/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.bme.mit.inf.modes3.components.gpiomanager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author zsoltmazlo
 */
public final class Gpio {

	public enum Level {
		LOW, HIGH
	}

	public enum Direction {
		OUT, IN
	}

	public interface InputStateListener {

		public void levelStateChanged(Level newLevel);

	}

	private static final String TAG = "GPIO";

	private static final String GPIO_BASE_FOLDER = "/sys/class/gpio/";

	private final int _pin;

	private final Direction _direction;

	private final List<InputStateListener> listeners = Collections.synchronizedList(new ArrayList<>());

	private String _gpioFolder;

	private volatile Level _level;

	private volatile boolean _isInputListenerRunning = false;

	private Timer _inputListener;

	private TimerTask _inputListenerTask;

	private static CommandWriter WRITER;

	private static CommandReader READER;

	public Gpio(int pin, Direction direction) throws IOException, GpioNotConfiguratedException {
		this._pin = pin;
		this._direction = direction;

		// export gpio pin first
		try {
			WRITER.executeCommand(String.valueOf(_pin), GPIO_BASE_FOLDER + "export");
		} catch (Exception ex) {
			Logger.error(TAG, "Pin export failed! Pin: %d", _pin);

			// if GPIO pin exporting failed, the cause of the failure is the
			// fact that the GPIO has already exported previously
			// in this case, we should unexport it and try to export it again
			// if unexport fails also, there should be something wrong
			try {
				WRITER.executeCommand(String.valueOf(_pin), GPIO_BASE_FOLDER + "unexport");
				WRITER.executeCommand(String.valueOf(_pin), GPIO_BASE_FOLDER + "export");
			} catch (Exception ex2) {
				Logger.error(TAG, "Pin re-export failed! Pin: %d", _pin);
				throw ex2;
			}
		}

		this._gpioFolder = GPIO_BASE_FOLDER + "gpio" + this._pin + "/";

		// set direction of pin
		try {
			WRITER.executeCommand(this._direction.toString().toLowerCase(), _gpioFolder + "direction");
		} catch (Exception ex) {
			Logger.error(TAG, "Pin direction setup failed! Pin: %d", _pin);
			throw ex;
		}

		switch (this._direction) {
		case IN:
			// setup edge detection as well
			WRITER.executeCommand("both", _gpioFolder + "edge");

			setupInputChangeListening();
			break;
		case OUT:
			// set level immediately to low
			this.setLevel(Level.LOW);
			break;
		}

	}

	public final void setLevel(Level level) throws IOException {
		try {
			WRITER.executeCommand(level == Level.HIGH ? "1" : "0", _gpioFolder + "value");
			this._level = level;
		} catch (IOException ex) {
			Logger.error(TAG, "Level setting failed! Pin: %d", _pin);
			throw ex;
		}
	}

	public final Level getLevel() {
		return _level;
	}

	public final void invertLevel() throws IOException {
		if (_level == Level.HIGH) {
			setLevel(Level.LOW);
		} else {
			setLevel(Level.HIGH);
		}
	}

	public final void impulse(int milliseconds, boolean nonBlocking) throws InterruptedException, IOException {
		if (nonBlocking) {
			Thread impulse = new Thread(() -> {
				try {
					this.invertLevel();
					Thread.sleep(milliseconds);
					this.invertLevel();
				} catch (InterruptedException | IOException ex) {
					Logger.error(TAG, "Exception during impulse: %s.", ex.getMessage());
				}

			});
			impulse.start();
		} else {
			invertLevel();
			Thread.sleep(milliseconds);
			invertLevel();
		}
	}

	public final void addInputStateListener(InputStateListener listener) {
		this.listeners.add(listener);
	}

	public final void removeInputStateListener(InputStateListener listener) {
		this.listeners.remove(listener);
	}

	public static void setWriter(CommandWriter writer) {
		WRITER = writer;
	}

	public static void setReader(CommandReader reader) {
		READER = reader;
	}

	private class InputStateChangeListenerTask extends TimerTask {

		@Override
		public void run() {
			try {
				Level newLvl = READER.getGpioValue(_gpioFolder + "value");
//				try (BufferedReader reader = new BufferedReader(new FileReader(_gpioFolder + "value"))) {
//					Level newLevel = reader.readLine().equals("1") ? Level.HIGH : Level.LOW;
				if (newLvl != null && !newLvl.equals(_level)) {
					Logger.info(TAG, "Pin state changed! Pin: %d Value: %s", _pin, newLvl.toString());
					_level = newLvl;
					listeners.stream().forEach((listener) -> {
						try {
							listener.levelStateChanged(_level);
						} catch (Exception ex) {
							Logger.error(TAG, "Error while notifying the InputStateChangeListener (%s)",
									listener.toString());
							Logger.error(TAG, ex.getMessage());
						}
					});
				}
//				}

			} catch (FileNotFoundException ex) {
				Logger.error(TAG, "InputStateChangeListenerTask is aborted, because pin #%d's value file not found!",
						_pin);
				Logger.error(TAG, ex.getMessage());
			} catch (IOException ex) {
				Logger.error(TAG, "InputStateChangeListenerTask is aborted, IO error during file read on pin #%d",
						_pin);
				Logger.error(TAG, ex.getMessage());
			} catch (Exception ex) {
				ex.printStackTrace();
				Logger.error(TAG, "Something is wrong here: " + ex.getMessage());
			}
		}

	}

	private void setupInputChangeListening() throws IOException {
		if (!_isInputListenerRunning) {
			_isInputListenerRunning = true;
			_inputListener = new Timer();
			_inputListenerTask = new InputStateChangeListenerTask();
			_inputListener.schedule(_inputListenerTask, 0, 50);
		}
	}

	public final void cleanup() throws IOException, InterruptedException {
		// unexport gpio pin
		try {
			if (_isInputListenerRunning) {
				Logger.info(TAG, "Stopping listening service for pin #%d", _pin);
				_inputListenerTask.cancel();
				_inputListener.purge();
				_inputListener.cancel();
				_isInputListenerRunning = false;
				Logger.info(TAG, "Listening service for pin #%d stopped.", _pin);
			}
			WRITER.executeCommand(String.valueOf(_pin), GPIO_BASE_FOLDER + "unexport");
		} catch (IOException ex) {
			Logger.error(TAG, "Pin unexport failed! Pin: %d", _pin);
			throw ex;
		}
	}

}
