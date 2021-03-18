package springboot.cloud.zuulexample.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

@Component
public class ResponseFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        String CORRELATION_id = "correlation-id";
        RequestContext context = RequestContext.getCurrentContext();
        if(!context.getResponse().containsHeader(CORRELATION_id)) {
            context.getResponse().addHeader(CORRELATION_id, context.getZuulRequestHeaders().get(CORRELATION_id));
        }
        return null;
    }
}
