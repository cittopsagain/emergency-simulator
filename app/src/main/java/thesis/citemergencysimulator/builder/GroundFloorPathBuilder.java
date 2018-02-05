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
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import thesis.citemergencysimulator.helpers.Device;

/**
 * Created by Dave Tolentin on 10/28/2017.
 */

public class GroundFloorPathBuilder extends View {
    private Path path;
    private Paint paint;
    private float length;
    private int height;
    private int width;
    private Context context;

    private static final String TAG = GroundFloorPathBuilder.class.getSimpleName();

    public GroundFloorPathBuilder(Context context) {
        super(context);
        this.context = context;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public GroundFloorPathBuilder(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public GroundFloorPathBuilder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void init(int direction, ImageView imgView) {
        imgView.setVisibility(View.VISIBLE);
        // Note: You cannot run all the animations
        height = Device.getScreenHeight();
        width = Device.getScreenWidth();

        // Bring the marker at the top of button
        imgView.bringToFront();
        /*imgView.invalidate();*/
        // ViewCompat.setTranslationZ(imgView, 0.0f);

        Log.e(TAG, "Width: "+width+" Height: "+height);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
        if ((width == 1920 || width == 1776) && height == 1080) {
            // Samsung S5 devices
            if (direction == 0) {
                elementaryLibToExit(path, 1);
                imgView.setX(Device.convertPixelsToDp(270.0f, context));
                imgView.setY(Device.convertPixelsToDp(480.0f, context));
            }

            if (direction == 1) {
                Log.e(TAG, "Init: "+direction);
                room112ToExit(path);
                imgView.setX(Device.convertPixelsToDp(900.0f, context));
                imgView.setY(Device.convertPixelsToDp(480.0f, context));
            }

            if (direction == 2) {
                room111ToExit(path);
                imgView.setX(Device.convertPixelsToDp(1230.0f, context));
                imgView.setY(Device.convertPixelsToDp(480.0f, context));
            }

            if (direction == 3) {
                room110ToExit(path);
                imgView.setX(Device.convertPixelsToDp(1560.0f, context));
                imgView.setY(Device.convertPixelsToDp(480.0f, context));
            }

            if (direction == 4) {
                room109ToExit(path);
                imgView.setX(Device.convertPixelsToDp(1890.0f, context));
                imgView.setY(Device.convertPixelsToDp(480.0f, context));
            }

            if (direction == 5) {
                stairsToExit(path, 1);
                imgView.setVisibility(View.GONE);
            }

            if (direction == 6) {
                room108ToExit(path);
                imgView.setX(Device.convertPixelsToDp(2478.0f, context));
                imgView.setY(Device.convertPixelsToDp(480.0f, context));
            }

            if (direction == 7) {
                room107ToExit(path);
                imgView.setX(Device.convertPixelsToDp(2880.0f, context));
                imgView.setY(Device.convertPixelsToDp(480.0f, context));
            }

            if (direction == 8) {
                room106ToExit(path);
                imgView.setX(Device.convertPixelsToDp(3280.0f, context));
                imgView.setY(Device.convertPixelsToDp(480.0f, context));
            }

            if (direction == 9) {
                room105ToExit(path);
                imgView.setX(Device.convertPixelsToDp(3680.0f, context));
                imgView.setY(Device.convertPixelsToDp(480.0f, context));
            }

            if (direction == 10) {
                stairsToExit(path, 0);
                imgView.setVisibility(View.GONE);
            }

            if (direction == 11) {
                elementaryLibToExit(path, 0);
                imgView.setX(Device.convertPixelsToDp(4350, context));
                imgView.setY(Device.convertPixelsToDp(480.0f, context));
            }

            if (direction == 12) {
                FEIERRACP(path);
                imgView.setX(Device.convertPixelsToDp(5240.0f, context));
                imgView.setY(Device.convertPixelsToDp(700.0f, context));
            }
        }

        PathMeasure measure = new PathMeasure(path, false);
        length = measure.getLength();

        float[] intervals = new float[]{length, length};

        ObjectAnimator animator = ObjectAnimator.ofFloat(GroundFloorPathBuilder.this,
                "phase", 1.0f, 0.0f);
        animator.setDuration(3000);
        animator.start();
    }

    private Path stairsToExit(Path path, int pos) {
        if (pos == 1) {
            // Left side
            path.moveTo(Device.convertPixelsToDp(788.0f, context), Device.convertPixelsToDp(812.0f, context));
            path.lineTo(Device.convertPixelsToDp(788.0f, context), Device.convertPixelsToDp(550.0f, context));
            path.lineTo(Device.convertPixelsToDp(720.0f, context), Device.convertPixelsToDp(550.0f, context));
            path.lineTo(Device.convertPixelsToDp(720.0f, context), Device.convertPixelsToDp(886.0f, context));
            path.lineTo(Device.convertPixelsToDp(1277.0f, context), Device.convertPixelsToDp(886.0f, context));
            LeftTail(path);

            /*path.lineTo(Device.convertPixelsToDp(1260.0f, context), Device.convertPixelsToDp(880.0f, context));
            path.lineTo(Device.convertPixelsToDp(1260.0f, context), Device.convertPixelsToDp(1000.0f, context));
            path.lineTo(Device.convertPixelsToDp(400.0f, context), Device.convertPixelsToDp(1590.0f, context));
            path.lineTo(Device.convertPixelsToDp(230.0f, context), Device.convertPixelsToDp(1590.0f, context));
            path.lineTo(Device.convertPixelsToDp(230.0f, context), Device.convertPixelsToDp(1900.0f, context));*/
        } else {
            // Right side
            path.moveTo(Device.convertPixelsToDp(4000.0f, context), Device.convertPixelsToDp(822.0f, context));
            path.lineTo(Device.convertPixelsToDp(4000.0f, context), Device.convertPixelsToDp(550.0f, context));
            path.lineTo(Device.convertPixelsToDp(4050.0f, context), Device.convertPixelsToDp(550.0f, context));
            path.lineTo(Device.convertPixelsToDp(4050.0f, context), Device.convertPixelsToDp(1000.0f, context));

            path.lineTo(Device.convertPixelsToDp(4050, context), Device.convertPixelsToDp(1040.0f, context));
            path.lineTo(Device.convertPixelsToDp(4230.0f, context), Device.convertPixelsToDp(1350.0f, context));
            path.lineTo(Device.convertPixelsToDp(4230.0f, context), Device.convertPixelsToDp(1670.0f, context));
            path.lineTo(Device.convertPixelsToDp(2510, context), Device.convertPixelsToDp(1670.0f, context));
            path.lineTo(Device.convertPixelsToDp(2510.0f, context), Device.convertPixelsToDp(1790.0f, context));
        }

        return path;
    }

    private Path rightSideElemLibDoor1(Path path) {
        path.moveTo(Device.convertPixelsToDp(4180.0f, context), Device.convertPixelsToDp(838.0f, context));
        path.lineTo(Device.convertPixelsToDp(4180.0f, context), Device.convertPixelsToDp(886.0f, context));
        path.lineTo(Device.convertPixelsToDp(4050.0f, context), Device.convertPixelsToDp(886.0f, context));

        path.lineTo(Device.convertPixelsToDp(4050, context), Device.convertPixelsToDp(1040.0f, context));
        path.lineTo(Device.convertPixelsToDp(4230.0f, context), Device.convertPixelsToDp(1350.0f, context));
        path.lineTo(Device.convertPixelsToDp(4230.0f, context), Device.convertPixelsToDp(1670.0f, context));
        path.lineTo(Device.convertPixelsToDp(2510, context), Device.convertPixelsToDp(1670.0f, context));
        path.lineTo(Device.convertPixelsToDp(2510.0f, context), Device.convertPixelsToDp(1790.0f, context));

        return path;
    }

    private Path rightSideElemLibDoor2(Path path) {
        path.moveTo(Device.convertPixelsToDp(4630.0f, context), Device.convertPixelsToDp(838.0f, context));
        path.lineTo(Device.convertPixelsToDp(4630.0f, context), Device.convertPixelsToDp(886.0f, context));
        path.lineTo(Device.convertPixelsToDp(4050.0f, context), Device.convertPixelsToDp(886.0f, context));

        return path;
    }

    private Path rightSideElemLibDoor3(Path path) {
        path.moveTo(Device.convertPixelsToDp(4880.0f, context), Device.convertPixelsToDp(838.0f, context));
        path.lineTo(Device.convertPixelsToDp(4880.0f, context), Device.convertPixelsToDp(886.0f, context));
        path.lineTo(Device.convertPixelsToDp(4050.0f, context), Device.convertPixelsToDp(886.0f, context));

        return path;
    }

    private Path elementaryLibToExit(Path path, int pos) {
        if (pos == 1) {
            // Left side
            path.moveTo(Device.convertPixelsToDp(264.0f, context), Device.convertPixelsToDp(829.0f, context));
            path.lineTo(Device.convertPixelsToDp(264.0f, context), Device.convertPixelsToDp(886.0f, context));
            path.lineTo(Device.convertPixelsToDp(1277.0f, context), Device.convertPixelsToDp(886.0f, context));
            LeftTail(path);
        } else {
            // Right side
            rightSideElemLibDoor1(path);
            rightSideElemLibDoor2(path);
            rightSideElemLibDoor3(path);
        }

        return path;
    }

    // Elementary Lib left
    // Left stairs
    // Room 112
    // Room 111
    private Path LeftTail(Path path) {
        path.lineTo(Device.convertPixelsToDp(1277.0f, context), Device.convertPixelsToDp(1070.0f, context));
        path.lineTo(Device.convertPixelsToDp(400.0f, context), Device.convertPixelsToDp(1590.0f, context));
        path.lineTo(Device.convertPixelsToDp(188.0f, context), Device.convertPixelsToDp(1590.0f, context));
        path.lineTo(Device.convertPixelsToDp(188.0f, context), Device.convertPixelsToDp(1790.0f, context));

        return path;
    }

    // Room110
    // Room 109
    private Path RightTail(Path path) {
        path.lineTo(Device.convertPixelsToDp(1314.0f, context), Device.convertPixelsToDp(1080.0f, context));
        path.lineTo(Device.convertPixelsToDp(2204.0f, context), Device.convertPixelsToDp(1080.0f, context));
        path.lineTo(Device.convertPixelsToDp(2204.0f, context), Device.convertPixelsToDp(1790.0f, context));

        return path;
    }

    // Room 108
    // Room 107
    // Room 106
    // Room 105
    private Path Room8765LeftTail(Path path) {
        path.lineTo(Device.convertPixelsToDp(2290.0f, context), Device.convertPixelsToDp(1790.0f, context));

        return path;
    }

    private Path room112ToExit(Path path) {
        // Arrow head
        /*int p0[] = {50, 100};
        int p1[] = {250, 50};
        int headLength = 15;

        double PI = Math.PI;

        double degreesInRadians225 = 225 * PI / 180;
        double degreesInRadians135 = 135 * PI / 180;

        int dx = p1[0] - p0[0];
        int dy = p1[1] - p0[1];
        double angle = Math.atan2(dy, dx);

        double x225 = p1[0] + headLength*Math.cos(angle + degreesInRadians225);
        double y225 = p1[1] + headLength*Math.sin(angle + degreesInRadians225);
        double x135 = p1[0] + headLength*Math.cos(angle + degreesInRadians135);
        double y135 = p1[1] + headLength*Math.sin(angle + degreesInRadians135);

        path.moveTo(p0[0], p0[1]);
        path.lineTo(p1[0], p1[1]);

        // draw partial arrowhead at 225 degrees
        path.moveTo(p1[0], p1[1]);
        path.lineTo((float)x225, (float)y225);

        // draw partial arrowhead at 135 degrees
        path.moveTo(p1[0], p1[1]);
        path.lineTo((float)x135, (float)y135);*/

        path.moveTo(Device.convertPixelsToDp(902.0f, context), Device.convertPixelsToDp(838.0f, context));
        path.lineTo(Device.convertPixelsToDp(902.0f, context), Device.convertPixelsToDp(886.0f, context));
        path.lineTo(Device.convertPixelsToDp(1277.0f, context), Device.convertPixelsToDp(886.0f, context));
        LeftTail(path);

        return path;
    }

    private Path room111ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(1277.0f, context), Device.convertPixelsToDp(838.0f, context));
        path.lineTo(Device.convertPixelsToDp(1277.0f, context), Device.convertPixelsToDp(886.0f, context));
        LeftTail(path);

        return path;
    }

    private Path room110ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(1569.0f, context), Device.convertPixelsToDp(838.0f, context));
        path.lineTo(Device.convertPixelsToDp(1569.0f, context), Device.convertPixelsToDp(886.0f, context));
        path.lineTo(Device.convertPixelsToDp(1314.0f, context), Device.convertPixelsToDp(886.0f, context));
        RightTail(path);

        return path;
    }

    private Path room109ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(1960.0f, context), Device.convertPixelsToDp(838.0f, context));
        path.lineTo(Device.convertPixelsToDp(1960.0f, context), Device.convertPixelsToDp(886.0f, context));
        path.lineTo(Device.convertPixelsToDp(1314.0f, context), Device.convertPixelsToDp(886.0f, context));
        RightTail(path);

        return path;
    }

    private Path room108ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(2700.0f, context), Device.convertPixelsToDp(838.0f, context));
        path.lineTo(Device.convertPixelsToDp(2700.0f, context), Device.convertPixelsToDp(886.0f, context));
        path.lineTo(Device.convertPixelsToDp(3100.0f, context), Device.convertPixelsToDp(886.0f, context));
        path.lineTo(Device.convertPixelsToDp(3100.0f, context), Device.convertPixelsToDp(1080.0f, context));
        path.lineTo(Device.convertPixelsToDp(2290.0f, context), Device.convertPixelsToDp(1080.0f, context));
        Room8765LeftTail(path);

        return path;
    }

    private Path room107ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(3100.0f, context), Device.convertPixelsToDp(838.0f, context));
        path.lineTo(Device.convertPixelsToDp(3100.0f, context), Device.convertPixelsToDp(886.0f, context));
        path.lineTo(Device.convertPixelsToDp(3100.0f, context), Device.convertPixelsToDp(1080.0f, context));
        path.lineTo(Device.convertPixelsToDp(2290.0f, context), Device.convertPixelsToDp(1080.0f, context));
        Room8765LeftTail(path);

        return path;
    }

    private Path room106ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(3460.0f, context), Device.convertPixelsToDp(838.0f, context));
        path.lineTo(Device.convertPixelsToDp(3460.0f, context), Device.convertPixelsToDp(886.0f, context));
        path.lineTo(Device.convertPixelsToDp(3200.0f, context), Device.convertPixelsToDp(886.0f, context));
        path.lineTo(Device.convertPixelsToDp(3200.0f, context), Device.convertPixelsToDp(1080.0f, context));
        path.lineTo(Device.convertPixelsToDp(2290.0f, context), Device.convertPixelsToDp(1080.0f, context));
        Room8765LeftTail(path);

        return path;
    }

    private Path room105ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(3900.0f, context), Device.convertPixelsToDp(838.0f, context));
        path.lineTo(Device.convertPixelsToDp(3900.0f, context), Device.convertPixelsToDp(886.0f, context));
        path.lineTo(Device.convertPixelsToDp(3200.0f, context), Device.convertPixelsToDp(886.0f, context));
        path.lineTo(Device.convertPixelsToDp(3200.0f, context), Device.convertPixelsToDp(1080.0f, context));
        path.lineTo(Device.convertPixelsToDp(2290.0f, context), Device.convertPixelsToDp(1080.0f, context));
        Room8765LeftTail(path);

        return path;
    }

    private Path FEIERRACPDoorFacultyLoungeDoor(Path path) {
        path.moveTo(Device.convertPixelsToDp(5200.0f, context), Device.convertPixelsToDp(540.0f, context));
        path.lineTo(Device.convertPixelsToDp(5200.0f, context), Device.convertPixelsToDp(488.0f, context));
        path.lineTo(Device.convertPixelsToDp(5000.0f, context), Device.convertPixelsToDp(488.0f, context));
        path.lineTo(Device.convertPixelsToDp(5000.0f, context), Device.convertPixelsToDp(1270.0f, context));

        path.lineTo(Device.convertPixelsToDp(4230.0f, context), Device.convertPixelsToDp(1550.0f, context));
        path.lineTo(Device.convertPixelsToDp(4230.0f, context), Device.convertPixelsToDp(1670.0f, context));

        path.lineTo(Device.convertPixelsToDp(2510, context), Device.convertPixelsToDp(1670.0f, context));
        path.lineTo(Device.convertPixelsToDp(2510.0f, context), Device.convertPixelsToDp(1790.0f, context));


        return path;
    }

    private Path FEIERRACPDoorFacultyInfirmaryDoor(Path path) {
        path.moveTo(Device.convertPixelsToDp(5520.0f, context), Device.convertPixelsToDp(540.0f, context));
        path.lineTo(Device.convertPixelsToDp(5520.0f, context), Device.convertPixelsToDp(488.0f, context));
        path.lineTo(Device.convertPixelsToDp(5000.0f, context), Device.convertPixelsToDp(488.0f, context));

        return path;
    }

    private Path FEIERRACP(Path path) {
        // Faculty Lounge
        // Elementary Clinic
        // Infirmary
        // Encoding area
        // Registrar
        // Reception area
        // Assistant principal office
        // Conference room
        // Principals office
        FEIERRACPDoorFacultyLoungeDoor(path);
        FEIERRACPDoorFacultyInfirmaryDoor(path);

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
                makeArrow(24.0f, 12.0f),    // "stamp"
                26.0f,                            // advance, or distance between two stamps (36) <== Default
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
            c.drawPath(path, paint);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e(TAG, "On draw");
    }
}
