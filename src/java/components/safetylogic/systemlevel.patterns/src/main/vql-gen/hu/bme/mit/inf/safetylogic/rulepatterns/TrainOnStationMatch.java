/**
 * Generated from platform:/resource/hu.bme.mit.inf.modes3.components.safetylogic.systemlevel.patterns/src/main/java/hu/bme/mit/inf/safetylogic/rulepatterns/RulePatterns.vql
 */
package hu.bme.mit.inf.safetylogic.rulepatterns;

import hu.bme.mit.inf.modes3.components.safetylogic.systemlevel.model.RailRoadModel.Train;
import hu.bme.mit.inf.safetylogic.rulepatterns.util.TrainOnStationQuerySpecification;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.impl.BasePatternMatch;
import org.eclipse.viatra.query.runtime.exception.ViatraQueryException;

/**
 * Pattern-specific match representation of the hu.bme.mit.inf.safetylogic.rulepatterns.trainOnStation pattern,
 * to be used in conjunction with {@link TrainOnStationMatcher}.
 * 
 * <p>Class fields correspond to parameters of the pattern. Fields with value null are considered unassigned.
 * Each instance is a (possibly partial) substitution of pattern parameters,
 * usable to represent a match of the pattern in the result of a query,
 * or to specify the bound (fixed) input parameters when issuing a query.
 * 
 * @see TrainOnStationMatcher
 *  @see TrainOnStationProcessor
 * 
 */
@SuppressWarnings("all")
public abstract class TrainOnStationMatch extends BasePatternMatch {
  private Train fT;
  
  private static List<String> parameterNames = makeImmutableList("t");
  
  private TrainOnStationMatch(final Train pT) {
    this.fT = pT;
  }
  
  @Override
  public Object get(final String parameterName) {
    if ("t".equals(parameterName)) return this.fT;
    return null;
  }
  
  public Train getT() {
    return this.fT;
  }
  
  @Override
  public boolean set(final String parameterName, final Object newValue) {
    if (!isMutable()) throw new java.lang.UnsupportedOperationException();
    if ("t".equals(parameterName) ) {
        this.fT = (Train) newValue;
        return true;
    }
    return false;
  }
  
  public void setT(final Train pT) {
    if (!isMutable()) throw new java.lang.UnsupportedOperationException();
    this.fT = pT;
  }
  
  @Override
  public String patternName() {
    return "hu.bme.mit.inf.safetylogic.rulepatterns.trainOnStation";
  }
  
  @Override
  public List<String> parameterNames() {
    return TrainOnStationMatch.parameterNames;
  }
  
  @Override
  public Object[] toArray() {
    return new Object[]{fT};
  }
  
  @Override
  public TrainOnStationMatch toImmutable() {
    return isMutable() ? newMatch(fT) : this;
  }
  
  @Override
  public String prettyPrint() {
    StringBuilder result = new StringBuilder();
    result.append("\"t\"=" + prettyPrintValue(fT));
    return result.toString();
  }
  
  @Override
  public int hashCode() {
    return Objects.hash (fT);
  }
  
  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
        return true;
    if (obj == null) {
        return false;
    }
    if ((obj instanceof TrainOnStationMatch)) {
        TrainOnStationMatch other = (TrainOnStationMatch) obj;
        return Objects.equals(fT, other.fT);
    } else {
        // this should be infrequent
        if (!(obj instanceof IPatternMatch)) {
            return false;
        }
        IPatternMatch otherSig  = (IPatternMatch) obj;
        return Objects.equals(specification(), otherSig.specification()) && Arrays.deepEquals(toArray(), otherSig.toArray());
    }
  }
  
  @Override
  public TrainOnStationQuerySpecification specification() {
    try {
        return TrainOnStationQuerySpecification.instance();
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
  public static TrainOnStationMatch newEmptyMatch() {
    return new Mutable(null);
  }
  
  /**
   * Returns a mutable (partial) match.
   * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
   * 
   * @param pT the fixed value of pattern parameter t, or null if not bound.
   * @return the new, mutable (partial) match object.
   * 
   */
  public static TrainOnStationMatch newMutableMatch(final Train pT) {
    return new Mutable(pT);
  }
  
  /**
   * Returns a new (partial) match.
   * This can be used e.g. to call the matcher with a partial match.
   * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
   * @param pT the fixed value of pattern parameter t, or null if not bound.
   * @return the (partial) match object.
   * 
   */
  public static TrainOnStationMatch newMatch(final Train pT) {
    return new Immutable(pT);
  }
  
  private static final class Mutable extends TrainOnStationMatch {
    Mutable(final Train pT) {
      super(pT);
    }
    
    @Override
    public boolean isMutable() {
      return true;
    }
  }
  
  private static final class Immutable extends TrainOnStationMatch {
    Immutable(final Train pT) {
      super(pT);
    }
    
    @Override
    public boolean isMutable() {
      return false;
    }
  }
}
