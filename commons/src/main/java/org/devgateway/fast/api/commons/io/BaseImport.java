package org.devgateway.fast.api.commons.io;

import org.devgateway.fast.api.commons.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * @author Sebastian Dimunzio
 */
@Transactional
public abstract class BaseImport<T, R> {


    protected void beforeStart() {
        clean();
        init();
    }

    public abstract T read(R row);

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected CategoryService categoryService;

    @PersistenceContext
    protected EntityManager entityManager;

    protected Boolean toBoolean(final String val) {
        return "y".equalsIgnoreCase(val) || "yes".equalsIgnoreCase(val) || "true".equalsIgnoreCase(val);
    }

    protected Integer toInteger(final Object value) {

        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Double) {
            return ((Double) value).intValue();
        }
        if (value instanceof String) {
            if (!((String) value).isEmpty()) {
                return Integer.parseInt((String) value);
            } else {
                return null;
            }
        }
        return null;
    }

    protected Long toLong(final Object value) {

        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }
        if (value instanceof Double) {
            return ((Double) value).longValue();
        }

        if (value instanceof Long) {
            return ((Long) value);
        }
        if (value instanceof String) {
            if (!((String) value).isEmpty()) {
                return Long.parseLong((String) value);
            } else {
                return null;
            }
        }
        return null;
    }


    public Double toDouble(final Object value) {
        if (value != null) {

            if (value instanceof Integer) {
                return ((Integer) value).doubleValue();
            }
            if (value instanceof String) {
                if (!((String) value).isEmpty()) {
                    return Double.parseDouble((String) value);
                } else {
                    return null;
                }
            }

            if (value instanceof Double) {
                return (Double) value;
            }
        }
        return null;
    }

    protected abstract void init();

    protected abstract void clean();


    public void save(T record) {
        entityManager.persist(record);
    }

}
