package hu.bme.mit.inf.master.bbb.strategy;

import static hu.bme.mit.inf.mqtt.common.util.logging.LogManager.logInfoMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import hu.bme.mit.inf.master.bbb.conf.ExpanderControllerConfiguration;
import hu.bme.mit.inf.master.bbb.conf.IControllerConfiguration;
import hu.bme.mit.inf.master.bbb.utils.HexConversionUtil;
import hu.bme.mit.inf.mqtt.common.data.Section;
import hu.bme.mit.inf.mqtt.common.data.SectionStatus;
import hu.bme.mit.inf.mqtt.common.data.Turnout;
import hu.bme.mit.inf.mqtt.common.data.TurnoutStatus;
import io.silverspoon.bulldog.core.Signal;

public class ExpanderController extends AbstractControllerStrategy implements IControllerConfiguration {

	Thread t;
	ExpanderControllerConfiguration controllerConf;
	Map<String, SectionStatus> sectionStatus;
	Map<String, TurnoutStatus> turnoutStatus;

	public ExpanderController() throws Exception {
		controllerConf = new ExpanderControllerConfiguration();

		sectionStatus = new ConcurrentHashMap<String, SectionStatus>();
		for (String sec: controllerConf.getAllSection()) {
			onEnableSection(HexConversionUtil.fromString(sec));
		}
		
		turnoutStatus = new ConcurrentHashMap<String, TurnoutStatus>();
		for (String to: controllerConf.getAllTurnout()) {
			turnoutStatus.put(to, TurnoutStatus.STRAIGHT);
		}

		t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					for(String to: controllerConf.getAllTurnout()) {
						String[] pins = controllerConf.getTurnoutExpander(to);
						if (getPinLevel(pins[0]) == Signal.High) {
							synchronized (turnoutStatus) {
								turnoutStatus.put(to, TurnoutStatus.STRAIGHT);
							}
						}
						if (getPinLevel(pins[1]) == Signal.High) {
							synchronized (turnoutStatus) {
								turnoutStatus.put(to, TurnoutStatus.DIVERGENT);
							}
						}
					}
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	@Override
	protected TurnoutStatus onGetTurnoutStatus(int turnoutId) {
		synchronized (turnoutStatus) {
			return turnoutStatus.get(HexConversionUtil.fromNumber(turnoutId));
		}
	}

	@Override
	protected SectionStatus onGetSectionStatus(int sectionId) {
		return sectionStatus.get(HexConversionUtil.fromNumber(sectionId));
	}

	@Override
	protected void onEnableSection(int sectionId) {
		String[] sectionExpander = controllerConf.getSectionExpander(sectionId);
		this.setPinLevel(sectionExpander[1], Signal.High);
		sectionStatus.put(HexConversionUtil.fromNumber(sectionId), SectionStatus.ENABLED);
	}

	@Override
	protected void onDisableSection(int sectionId) {
		String[] sectionExpander = controllerConf.getSectionExpander(sectionId);
		this.setPinLevel(sectionExpander[1], Signal.Low);
		sectionStatus.put(HexConversionUtil.fromNumber(sectionId), SectionStatus.DISABLED);
	}

	@Override
	public boolean controllerManagesTurnout(Turnout turnout) {
		return controllerConf.controllerManagesTurnout(turnout);
	}

	@Override
	public boolean controllerManagesSection(Section section) {
		return controllerConf.controllerManagesSection(section);
	}
}
