/**
 * Generated from platform:/resource/hu.bme.mit.inf.modes3.components.safetylogic.systemlevel.patterns/src/main/java/hu/bme/mit/inf/safetylogic/patterns/SafetyPatterns.vql
 */
package hu.bme.mit.inf.safetylogic.patterns;

import hu.bme.mit.inf.modes3.components.safetylogic.systemlevel.model.RailRoadModel.RailRoadElement;
import hu.bme.mit.inf.safetylogic.patterns.util.NextSectionQuerySpecification;
import java.util.Arrays;
import java.util.List;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.impl.BasePatternMatch;
import org.eclipse.viatra.query.runtime.exception.ViatraQueryException;

/**
 * Pattern-specific match representation of the hu.bme.mit.inf.safetylogic.patterns.nextSection pattern,
 * to be used in conjunction with {@link NextSectionMatcher}.
 * 
 * <p>Class fields correspond to parameters of the pattern. Fields with value null are considered unassigned.
 * Each instance is a (possibly partial) substitution of pattern parameters,
 * usable to represent a match of the pattern in the result of a query,
 * or to specify the bound (fixed) input parameters when issuing a query.
 * 
 * @see NextSectionMatcher
 * @see NextSectionProcessor
 * 
 */
@SuppressWarnings("all")
public abstract class NextSectionMatch extends BasePatternMatch {
  private RailRoadElement fOld;
  
  private RailRoadElement fCurrent;
  
  private RailRoadElement fNext;
  
  private static List<String> parameterNames = makeImmutableList("Old", "Current", "Next");
  
  private NextSectionMatch(final RailRoadElement pOld, final RailRoadElement pCurrent, final RailRoadElement pNext) {
    this.fOld = pOld;
    this.fCurrent = pCurrent;
    this.fNext = pNext;
  }
  
  @Override
  public Object get(final String parameterName) {
    if ("Old".equals(parameterName)) return this.fOld;
    if ("Current".equals(parameterName)) return this.fCurrent;
    if ("Next".equals(parameterName)) return this.fNext;
    return null;
  }
  
  public RailRoadElement getOld() {
    return this.fOld;
  }
  
  public RailRoadElement getCurrent() {
    return this.fCurrent;
  }
  
  public RailRoadElement getNext() {
    return this.fNext;
  }
  
  @Override
  public boolean set(final String parameterName, final Object newValue) {
    if (!isMutable()) throw new java.lang.UnsupportedOperationException();
    if ("Old".equals(parameterName) ) {
    	this.fOld = (RailRoadElement) newValue;
    	return true;
    }
    if ("Current".equals(parameterName) ) {
    	this.fCurrent = (RailRoadElement) newValue;
    	return true;
    }
    if ("Next".equals(parameterName) ) {
    	this.fNext = (RailRoadElement) newValue;
    	return true;
    }
    return false;
  }
  
  public void setOld(final RailRoadElement pOld) {
    if (!isMutable()) throw new java.lang.UnsupportedOperationException();
    this.fOld = pOld;
  }
  
  public void setCurrent(final RailRoadElement pCurrent) {
    if (!isMutable()) throw new java.lang.UnsupportedOperationException();
    this.fCurrent = pCurrent;
  }
  
  public void setNext(final RailRoadElement pNext) {
    if (!isMutable()) throw new java.lang.UnsupportedOperationException();
    this.fNext = pNext;
  }
  
  @Override
  public String patternName() {
    return "hu.bme.mit.inf.safetylogic.patterns.nextSection";
  }
  
  @Override
  public List<String> parameterNames() {
    return NextSectionMatch.parameterNames;
  }
  
  @Override
  public Object[] toArray() {
    return new Object[]{fOld, fCurrent, fNext};
  }
  
  @Override
  public NextSectionMatch toImmutable() {
    return isMutable() ? newMatch(fOld, fCurrent, fNext) : this;
  }
  
  @Override
  public String prettyPrint() {
    StringBuilder result = new StringBuilder();
    result.append("\"Old\"=" + prettyPrintValue(fOld) + ", ");
    
    result.append("\"Current\"=" + prettyPrintValue(fCurrent) + ", ");
    
    result.append("\"Next\"=" + prettyPrintValue(fNext)
    );
    return result.toString();
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((fOld == null) ? 0 : fOld.hashCode());
    result = prime * result + ((fCurrent == null) ? 0 : fCurrent.hashCode());
    result = prime * result + ((fNext == null) ? 0 : fNext.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
    	return true;
    if (!(obj instanceof NextSectionMatch)) { // this should be infrequent
    	if (obj == null) {
    		return false;
    	}
    	if (!(obj instanceof IPatternMatch)) {
    		return false;
    	}
    	IPatternMatch otherSig  = (IPatternMatch) obj;
    	if (!specification().equals(otherSig.specification()))
    		return false;
    	return Arrays.deepEquals(toArray(), otherSig.toArray());
    }
    NextSectionMatch other = (NextSectionMatch) obj;
    if (fOld == null) {if (other.fOld != null) return false;}
    else if (!fOld.equals(other.fOld)) return false;
    if (fCurrent == null) {if (other.fCurrent != null) return false;}
    else if (!fCurrent.equals(other.fCurrent)) return false;
    if (fNext == null) {if (other.fNext != null) return false;}
    else if (!fNext.equals(other.fNext)) return false;
    return true;
  }
  
  @Override
  public NextSectionQuerySpecification specification() {
    try {
    	return NextSectionQuerySpecification.instance();
    } catch (ViatraQueryException ex) {
     	// This cannot happen, as the match object can only be instantiated if the query specification exists
     	throw new IllegalStateException (ex);
    }
  }
  
  /**
   * Returns an empty, mutable match.
   * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
   * 
   * @return the empty match.
   * 
   */
  public static NextSectionMatch newEmptyMatch() {
    return new Mutable(null, null, null);
  }
  
  /**
   * Returns a mutable (partial) match.
   * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
   * 
   * @param pOld the fixed value of pattern parameter Old, or null if not bound.
   * @param pCurrent the fixed value of pattern parameter Current, or null if not bound.
   * @param pNext the fixed value of pattern parameter Next, or null if not bound.
   * @return the new, mutable (partial) match object.
   * 
   */
  public static NextSectionMatch newMutableMatch(final RailRoadElement pOld, final RailRoadElement pCurrent, final RailRoadElement pNext) {
    return new Mutable(pOld, pCurrent, pNext);
  }
  
  /**
   * Returns a new (partial) match.
   * This can be used e.g. to call the matcher with a partial match.
   * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
   * @param pOld the fixed value of pattern parameter Old, or null if not bound.
   * @param pCurrent the fixed value of pattern parameter Current, or null if not bound.
   * @param pNext the fixed value of pattern parameter Next, or null if not bound.
   * @return the (partial) match object.
   * 
   */
  public static NextSectionMatch newMatch(final RailRoadElement pOld, final RailRoadElement pCurrent, final RailRoadElement pNext) {
    return new Immutable(pOld, pCurrent, pNext);
  }
  
  private static final class Mutable extends NextSectionMatch {
    Mutable(final RailRoadElement pOld, final RailRoadElement pCurrent, final RailRoadElement pNext) {
      super(pOld, pCurrent, pNext);
    }
    
    @Override
    public boolean isMutable() {
      return true;
    }
  }
  
  private static final class Immutable extends NextSectionMatch {
    Immutable(final RailRoadElement pOld, final RailRoadElement pCurrent, final RailRoadElement pNext) {
      super(pOld, pCurrent, pNext);
    }
    
    @Override
    public boolean isMutable() {
      return false;
    }
  }
}
