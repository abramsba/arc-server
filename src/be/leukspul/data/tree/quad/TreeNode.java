package be.leukspul.data.tree.quad;

public class TreeNode<T> {
    public TreeNode(T ref, double x, double y) {
        data = ref;
        X = x;
        Y = y;
    }
    public T get() {
        return data;
    }
    public void set(T ref) {
        data = ref;
    }
    public Class<T> type() {
        return (Class<T>)this.getClass();
    }
    private T data;
    public double X;
    public double Y;
}
