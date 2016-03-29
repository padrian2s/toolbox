/*
 *
 *
 *    Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.
 *    See the NOTICE file distributed with this work for additional information regarding copyright ownership.
 *    The ASF licenses this file to You under the Apache License, Version 2.0 (the "License"); you may not use
 *    this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software distributed under the License is
 *    distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and limitations under the License.
 *
 *
 */

package eu.amidst.core.models;


import eu.amidst.core.datastream.filereaders.arffFileReader.ARFFDataReader;
import eu.amidst.core.variables.Variables;
import eu.amidst.core.variables.Variable;
import org.junit.Assert;
import org.junit.Test;


/**
 * Testing the DAG class
 */
public class DAGTest {

    @Test
    public void testingDAG() {

        ARFFDataReader reader = new ARFFDataReader();
        reader.loadFromFile("datasetsTests/dataWeka/contact-lenses.arff");
        Variables variables = new Variables(reader.getAttributes());
        DAG dag = new DAG(variables);
        DAG dag2 = new DAG(variables);

        variables = dag.getVariables();
        Variable A = variables.getVariableById(0);
        Variable B = variables.getVariableById(1);
        Variable C = variables.getVariableById(2);
        Variable D = variables.getVariableById(3);
        Variable E = variables.getVariableById(4);

        /* test with a cyclic dag */

        dag.getParentSet(A).addParent(C);
        dag.getParentSet(B).addParent(A);
        dag.getParentSet(C).addParent(B);
        dag.getParentSet(D).addParent(B);
        dag.getParentSet(E).addParent(B);

        Assert.assertTrue(dag.containCycles());

        /* remove the cycle and test again */

        dag.getParentSet(A).removeParent(C);
        dag.getParentSet(C).addParent(A);

        Assert.assertFalse(dag.containCycles());


        /* test the parent set*/

        Assert.assertEquals(2, dag.getParentSet(C).getNumberOfParents());
        Assert.assertEquals(0, dag.getParentSet(A).getNumberOfParents());

        /* test if dag and dag2 (no arcs) are equals*/

        Assert.assertFalse(dag.equals(dag2));

        /* define dag2 as a copy of dag and test again */

        dag2.getParentSet(B).addParent(A);
        dag2.getParentSet(C).addParent(B);
        dag2.getParentSet(D).addParent(B);
        dag2.getParentSet(E).addParent(B);
        dag2.getParentSet(C).addParent(A);

        Assert.assertTrue(dag.equals(dag2));
    }
}
