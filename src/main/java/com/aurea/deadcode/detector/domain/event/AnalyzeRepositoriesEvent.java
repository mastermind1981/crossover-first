package com.aurea.deadcode.detector.domain.event;

import com.aurea.deadcode.detector.domain.model.SourceRepository;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;

@Data
public class AnalyzeRepositoriesEvent extends ApplicationEvent {

    private Collection<SourceRepository> repositories;

    public AnalyzeRepositoriesEvent(Collection<SourceRepository> repositories) {
        super(repositories);
        this.repositories = repositories;
    }

}
