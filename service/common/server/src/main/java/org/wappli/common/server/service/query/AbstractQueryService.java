package org.wappli.common.server.service.query;

import org.wappli.common.api.rest.filter.Filter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.SingularAttribute;
import java.time.Instant;
import java.util.Collection;

public abstract class AbstractQueryService<ENTITY, CRITERIA> extends QueryService<ENTITY, CRITERIA> {

    protected <OTHER, ANOTHER, X> Specification<ENTITY> buildReferringTwoLevelEntitySpecification(Filter<X> filter, SingularAttribute<? super ENTITY, OTHER> reference,
                                                                                                  SingularAttribute<? super OTHER, ANOTHER> twoLevelReference,
                                                                                                  SingularAttribute<ANOTHER, X> valueField) {
        return (root, query, builder) -> builder.equal(root.get(reference).get(twoLevelReference).get(valueField), filter.getEquals());
    }

    protected <OTHER, ANOTHER, NEXTANOTHER, X> Specification<ENTITY> buildReferringThirdLevelEntitySpecification(Filter<X> filter, SingularAttribute<? super ENTITY, OTHER> reference,
                                                                                                                 SingularAttribute<? super OTHER, ANOTHER> twoLevelReference,
                                                                                                                 SingularAttribute<? super ANOTHER, NEXTANOTHER> thirdLevelReference,
                                                                                                                 SingularAttribute<NEXTANOTHER, X> valueField) {
        return (root, query, builder) -> builder.equal(root.get(reference).get(twoLevelReference).get(thirdLevelReference).get(valueField), filter.getEquals());
    }

    protected Specification<ENTITY> ervenyessegIgIsGreaterThanNowOrNull(SingularAttribute<? super ENTITY, Instant> field) {
        Specifications<ENTITY> result = Specifications.where(null);
        result = result.or(byFieldSpecified(field, Boolean.FALSE));
        result = result.or(greaterThan(field, Instant.now()));
        return result;
    }

    protected <X> Specification<ENTITY> notInEnumSpecification(SingularAttribute<? super ENTITY, ? extends X> field, final Collection<? extends X> values){
        return (root, query, builder) -> {
            CriteriaBuilder.In<X> in = builder.in(root.get(field));
            for (X value : values) {
                in = in.value(value);
            }
            return in.not();
        };
    }

    protected <X> Specification<ENTITY> valueEnumIn(SingularAttribute<? super ENTITY, ? extends X> field, Collection<? extends X> values) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<X> in = builder.in(root.get(field));
            for (X value : values) {
                in = in.value(value);
            }
            return in;
        };
    }

    protected <X> Specification<ENTITY> equalsEnumSpecification(SingularAttribute<? super ENTITY, ? extends X> field, final X value) {
        return (root, query, builder) -> builder.equal(root.get(field), value);
    }
}
