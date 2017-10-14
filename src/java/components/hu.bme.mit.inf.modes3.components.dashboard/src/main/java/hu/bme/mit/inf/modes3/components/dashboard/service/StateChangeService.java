package hu.bme.mit.inf.modes3.components.dashboard.service;

import java.util.List;

import javax.inject.Inject;

import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.PathParam;
import org.atmosphere.config.service.Singleton;
import org.atmosphere.cpr.MetaBroadcaster;
import org.slf4j.Logger;

import hu.bme.mit.inf.modes3.components.dashboard.main.DashboardManager;
import hu.bme.mit.inf.modes3.components.dashboard.utils.Utils;
import hu.bme.mit.inf.modes3.messaging.communication.state.computervision.interfaces.ComputerVisionInformation;
import hu.bme.mit.inf.modes3.messaging.communication.state.computervision.interfaces.IComputerVisionListener;
import hu.bme.mit.inf.modes3.messaging.communication.state.trackelement.interfaces.ISegmentOccupancyChangeListener;
import hu.bme.mit.inf.modes3.messaging.communication.state.trackelement.interfaces.ISegmentStateChangeListener;
import hu.bme.mit.inf.modes3.messaging.communication.state.trackelement.interfaces.ITurnoutStateChangeListener;
import hu.bme.mit.inf.modes3.messaging.communication.state.train.speed.reference.ITrainReferenceSpeedListener;
import hu.bme.mit.inf.modes3.messaging.messages.enums.SegmentOccupancy;
import hu.bme.mit.inf.modes3.messaging.messages.enums.SegmentState;
import hu.bme.mit.inf.modes3.messaging.messages.enums.TrainDirection;
import hu.bme.mit.inf.modes3.messaging.messages.enums.TurnoutState;

@Singleton
@ManagedService(path = "/ws/state/{source}")
public class StateChangeService implements ISegmentOccupancyChangeListener, ITurnoutStateChangeListener,
		ISegmentStateChangeListener, ITrainReferenceSpeedListener, IComputerVisionListener {

	Logger logger = DashboardManager.INSTANCE.getLoggerFactory().getLogger(StateChangeService.class.getName());

	@Inject
	protected MetaBroadcaster metaBroadcaster;

	@PathParam("source")
	protected String source;

	public StateChangeService() {
		DashboardManager.INSTANCE.getLocator().getTrackElementStateRegistry().setSegmentOccupancyChangeListener(this);
		DashboardManager.INSTANCE.getLocator().getTrackElementStateRegistry().setSegmentStateChangeListener(this);
		DashboardManager.INSTANCE.getLocator().getTrackElementStateRegistry().setTurnoutStateChangeListener(this);
		DashboardManager.INSTANCE.getLocator().getTrainSpeedStateRegistry().addTrainReferenceSpeedListener(this);
		DashboardManager.INSTANCE.getLocator().getComputerVisionCallback().setComputerVisionListener(this);
	}

	@Override
	public void onSegmentOccupancyChange(int id, SegmentOccupancy oldValue, SegmentOccupancy newValue) {
		Utils.sendSegmentOccupancyStateChange(metaBroadcaster, id, newValue);
	}

	@Override
	public void onSegmentStateChange(int id, SegmentState oldValue, SegmentState newValue) {
		Utils.sendSegmentStateChange(metaBroadcaster, id, newValue);
	}

	@Override
	public void onTurnoutStateChange(int id, TurnoutState oldValue, TurnoutState newValue) {
		Utils.sendTurnutStateChange(metaBroadcaster, id, newValue);
	}

	@Override
	public void onTrainReferenceSpeed(int id, int speed, TrainDirection direction) {
		Utils.sendTrainReferenceSpeedChange(metaBroadcaster, id, speed, direction);
	}

	@Override
	public void onComputerVisionDetection(List<ComputerVisionInformation> information, long timestamp,
			long frameindex) {
	
		for(ComputerVisionInformation cvInfo: information) {
			if( cvInfo.getName().contains("SNCF") || cvInfo.getName().contains("Taurus")) {
				Utils.sendComputerVisionState(metaBroadcaster, cvInfo);
			}
		}
	}

}
