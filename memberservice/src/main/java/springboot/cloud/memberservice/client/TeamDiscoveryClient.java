package springboot.cloud.memberservice.client;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import springboot.cloud.memberservice.client.dto.TeamDto;

import java.util.List;
import java.util.Random;

@Component
public class TeamDiscoveryClient {

    private final DiscoveryClient discoveryClient;

    public TeamDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public TeamDto getTeam(Long teamId) {
        List<ServiceInstance> instances = discoveryClient.getInstances("team-service");

        int index = getRandomIndex(instances.size());
        ServiceInstance instance = instances.get(index);
        System.out.format("index : %d, instance : %s\n", index, instance);

        String serviceUrl = String.format("%s/teams/%d", instance.getUri(), teamId);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TeamDto> exchange = restTemplate.exchange(serviceUrl, HttpMethod.GET, null, TeamDto.class);
        return exchange.getBody();
    }

    private int getRandomIndex(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

}
