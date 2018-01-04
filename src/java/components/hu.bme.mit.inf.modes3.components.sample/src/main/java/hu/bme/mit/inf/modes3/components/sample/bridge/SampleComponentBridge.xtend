package hu.bme.mit.inf.modes3.components.sample.bridge

import hu.bme.mit.inf.modes3.components.sample.ISampleComponent
import hu.bme.mit.inf.modes3.messaging.communication.common.AbstractCommunicationComponent
import hu.bme.mit.inf.modes3.messaging.communication.state.trackelement.interfaces.ISegmentOccupancyChangeListener
import hu.bme.mit.inf.modes3.messaging.messages.enums.SegmentOccupancy
import hu.bme.mit.inf.modes3.messaging.messages.enums.SegmentState
import hu.bme.mit.inf.modes3.messaging.mms.MessagingService
import org.slf4j.ILoggerFactory

class SampleComponentBridge extends AbstractCommunicationComponent implements ISampleComponentBridge, ISegmentOccupancyChangeListener {

	val ISampleComponent sampleComponent

	new(ISampleComponent sampleComponent, MessagingService messagingService, ILoggerFactory factory) {
		super(messagingService, factory)
		this.sampleComponent = sampleComponent
		this.sampleComponent.sampleComponentBridge = this
		locator.trackElementStateRegistry.segmentOccupancyChangeListener = this
	}

	override run() {
		sampleComponent.run
	}

	override onSegmentOccupancyChange(int id, SegmentOccupancy oldValue, SegmentOccupancy newValue) {
		sampleComponent.onSegmentOccupancyChange(id, oldValue, newValue)
	}

	override disableSegment(int segmentId) {
		locator.trackElementCommander.sendSegmentCommand(segmentId, SegmentState.DISABLED)
	}

}
