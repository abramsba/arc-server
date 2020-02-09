package be.leukspul.data.tree.quad;


import be.leukspul.data.tree.condition.ICondition;

import java.util.ArrayList;
import java.util.List;

import static be.leukspul.data.tree.quad.Quadrant.*;


public class QuadTree<T extends TreeNode> extends QuadBase<T> {
    public QuadTree(Quadrant quadrant, QuadTree<T> parent, double x, double y, double width, double height, int limit, Class<List> listClass) {
        super(quadrant, parent, x, y, width, height);
        Limit = limit;
        ListClass = listClass;
    }

    @Override
    public synchronized void add(T ref) {
        Quadrant quadrant = pointQuadrant(ref.X, ref.Y);
        double qx = quadrantX(quadrant);
        double qy = quadrantY(quadrant);
        QuadBase<T> quadRef = getQuadrant(quadrant);
        if (quadRef == null) {
            try {
                List<T> newList = ListClass.newInstance();
                quadRef = new Quad<T>(quadrant,this, qx, qy, halfWidth(), halfHeight(), Limit, newList);
                quadRef.add(ref);
                setQuadrant(quadrant, quadRef);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (quadRef.full()) {
            QuadTree<T> newTree = new QuadTree<>(quadrant,this, qx, qy, halfWidth(), halfHeight(), Limit, ListClass);
            quadRef.copy(newTree);
            newTree.add(ref);
            setQuadrant(quadrant, newTree);
        }
        else {
            quadRef.add(ref);
        }
    }

    @Override
    public void clear() {
        // Todo Notify leaves
        LeafOne = null;
        LeafTwo = null;
        LeafThree = null;
        LeafFour = null;
    }

    @Override
    public synchronized void remove(T ref) {
        Quadrant quadrant = pointQuadrant(ref.X, ref.Y);
        QuadBase<T> quadRef = getQuadrant(quadrant);
        if (quadRef != null) {
            quadRef.remove(ref);
            if (quadRef instanceof Quad && quadRef.empty()) {
                setQuadrant(quadrant, null);
            }
            if (quadRef instanceof QuadTree) {
                try {
                    int totalNodes = quadRef.count();
                    if (totalNodes <= Limit) {
                        List<T> newList = ListClass.newInstance();
                        Quad<T> newQuad = new Quad<>(quadrant, this, quadRef.x(), quadRef.y(), quadRef.width(), quadRef.height(), Limit, newList);
                        quadRef.copy(newQuad);
                        setQuadrant(quadrant, newQuad);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public synchronized void copy(QuadBase<T> quad) {
        if (LeafOne != null) {
            LeafOne.copy(quad);
        }
        if (LeafTwo != null) {
            LeafTwo.copy(quad);
        }
        if (LeafThree != null) {
            LeafThree.copy(quad);
        }
        if (LeafFour != null) {
            LeafFour.copy(quad);
        }
    }

    @Override
    public synchronized boolean move(T ref, double x, double y) {
        Quadrant quadrant = pointQuadrant(ref.X, ref.Y);
        Quad<T> quad = getQuad(ref);
        if (quad == null) {
            this.add(ref);
            return true;
        }
        boolean outside = quad.move(ref, x, y);
        if (outside) {
            ref.X = x;
            ref.Y = y;
            quad.remove(ref);
            if (quad.count() == 0) {
                quad.parent().setQuadrant(quadrant, null);
                boolean moved = mv(ref, x, y, quad.parent());
                return moved;
            }
        }
        else {
            ref.X = x;
            ref.Y = y;
        }
        return true;
    }

    private synchronized boolean mv(T ref, double x, double y, QuadTree<T> current) {
        boolean moved = false;
        QuadTree<T> parent = current.parent();
        if (parent == null) {
            current.add(ref);
            return true;
        }
        else {
            boolean inside = current.pointInside(x, y);
            if (!inside) {
                return mv(ref, x, y, parent);
            }
            ref.X = x;
            ref.Y = y;
            current.add(ref);
            moved = true;
        }
        return moved;
    }

    @Override
    public Quad<T> getQuad(T ref) {
        QuadBase<T> quad = getQuadrant(pointQuadrant(ref.X, ref.Y));
        if (quad != null) {
            return quad.getQuad(ref);
        }
        return null;
    }

    @Override
    public int leaves() {
        int leaves = 0;
        if (LeafOne != null) {
            leaves += LeafOne.leaves();
        }
        if (LeafTwo != null) {
            leaves += LeafTwo.leaves();
        }
        if (LeafThree != null) {
            leaves += LeafThree.leaves();
        }
        if (LeafFour != null) {
            leaves += LeafFour.leaves();
        }
        return leaves;
    }

    @Override
    public int count() {
        int count = 0;
        if (LeafOne != null) {
            count += LeafOne.count();
        }
        if (LeafTwo != null) {
            count += LeafTwo.count();
        }
        if (LeafThree != null) {
            count += LeafThree.count();
        }
        if (LeafFour != null) {
            count += LeafFour.count();
        }
        return count;
    }

    @Override
    public boolean full() {
        return false;
    }

    public boolean empty() {
        return LeafOne == null && LeafTwo == null && LeafThree == null && LeafFour == null;
    }

    @Override
    public boolean contains(T ref) {
        QuadBase<T> quad = getQuadrant(pointQuadrant(ref.X, ref.Y));
        if (quad == null) {
            return false;
        }
        else {
            return quad.contains(ref);
        }
    }



    @Override
    public T nearest(double x, double y, ICondition<T> func) {
        Quadrant [] qs = { I, II, III, IV };
        double range = 100;
        double topLimit = 200000;
        double lowest = Double.MAX_VALUE;
        T closest = null;
        while (closest == null) {
            for (Quadrant q : qs) {
                QuadBase<T> quad = getQuadrant(q);
                if (quad != null && quad.circleInside(x, y, range)) {
                    T near = quad.nearest(x, y, func);
                    double dist2 = Math.pow(x - near.X, 2) + Math.pow(y - near.Y, 2);
                    if (dist2 < lowest) {
                        lowest = dist2;
                        closest = near;
                    }
                }
            }
            if (closest == null) {
                range *= 2;
            }
            if (range >= topLimit) {
                break;
            }
        }
        return closest;
    }

    @Override
    public List<T> circle(double x, double y, double r, ICondition<T> func) {
        List<T> found = new ArrayList<>();
        Quadrant [] qs = { I, II, III, IV };
        if (circleFits(x, y, r)) {
            for (Quadrant q : qs) {
                QuadBase<T> quad = getQuadrant(q);
                if (quad != null) {
                    found.addAll(quad.circle(x, y, r, func));
                }
            }
        }
        else {
            for (Quadrant q : qs) {
                QuadBase<T> quad = getQuadrant(q);
                if (quad != null && quad.circleInside(x, y, r)) {
                    found.addAll(quad.circle(x, y, r, func));
                }
            }
        }
        return found;
    }

    @Override
    public List<T> frustrum(double x, double y, double d, double fov, ICondition<T> func) {
        return null;
    }


    private QuadBase<T> getQuadrant(Quadrant quadrant) {
        switch (quadrant) {
            case I:
                return LeafOne;
            case II:
                return LeafTwo;
            case III:
                return LeafThree;
            case IV:
                return LeafFour;
        }
        return null;
    }

    private void setQuadrant(Quadrant quadrant, QuadBase<T> quad) {
        switch (quadrant) {
            case I:
                LeafOne = quad;
                break;
            case II:
                LeafTwo = quad;
                break;
            case III:
                LeafThree = quad;
                break;
            case IV:
                LeafFour = quad;
        }
    }

    private int Limit;
    private QuadBase<T> LeafOne;
    private QuadBase<T> LeafTwo;
    private QuadBase<T> LeafThree;
    private QuadBase<T> LeafFour;
    private Class<List> ListClass;
}
