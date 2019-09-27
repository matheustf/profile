package com.gym.fitpass.profile.config;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

import static org.owasp.esapi.Logger.EVENT_UNSPECIFIED;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Component
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final Logger LOG = ESAPI.getLogger(this.getClass());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
            LOG.info(EVENT_UNSPECIFIED, "=========================== request begin ===============================================");
            LOG.info(EVENT_UNSPECIFIED, "URI         : " + request.getURI());
            LOG.info(EVENT_UNSPECIFIED, "Method      : " + request.getMethod());
            LOG.info(EVENT_UNSPECIFIED, "Headers     : " + request.getHeaders());
            if(!Objects.toString(request.getHeaders().get(CONTENT_TYPE)).contains(MULTIPART_FORM_DATA_VALUE))
                LOG.info(EVENT_UNSPECIFIED, "Request body: " + new String(body, "UTF-8"));
            LOG.info(EVENT_UNSPECIFIED, "=========================== request end ================================================");
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
            LOG.info(EVENT_UNSPECIFIED, "============================ response begin ==========================================");
            LOG.info(EVENT_UNSPECIFIED, "Status code  : " + response.getStatusCode());
            LOG.info(EVENT_UNSPECIFIED, "Status text  : " + response.getStatusText());
            LOG.info(EVENT_UNSPECIFIED, "Headers      : " + response.getHeaders());
            if(response.getStatusCode() != NOT_FOUND && response.getStatusCode() != NO_CONTENT)
            	LOG.info(EVENT_UNSPECIFIED, "Response body: " + StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            LOG.info(EVENT_UNSPECIFIED, "============================ response end =============================================");
    }
}
