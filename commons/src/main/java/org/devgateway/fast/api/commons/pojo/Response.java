package org.devgateway.fast.api.commons.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"type", "value", "count", "sum", "value", "children"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private String type;
    private Object value;
    private Long count;
    private Double sum;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Response> children;

    public void addChild(final Response item) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(item);
    }

    public List<Response> getChildren() {
        return children;
    }

    public void setChildren(List<Response> children) {
        this.children = children;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}



