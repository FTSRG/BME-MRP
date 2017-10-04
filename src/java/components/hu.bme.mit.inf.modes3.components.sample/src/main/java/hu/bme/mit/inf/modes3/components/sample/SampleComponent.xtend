package hu.bme.mit.inf.modes3.components.sample

import hu.bme.mit.inf.modes3.components.sample.util.ChangeCounter
import hu.bme.mit.inf.modes3.components.sample.wrapper.ISampleComponentWrapper
import hu.bme.mit.inf.modes3.messaging.messages.enums.SegmentOccupancy
import java.util.concurrent.ConcurrentHashMap
import org.eclipse.xtend.lib.annotations.Accessors

class SampleComponent implements ISampleComponent {

	val ConcurrentHashMap<Integer, ChangeCounter> segmentOccupancyChanges

	@Accessors(PUBLIC_SETTER) var ISampleComponentWrapper sampleComponentWrapper

	new() {
		this.segmentOccupancyChanges = new ConcurrentHashMap
	}

	override onSegmentOccupancyChange(int id, SegmentOccupancy oldValue, SegmentOccupancy newValue) {
		val changeCounter = getNumberOfChanges(id)
		val changes = changeCounter.increment
		if (changes > 2) {
			sampleComponentWrapper.disableSegment(id)
			changeCounter.reset
		}
	}

	private def getNumberOfChanges(int id) {
		var changes = segmentOccupancyChanges.get(id)
		if (changes === null) {
			synchronized (segmentOccupancyChanges) {
				changes = segmentOccupancyChanges.get(id)
				if (changes === null) {
					changes = new ChangeCounter
					segmentOccupancyChanges.put(id, changes)
				}
			}
		}
		return changes
	}

	override run() {
		Thread.currentThread.join
	}

}
