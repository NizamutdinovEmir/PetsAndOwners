package nizamutdinov..config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic ownerRequests() {
        return TopicBuilder.name("owner-requests")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic ownerResponses() {
        return TopicBuilder.name("owner-responses")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic ownerGetRequests() {
        return TopicBuilder.name("owner-get-requests")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic ownerGetResponses() {
        return TopicBuilder.name("owner-get-responses")
                .partitions(1)
                .replicas(1)
                .build();
    }

    // ... аналогично для pet-*, если в pet-service есть запросы/ответы
}