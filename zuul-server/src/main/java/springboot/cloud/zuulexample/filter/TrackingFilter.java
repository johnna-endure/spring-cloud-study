package springboot.cloud.zuulexample.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/*
게이트웨이로 들어오는 모든 요청에 correlation id를 추가합니다.
 */
@Component
public class TrackingFilter extends ZuulFilter {
    private final String FILTER_TYPE = "pre";
    private final int FILTER_ORDER = 1;
    private final boolean SHOULD_FILTER = true;
    private final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    @Override
    public String filterType() {
        return FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        if(!hasCorrelationId(requestContext)) {
            String correlationId = generateCorrelationId();
            requestContext.addZuulRequestHeader("correlation-id", correlationId);
            logger.info("correlation-id generated in tracking filter: {}.", correlationId);
            return null;
        }
        logger.info("correlation-id already exists : {}.", requestContext.getZuulRequestHeaders().get("correlation-id"));
        return null;
    }

    private boolean hasCorrelationId(RequestContext requestContext) {
        return requestContext.getZuulRequestHeaders().containsKey("correlation-id");
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

}
