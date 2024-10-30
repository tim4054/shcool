package ru.hogwarts.school.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public GroupedOpenApi allGroups() {
        return GroupedOpenApi.builder().
                group("Все контроллеры").
                pathsToMatch("/**").
                build();
    }

    @Bean
    public GroupedOpenApi facultyGroup() {
        return GroupedOpenApi.builder().
                group("Контроллер факультетов").
                pathsToMatch("/faculty/**").
                build();
    }

    @Bean
    public GroupedOpenApi studentGroup() {
        return GroupedOpenApi.builder().
                group("Контроллер студентов").
                pathsToMatch("/student/**").
                build();
    }

    @Bean
    public GroupedOpenApi avatarGroup() {
        return GroupedOpenApi.builder().
                group("Контроллер аватарок").
                pathsToMatch("/avatar/**").
                build();
    }
}
