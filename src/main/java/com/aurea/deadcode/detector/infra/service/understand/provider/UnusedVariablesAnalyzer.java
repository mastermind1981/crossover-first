package com.aurea.deadcode.detector.infra.service.understand.provider;

import com.aurea.deadcode.detector.domain.enums.DeadcodeType;
import com.aurea.deadcode.detector.infra.service.understand.Analyzable;
import com.aurea.deadcode.detector.infra.service.understand.UnderstandDatabase;
import com.aurea.deadcode.detector.infra.util.PairTuple;
import com.scitools.understand.Database;
import com.scitools.understand.Entity;
import com.scitools.understand.Reference;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

@Component
public class UnusedVariablesAnalyzer implements Analyzable {

    @Override
    public PairTuple<DeadcodeType, List<String>> analyze(final UnderstandDatabase db) {

        final List<String> deadVariables = new ArrayList<>();
        Entity[] funcs = db.ents("Variable Member ~unknown ~unresolved");

        Arrays.asList(funcs)
            .forEach(e -> {

                Reference[] methods = e.refs("Useby", "Method", true);
                Reference[] classes = e.refs("Useby", "Class", true);

                if(methods.length == 0 && classes.length == 0) {
                    deadVariables.add(e.longname(true));
                }

            });

        return new PairTuple<>(DeadcodeType.UNUSED_VARIABLES, deadVariables);
    }

}
