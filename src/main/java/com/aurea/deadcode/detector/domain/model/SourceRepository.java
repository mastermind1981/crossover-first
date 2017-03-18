package com.aurea.deadcode.detector.domain.model;

import com.aurea.deadcode.detector.domain.enums.DeadcodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document
public class SourceRepository implements Serializable {

    @Id
    private String id;
    private String name;
    @NotNull
    private String url;
    @NotNull
    private String udbPath;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProcessingStatus status;
    private Long startProcessingTime;
    private Long finishedProcessingTime;
    private Map<DeadcodeType, List<String>> ocurrences;

    public SourceRepository() {
    }
}