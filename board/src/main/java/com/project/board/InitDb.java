package com.project.board;

import com.project.board.entity.Post;
import com.project.board.entity.SiteUser;
import com.project.board.repository.PostRepository;
import com.project.board.repository.SiteSiteUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit3();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final PostRepository postRepository;
        private final SiteSiteUserRepository siteUserRepository;

        public void dbInit1() {
            Post post = createPost("board에 대해서 알고 싶습니다.", "board가 무엇인가요?", null);
            postRepository.save(post);
        }

        public void dbInit2() {
            Post post = createPost("id는 자동으로 생성되나요?", "스프링 부트 모델 질문입니다.", null);
            postRepository.save(post);
        }

        public void dbInit3(){

            SiteUser siteUser = new SiteUser();
            siteUser.setUsername("ㅇㅇ");
            siteUser.setPassword("123");
            siteUser.setEmail("123@gmail.com");
            siteUserRepository.save(siteUser);

            for(int i = 1; i <= 5; i++){
                String subject = String.format("테스트 데이터입니다:[%03d]", i);
                String content = "내용무";
                Post post = createPost(subject, content, siteUser);
                postRepository.save(post);
            }
        }

        private Post createPost(String title, String content, SiteUser siteUser) {
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setSiteUser(siteUser);
            return post;
        }
    }
}
