package hu.bme.mit.inf.modes3.safetylogic.sc.network.handler

import hu.bme.mit.inf.modes3.messaging.mms.handlers.MessageHandler
import hu.bme.mit.inf.modes3.messaging.mms.messages.YakinduReserveToOrBuilder
import hu.bme.mit.inf.modes3.safetylogic.sc.util.ConnectionDirectionTransformator

class IYakinduReserveToHandler implements MessageHandler<YakinduReserveToOrBuilder> {

	private var IYakinduMessageHandler handler

	new(IYakinduMessageHandler _handler) {
		handler = _handler
	}

	override handleMessage(YakinduReserveToOrBuilder message) {
		handler.reserveTo(message.targetID,
			ConnectionDirectionTransformator.toInternalDirection(message.direction))
	}

}
