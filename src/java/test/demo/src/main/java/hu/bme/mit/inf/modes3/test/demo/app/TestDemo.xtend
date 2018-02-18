package hu.bme.mit.inf.modes3.test.demo.app

import hu.bme.mit.inf.modes3.messaging.communication.common.AbstractCommunicationComponent
import hu.bme.mit.inf.modes3.messaging.mms.MessagingService
import java.util.Set
import org.slf4j.ILoggerFactory

class TestDemo extends AbstractCommunicationComponent {

	val ILoggerFactory factory
	val Set<AbstractCommunicationComponent> demos

	new(MessagingService messagingService, ILoggerFactory factory) {
		super(messagingService, factory)
		this.factory = factory
		this.demos = #{new hu.bme.mit.inf.modes3.test.demo.SegmentsDemo(locator, factory), new hu.bme.mit.inf.modes3.test.demo.SectionsDemo(locator, factory),
			new hu.bme.mit.inf.modes3.test.demo.TurnoutsDemo(locator, factory), new hu.bme.mit.inf.modes3.test.demo.SendAllStatusDemo(locator, factory),
			new hu.bme.mit.inf.modes3.test.demo.TrainsDemo(locator, factory)}
		}

		// The 'main' method of the sample component
		override run() {
			demos.forEach[it.run]
		}

	}
	