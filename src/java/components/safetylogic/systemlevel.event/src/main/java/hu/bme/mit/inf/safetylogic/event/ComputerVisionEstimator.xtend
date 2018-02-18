package hu.bme.mit.inf.safetylogic.event

import hu.bme.mit.inf.modes3.components.safetylogic.systemlevel.model.RailRoadModel.Point
import hu.bme.mit.inf.modes3.components.safetylogic.systemlevel.model.RailRoadModel.RailRoadElement
import hu.bme.mit.inf.modes3.components.safetylogic.systemlevel.model.RailRoadModel.RailRoadModelFactory

class ComputerVisionEstimator {

	val IModelInteractor model

	new(IModelInteractor model) {
		this.model = model
	}

	def RailRoadElement getElementByCoordinates(double x, double y) {
		val turnouts = model.turnouts
		val segments = model.segments
		val tp = RailRoadModelFactory.eINSTANCE.createPoint => [
			it.x = x
			it.y = y
		]

		val matchingTurnout = turnouts.findFirst [
			rectangle.origin.x < x && x < rectangle.size.width + rectangle.origin.x &&
			rectangle.origin.y < y && y < rectangle.size.height + rectangle.origin.y 
		]
		if (matchingTurnout !== null) {
			return matchingTurnout
		}
		
		segments.minBy[
			it.points.map[distance(it, tp)].min
		]
	}

	def static double distance(Point one, Point other) {
		return Math.sqrt((one.x - other.x) * (one.x - other.x) + (one.y - other.y) * (one.y - other.y))
	}
}
