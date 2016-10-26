/**
 * Generated from platform:/resource/hu.bme.mit.inf.modes3.components.safetylogic.systemlevel.patterns/src/main/java/hu/bme/mit/inf/safetylogic/rulepatterns/RulePatterns.vql
 */
package hu.bme.mit.inf.safetylogic.rulepatterns;

import hu.bme.mit.inf.safetylogic.rulepatterns.TrainLeftStationMatcher;
import hu.bme.mit.inf.safetylogic.rulepatterns.TrainOnStationMatcher;
import hu.bme.mit.inf.safetylogic.rulepatterns.util.TrainLeftStationQuerySpecification;
import hu.bme.mit.inf.safetylogic.rulepatterns.util.TrainOnStationQuerySpecification;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedPatternGroup;
import org.eclipse.viatra.query.runtime.exception.ViatraQueryException;

/**
 * A pattern group formed of all patterns defined in RulePatterns.vql.
 * 
 * <p>Use the static instance as any {@link org.eclipse.viatra.query.runtime.api.IPatternGroup}, to conveniently prepare
 * a VIATRA Query engine for matching all patterns originally defined in file RulePatterns.vql,
 * in order to achieve better performance than one-by-one on-demand matcher initialization.
 * 
 * <p> From package hu.bme.mit.inf.safetylogic.rulepatterns, the group contains the definition of the following patterns: <ul>
 * <li>trainOnStation</li>
 * <li>trainLeftStation</li>
 * </ul>
 * 
 * @see IPatternGroup
 * 
 */
@SuppressWarnings("all")
public final class RulePatterns extends BaseGeneratedPatternGroup {
  /**
   * Access the pattern group.
   * 
   * @return the singleton instance of the group
   * @throws ViatraQueryException if there was an error loading the generated code of pattern specifications
   * 
   */
  public static RulePatterns instance() throws ViatraQueryException {
    if (INSTANCE == null) {
    	INSTANCE = new RulePatterns();
    }
    return INSTANCE;
  }
  
  private static RulePatterns INSTANCE;
  
  private RulePatterns() throws ViatraQueryException {
    querySpecifications.add(TrainOnStationQuerySpecification.instance());
    querySpecifications.add(TrainLeftStationQuerySpecification.instance());
  }
  
  public TrainOnStationQuerySpecification getTrainOnStation() throws ViatraQueryException {
    return TrainOnStationQuerySpecification.instance();
  }
  
  public TrainOnStationMatcher getTrainOnStation(final ViatraQueryEngine engine) throws ViatraQueryException {
    return TrainOnStationMatcher.on(engine);
  }
  
  public TrainLeftStationQuerySpecification getTrainLeftStation() throws ViatraQueryException {
    return TrainLeftStationQuerySpecification.instance();
  }
  
  public TrainLeftStationMatcher getTrainLeftStation(final ViatraQueryEngine engine) throws ViatraQueryException {
    return TrainLeftStationMatcher.on(engine);
  }
}
