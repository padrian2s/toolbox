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

package eu.amidst.flinklink.core.io;


import eu.amidst.core.datastream.Attribute;
import eu.amidst.core.datastream.DataInstance;
import eu.amidst.core.datastream.DataOnMemory;
import eu.amidst.flinklink.core.data.DataFlink;
import junit.framework.TestCase;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by andresmasegosa on 2/9/15.
 */
public class DataFlinkLoaderTest extends TestCase {

    public static void test1() throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataFlink<DataInstance> dataFlink = DataFlinkLoader.loadDataFromFile(env,
                "./datasets/dataFlink/test_not_modify/SmallDataSet.arff", false);
        DataSet<DataInstance> data = dataFlink.getDataSet();

        data.print();

        List<DataInstance> instanceList = data.collect();

        assertEquals(16, instanceList.size());
        List<String> names = Arrays.asList("A", "B", "C", "D", "E", "G");
        List<Integer> states = Arrays.asList(2, 3, 2, 2, 2, -1);

        List<Attribute> atts = dataFlink.getAttributes().getListOfNonSpecialAttributes();
        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i));
            assertEquals(atts.get(i).getName(), names.get(i));
            assertEquals(atts.get(i).getNumberOfStates(), states.get(i).intValue());
        }
    }


    public static void test2() throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataFlink<DataInstance> dataFlink = DataFlinkLoader.loadDataFromFile(env,
                "./datasets/dataFlink/test_not_modify/SmallDataSet.arff", false);
        DataSet<DataOnMemory<DataInstance>> data = dataFlink.getBatchedDataSet(3);

        data.print();

        List<DataOnMemory<DataInstance>> batchList = data.collect();

        int size = 0;
        for (DataOnMemory<DataInstance> dataInstanceDataBatch : batchList) {
            System.out.println("Batch :" + dataInstanceDataBatch.getList().size());
            size += dataInstanceDataBatch.getList().size();
        }
        assertEquals(16, size);

        List<DataInstance> instanceList = batchList.stream().flatMap(batch -> batch.getList().stream()).collect(Collectors.toList());

        assertEquals(16, instanceList.size());
        List<String> names = Arrays.asList("A", "B", "C", "D", "E", "G");
        List<Integer> states = Arrays.asList(2, 3, 2, 2, 2, -1);

        List<Attribute> atts = dataFlink.getAttributes().getListOfNonSpecialAttributes();
        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i));
            assertEquals(atts.get(i).getName(), names.get(i));
            assertEquals(atts.get(i).getNumberOfStates(), states.get(i).intValue());
        }
    }

    public static void test3() throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataFlinkWriterTest.test1();
        DataFlink<DataInstance> dataFlink = DataFlinkLoader.loadDataFromFolder(env, "./datasets/dataFlink/tmp.arff", false);
        DataSet<DataInstance> data = dataFlink.getDataSet();

        data.print();

        List<DataInstance> instanceList = data.collect();

        assertEquals(16, instanceList.size());
        List<String> names = Arrays.asList("A", "B", "C", "D", "E", "G");
        List<Integer> states = Arrays.asList(2, 3, 2, 2, 2, -1);

        List<Attribute> atts = dataFlink.getAttributes().getListOfNonSpecialAttributes();
        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i));
            assertEquals(atts.get(i).getName(), names.get(i));
            assertEquals(atts.get(i).getNumberOfStates(), states.get(i).intValue());
        }

    }

}