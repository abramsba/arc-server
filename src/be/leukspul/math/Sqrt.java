package be.leukspul.math;

import java.nio.ByteBuffer;

public class Sqrt {

    private static float invsqrt0(float x)
    {
        return 1.0f / (float)Math.sqrt( x );
    }

    private static float invsqrt1(float x)
    {
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits( x );
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat( i );
        x = x * (1.5f - (xhalf * x * x));
        return x;
    }

    private static ByteBuffer buf = ByteBuffer.allocateDirect( 4 );

    private static float invsqrt2(float x)
    {
        float xhalf = 0.5f * x;
        int i = buf.putFloat( 0, x ).getInt( 0 );
        i = 0x5f3759df - (i >> 1);
        x = buf.putInt( 0, i ).getFloat( 0 );
        x = x * (1.5f - (xhalf * x * x));
        return x;
    }

    public static void main(String[] args)
    {
        float[] values = new float[ 10000 ];
        for (int i = 0; i < values.length; i++) {
            values[i] = (float)Math.random() * 1000 + 0.000001f;
        }

        float[] result = new float[ 100000 ];

        // warm-up
        for (int i = 0; i < result.length; i++) {
            float r = 0;
            long x = 0;
            for (int k = 0; k < values.length; k++) {
                r += invsqrt0( values[k] );
                x += System.nanoTime();
            }
            for (int k = 0; k < values.length; k++) {
                r += invsqrt1( values[k] );
                x += System.nanoTime();
            }
            for (int k = 0; k < values.length; k++) {
                r += invsqrt2( values[k] );
                x += System.nanoTime();
            }
            result[i] = r / x;
        }

        long t0 = 0, t1 = 0, t2 = 0, t3 = 0;
        float r = 0, g = 0, b = 0;

        // timing
        for (int i = 0; i < result.length; i++) {
            r = g = b = 0;
            t0 = System.nanoTime();
            for (int k = 0; k < values.length; k++) {
                r += invsqrt0( values[k] );
            }
            t1 = System.nanoTime();
            for (int k = 0; k < values.length; k++) {
                g += invsqrt1( values[k] );
            }
            t2 = System.nanoTime();
            for (int k = 0; k < values.length; k++) {
                b += invsqrt2( values[k] );
            }
            t3 = System.nanoTime();
        }

        System.out.println("Math.sqrt: " + (t1 - t0) + " = " + r);
        System.out.println("Fast invsqrt with Float: " + (t2 - t1) + " = " + g);
        System.out.println("Fast invsqrt with ByteBuffer: " + (t3 - t2) + " = " + b);

        float dif = 0.0f;
        for (int k = 0; k < values.length; k++) {
            float x = invsqrt0( values[k] );
            float y = invsqrt1( values[k] );
            dif += Math.abs(x - y);
        }

        System.out.println("Difference on average: " + (dif / values.length));
    }
}
