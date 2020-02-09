package be.leukspul.math;

import org.json.JSONObject;

public class Vector3 {

    public Vector3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vec) {
        x = vec.x;
        y = vec.y;
        z = vec.z;
    }

    public void add(Vector3 vec) {
        x += vec.x;
        y += vec.y;
        z += vec.z;
    }

    public void sub(Vector3 vec) {
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
    }

    public void mul(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
    }

    public void div(double scalar) {
        if (scalar == 0) {
            return;
        }
        x /= scalar;
        y /= scalar;
        z /= scalar;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public void x(double x) {
        this.x = x;
    }

    public void y(double y) {
        this.y = y;
    }

    public void z(double z) {
        this.z = z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double atan2(Vector3 vec) {
        return Math.atan2(vec.y - y, vec.x - x);
    }

    public double dist2 (Vector3 pos) {
        return (Math.pow(x - pos.x, 2) + Math.pow(y - pos.y, 2));
    }

    public void copy(Vector3 pos) {
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("x", x);
        json.put("y", y);
        json.put("z", z);
        return json;
    }

    private double x;
    private double y;
    private double z;
    private static Vector3 center = new Vector3();
}
