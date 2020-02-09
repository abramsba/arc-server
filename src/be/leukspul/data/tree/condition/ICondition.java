package be.leukspul.data.tree.condition;

public interface ICondition<T> {
    boolean condition(T ref);
}
