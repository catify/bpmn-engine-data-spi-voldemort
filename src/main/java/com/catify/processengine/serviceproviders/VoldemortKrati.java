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
		super();
		
		this.implementationId = "voldemort";

		// init server
//		VoldemortConfig config = VoldemortConfig.loadFromVoldemortHome("voldemort");
		VoldemortConfig config = VoldemortConfig.loadFromEnvironmentVariable();
		
		System.out.println("Voldemort config: " + config);
		
		VoldemortServer server = new VoldemortServer(config);
		server.start();

		// init client
		String bootstrapUrl = "tcp://localhost:6666";
		StoreClientFactory factory = new SocketStoreClientFactory(
				new ClientConfig().setBootstrapUrls(bootstrapUrl));

		// create a client that executes operations on a single store
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
