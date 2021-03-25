package org.devgateway.fast.api.example.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.jpa.impl.JPAQuery;
import org.devgateway.fast.api.commons.domain.Filtrable;
import org.devgateway.fast.api.example.repositories.SurveyRepository;
import org.devgateway.fast.api.commons.domain.Dimension;
import org.devgateway.fast.api.commons.domain.categories.Category;
import org.devgateway.fast.api.commons.pojo.Response;
import org.devgateway.fast.api.example.domain.QSurvey;
import org.devgateway.fast.api.example.domain.Survey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PrevalenceSurveyService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager em;

    private SurveyRepository surveyRepository;

    @Autowired
    public PrevalenceSurveyService(final SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    private static final QSurvey qSurvey = QSurvey.survey;


    private List parseParams(String value, Class type) {
        String[] strValues = value.split(",");
        return Arrays.stream(strValues).map(s -> {
            try {
                return type.getConstructor(String.class).newInstance(s);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

    }


    private BooleanBuilder getFilters(Map<String, String> params) {
        QSurvey qSurvey = QSurvey.survey;
        BooleanBuilder builder = new BooleanBuilder();

        if (params != null) {


            for (String key : params.keySet()) {
                Field f = getFieldByFilter(key);
                if (f != null) {
                    if (f.getType().getGenericSuperclass().getTypeName().equalsIgnoreCase(Category.class.getTypeName())) {
                        builder.and(Expressions.predicate(Ops.IN,
                                Expressions.path(List.class, qSurvey, f.getName() + ".id"),
                                Expressions.constant(Arrays.stream(params.get(key).split(",")).map(o -> Long.parseLong(o)).collect(Collectors.toList()))));

                    } else {

                        builder.and(Expressions.predicate(Ops.IN,
                                Expressions.path(List.class, qSurvey, f.getName()),
                                Expressions.constant(parseParams(params.get(key), f.getType()))));

                    }
                }


            }
        }
        return builder;
    }

    public Dimension getDimensionByName(String name) {
        for (Field field : Survey.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Dimension.class)) {
                Dimension[] d;
                Dimension a = field.getAnnotation(Dimension.class);
                if (a.name().equalsIgnoreCase(name)) {
                    return a;
                }
            }
        }
        logger.info("can't find dimension with given name " + name);
        return null;
    }

    public List<String> getDimensions() {
        List<String> list = new ArrayList<>();
        for (Field field : Survey.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Dimension.class)) {
                Dimension[] d;
                d = field.getAnnotationsByType(Dimension.class);
                list.add(d[0].name());
            }
        }
        return list;
    }

    public Field getFieldByDimension(String dimension) {
        Field f = null;
        for (Field field : Survey.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Dimension.class)) {
                Dimension[] d;
                d = field.getAnnotationsByType(Dimension.class);
                if (d[0].name().equalsIgnoreCase(dimension)) {
                    f = field;
                }
            }
        }

        return f;
    }


    public SimplePath gePathByDimension(String dimension) {
        Field f = getFieldByDimension(dimension);
        if (f != null) {
            if (f.getType().getGenericSuperclass().getTypeName().equalsIgnoreCase(Category.class.getTypeName())) {
                return Expressions.path(String.class, qSurvey, f.getName() + ".value");

            } else {
                return Expressions.path(String.class, qSurvey, f.getName());

            }
        }
        logger.info("Not valid dimension " + dimension);
        return null;
    }

    public SimplePath getOrderPath(String dimension) {

        Field f = getFieldByDimension(dimension);

        if (f != null) {
            if (f.getType().getGenericSuperclass().getTypeName().equalsIgnoreCase(Category.class.getTypeName())) {
                return Expressions.path(String.class, qSurvey, f.getName() + ".position");

            } else {
                return Expressions.path(String.class, qSurvey, f.getName());

            }
        }
        return null;
    }


    public List getFilters() {
        List list = new ArrayList<>();
        for (Field field : Survey.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Filtrable.class)) {
                Filtrable[] d;
                d = field.getAnnotationsByType(Filtrable.class);
                list.add(new String[]{d[0].param(), d[0].description()});
            }
        }
        return list;
    }

    public Field getFieldByFilter(String dimension) {
        Field f = null;
        for (Field field : Survey.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Filtrable.class)) {
                Filtrable[] d;
                d = field.getAnnotationsByType(Filtrable.class);
                if (d[0].param().equalsIgnoreCase(dimension)) {
                    f = field;
                }
            }
        }

        return f;
    }

    public Response stats(Map<String, String> params, List<String> dimensions) {
        List<Dimension> ds = dimensions.stream().map(this::getDimensionByName).filter(dimension -> dimension != null).collect(Collectors.toList());
        if (ds.size() != dimensions.size()) {
            logger.info("not valid dimensions provided");
            return new Response();
        }

        Integer i = 0;

        HashMap<String, Response> map = new HashMap<>();


        Response root = new Response();
        root.setCount(sum().get(1, Long.class));
        root.setSum(sum().get(0, Double.class));
        root.setType("Total");
        map.put("", root);
        List<String> sub = new ArrayList<>();

        while (sub.size() < dimensions.size()) {
            String d = dimensions.get(i++);
            sub.add(d);
            List<Tuple> totals = sum(params, sub);
            for (Tuple tuple : totals) {
                Response r = new Response();
                r.setType(d);
                r.setValue(tuple.get(tuple.size() - 3, Object.class));
                r.setSum(tuple.get(tuple.size() - 2, Double.class));
                r.setCount(tuple.get(tuple.size() - 1, Long.class));
                String parentKey = "";
                for (int j = 0; j < tuple.size() - 3; j++) {
                    parentKey = parentKey.concat(tuple.get(j, Object.class).toString()).toUpperCase();
                }

                Response parent = map.get(parentKey);
                logger.info("parent = " + parentKey);
                parent.addChild(r);

                String key = "";
                for (int j = 0; j < tuple.size() - 2; j++) {
                    key = key.concat(tuple.get(j, Object.class).toString()).toUpperCase();
                }

                logger.info("_key =" + key);
                map.put(key, r);
            }
        }
        return root;
    }

    public Long count() {
        JPAQuery query = new JPAQuery<>(em);
        QSurvey qSurvey = QSurvey.survey;
        query.select(qSurvey.count()).from(qSurvey);
        return query.fetchCount();

    }

    public Tuple sum() {
        JPAQuery query = new JPAQuery<>(em);
        QSurvey qSurvey = QSurvey.survey;
        query.select(qSurvey.weight.sum(), qSurvey.count()).from(qSurvey);

        return (Tuple) query.fetchFirst();
    }

    public List<Tuple> count(HashMap<String, String> params, List<String> dimensions) {
        JPAQuery query = new JPAQuery<>(em);
        BooleanBuilder builder = getFilters(params);
        List<Expression> expressions = dimensions.stream()
                .map(s -> gePathByDimension(s)).collect(Collectors.toList());
        if (expressions.size() > 0) {
            List<Expression> select = new ArrayList<>(expressions);
            List<Expression> group = new ArrayList<>(expressions);
            select.add(qSurvey.count());
            query.select(select.toArray(new Expression[select.size()]))
                    .from(qSurvey)
                    .where(builder);
            if (dimensions.size() > 0) {
                query.orderBy(new OrderSpecifier(Order.ASC, getOrderPath(dimensions.get(0))));
                group.add(getOrderPath(dimensions.get(0)));
            }
            query.groupBy(group.toArray(new Expression[group.size()]));
            List<Tuple> data = query.fetch();
            return data;
        } else {
            return null;
        }
    }

    public List<Tuple> sum(Map<String, String> params, List<String> dimensions) {
        JPAQuery query = new JPAQuery<>(em);
        BooleanBuilder builder = getFilters(params);
        List<Expression> expressions = dimensions.stream()
                .map(s -> gePathByDimension(s)).collect(Collectors.toList());
        if (expressions.size() > 0) {
            List<Expression> select = new ArrayList<>(expressions);
            List<Expression> group = new ArrayList<>(expressions);
            select.add(qSurvey.weight.sum());
            select.add(qSurvey.count());

            query.select(select.toArray(new Expression[select.size()]))
                    .from(qSurvey)
                    .where(builder);
            if (dimensions.size() > 0) {
                query.orderBy(new OrderSpecifier(Order.ASC, getOrderPath(dimensions.get(0))));
                group.add(getOrderPath(dimensions.get(0)));
            }
            query.groupBy(group.toArray(new Expression[group.size()]));
            List<Tuple> data = query.fetch();
            return data;
        } else {
            return null;
        }
    }

    public Survey save(final Survey element) {
        return this.surveyRepository.save(element);
    }

    public void clean() {
        this.surveyRepository.deleteAll();
    }
}

