package com.aurea.deadcode.detector.infra.service.understand;

import com.aurea.deadcode.detector.domain.enums.DeadcodeType;
import com.aurea.deadcode.detector.domain.model.SourceRepository;
import com.aurea.deadcode.detector.infra.util.PairTuple;
import com.scitools.understand.Database;

import java.util.List;

public interface Analyzable {

    PairTuple<DeadcodeType, List<String>> analyze(final UnderstandDatabase db);

}
