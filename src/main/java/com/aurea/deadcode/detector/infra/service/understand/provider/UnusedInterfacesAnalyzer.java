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
public class UnusedInterfacesAnalyzer implements Analyzable {

    @Override
    public PairTuple<DeadcodeType, List<String>> analyze(final UnderstandDatabase db) {

        final List<String> deadInterfaces = new ArrayList<>();
        Entity[] funcs = db.ents("Interface ~unknown ~unresolved");

        System.out.println("Unused interfaces: " + funcs.length);

        Arrays.asList(funcs)
                .forEach(e -> {
                    Reference[] implemented = e.refs("Implementby", "Class", true);

                    if (implemented.length == 0) {
                        deadInterfaces.add(e.name());
                    }
                });

        return new PairTuple<>(DeadcodeType.UNUSED_INTERFACES, deadInterfaces);
    }
}