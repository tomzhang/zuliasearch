package io.zulia.server.rest;

import com.cedarsoftware.util.io.JsonWriter;
import com.google.protobuf.util.JsonFormat;
import io.zulia.ZuliaConstants;
import io.zulia.message.ZuliaServiceOuterClass;
import io.zulia.message.ZuliaServiceOuterClass.GetIndexSettingsResponse;
import io.zulia.server.index.ZuliaIndexManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Payam Meyer on 8/7/17.
 * @author pmeyer
 */
@Path(ZuliaConstants.INDEX_URL)
public class IndexResource {

	private ZuliaIndexManager indexManager;

	public IndexResource(ZuliaIndexManager indexManager) {
		this.indexManager = indexManager;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
	public Response get(@Context Response response, @QueryParam(ZuliaConstants.INDEX) String index, @QueryParam(ZuliaConstants.PRETTY) boolean pretty) {

		try {
			StringBuilder responseBuilder = new StringBuilder();

			GetIndexSettingsResponse getIndexSettingsResponse = indexManager
					.getIndexSettings(ZuliaServiceOuterClass.GetIndexSettingsRequest.newBuilder().setIndexName(index).build());

			responseBuilder.append("{");
			responseBuilder.append("\"indexSettings\": ");
			JsonFormat.Printer printer = JsonFormat.printer();
			responseBuilder.append(printer.print(getIndexSettingsResponse.getIndexSettings()));
			responseBuilder.append("}");

			String docString = responseBuilder.toString();

			if (pretty) {
				docString = JsonWriter.formatJson(docString);
			}

			return Response.status(ZuliaConstants.SUCCESS).entity(docString).build();

		}
		catch (Exception e) {
			return Response.status(ZuliaConstants.INTERNAL_ERROR).entity("Failed to get index names: " + e.getMessage()).build();
		}

	}

}
