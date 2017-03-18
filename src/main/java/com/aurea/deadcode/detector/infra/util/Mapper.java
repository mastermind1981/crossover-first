package com.aurea.deadcode.detector.infra.util;

import com.aurea.deadcode.detector.application.api.mapper.SourceRepositoryResponseMapper;
import com.aurea.deadcode.detector.application.api.resource.SourceRepositoryResponse;
import com.aurea.deadcode.detector.infra.rest.RestResponsePage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public interface Mapper<O, D> {

    D map(final O o);

    default Collection<D> map(final Collection<O> ts) {
        final Collection<D> entites = ts instanceof Set ? new HashSet<>() : new ArrayList<>();
        ts.forEach(t -> entites.add(map(t)));
        return entites;
    }

    default Page<D> map(final Page<O> ts) {
        return ts.map(this::map);
    }

    default RestResponsePage<D> mapToRestResponsePage(final Page<O> ts) {
        Page<D> page = map(ts);
        return new RestResponsePage<>(page.getContent(), new PageRequest(page.getNumber(),
                page.getSize(), page.getSort()), page.getTotalElements());
    }
}