/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.rudi.facet.apimaccess.api.gateway;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.wso2.carbon.apimgt.rest.api.gateway.DeployResponse;
import org.wso2.carbon.apimgt.rest.api.gateway.Error;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-12-18T10:15:20.914180600+01:00[Europe/Paris]")
@Validated
@Tag(name = "redeploy-api", description = "the redeploy-api API")
public interface RedeployApiApi {

	default Optional<NativeWebRequest> getRequest() {
		return Optional.empty();
	}

	/**
	 * POST /redeploy-api : Redeploy the API in the gateway This operation is used to re deploy an API in the gateway. If the Tenant domain is not
	 * provided carbon.super will be picked as the Tenant domain.
	 *
	 * @param apiName      Name of the API (required)
	 * @param version      version of the API (required)
	 * @param tenantDomain Tenant Domain of the API (optional)
	 * @return OK. API successfully deployed in the Gateway. (status code 200) or Bad Request. Invalid request or validation error (status code 400) or
	 *         Not Found. Requested API does not exist. (status code 404)
	 */
	@Operation(operationId = "redployAPI", summary = "Redeploy the API in the gateway", responses = {
			@ApiResponse(responseCode = "200", description = "OK. API successfully deployed in the Gateway. ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeployResponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request. Invalid request or validation error ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
			@ApiResponse(responseCode = "404", description = "Not Found. Requested API does not exist. ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(method = RequestMethod.POST, value = "/api/am/gateway/v2/redeploy-api", produces = {
			"application/json" })
	default ResponseEntity<DeployResponse> redployAPI(
			@NotNull @Parameter(name = "apiName", description = "Name of the API ", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "apiName", required = true) String apiName,
			@NotNull @Parameter(name = "version", description = "version of the API ", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "version", required = true) String version,
			@Parameter(name = "tenantDomain", description = "Tenant Domain of the API ", in = ParameterIn.QUERY) @Valid @RequestParam(value = "tenantDomain", required = false) String tenantDomain)
			throws Exception {
		getRequest().ifPresent(request -> {
			for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
				if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
//                    String exampleString = "{ \"jsonPayload\" : \"jsonPayload\", \"deployStatus\" : \"DEPLOYED\" }";
//                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
					break;
				}
			}
		});
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

}
