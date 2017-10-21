package hu.bme.mit.inf.modes3.components.barrier

import hu.bme.mit.inf.modes3.components.barrier.bridge.ITrackSupervisorBridge
import hu.bme.mit.inf.modes3.messaging.messages.enums.SegmentOccupancy
import java.util.Set
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import org.eclipse.xtend.lib.annotations.Accessors

class TrackSupervisor implements ITrackSupervisor {

	val ConcurrentMap<Integer, SegmentOccupancy> supervisedSections
	@Accessors(PUBLIC_SETTER) var ITrackSupervisorBridge supervisorBridge

	new(Set<Integer> supervisedSections) {
		this.supervisedSections = new ConcurrentHashMap<Integer, SegmentOccupancy>
		supervisedSections.forEach[this.supervisedSections.put(it, SegmentOccupancy.FREE)]
	}

	override onSegmentOccupancyChange(int id, SegmentOccupancy oldValue, SegmentOccupancy newValue) {
		val previousStatus = supervisedSections.get(id)

		if (previousStatus !== null && previousStatus !== newValue) {
			supervisedSections.put(id, newValue)

			if (newValue == SegmentOccupancy.OCCUPIED) {
				supervisorBridge.sendBarrierMessage("closed")
			} else if (supervisedSections.entrySet.forall[it.value == SegmentOccupancy.FREE]) {
				supervisorBridge.sendBarrierMessage("opened")
			}
		}
	}
}
