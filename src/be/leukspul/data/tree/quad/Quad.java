package be.leukspul.data.tree.quad;

import be.leukspul.data.tree.condition.ICondition;

import java.util.ArrayList;
import java.util.List;

public class Quad<T extends TreeNode> extends QuadBase<T> {

    protected Quad(Quadrant quadrant, QuadTree<T> parent, double x, double y, double width, double height, int limit, List<T> bucket) {
        super(quadrant, parent, x, y, width, height);
        Limit = limit;
        Bucket = bucket;
        Parent = parent;
    }

    @Override
    public synchronized void add(T ref) {
        Bucket.add(ref);
    }

    @Override
    public void clear() {
        Bucket.clear();
    }

    @Override
    public synchronized void remove(T ref) {
        Bucket.remove(ref);
    }

    @Override
    public synchronized void copy(QuadBase<T> quad) {
        for (T ref : this.Bucket) {
            quad.add(ref);
        }
    }

    @Override
    public synchronized boolean move(T ref, double x, double y) {
        if (pointInside(x, y)) {
            return false;
        }
        return true;
    }

    @Override
    public Quad<T> getQuad(T ref) {
        if (contains(ref)) {
            return this;
        }
        return null;
    }

    @Override
    public int leaves() {
        return 1;
    }

    @Override
    public int count() {
        return Bucket.size();
    }

    @Override
    public boolean full() {
        return (Bucket.size() >= Limit);
    }

    public boolean empty() {
        return Bucket.size() == 0;
    }

    @Override
    public boolean contains(T ref) {
        return Bucket.contains(ref);
    }

    @Override
    public T nearest(double x, double y, ICondition<T> func) {
        T found = null;
        double shortest = Double.MAX_VALUE;
        for (T entity : Bucket) {
            if (func != null) {
                if (!func.condition(entity)) {
                    continue;
                }
            }
            double distToTarget = (Math.pow(x - entity.X, 2) + Math.pow(y - entity.Y, 2));
            if (distToTarget < shortest) {
                found = entity;
                shortest = distToTarget;
            }
        }
        return found;
    }

    @Override
    public List<T> circle(double x, double y, double r, ICondition<T> func) {
        List<T> found = new ArrayList<>();
        if (circleFits(x, y, r)) {
            for(T entity : Bucket) {
                if (func != null && func.condition(entity)) {
                    found.add(entity);
                }
                else {
                    found.add(entity);
                }

            }
        }
        else {
            double r2 = r*r;
            for(T entity : Bucket) {
                boolean passed = false;
                if (func != null) {
                    passed = (func != null) && (func.condition(entity));
                }
                else {
                    passed = true;
                }
                if (passed) {
                    double dist2 = Math.pow(x - entity.X, 2) + Math.pow(y - entity.Y, 2);
                    if (dist2 <= r2) {
                        found.add(entity);
                    }
                }
            }
        }
        return found;
    }

    @Override
    public List<T> frustrum(double x, double y, double d, double fov, ICondition<T> func) {

        return null;
    }

    public List<T> nodes() {
        return Bucket;
    }

    public QuadTree<T> parent() {
        return Parent;
    }

    private int Limit;
    private List<T> Bucket;
    private QuadTree<T> Parent;
    private Quadrant Quadrant;
}
