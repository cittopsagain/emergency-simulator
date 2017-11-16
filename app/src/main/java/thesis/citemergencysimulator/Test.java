package thesis.citemergencysimulator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Dave Tolentin on 11/12/2017.
 */
public class Test extends View {

    private float mDrag;

    private MyPath path1;

    private int mDuration;

    //just some shape
    private static Path makeDragPath(int radius) {
        Path p = new Path();
        RectF oval = new RectF(10.0f, 10.0f, radius * 2.0f, radius * 2.0f);

        float cx = oval.centerX();
        float cy = oval.centerY();
        float rx = oval.width() / 2.0f;
        float ry = oval.height() / 2.0f;

        final float TAN_PI_OVER_8 = 0.414213562f;
        final float ROOT_2_OVER_2 = 0.707106781f;

        float sx = rx * TAN_PI_OVER_8;
        float sy = ry * TAN_PI_OVER_8;
        float mx = rx * ROOT_2_OVER_2;
        float my = ry * ROOT_2_OVER_2;

        float L = oval.left;
        float T = oval.top;
        float R = oval.right;
        float B = oval.bottom;

        p.moveTo(R, cy);
        p.quadTo(      R, cy + sy, cx + mx, cy + my);
        p.quadTo(cx + sx, B, cx, B);
        p.quadTo(cx - sx,       B, cx - mx, cy + my);
        p.quadTo(L, cy + sy, L, cy);
        p.quadTo(      L, cy - sy, cx - mx, cy - my);
        p.quadTo(cx - sx, T, cx, T);


        return p;
    }




    public Test(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }




    public static class MyPath {
        private static final Region sRegion = new Region();
        private static final Region sMaxClip = new Region(
                Integer.MIN_VALUE, Integer.MIN_VALUE,
                Integer.MAX_VALUE, Integer.MAX_VALUE);

        final Path path;
        final Paint paint;
        final float length;
        final Rect bounds;

        MyPath(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;

            PathMeasure measure = new PathMeasure(path, false);
            this.length = measure.getLength();

            sRegion.setPath(path, sMaxClip);
            bounds = sRegion.getBounds();
        }


    }


    private static PathEffect createPathEffect2(float pathLength, float phase) {

        //I modified the original approach using phase, to use only path instead because later I want to animate also the starting point and phase alone is not enough for this

        float full = phase * pathLength;

        return new DashPathEffect(new float[] {full, Float.MAX_VALUE}, //on, off
                0);
    }

    ObjectAnimator current;


    public void startAnim() {
        if (current != null) {
            current.cancel();
        }

        current = ObjectAnimator.ofFloat(Test.this, "drag", 0.0f, 1.0f).setDuration(mDuration);
        current.start();
    }

    private void scalePath(Path path, float scale) {
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scale, scale);
        path.transform(scaleMatrix);
    }

    private void init() {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeWidth(8.0f);
        paint.setColor(Color.RED);

        mDuration = 3000;

        Path p1 = makeDragPath(40);
        scalePath(p1, 3);

        path1 = new MyPath(p1, paint);


        startAnim();
    }

    public float getDrag() {
        return mDrag;
    }

    public void setDrag(float drag) {

        mDrag = drag;

        path1.paint.setPathEffect(createPathEffect2(path1.length, mDrag));

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // canvas.drawColor(Color.BLACK); //doesn't help

        canvas.drawPath(path1.path, path1.paint);
    }

}
