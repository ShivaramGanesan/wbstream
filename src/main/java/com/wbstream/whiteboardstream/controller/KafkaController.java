package com.wbstream.whiteboardstream.controller;

import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

//    @Autowired
//    KafkaAdmin admin;
//    @Autowired
//    AdminClient adminClient;
//
//    @PostMapping("/topic/{name}")
//    public String createTopic(@RequestParam(value = "name") String topicName){
//        NewTopic topic = new NewTopic(topicName, 2, (short)2);
//        admin.createOrModifyTopics(topic);
//        return "topic created";
//    }
//
//    @GetMapping("/topics")
//    public List<String> listTopics() throws Exception{
//        Set<String> set = adminClient.listTopics().names().get();
//        return new ArrayList<>(set);
//    }
//
//    @DeleteMapping("topic/{topicName}")
//    public String deleteTopic(@PathVariable(value = "topicName") String topicName){
//        adminClient.deleteTopics(Arrays.asList(new String[]{topicName}));
//        return "topic deleted";
//    }

}
