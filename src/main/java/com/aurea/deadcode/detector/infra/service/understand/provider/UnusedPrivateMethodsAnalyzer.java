package com.aurea.deadcode.detector.infra.service.understand.provider;

import com.aurea.deadcode.detector.domain.enums.DeadcodeType;
import com.aurea.deadcode.detector.infra.service.understand.Analyzable;
import com.aurea.deadcode.detector.infra.service.understand.UnderstandDatabase;
import com.aurea.deadcode.detector.infra.util.PairTuple;
import com.scitools.understand.Database;
import com.scitools.understand.Entity;
import com.scitools.understand.Reference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UnusedPrivateMethodsAnalyzer implements Analyzable {

    @Override
    public PairTuple<DeadcodeType, List<String>> analyze(final UnderstandDatabase db) {

        final List<String> deadMethods = new ArrayList<>();
        Entity[] funcs = db.ents(" Method private ~unknown ~unresolved");
        Arrays.asList(funcs)
            .forEach(e -> {
                Reference[] methods = e.refs("Callby", "Method", true);
                Reference[] classes = e.refs("Callby", "Class", true);

                if (methods.length == 0 && classes.length == 0) {
                    deadMethods.add(e.longname(true));
                }
            });

        return new PairTuple<>(DeadcodeType.UNUSED_PRIVATE_METHOD, deadMethods);
    }
}
