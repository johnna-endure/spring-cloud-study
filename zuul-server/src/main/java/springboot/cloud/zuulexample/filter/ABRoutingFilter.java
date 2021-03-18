package springboot.cloud.zuulexample.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Random;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

@Component
public class ABRoutingFilter extends ZuulFilter {

    @Autowired
    private ProxyRequestHelper helper;
    private final Logger logger = LoggerFactory.getLogger(ABRoutingFilter.class);


    @Override
    public String filterType() {
        return ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SIMPLE_HOST_ROUTING_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        boolean isMemberService = context.get(SERVICE_ID_KEY).equals("member-service");

        return isMemberService;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        logger.info("run() 호출");

        int randomInt = generate0or1();
        //0이면 새로운 서비스로 라우팅
        if(randomInt == 0) {
            HttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet("http://localhost:8081/hello");
            MultiValueMap<String, String> requestHeaders = helper.buildZuulRequestHeaders(context.getRequest());
            try {
                HttpResponse response = client.execute(request);
                helper.setResponse(response.getStatusLine().getStatusCode(), response.getEntity().getContent(), requestHeaders);
                context.setRouteHost(null);
            } catch (IOException e) {
                e.printStackTrace();
                context.setRouteHost(null);
            }
        }

        return null;
    }


    private int generate0or1() {
        return new Random().nextInt(2);
    }
}
