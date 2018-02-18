package hu.bme.mit.inf.modes3.components.safetylogic.componentlevel.sc.app

import hu.bme.mit.inf.modes3.components.safetylogic.componentlevel.sc.SafetyLogic
import hu.bme.mit.inf.modes3.components.safetylogic.componentlevel.sc.bridge.SafetyLogicBridge
import hu.bme.mit.inf.modes3.components.safetylogic.componentlevel.sc.comm.dispatcher.JsonDispatcherFactory
import hu.bme.mit.inf.modes3.components.safetylogic.componentlevel.sc.comm.dispatcher.JsonMessageDispatcher
import hu.bme.mit.inf.modes3.components.safetylogic.componentlevel.sc.wrapper.YakinduProtocolDispatcher
import hu.bme.mit.inf.modes3.messaging.communication.factory.MessagingServiceFactory
import hu.bme.mit.inf.modes3.messaging.communication.factory.TopicFactory
import hu.bme.mit.inf.modes3.messaging.messages.command.SegmentCommand
import hu.bme.mit.inf.modes3.messaging.messages.status.SegmentOccupancyMessage
import hu.bme.mit.inf.modes3.messaging.messages.status.SegmentStateMessage
import hu.bme.mit.inf.modes3.utils.common.jopt.ArgumentDescriptorWithParameter
import hu.bme.mit.inf.modes3.utils.common.jopt.ArgumentRegistry
import hu.bme.mit.inf.modes3.utils.conf.layout.LayoutConfiguration
import org.slf4j.impl.SimpleLogger
import org.slf4j.impl.SimpleLoggerFactory

class Main {

	def static void main(String[] args) {
		System.setProperty(SimpleLogger.LOG_FILE_KEY, "System.out")
		//System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "trace")

		val loggerFactory = new SimpleLoggerFactory
		val logger = loggerFactory.getLogger(Main.name)

		val registry = new ArgumentRegistry(loggerFactory)
		registry.registerArgumentWithOptions(new ArgumentDescriptorWithParameter("id", "ID of the turnout", String))
		registry.registerArgumentWithOptions(new ArgumentDescriptorWithParameter("address", "The address of the transport server", String))
		registry.registerArgumentWithOptions(new ArgumentDescriptorWithParameter("port", "The port used by the transport server", Integer))

		registry.parseArguments(args);

		val hostname = registry.getParameterStringValue("id")
		val turnoutID = Integer.valueOf(hostname.split("\\.").head.replace('t', ''))

		val controlledSections = LayoutConfiguration.INSTANCE.getControlledSections(turnoutID)
		val turnoutSegmentItself = LayoutConfiguration.INSTANCE.getSegmentIdsOfTurnout(turnoutID)

		val sectionTopics = controlledSections.map[TopicFactory::createSegmentTopics(it, #{SegmentCommand, SegmentStateMessage, SegmentOccupancyMessage})].flatten.toSet
		val turnoutOccupancyTopic = turnoutSegmentItself.map[TopicFactory::createSegmentTopics(it, #{SegmentOccupancyMessage})].flatten.toSet

		val turnoutTopics = TopicFactory::createTurnoutTopics(turnoutID)

		val topics = newHashSet
		topics.addAll(turnoutTopics)
		topics.addAll(sectionTopics)
		topics.addAll(turnoutOccupancyTopic)

		val communicationStack = MessagingServiceFactory::createStackForTopics(registry, loggerFactory, topics)
		logger.debug('''Subscribed for railway track topics: «topics»''')

		val yakinduStack = JsonDispatcherFactory::createMQTTStackWithJSON(registry, loggerFactory)
		val yakinduDispatcher = (yakinduStack.dispatcher as JsonMessageDispatcher)
		val yakinduProtocolDispatcher = new YakinduProtocolDispatcher(loggerFactory)
		yakinduDispatcher.canGoToListener = yakinduProtocolDispatcher
		yakinduDispatcher.cannotGoToListener = yakinduProtocolDispatcher
		yakinduDispatcher.releaseToListener = yakinduProtocolDispatcher
		yakinduDispatcher.reserveToListener = yakinduProtocolDispatcher

		val safetyLogic = new SafetyLogic(turnoutID, loggerFactory)
		val slWrapper = new SafetyLogicBridge(safetyLogic, communicationStack, yakinduStack, yakinduProtocolDispatcher, loggerFactory)
		slWrapper.run // The component will run on this thread
	}

}
