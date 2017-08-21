package io.zulia.server.connection.server.handler;

import io.zulia.message.ZuliaServiceOuterClass.CreateIndexRequest;
import io.zulia.message.ZuliaServiceOuterClass.CreateIndexResponse;
import io.zulia.server.index.ZuliaIndexManager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InternalCreateIndexServerRequest extends ServerRequestHandler<CreateIndexResponse, CreateIndexRequest> {

	private final static Logger LOG = Logger.getLogger(InternalCreateIndexServerRequest.class.getSimpleName());

	public InternalCreateIndexServerRequest(ZuliaIndexManager indexManager) {
		super(indexManager);
	}

	@Override
	protected CreateIndexResponse handleCall(ZuliaIndexManager indexManager, CreateIndexRequest request) throws Exception {
		return indexManager.internalCreateIndex(request);
	}

	@Override
	protected void onError(Exception e) {
		LOG.log(Level.SEVERE, "Failed to handle internal create index", e);
	}
}
