package be.leukspul.math;

import org.json.JSONObject;

public class Direction {
    public Vector3 vector(Vector3 origin, double distance) {
        double x = (Math.cos(radians) * distance) + origin.x();
        double y = (Math.sin(radians) * distance) + origin.y();
        return new Vector3(x, y, origin.z());
    }
    public void radians(double rads) {
        this.radians = rads;
        this.heading = (radians * 65535.0) / 6.283185307179586;
    }
    public void heading(double heading) {
        this.heading = heading;
        this.radians = (heading * 6.283185307179586) / 65535.0;
    }
    public double heading() {
        return heading;
    }
    public double radians() {
        return radians;
    }
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("radians", radians);
        json.put("heading", heading);
        return json;
    }
    private double radians;
    private double heading;
}