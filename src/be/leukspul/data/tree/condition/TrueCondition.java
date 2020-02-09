package be.leukspul.data.tree.condition;

public class TrueCondition<T> implements ICondition<T> {
    @Override
    public boolean condition(T ref) {
        return true;
    }
}
