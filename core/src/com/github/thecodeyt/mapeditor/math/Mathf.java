package com.github.thecodeyt.mapeditor.math;

import com.badlogic.gdx.math.Vector2;

public class Mathf {
    public static double distance(Vector2 p0, Vector2 p1) {
        Vector2 delta = abs(delta(p0, p1));

        return Math.sqrt(Math.pow(delta.x, 2) + Math.pow(delta.y, 2));
    }
    public static Vector2 delta(Vector2 p0, Vector2 p1) {
        float delta_x = p1.x - p0.x;
        float delta_y = p1.y - p0.y;

        return new Vector2(delta_x, delta_y);
    }
    public static Vector2 abs(Vector2 vector) {
        return new Vector2(Math.abs(vector.x), Math.abs(vector.y));
    }
    public static boolean isColliding(Vector2 p, Vector2 p0, Vector2 p1) {
        if(p.x >= p0.x && p.x <= p1.x) {
            if(p.y >= p0.y && p.y <= p1.y) {
                return true;
            }
        }
        return false;
    }
    public static float align(float number, float interval) {
        number /= interval;
        number = Math.round(number);
        number *= interval;
        return number;
    }
    public static Vector2 align(Vector2 vector, float interval) {
        vector.x = align(vector.x, interval);
        vector.y = align(vector.y, interval);
        return vector;
    }
}
