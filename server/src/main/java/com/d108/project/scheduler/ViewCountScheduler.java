package com.d108.project.scheduler;

import com.d108.project.domain.forum.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ViewCountScheduler {

    private final PostService postService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void syncViewCounts() {
        List<Long> postIds = postService.getAllPostIds();
        for (Long postId : postIds) {
            postService.syncCountToDatabase(postId);
        }
    }
}
