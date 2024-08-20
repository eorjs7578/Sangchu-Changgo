package com.d108.project.config;

import com.d108.project.domain.KafkaSampleConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaListeners {

    private final KafkaSampleConsumerService kafkaSampleConsumerService;

//    @KafkaListener(topics = "${setting.topics}",groupId = "${spring.kafka.consumer.group-id}")
    @KafkaListener(topics = "oingdaddy", groupId = "group-id-oing")
    public void consume(ConsumerRecord<String, String> consumerRecord, @Header(KafkaHeaders.OFFSET) Long offset
            , Acknowledgment acknowledgment
            , Consumer<?, ?> consumer){
        try {
/**
 * 이 로그 메시지는 Kafka 소비자(Consumer)가 특정 토픽의 파티션에 성공적으로 할당되었음을 나타냅니다. 구체적으로, 이 메시지는 다음과 같은 내용을 담고 있습니다:
 *
 * group-id-oing: 이 부분은 Kafka 컨슈머 그룹 ID를 나타냅니다. Kafka에서 메시지를 소비하는 애플리케이션은 특정 그룹에 속할 수 있으며, 같은 그룹에 속한 컨슈머들 간에는 메시지 소비가 분산됩니다.
 *
 * partitions assigned: [oingdaddy-0]: 이 부분은 특정 컨슈머에게 할당된 파티션을 나타냅니다. 여기서 oingdaddy는 토픽의 이름이며 0은 해당 토픽의 파티션 번호를 나타냅니다. 즉, 이 메시지는 oingdaddy 토픽의 0번 파티션이 group-id-oing 컨슈머 그룹 내의 한 컨슈머에게 할당되었음을 의미합니다.
 *
 * 로그의 의미
 * Kafka에서는 하나의 토픽이 여러 파티션으로 나뉠 수 있습니다. 각 파티션은 개별적으로 메시지를 저장하고 관리합니다. 여러 개의 컨슈머가 같은 컨슈머 그룹에 속해 있을 때, 각 컨슈머는 하나 이상의 파티션을 할당받아 메시지를 소비하게 됩니다. 이 로그는 그러한 할당이 성공적으로 이루어졌다는 것을 알려줍니다.
 *
 * 이 메시지를 통해 다음과 같은 것을 확인할 수 있습니다:
 *
 * 해당 컨슈머가 Kafka 브로커로부터 정상적으로 파티션을 할당받았다는 것.
 * oingdaddy 토픽의 0번 파티션에 대해 메시지 소비를 시작할 준비가 되었다는 것.
 */
            log.info("Consumer Data = {}, Offset = {}, Header OffSet = {}, Partition = {}"
                    , consumerRecord.value(), consumerRecord.offset(),offset,consumerRecord.partition());
            //처리 후 커밋

            //해당 비지니스 로직 처리 후 커밋로직 작성
            kafkaSampleConsumerService.consume(consumerRecord.value());

            consumer.commitAsync();
        }
        catch (Exception e){
            log.error(e.getMessage());
        }

    }
}
