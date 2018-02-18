package hu.bme.mit.inf.modes3.components.safetylogic.componentlevel.sc.wrapper

import hu.bme.mit.gamma.impl.interfaces.TurnoutControlInterface
import hu.bme.mit.inf.modes3.messaging.communication.state.trackelement.interfaces.ITrackElementStateRegistry
import hu.bme.mit.inf.modes3.messaging.communication.state.trackelement.interfaces.ITurnoutStateChangeListener
import hu.bme.mit.inf.modes3.messaging.messages.enums.TurnoutState
import org.slf4j.ILoggerFactory
import org.slf4j.Logger

class TurnoutDirectionNotifier implements ITurnoutStateChangeListener {

	val Logger logger
	val int turnoutId
	val TurnoutControlInterface.Provided turnoutToBeNotified

	new(ILoggerFactory factory, int turnoutId, TurnoutControlInterface.Provided turnoutToBeNotified, ITrackElementStateRegistry stateRegistry) {
		this.logger = factory.getLogger(class.name)
		this.turnoutId = turnoutId
		this.turnoutToBeNotified = turnoutToBeNotified
		stateRegistry.registerTurnoutStateChangeListener = this
	}

	override onTurnoutStateChange(int id, TurnoutState oldValue, TurnoutState newValue) {
		if(id == turnoutId) {
			logger.debug('''Turnout (ID=«id») is «newValue»''')
			switch (newValue) {
				case STRAIGHT:
					turnoutToBeNotified.raiseTurnoutStraight
				case DIVERGENT:
					turnoutToBeNotified.raiseTurnoutDivergent
				default: {
				}
			}
		}
	}

}
