package thesis.citemergencysimulator.builder;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import thesis.citemergencysimulator.helpers.DeviceHelper;

/**
 * Created by Dave Tolentin on 10/28/2017.
 */

public class GroundFloorPathBuilder extends View {
    private Path path;
    private Paint paint;
    private float length;
    private int height;
    private int width;

    private static final String TAG = GroundFloorPathBuilder.class.getSimpleName();

    public GroundFloorPathBuilder(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public GroundFloorPathBuilder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroundFloorPathBuilder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        height = DeviceHelper.getScreenHeight();
        width = DeviceHelper.getScreenWidth();

        Log.e(TAG, "Width: "+width+" Height: "+height);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
        for (int i = 0; i < 2; i++) {
            if (width == 1920 && height == 1080) {
                // Samsung S5 devices
                // 1st level path
                firstPath(path); // Ok
                // secondPath(path);
            }
        }

        // Measure the path
        for (int i = 0; i < 2; i++) {
            PathMeasure measure = new PathMeasure(path, false);
            length = measure.getLength();
        }

        float[] intervals = new float[]{length, length};

        ObjectAnimator animator = ObjectAnimator.ofFloat(GroundFloorPathBuilder.this,
                "phase", 1.0f, 0.0f);
        animator.setDuration(5000);
        animator.start();
    }

    private Path firstPath(Path path) {
        path.moveTo(80.0f, 290.0f);
        path.lineTo(370.0f, 290.0f);
        path.lineTo(422.0f, 290.0f);
        path.lineTo(422.0f, 340.0f);
        path.lineTo(122.0f, 524.0f);
        path.lineTo(80.0f, 524.0f);
        path.lineTo(80.0f, 600.0f);
        return path;
    }

    private Path secondPath(Path path) {
        path.moveTo(690.0f, 290.0f);
        path.lineTo(439.0f, 290.0f);
        // path.lineTo(422.0f, 290.0f);
        return path;
    }

    public Path makePath() {
        Path path = new Path();
        path.moveTo(90, 290);
        path.lineTo(370, 290);
        path.lineTo(422, 290);
        path.lineTo(450, 700);

        return path;
    }

    // is called by animator object
    public void setPhase(float phase) {
        Log.d("pathview","setPhase called with:" + String.valueOf(phase));
        paint.setPathEffect(createPathEffect(length, phase, 0.0f));
        invalidate(); // will call onDraw
    }

    private static PathEffect createPathEffect(float pathLength, float phase, float offset) {
        return new DashPathEffect(new float[] { pathLength, pathLength },
                Math.max(phase * pathLength, offset));
    }

    private Path makeConvexArrow(float length, float height) {
        Path p = new Path();
        p.moveTo(0.0f, -height / 2.0f);
        p.lineTo(length - height / 4.0f, -height / 2.0f);
        p.lineTo(length, 0.0f);
        p.lineTo(length - height / 4.0f, height / 2.0f);
        p.lineTo(0.0f, height / 2.0f);
        p.lineTo(0.0f + height / 4.0f, 0.0f);
        p.close();
        return p;
    }

    private static Path makeArrow(float length, float height) {
        Path p = new Path();
        p.moveTo(-2.0f, 0.0f);
        p.lineTo(length, height / 2.0f);
        p.lineTo(-2.0f, height);
        p.lineTo(-2.0f, 0.0f);
        p.close();
        return p;
    }

    private PathEffect pathEffect() {
        // Stamp a concave arrow along the line
        PathEffect effect = new PathDashPathEffect(
                makeConvexArrow(24.0f, 12.0f),    // "stamp"
                36.0f,                            // advance, or distance between two stamps
                0.0f,                             // phase, or offset before the first stamp
                PathDashPathEffect.Style.ROTATE); // how to transform each stamp
        return effect;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        try {
            // paint.setPathEffect(pathEffect());
            // paint.setStyle(Paint.Style.STROKE);
            c.drawPath(path, paint);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e(TAG, "On draw");
    }
}
