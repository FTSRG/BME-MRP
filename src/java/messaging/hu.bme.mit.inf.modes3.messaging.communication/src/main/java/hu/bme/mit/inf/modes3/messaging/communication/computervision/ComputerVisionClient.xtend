package hu.bme.mit.inf.modes3.messaging.communication.computervision

import hu.bme.mit.inf.modes3.messaging.communication.state.interfaces.ComputerVisionInformation
import hu.bme.mit.inf.modes3.messaging.communication.state.interfaces.ThreeDimensionalPosition
import hu.bme.mit.inf.modes3.messaging.communication.state.interfaces.TwoDimensionalPosition
import hu.bme.mit.inf.modes3.messaging.messages.status.ComputerVisionObjectPositionsMessage
import hu.bme.mit.inf.modes3.messaging.mms.handler.IMessageHandler
import java.util.ArrayList

package class ComputerVisionClient implements IMessageHandler<ComputerVisionObjectPositionsMessage> {

	private var ComputerVisionCallback callback

	new(ComputerVisionCallback controller) {
		callback = controller
	}

	override handleMessage(ComputerVisionObjectPositionsMessage message) {
//		println('''DEBUG : «message»''')
//		println('''MORE DEBUG «message.physicalObjectsMap.keySet.length»''')
//		println('''EOF DEBUG''')
		val information = new ArrayList<ComputerVisionInformation>
		message.physicalObjects.forEach [ name, physicalObject |
			physicalObject.markers.forEach [ name_, marker |
				information.add(new ComputerVisionInformation(marker.name, new TwoDimensionalPosition => [
					x = marker.screenPositions.head.x;
					y = marker.screenPositions.head.y
				], new ThreeDimensionalPosition => [
					x = marker.realposition.x
					y = marker.realposition.y
					z = marker.realposition.z
				], marker.tracked.head))
			]
		]
		callback.onComputerVisionDetection(information, message.timestamp, message.frameIndex)
	}

}
