package org.yakindu.scr.turnout;

import java.util.List;
import org.yakindu.scr.IStatemachine;

public interface ITurnoutStatemachine extends IStatemachine {

	public interface SCITurnoutControlProvided {
	
		public void raiseTurnoutStraight();
		
		public void raiseTurnoutDivergent();
		
	}
	
	public SCITurnoutControlProvided getSCITurnoutControlProvided();
	
	public interface SCIProtocolProvidedTop {
	
		public void raiseReserve();
		
		public void raiseCanGo();
		
		public void raiseCannotGo();
		
		public void raiseRelease();
		
	}
	
	public SCIProtocolProvidedTop getSCIProtocolProvidedTop();
	
	public interface SCIProtocolRequiredTop {
	
		public boolean isRaisedReserve();
		
		public boolean isRaisedCanGo();
		
		public boolean isRaisedCannotGo();
		
		public boolean isRaisedRelease();
		
	public List<SCIProtocolRequiredTopListener> getListeners();
	}
	
	public interface SCIProtocolRequiredTopListener {
	
		public void onReserveRaised();
		public void onCanGoRaised();
		public void onCannotGoRaised();
		public void onReleaseRaised();
		}
	
	public SCIProtocolRequiredTop getSCIProtocolRequiredTop();
	
	public interface SCIProtocolProvidedStraight {
	
		public void raiseReserve();
		
		public void raiseCanGo();
		
		public void raiseCannotGo();
		
		public void raiseRelease();
		
	}
	
	public SCIProtocolProvidedStraight getSCIProtocolProvidedStraight();
	
	public interface SCIProtocolRequiredStraight {
	
		public boolean isRaisedReserve();
		
		public boolean isRaisedCanGo();
		
		public boolean isRaisedCannotGo();
		
		public boolean isRaisedRelease();
		
	public List<SCIProtocolRequiredStraightListener> getListeners();
	}
	
	public interface SCIProtocolRequiredStraightListener {
	
		public void onReserveRaised();
		public void onCanGoRaised();
		public void onCannotGoRaised();
		public void onReleaseRaised();
		}
	
	public SCIProtocolRequiredStraight getSCIProtocolRequiredStraight();
	
	public interface SCIProtocolProvidedDivergent {
	
		public void raiseReserve();
		
		public void raiseCanGo();
		
		public void raiseCannotGo();
		
		public void raiseRelease();
		
	}
	
	public SCIProtocolProvidedDivergent getSCIProtocolProvidedDivergent();
	
	public interface SCIProtocolRequiredDivergent {
	
		public boolean isRaisedReserve();
		
		public boolean isRaisedCanGo();
		
		public boolean isRaisedCannotGo();
		
		public boolean isRaisedRelease();
		
	public List<SCIProtocolRequiredDivergentListener> getListeners();
	}
	
	public interface SCIProtocolRequiredDivergentListener {
	
		public void onReserveRaised();
		public void onCanGoRaised();
		public void onCannotGoRaised();
		public void onReleaseRaised();
		}
	
	public SCIProtocolRequiredDivergent getSCIProtocolRequiredDivergent();
	
	public interface SCITrainProvided {
	
		public void raiseOccupy();
		
		public void raiseUnoccupy();
		
	}
	
	public SCITrainProvided getSCITrainProvided();
	
	public interface SCIDirection {
	
		public static final long tOP = 3;
	
		public static final long sTRAIGHT = 4;
	
		public static final long dIVERGENT = 5;
	
		public long getTOP();
		
		public long getSTRAIGHT();
		
		public long getDIVERGENT();
		
	}
	
	public SCIDirection getSCIDirection();
	
}
