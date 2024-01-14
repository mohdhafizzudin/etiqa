package com.etiqa.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpecificationObject<T> implements Specification<T> {
    private List<Criteria> criterias = new ArrayList<>();
    public SpecificationObject(List<Criteria> criterias) {
        this.criterias = criterias;
    }
    public void add(Criteria criteria) {
        criterias.add(criteria);
    }
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        var predicates = new ArrayList<Predicate>();
        for (var cr : criterias) {
            var key = cr.getKey();
            var value = cr.getValue();
            if (cr.getOperation().equals(SearchCriteria.GREATER_THAN))
                predicates.add((value instanceof Date) ? cb.greaterThan(root.get(key), (Date) value)
                        : cb.greaterThan(root.get(key), String.valueOf(value)));
            else if (cr.getOperation().equals(SearchCriteria.LESS_THAN))
                predicates.add((cr.getValue() instanceof Date d) ? cb.lessThan(root.get(key), d)
                        : cb.lessThan(root.get(key), String.valueOf(value)));
            else if (cr.getOperation().equals(SearchCriteria.GREATER_THAN_EQUAL))
                predicates.add((value instanceof Date d) ? cb.greaterThanOrEqualTo(root.get(key), d)
                        : cb.greaterThanOrEqualTo(root.get(key), String.valueOf(value)));
            else if (cr.getOperation().equals(SearchCriteria.LESS_THAN_EQUAL))
                predicates.add((value instanceof Date d) ? cb.lessThanOrEqualTo(root.get(key), d)
                        : cb.lessThanOrEqualTo(root.get(key), String.valueOf(value)));
            else if (cr.getOperation().equals(SearchCriteria.NOT_EQUAL))
                predicates.add(cb.notEqual(root.get(key), value));
            else if (cr.getOperation().equals(SearchCriteria.EQUAL))
                predicates.add(cb.equal(root.get(key), value));
            else if (cr.getOperation().equals(SearchCriteria.EQUAL_IGNORE_CASE))
                predicates.add(cb.equal(cb.lower(root.get(key)), String.valueOf(value).toLowerCase()));
            else if (cr.getOperation().equals(SearchCriteria.MATCH))
                predicates.add(cb.like(cb.lower(root.get(key)), "%" + String.valueOf(value).toLowerCase() + "%"));
            else if (cr.getOperation().equals(SearchCriteria.MATCH_END))
                predicates.add(cb.like(cb.lower(root.get(key)), String.valueOf(value).toLowerCase() + "%"));
            else if (cr.getOperation().equals(SearchCriteria.MATCH_START))
                predicates.add(cb.like(cb.lower(root.get(key)), "%" + String.valueOf(value).toLowerCase()));
            else if (cr.getOperation().equals(SearchCriteria.IN))
                predicates.add(cb.in(root.get(key)).value(value));
            else if (cr.getOperation().equals(SearchCriteria.NOT_IN))
                predicates.add(cb.not(root.get(key)).in(value));
            else
                throw new RuntimeException("Operation Not Supported");
        }
        return cb.and(predicates.toArray(Predicate[]::new));
    }
    public static <T> Specification<T> join(Criteria cr, JoinType type, String column) {
        var key = cr.getKey();
        var value = cr.getValue();
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();
            Join<?, ?> join = root.join(column, type);
            if (cr.getOperation().equals(SearchCriteria.MATCH))
                predicates.add(cb.like(cb.lower(join.get(key)), "%" + String.valueOf(value).toLowerCase() + "%"));
            else if (cr.getOperation().equals(SearchCriteria.NOT_EQUAL))
                if (value instanceof Long l)
                    predicates.add(cb.notEqual(join.get(key).as(Long.class), l));
                else if (value instanceof Float f)
                    predicates.add(cb.notEqual(join.get(key).as(Float.class), f));
                else if (value instanceof Double db)
                    predicates.add(cb.notEqual(join.get(key).as(Double.class), db));
                else if (value instanceof Integer i)
                    predicates.add(cb.notEqual(join.get(key).as(Integer.class), i));
                else
                    predicates.add(cb.notEqual(root.get(key), value));
            else if (cr.getOperation().equals(SearchCriteria.EQUAL))
                if (value instanceof Long l)
                    predicates.add(cb.equal(join.get(key).as(Long.class), l));
                else if (value instanceof Float f)
                    predicates.add(cb.equal(join.get(key).as(Float.class), f));
                else if (value instanceof Double db)
                    predicates.add(cb.equal(join.get(key).as(Double.class), db));
                else if (value instanceof Integer i)
                    predicates.add(cb.equal(join.get(key).as(Integer.class), i));
                else
                    predicates.add(cb.equal(root.get(key), value));
            else if (cr.getOperation().equals(SearchCriteria.GREATER_THAN_EQUAL))
                predicates.add((value instanceof Date d) ? cb.greaterThanOrEqualTo(root.get(key), d)
                        : cb.greaterThanOrEqualTo(root.get(key), String.valueOf(value)));
            else if (cr.getOperation().equals(SearchCriteria.LESS_THAN_EQUAL))
                predicates.add((value instanceof Date d) ? cb.lessThanOrEqualTo(root.get(key), d)
                        : cb.lessThanOrEqualTo(root.get(key), String.valueOf(value)));
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

}
