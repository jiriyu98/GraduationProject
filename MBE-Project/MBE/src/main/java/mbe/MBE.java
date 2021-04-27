/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mbe;

import mbe.common.CustomizedBipartiteGraph;
import mbe.common.Edge;
import mbe.common.Vertex;
import mbe.source.CustomizedTextInputFormat;
import mbe.utils.SerializableUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.List;

/**
 * @ClassName: DynamicBC
 * @author: Jiri Yu
 * @date: 2021/3/23 
 */
public class MBE {
	public static void main(String[] args) throws Exception {
		// localhost:8081
		// it needs local environment, that is why we include flink-dist.
		StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

//		DataStream<Transaction> transactions = env
//			.addSource(new TransactionSource())
//			.name("new edges added : (v, v)");

//		DataStream<Alert> alerts = transactions
//			.keyBy(Transaction::getAccountId)
//			.process(new DynamicBC())
//			.name("Dynamic BC - output BC");

//		alerts
//				.addSink(new AlertSink())
//				.name("send-alerts");
//
//		env.execute("Dynamic BC");

		// Step 1, create Graph and insert vertices.
		CustomizedBipartiteGraph customizedBipartiteGraph = new CustomizedBipartiteGraph();
		List<Vertex> verticesL = SerializableUtils.deserializePojos("case1Vertices100L.csv", Vertex.class);
		List<Vertex> verticesR = SerializableUtils.deserializePojos("case1Vertices100R.csv", Vertex.class);
		customizedBipartiteGraph.insertAllVertices(verticesL);
		customizedBipartiteGraph.insertAllVertices(verticesR);

		// Step, create source node, import edge from deserialization
		DataStream<Edge> source = env.readFile(new CustomizedTextInputFormat(), SerializableUtils.directory+"case1Edges100.csv");
		source.print();

		env.execute("Dynamic BC");
	}
}
