package hu.bme.mit.inf.modes3.components.safetylogic.componentlevel.sc.comm.dispatcher

import hu.bme.mit.inf.modes3.components.safetylogic.componentlevel.sc.ConnectionDirection

interface ICannotGoToListener {
	def void cannotGoTo(int targetID, ConnectionDirection direction)
}