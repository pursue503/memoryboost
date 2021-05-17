package com.memoryboost.controller;


import com.memoryboost.controller.deploy.ProfileController;
import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileControllerUnitTest {

    @Test
    public void realProfileFetch() {
        //given
        String expectedProfile = "real";
        MockEnvironment environment = new MockEnvironment();
        environment.addActiveProfile(expectedProfile);
        environment.addActiveProfile("aws");
        environment.addActiveProfile("kakao-key");
        environment.addActiveProfile("mail");
        environment.addActiveProfile("oauth");
        environment.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(environment);

        // when

        String profile = controller.profile();

        // then

        assertThat(profile).isEqualTo(expectedProfile);


    }
}
