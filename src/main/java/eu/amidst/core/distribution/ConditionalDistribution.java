package eu.amidst.core.distribution;


import eu.amidst.core.header.Assignment;
import eu.amidst.core.header.Variable;

import java.util.List;

/**
 * <h2>This interface generalizes the set of possible conditional distributions.</h2>
 *
 * @author Antonio Fernández
 * @version 1.0
 * @since 2014-11-3
 */
public interface ConditionalDistribution extends Distribution {

    /**
     * Gets the set of conditioning variables
     * @return A <code>List</code> object with the set of conditioning variables.
     */
    List<Variable> getConditioningVariables();

    /**
     * Evaluates the conditional distribution given a value of the variable and an assignment of the parents.
     * @param value The value to be evaluated.
     * @param parentsAssignment An <code>Assignment</code> for the parents.
     * @return A <code>double</code> value with the evaluated distribution.
     */
    double getProbability(double value, Assignment parentsAssignment);

    /**
     * Evaluates the conditional distribution given a value of the variable and an assignment of the parents.
     * @param value The value to be evaluated.
     * @param parentsAssignment An <code>Assignment</code> for the parents.
     * @return A <code>double</code> value with the logarithm of the evaluated distribution.
     */
    double getLogProbability(double value, Assignment parentsAssignment);
}
