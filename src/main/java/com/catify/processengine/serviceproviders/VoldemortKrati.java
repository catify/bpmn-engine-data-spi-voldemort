/**
 * *******************************************************
 * Copyright (C) 2013 catify <info@catify.com>
 * *******************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.catify.processengine.serviceproviders;

import com.catify.processengine.core.data.dataobjects.DataObjectSPI;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;
import voldemort.server.VoldemortConfig;
import voldemort.server.VoldemortServer;
import voldemort.versioning.Versioned;

/**
 * @author chris
 * 
 */
public class VoldemortKrati extends DataObjectSPI {

	StoreClient<String, Object> client;

	public VoldemortKrati() {
		this.implementationId = "voldemort";
		
		// init server
		VoldemortConfig config = VoldemortConfig.loadFromVoldemortHome("data", "config");
		
		VoldemortServer server = new VoldemortServer(config);
		server.start();
		
		// init client
		String bootstrapUrl = "tcp://localhost:6666";
		StoreClientFactory factory = new SocketStoreClientFactory(
				new ClientConfig().setBootstrapUrls(bootstrapUrl));

		// create a client that executes operations on a single krati store
		this.client = factory.getStoreClient("test-krati");
	}

	@Override
	public void saveObject(String uniqueProcessId, String objectId, String instanceId,
			Object payloadObject) {
		if (payloadObject == null) {payloadObject = "NO PAYLOAD";}
		Versioned<Object> value = new Versioned<Object>(payloadObject);
		
		this.client.put(getObjectKey(uniqueProcessId, objectId, instanceId), value);
	}

	@Override
	public Object loadObject(String uniqueProcessId, String objectId, String instanceId) {
		Versioned<Object> value = this.client.get(getObjectKey(uniqueProcessId, objectId, instanceId));
		return value.getValue();
	}
	
	@Override
	public void deleteObject(String uniqueProcessId, String objectId,
			String instanceId) {
		this.client.delete(getObjectKey(uniqueProcessId, objectId, instanceId));
	}

	public StoreClient<String, Object> getClient() {
		return client;
	}

	public void setClient(StoreClient<String, Object> client) {
		this.client = client;
	}
}
