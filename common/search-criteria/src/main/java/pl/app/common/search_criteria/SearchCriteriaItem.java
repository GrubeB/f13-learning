package pl.app.common.search_criteria;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchCriteriaItem implements
        Serializable {

    @NotNull
    private String field;

    @NotNull
    private Operator operator;
    private transient String value;

    private transient String valueTo;

    private transient List<String> values;
    private ConditionOperator conditionOperator;

    public SearchCriteriaItem() {
        this.values = new ArrayList<>();
        this.conditionOperator = ConditionOperator.AND;
    }

    public SearchCriteriaItem(String field, Operator operator, String value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.conditionOperator = ConditionOperator.AND;
    }

    public SearchCriteriaItem(String field, Operator operator, String value, ConditionOperator conditionOperator) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.conditionOperator = conditionOperator;
    }

    public SearchCriteriaItem(String field, Operator operator, String value, String valueTo) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.valueTo = valueTo;
        this.conditionOperator = ConditionOperator.AND;
    }

    public SearchCriteriaItem(String field, Operator operator, String value, String valueTo, ConditionOperator conditionOperator) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.valueTo = valueTo;
        this.conditionOperator = conditionOperator;
    }

    public SearchCriteriaItem(String field, Operator operator, List<String> values) {
        this.field = field;
        this.operator = operator;
        this.values = values;
        this.conditionOperator = ConditionOperator.AND;
    }

    public SearchCriteriaItem(String field, Operator operator, List<String> values, ConditionOperator conditionOperator) {
        this.field = field;
        this.operator = operator;
        this.values = values;
        this.conditionOperator = conditionOperator;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueTo() {
        return valueTo;
    }

    public void setValueTo(String valueTo) {
        this.valueTo = valueTo;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public ConditionOperator getConditionOperator() {
        return conditionOperator;
    }

    public void setConditionOperator(ConditionOperator conditionOperator) {
        this.conditionOperator = conditionOperator;
    }

    @Override
    public String toString() {
        return "SearchCriteriaItem{" +
                "key='" + field + '\'' +
                ", operator=" + operator +
                ", value=" + value +
                ", valueTo=" + valueTo +
                ", values=" + values +
                '}';
    }
}
