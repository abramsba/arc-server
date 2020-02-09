package be.leukspul.data.tree.quad;

import be.leukspul.data.tree.condition.ICondition;

import java.util.List;

public abstract class QuadBase<T extends TreeNode> {

    public QuadBase(Quadrant quadrant, QuadTree<T> parent, double x, double y, double width, double height) {
        Quadrant = quadrant;
        Parent = parent;
        X = x;
        Y = y;
        Width = width;
        Height = height;
        HalfWidth = width / 2;
        HalfHeight = height / 2;
        TopLeftX = X;
        TopLeftY = Y;
        TopRightX = X + Width;
        TopRightY = Y;
        BottomLeftX = X;
        BottomLeftY = Y + Height;
        BottomRightX = X + Width;
        BottomRightY = Y + Height;
        OriginX = X + (width / 2);
        OriginY = Y + (height / 2);
        FarX = X + width;
        FarY = Y + height;
        Area = width * height;
        Segments = new double[][][]{
                segment(vector(TopLeftX, TopLeftY), vector(TopRightX, TopRightY)),
                segment(vector(TopRightX, TopRightY), vector(BottomRightX, BottomRightY)),
                segment(vector(BottomRightX, BottomRightY), vector(BottomLeftX, BottomLeftY)),
                segment(vector(BottomLeftX, BottomLeftY), vector(TopLeftX, TopLeftY))
        };
    }

    public double x() { return X; }
    public double y() { return Y; }
    public double originX() { return OriginX; }
    public double originY() { return OriginY; }
    public double farX() { return FarX; }
    public double farY() { return FarY; }
    public double width() { return Width; }
    public double height() { return Height; }
    public double halfWidth() { return HalfWidth; }
    public double halfHeight() { return HalfHeight; }
    public double area() { return Area; }
    public Quadrant quadrant() { return Quadrant; }

    public boolean pointInside(double x, double y) {
        return (x >= X && x < FarX) && (y >= Y && y < FarY);
    }

    public boolean circleInside(double x, double y, double r) {
        if (pointInside(x, y)) {
            return true;
        }
        Quadrant quadrant = pointQuadrant(x, y);
        double dist = Double.MAX_VALUE;
        switch (quadrant) {
            case I:
                dist = Math.pow(x - TopRightX, 2) + Math.pow(y - TopRightY, 2);
                break;
            case II:
                dist = Math.pow(x - TopLeftX, 2) + Math.pow(y - TopLeftY, 2);
                break;
            case III:
                dist = Math.pow(x - BottomLeftX, 2) + Math.pow(y - BottomLeftY, 2);
                break;
            case IV:
                dist = Math.pow(x - BottomRightX, 2) + Math.pow(y - BottomRightY, 2);
        }
        return dist <= (r * r);
    }

    public boolean circleFits(double x, double y, double r) {
        double dx = Math.max(x - X, FarX - X);
        double dy = Math.max(y - Y, FarY - y);
        return (r*r) >= (dx*dx) + (dy*dy);
    }

    public boolean lineIntersects(double x1, double y1, double x2, double y2) {
        if (pointInside(x1, y1) || pointInside(x2, y2)) {
            return true;
        }
        boolean intersects = false;
        double [] start = vector(x1, y1);
        double [] end = vector(x2, y2);
        double a1 = y(end) - y(start);
        double b1 = x(start) - x(end);
        double c1 = (a1 * x(start)) + (b1 * y(start));
        double xh = Math.max(x1, x2);
        double xl = Math.min(x1, x2);
        double yh = Math.max(y1, y2);
        double yl = Math.min(y1, y2);
        for (double [][] segment : Segments) {
            double [] segStart = start(segment);
            double [] segEnd = end(segment);
            double a2 = y(segEnd) - y(segStart);
            double b2 = y(segStart) - x(segEnd);
            double delta = (a1 * b2) - (a2 * b1);
            if (delta == 0) {
                continue;
            }
            double c2 = (a2 * x(segStart)) + (b2 * y(segStart));
            double inverse = 1 / delta;
            double [] point = vector(
                    ((b2 * c1) - (b1 * c2)) * inverse,
                    ((a1 * c2) - (a2 * c1) * inverse)
            );
            // Check if the intersection point falls inside the line segment
            if ((x(point) <= xh && x(point) >= xl) && (y(point) <= yh && y(point) >= yl)) {
                if (pointInside(x(point), y(point))) {
                    intersects = true;
                    break;
                }
            }
        }
        return intersects;
    }

    public abstract void add(T ref);
    public abstract void clear();
    public abstract void remove(T ref);
    public abstract void copy(QuadBase<T> quad);
    public abstract boolean move(T ref, double x, double y);
    public abstract Quad<T> getQuad(T ref);
    public abstract int leaves();
    public abstract int count();
    public abstract boolean full();
    public abstract boolean empty();
    public abstract boolean contains(T ref);
    public abstract T nearest(double x, double y, ICondition<T> func);
    public abstract List<T> circle(double x, double y, double r, ICondition<T> func);
    public abstract List<T> frustrum(double x, double y, double d, double fov, ICondition<T> func);

    private double x(double [] vector) {
        return vector[0];
    }

    private double y(double [] vector) {
        return vector[1];
    }

    private double z(double [] vector) {
        return vector[2];
    }

    private double[] vector(double x, double y) {
        return new double[]{x, y};
    }

    private double[] start(double [][] segment) {
        return segment[0];
    }

    private double[] end(double [][] segment) {
        return segment[1];
    }

    private double[][] segment(double [] start, double [] end) {
        return new double[][]{ start, end };
    }

    private double X;
    private double Y;
    private double OriginX;
    private double OriginY;
    private double FarX;
    private double FarY;
    private double Width;
    private double Height;
    private double TopLeftX;
    private double TopLeftY;
    private double TopRightX;
    private double TopRightY;
    private double BottomLeftX;
    private double BottomLeftY;
    private double BottomRightX;
    private double BottomRightY;
    private double Area;
    private double [][][] Segments;
    private double HalfWidth;
    private double HalfHeight;
    private Quadrant Quadrant;

    protected Quadrant pointQuadrant(double x, double y) {
        if (x < OriginX && y < OriginY) {
            return Quadrant.II;
        }
        else if (x < OriginX && y >= OriginY) {
            return Quadrant.III;
        }
        else if (x >= OriginX && y < OriginY) {
            return Quadrant.I;
        }
        else if (x >= OriginX && y >= OriginY) {
            return Quadrant.IV;
        }
        return null;
    }

    protected double quadrantX(Quadrant quadrant) {
        switch(quadrant) {
            case II:
            case III:
                return X;
            default:
                return OriginX;
        }
    }

    protected double quadrantY(Quadrant quadrant) {
        switch(quadrant) {
            case I:
            case II:
                return Y;
            default:
                return OriginY;
        }
    }

    public QuadTree<T> parent() {
        return Parent;
    }

    private QuadTree<T> Parent;

}
