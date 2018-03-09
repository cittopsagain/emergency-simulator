package thesis.citemergencysimulator.builder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import java.util.ArrayList;

import thesis.citemergencysimulator.DashboardActivity;
import thesis.citemergencysimulator.GroundFloorFragment;
import thesis.citemergencysimulator.R;
import thesis.citemergencysimulator.SecondFloorFragment;
import thesis.citemergencysimulator.areas.SecondFloor;
import thesis.citemergencysimulator.helpers.Device;
import thesis.citemergencysimulator.helpers.ZoomLayout;

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
    private int position;

    private ObjectAnimator animator;

    private ArrayList<Float> animatedValueList = new ArrayList<>();
    private ArrayList<String> startList = new ArrayList<>();
    private ArrayList<String> endList = new ArrayList<>();
    private ArrayList<String> runList = new ArrayList<>();
    private ArrayList<Integer> dirList = new ArrayList<>();

    private ArrayList<String> animationList = new ArrayList<>();

    private static final String TAG = GroundFloorPathBuilder.class.getSimpleName();

    public interface CallFragment {
        void showGroundFloor(int position);
        void finishDrawing(int position, ArrayList<String> arrayList);
    }

    private static CallFragment callFragment = null;

    public SecondFloorPathBuilder(Context context, CallFragment callFragment, String p, String p1) {
        super(context);
        this.context = context;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        this.callFragment = callFragment;
    }

    public SecondFloorPathBuilder(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public SecondFloorPathBuilder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private float getX(float x) {
        // return (Device.getScreenWidth() / Device.DEFAULT_SCREEN_WIDTH) * x;
        return (Device.DEFAULT_SCREEN_WIDTH / Device.getScreenWidth()) * x;
    }

    private float getY(float y) {
        // return (Device.getScreenHeight() / Device.DEFAULT_SCREEN_HEIGHT) * y;
        return (Device.DEFAULT_SCREEN_HEIGHT / Device.getScreenHeight()) * y;
    }

    public void init(int direction, ImageView imgView) {
        imgView.setVisibility(View.VISIBLE);
        // Note: You cannot run all the animations
        height = Device.getScreenHeight();
        width = Device.getScreenWidth();

        // Stopping the animation, useful when tapping multiple rooms
        try {
            if (animator.isRunning()) {
                animator.end();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        Log.e(TAG, "Width: " + width + " Height: " + height);

        if (animationList.size() > 0) {
            for (int i = 0; i < animationList.size(); i++) {
                if (animationList.get(i).equals("END")) {
                    animationList.set(i, "START");
                }
            }
        }

        // Bring the marker at the top of button
        imgView.bringToFront();

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(6);
        paint.setStyle(Paint.Style.STROKE);

        position = direction;

        path = new Path();
        if ((width == 1920 || width == 1776) && height == 1080) {
            // Samsung S5 devices
            if (direction == 0) {
                LeftCRToExit(path, 1);
                imgView.setX(Device.convertPixelsToDp(450.0f, context));
                imgView.setY(Device.convertPixelsToDp(540.0f, context));
            }

            if (direction == 1) {
                room209ToExit(path);
                imgView.setX(Device.convertPixelsToDp(1040.0f, context));
                imgView.setY(Device.convertPixelsToDp(540.0f, context));
            }

            if (direction == 2) {
                room208ToExit(path);
                imgView.setX(Device.convertPixelsToDp(1540.0f, context));
                imgView.setY(Device.convertPixelsToDp(540.0f, context));
            }

            if (direction == 3) {
                room207ToExit(path);
                imgView.setX(Device.convertPixelsToDp(2010.0f, context));
                imgView.setY(Device.convertPixelsToDp(540.0f, context));
            }

            if (direction == 4) {
                room206ToExit(path);
                imgView.setX(Device.convertPixelsToDp(2520.0f, context));
                imgView.setY(Device.convertPixelsToDp(540.0f, context));
            }

            if (direction == 5) {
                room205ToExit(path);
                imgView.setX(Device.convertPixelsToDp(3010.0f, context));
                imgView.setY(Device.convertPixelsToDp(540.0f, context));
            }

            if (direction == 6) {
                room204ToExit(path);
                imgView.setX(Device.convertPixelsToDp(3500.0f, context));
                imgView.setY(Device.convertPixelsToDp(540.0f, context));
            }

            if (direction == 7) {
                room203ToExit(path);
                imgView.setX(Device.convertPixelsToDp(3980.0f, context));
                imgView.setY(Device.convertPixelsToDp(540.0f, context));
            }

            if (direction == 8) {
                room202ToExit(path);
                imgView.setX(Device.convertPixelsToDp(4460.0f, context));
                imgView.setY(Device.convertPixelsToDp(540.0f, context));
            }

            if (direction == 9) {
                room201ToExit(path);
                imgView.setX(Device.convertPixelsToDp(4950.0f, context));
                imgView.setY(Device.convertPixelsToDp(540.0f, context));
            }

            if (direction == 10) {
                // Left stairs
                position = 5;
                imgView.setVisibility(View.GONE); // Hide the marker
                leftStairs(path);
            }

            if (direction == 11) {
                // Right stairs
                position = 11;
                imgView.setVisibility(View.GONE); // Hide the marker
                rightStairs(path);
            }
        }

        PathMeasure measure = new PathMeasure(path, false);
        length = measure.getLength();

        float[] intervals = new float[]{length, length};

        /*animator = ObjectAnimator.ofFloat(SecondFloorPathBuilder.this,
                "phase", 1.0f, 0.0f);
        animator.setDuration(3000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationList.size() > 0) {
                    for (int i = 0; i < animationList.size(); i++) {
                        if (animationList.get(i).equals("END")) {
                            animationList.set(i, "START");
                        }
                    }
                }

                super.onAnimationEnd(animation);

                animationList.add("END");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (animationList.size() > 0) {
                    Log.e(TAG, "Last animation: "+animationList.get(animationList.size() - 1));
                    // animationList.set(animationList.size() - 1, "START");
                    for (int i = 0; i < animationList.size(); i++) {
                        if (animationList.get(i).equals("END")) {
                            animationList.set(i, "START");
                        }
                    }
                }

                super.onAnimationStart(animation);

                animationList.add("START");

                if (animationList.size() > 0) {
                    Log.e(TAG, "Last animation: "+animationList.get(animationList.size() - 1));
                    // animationList.set(animationList.size() - 1, "START");
                    for (int i = 0; i < animationList.size(); i++) {
                        if (animationList.get(i).equals("END")) {
                            animationList.set(i, "START");
                        }
                    }
                }
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }
        });
        animator.start();*/

        animator = ObjectAnimator.ofFloat(SecondFloorPathBuilder.this,
                "phase", 1.0f, 0.0f);
        // animator.setRepeatCount(ValueAnimator.INFINITE); // Infinite animation
        animator.setDuration(3000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                Log.e(TAG, "Animation Cancel");

                if (dirList.size() > 1) {
                    // dirList.clear();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationList.size() > 0) {
                    for (int i = 0; i < animationList.size(); i++) {
                        if (animationList.get(i).equals("END")) {
                            animationList.set(i, "START");
                        }
                    }
                }

                super.onAnimationEnd(animation);
                Log.e(TAG, "Animation End");
                Log.e(TAG, "Is running: "+animation.isRunning());
                endList.add("END");

                for (int i = 0; i < animationList.size(); i++) {
                    Log.e(TAG, "On animation end: "+animationList.get(i));
                }

                animationList.add("END");

                Log.e(TAG, "On animation end: "+animationList.get(animationList.size() - 1));

                for (int i = 0; i < animationList.size(); i++) {
                    Log.e(TAG, "Final Animation List: "+animationList.get(i));
                }

                if (startList.size() != 1 && endList.size() != 1) {
                    if (startList.size() == endList.size()) {

                        // callFragment.showSecondFloor(position, dirList);

                        /*Log.e(TAG, "List End Trigger Second Floor: "+endList.size());
                        showSecondFlor = true;*/
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Log.e(TAG, "Call Second Floor :( Called after 5 seconds!");
                                runList.add("RUN");
                            }
                        }, 7000);

                        Log.e(TAG, "Call Second Floor :( Need to wait for about 5 seconds!");
                    }
                }
                Log.e(TAG, "List End: "+endList.size());
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                Log.e(TAG, "Animation Repeat");
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (animationList.size() > 0) {
                    Log.e(TAG, "Last animation: "+animationList.get(animationList.size() - 1));
                    // animationList.set(animationList.size() - 1, "START");
                    for (int i = 0; i < animationList.size(); i++) {
                        if (animationList.get(i).equals("END")) {
                            animationList.set(i, "START");
                        }
                    }
                }

                super.onAnimationStart(animation);
                startList.add("START");
                Log.e(TAG, "Animation Start");
                Log.e(TAG, "List Start: "+startList.size());

                animationList.add("START");

                if (animationList.size() > 0) {
                    Log.e(TAG, "Last animation: "+animationList.get(animationList.size() - 1));
                    // animationList.set(animationList.size() - 1, "START");
                    for (int i = 0; i < animationList.size(); i++) {
                        if (animationList.get(i).equals("END")) {
                            animationList.set(i, "START");
                        }
                    }
                }

                if (dirList.size() > 1) {
                    // dirList.clear();
                }
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
                Log.e(TAG, "Animation Resume ");
            }
        });
        animator.start();

        Log.e(TAG, "Animated Duration: ");

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e(TAG, "Add update listener: "+animation.getAnimatedValue());
                animatedValueList.add((Float)animation.getAnimatedValue());
                if (animationList.size() > 0) {
                    for (int i = 0; i < animationList.size(); i++) {
                        if (animationList.get(i).equals("END")) {
                            animationList.set(i, "START");
                        }
                    }
                }
            }
        });

        for (int i = 0; i < animatedValueList.size(); i++) {
            Log.e(TAG, "Value List: "+i+" << "+animatedValueList.get(i)+" >> Trigger second floor");
            try {
                /*if (animatedValueList.get((i + 1)).isNaN()) {
                    Log.e(TAG, "Value List: "+i+" << "+animatedValueList.get(i)+" >> Trigger second floor");
                } else {
                    Log.e(TAG, "Value List: "+i+" << "+animatedValueList.get(i));
                }*/
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        for (int i = 0; i < animationList.size(); i++) {
            // Log.e(TAG, "Outside animation: "+animationList.get(i));
        }
        // Log.e(TAG, "Get Animation: "+this.getX());
    }

    private Path LeftCRToExit(Path path, int pos) {
        if (pos == 1) {
            // Left side
            path.moveTo(Device.convertPixelsToDp(614.0f, context), Device.convertPixelsToDp(700.0f, context));
            path.lineTo(Device.convertPixelsToDp(614.0f, context), Device.convertPixelsToDp(1050.0f, context));
            path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(1050.0f, context));
            path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(600.0f, context));
            path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(600.0f, context));
            path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(960.0f, context));
        } else {
            // Right side

        }

        return path;
    }

    private Path room209ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(970.0f, context), Device.convertPixelsToDp(980.0f, context));
        path.lineTo(Device.convertPixelsToDp(970.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(600.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(600.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(960.0f, context));

        return path;
    }

    private Path room208ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(1530.0f, context), Device.convertPixelsToDp(980.0f, context));
        path.lineTo(Device.convertPixelsToDp(1530.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(600.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(600.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(960.0f, context));

        return path;
    }

    private Path room207ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(2090.0f, context), Device.convertPixelsToDp(980.0f, context));
        path.lineTo(Device.convertPixelsToDp(2090.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(600.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(600.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(960.0f, context));

        return path;
    }

    private Path room206ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(2640.0f, context), Device.convertPixelsToDp(980.0f, context));
        path.lineTo(Device.convertPixelsToDp(2640.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(600.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(600.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(960.0f, context));

        return path;
    }

    private Path room205ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(3010.0f, context), Device.convertPixelsToDp(980.0f, context));
        path.lineTo(Device.convertPixelsToDp(3010.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(600.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(600.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(960.0f, context));

        return path;
    }

    private Path room204ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(3540.0f, context), Device.convertPixelsToDp(980.0f, context));
        path.lineTo(Device.convertPixelsToDp(3540.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(700.0f, context));
        path.lineTo(Device.convertPixelsToDp(5420.0f, context), Device.convertPixelsToDp(700.0f, context));
        path.lineTo(Device.convertPixelsToDp(5420.0f, context), Device.convertPixelsToDp(940.0f, context));

        return path;
    }

    private Path room203ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(4080.0f, context), Device.convertPixelsToDp(980.0f, context));
        path.lineTo(Device.convertPixelsToDp(4080.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(700.0f, context));
        path.lineTo(Device.convertPixelsToDp(5420.0f, context), Device.convertPixelsToDp(700.0f, context));
        path.lineTo(Device.convertPixelsToDp(5420.0f, context), Device.convertPixelsToDp(940.0f, context));

        return path;
    }

    private Path room202ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(4620.0f, context), Device.convertPixelsToDp(980.0f, context));
        path.lineTo(Device.convertPixelsToDp(4620.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(700.0f, context));
        path.lineTo(Device.convertPixelsToDp(5420.0f, context), Device.convertPixelsToDp(700.0f, context));
        path.lineTo(Device.convertPixelsToDp(5420.0f, context), Device.convertPixelsToDp(940.0f, context));

        return path;
    }

    private Path room201ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(5170.0f, context), Device.convertPixelsToDp(980.0f, context));
        path.lineTo(Device.convertPixelsToDp(5170.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(1050.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(700.0f, context));
        path.lineTo(Device.convertPixelsToDp(5420.0f, context), Device.convertPixelsToDp(700.0f, context));
        path.lineTo(Device.convertPixelsToDp(5420.0f, context), Device.convertPixelsToDp(940.0f, context));

        return path;
    }

    private Path rightStairs(Path path) {
        path.moveTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(980.0f, context));
        path.lineTo(Device.convertPixelsToDp(5320.0f, context), Device.convertPixelsToDp(700.0f, context));
        path.lineTo(Device.convertPixelsToDp(5420.0f, context), Device.convertPixelsToDp(700.0f, context));
        path.lineTo(Device.convertPixelsToDp(5420.0f, context), Device.convertPixelsToDp(940.0f, context));

        return path;
    }

    private Path leftStairs(Path path) {
        path.moveTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(980.0f, context));
        path.lineTo(Device.convertPixelsToDp(730.0f, context), Device.convertPixelsToDp(600.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(600.0f, context));
        path.lineTo(Device.convertPixelsToDp(810.0f, context), Device.convertPixelsToDp(960.0f, context));

        return  path;
    }

    // is called by animator object
    public void setPhase(float phase) {
        Log.e(TAG,"setPhase called with:" + String.valueOf(phase));
        paint.setPathEffect(createPathEffect(length, phase, 0.0f));

        try {
            if (phase == 0.0) {
                // Proceed to ground floor
                // callFragment.showGroundFloor(position);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // Put logic here, can handle multiple tapping of floors
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
        /*if (animationList.size() > 0) {
            for (int i = 0; i < animationList.size(); i++) {
                if (animationList.get(i).equals("END")) {
                    animationList.set(i, "START");
                }
            }
        }*/
        callFragment.finishDrawing(position, animationList);
        if (animationList.size() > 0) {
            for (int i = 0; i < animationList.size(); i++) {
                if (animationList.get(i).equals("END")) {
                    animationList.set(i, "START");
                }
            }
        }
        Log.e(TAG, "On draw");
    }
}
