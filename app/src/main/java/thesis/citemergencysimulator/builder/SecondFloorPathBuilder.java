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
import android.widget.ImageView;

import thesis.citemergencysimulator.helpers.Device;

/**
 * Created by Dave Tolentin on 12/6/2017.
 */
public class SecondFloorPathBuilder extends View {
    private Path path;
    private Paint paint;
    private float length;
    private int height;
    private int width;
    private Context context;

    private static final String TAG = GroundFloorPathBuilder.class.getSimpleName();

    public SecondFloorPathBuilder(Context context) {
        super(context);
        this.context = context;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public SecondFloorPathBuilder(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public SecondFloorPathBuilder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void init(int direction, ImageView imgView) {
        imgView.setVisibility(View.VISIBLE);
        // Note: You cannot run all the animations
        height = Device.getScreenHeight();
        width = Device.getScreenWidth();

        Log.e(TAG, "Width: " + width + " Height: " + height);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
        if ((width == 1920 || width == 1776) && height == 1080) {
            // Samsung S5 devices
            if (direction == 0) {
                LeftCRToExit(path, 1);
                imgView.setX(Device.convertPixelsToDp(450.0f, context));
                imgView.setY(Device.convertPixelsToDp(760.0f, context));
            }

            if (direction == 1) {
                room209ToExit(path);
                imgView.setX(Device.convertPixelsToDp(1040.0f, context));
                imgView.setY(Device.convertPixelsToDp(760.0f, context));
            }

            if (direction == 2) {
                room208ToExit(path);
                imgView.setX(Device.convertPixelsToDp(1540.0f, context));
                imgView.setY(Device.convertPixelsToDp(760.0f, context));
            }

            if (direction == 3) {
                room207ToExit(path);
                imgView.setX(Device.convertPixelsToDp(2010.0f, context));
                imgView.setY(Device.convertPixelsToDp(760.0f, context));
            }

            if (direction == 4) {
                room206ToExit(path);
                imgView.setX(Device.convertPixelsToDp(2520.0f, context));
                imgView.setY(Device.convertPixelsToDp(760.0f, context));
            }

            if (direction == 5) {
                room205ToExit(path);
                imgView.setX(Device.convertPixelsToDp(3010.0f, context));
                imgView.setY(Device.convertPixelsToDp(760.0f, context));
            }

            if (direction == 6) {
                room204ToExit(path);
                imgView.setX(Device.convertPixelsToDp(3500.0f, context));
                imgView.setY(Device.convertPixelsToDp(760.0f, context));
            }

            if (direction == 7) {
                room203ToExit(path);
                imgView.setX(Device.convertPixelsToDp(3980.0f, context));
                imgView.setY(Device.convertPixelsToDp(760.0f, context));
            }

            if (direction == 8) {
                room202ToExit(path);
                imgView.setX(Device.convertPixelsToDp(4460.0f, context));
                imgView.setY(Device.convertPixelsToDp(760.0f, context));
            }

            if (direction == 9) {
                room201ToExit(path);
                imgView.setX(Device.convertPixelsToDp(4950.0f, context));
                imgView.setY(Device.convertPixelsToDp(760.0f, context));
            }
        }

        PathMeasure measure = new PathMeasure(path, false);
        length = measure.getLength();

        float[] intervals = new float[]{length, length};

        ObjectAnimator animator = ObjectAnimator.ofFloat(SecondFloorPathBuilder.this,
                "phase", 1.0f, 0.0f);
        animator.setDuration(5000);
        animator.start();
    }

    private Path LeftCRToExit(Path path, int pos) {
        if (pos == 1) {
            // Left side
            path.moveTo(Device.convertPixelsToDp(614.0f, context), Device.convertPixelsToDp(920.0f, context));
            path.lineTo(Device.convertPixelsToDp(614.0f, context), Device.convertPixelsToDp(1210.0f, context));
            path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(1210.0f, context));
            path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(790.0f, context));
            path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(790.0f, context));
            path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(1150.0f, context));
        } else {
            // Right side

        }

        return path;
    }

    private Path room209ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(970.0f, context), Device.convertPixelsToDp(1180.0f, context));
        path.lineTo(Device.convertPixelsToDp(970.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(790.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(790.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(1150.0f, context));

        return path;
    }

    private Path room208ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(1540.0f, context), Device.convertPixelsToDp(1180.0f, context));
        path.lineTo(Device.convertPixelsToDp(1540.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(790.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(790.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(1150.0f, context));

        return path;
    }

    private Path room207ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(2100.0f, context), Device.convertPixelsToDp(1180.0f, context));
        path.lineTo(Device.convertPixelsToDp(2100.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(790.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(790.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(1150.0f, context));

        return path;
    }

    private Path room206ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(2650.0f, context), Device.convertPixelsToDp(1180.0f, context));
        path.lineTo(Device.convertPixelsToDp(2650.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(790.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(790.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(1150.0f, context));

        return path;
    }

    private Path room205ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(3000.0f, context), Device.convertPixelsToDp(1180.0f, context));
        path.lineTo(Device.convertPixelsToDp(3000.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(790.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(790.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(1150.0f, context));

        return path;
    }

    private Path room204ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(3530.0f, context), Device.convertPixelsToDp(1180.0f, context));
        path.lineTo(Device.convertPixelsToDp(3530.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(900.0f, context));
        path.lineTo(Device.convertPixelsToDp(5400.0f, context), Device.convertPixelsToDp(900.0f, context));
        path.lineTo(Device.convertPixelsToDp(5400.0f, context), Device.convertPixelsToDp(1150.0f, context));

        return path;
    }

    private Path room203ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(4070.0f, context), Device.convertPixelsToDp(1180.0f, context));
        path.lineTo(Device.convertPixelsToDp(4070.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(900.0f, context));
        path.lineTo(Device.convertPixelsToDp(5400.0f, context), Device.convertPixelsToDp(900.0f, context));
        path.lineTo(Device.convertPixelsToDp(5400.0f, context), Device.convertPixelsToDp(1150.0f, context));

        return path;
    }

    private Path room202ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(4610.0f, context), Device.convertPixelsToDp(1180.0f, context));
        path.lineTo(Device.convertPixelsToDp(4610.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(900.0f, context));
        path.lineTo(Device.convertPixelsToDp(5400.0f, context), Device.convertPixelsToDp(900.0f, context));
        path.lineTo(Device.convertPixelsToDp(5400.0f, context), Device.convertPixelsToDp(1150.0f, context));

        return path;
    }

    private Path room201ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(5160.0f, context), Device.convertPixelsToDp(1180.0f, context));
        path.lineTo(Device.convertPixelsToDp(5160.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(1258.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(900.0f, context));
        path.lineTo(Device.convertPixelsToDp(5400.0f, context), Device.convertPixelsToDp(900.0f, context));
        path.lineTo(Device.convertPixelsToDp(5400.0f, context), Device.convertPixelsToDp(1150.0f, context));

        return path;
    }

    // is called by animator object
    public void setPhase(float phase) {
        Log.e(TAG,"setPhase called with:" + String.valueOf(phase));
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
            c.drawPath(path, paint);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e(TAG, "On draw");
    }
}