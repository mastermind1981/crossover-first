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
public class UnusedParametersAnalyzer implements Analyzable {

    @Override
    public PairTuple<DeadcodeType, List<String>> analyze(final UnderstandDatabase db) {
        final List<String> deadParams = new ArrayList<>();
        Entity[] funcs = db.ents("Parameter ~unknown ~unresolved");
        Arrays.asList(funcs)
                .forEach(f -> {
                    final Reference[] methods = f.refs("Useby", "Method", true);
                    final Set<String> ref = new HashSet<>();

                    Arrays.asList(methods)
                        .forEach(m -> {
                            Arrays.asList(m.ent().refs("Definein", "Class", true))
                                .forEach(c -> {
                                    System.out.println(c.ent().kind().name() + " from " + f.longname(true));
                                    if(!Pattern.matches(".*Interface.*|.*Annotation.*", c.ent().kind().name())) {
                                        ref.add(m.ent().longname(true));
                                    }
                                });
                        });

                        if(methods.length == 0) {
                            deadParams.add(f.longname(true));
                        }
                    });

        return new PairTuple<>(DeadcodeType.UNUSED_PARAMETERS, deadParams);
    }
}
