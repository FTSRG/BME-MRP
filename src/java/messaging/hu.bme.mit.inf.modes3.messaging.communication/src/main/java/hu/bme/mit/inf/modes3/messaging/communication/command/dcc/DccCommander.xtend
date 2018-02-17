package hu.bme.mit.inf.modes3.messaging.communication.command.dcc

import hu.bme.mit.inf.modes3.messaging.communication.command.dcc.interfaces.IDccCommander
import hu.bme.mit.inf.modes3.messaging.messages.command.DccOperationsCommand
import hu.bme.mit.inf.modes3.messaging.messages.enums.DccOperations
import hu.bme.mit.inf.modes3.messaging.mms.MessagingService
import org.eclipse.xtend.lib.annotations.Accessors
import org.slf4j.ILoggerFactory
import org.slf4j.Logger

class DccCommander implements IDccCommander {

	@Accessors(#[PROTECTED_GETTER, PRIVATE_SETTER]) val Logger logger
	var protected MessagingService mms

	new(MessagingService mms, ILoggerFactory factory) {
		this.mms = mms
		this.logger = factory.getLogger(this.class.name)
	}

	override stopEntireRailRoad() {
		mms.sendMessage(new DccOperationsCommand(DccOperations.STOP_OPERATIONS))
		logger.debug('''DccOperationsCommand stop entire railroad sent''')
	}

	override stopTrains() {
		mms.sendMessage(new DccOperationsCommand(DccOperations.STOP_ALL_LOCOMOTIVES))
		logger.debug('''DccOperationsCommand stop trains sent''')
	}

	override startEntireRailRoad() {
		mms.sendMessage(new DccOperationsCommand(DccOperations.NORMAL_OPERATIONS))
		logger.debug('''DccOperationsCommand start entire railroad sent''')
	}

}
