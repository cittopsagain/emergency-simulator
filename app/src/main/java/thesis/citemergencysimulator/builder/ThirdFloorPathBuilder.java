package thesis.citemergencysimulator.builder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
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
import android.view.animation.Animation;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import thesis.citemergencysimulator.helpers.Device;

/**
 * Created by Dave Tolentin on 12/29/2017.
 */
public class ThirdFloorPathBuilder extends View {
    private Path path;
    private Paint paint;
    private float length;
    private int height;
    private int width;
    private Context context;
    private int position;
    private int tap = 0;

    private ImageView imgView;

    private ArrayList<Integer> dirList = new ArrayList<>();
    private ArrayList<Date> dateList = new ArrayList<>();

    private ArrayList<Float> animatedValueList = new ArrayList<>();
    private ArrayList<String> startList = new ArrayList<>();
    private ArrayList<String> endList = new ArrayList<>();
    private ArrayList<String> runList = new ArrayList<>();

    private ArrayList<String> animationList1 = new ArrayList<>();

    private ObjectAnimator animator;

    private Handler handler = new Handler();

    private boolean isFinishedBuildingPath = true;
    private int time = 0;

    private boolean showSecondFlor = false;

    private static final String TAG = GroundFloorPathBuilder.class.getSimpleName();

    public interface CallFragment {
        void showSecondFloor(int position, ArrayList<Integer> size);
        void finishDrawing(int position, ArrayList<String> arrayList);
    }

    private static CallFragment callFragment = null;

    public ThirdFloorPathBuilder(Context context, CallFragment callFragment, String p, String p1) {
        super(context);
        this.context = context;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        this.callFragment = callFragment;
    }

    public ThirdFloorPathBuilder(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ThirdFloorPathBuilder(Context context, AttributeSet attrs, int defStyleAttr) {
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
        // endList.clear();
        // startList.clear();
        Path renderedPath;

        // Stopping the animation, useful when tapping multiple rooms
        try {
            if (animator.isRunning()) {
                animator.end();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        Date currentTime = Calendar.getInstance().getTime();
        dateList.add(currentTime);
        this.imgView = imgView;
        dirList.add(direction);
        tap++;
        imgView.setVisibility(View.VISIBLE);
        // Note: You cannot run all the animations
        height = Device.getScreenHeight();
        width = Device.getScreenWidth();

        // Bring the marker at the top of button
        imgView.bringToFront();

        Log.e(TAG, "Width: " + width + " Height: " + height);

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
                float x = getX(450.0f);
                float y = getY(540.0f);
                imgView.setX(Device.convertPixelsToDp(x, context));
                imgView.setY(Device.convertPixelsToDp(y, context));
            }

            if (direction == 1) {
                room309ToExit(path);
                float x = getX(1040.0f);
                float y = getY(540.0f);
                imgView.setX(Device.convertPixelsToDp(x, context));
                imgView.setY(Device.convertPixelsToDp(y, context));
                renderedPath = room309ToExit(path);
            }

            if (direction == 2) {
                room308ToExit(path);
                float x = getX(1540.0f);
                float y = getY(540.0f);
                imgView.setX(Device.convertPixelsToDp(x, context));
                imgView.setY(Device.convertPixelsToDp(y, context));
            }

            if (direction == 3) {
                room307ToExit(path);
                float x = getX(2010.0f);
                float y = getY(540.0f);
                imgView.setX(Device.convertPixelsToDp(x, context));
                imgView.setY(Device.convertPixelsToDp(y, context));
            }

            if (direction == 4) {
                room306ToExit(path);
                imgView.setX(Device.convertPixelsToDp(2520.0f, context));
                imgView.setY(Device.convertPixelsToDp(540.0f, context));
            }

            if (direction == 5) {
                room305ToExit(path);
                float x = getX(3010.0f);
                float y = getY(540.0f);
                imgView.setX(Device.convertPixelsToDp(x, context));
                imgView.setY(Device.convertPixelsToDp(y, context));
            }

            if (direction == 6) {
                room304ToExit(path);
                float x = getX(3500.0f);
                float y = getY(540.0f);
                imgView.setX(Device.convertPixelsToDp(x, context));
                imgView.setY(Device.convertPixelsToDp(y, context));
            }

            if (direction == 7) {
                room303ToExit(path);
                float x = getX(3980.0f);
                float y = getY(540.0f);
                imgView.setX(Device.convertPixelsToDp(x, context));
                imgView.setY(Device.convertPixelsToDp(y, context));
            }

            if (direction == 8) {
                room302ToExit(path);
                float x = getX(4460.0f);
                float y = getY(540.0f);
                imgView.setX(Device.convertPixelsToDp(x, context));
                imgView.setY(Device.convertPixelsToDp(y, context));
            }

            if (direction == 9) {
                room301ToExit(path);
                float x = getX(4950.0f);
                float y = getY(540.0f);
                imgView.setX(Device.convertPixelsToDp(x, context));
                imgView.setY(Device.convertPixelsToDp(y, context));
            }
        }

        PathMeasure measure = new PathMeasure(path, false);
        length = measure.getLength();

        float[] intervals = new float[]{length, length};

        animator = ObjectAnimator.ofFloat(ThirdFloorPathBuilder.this,
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
                if (animationList1.size() > 0) {
                    for (int i = 0; i < animationList1.size(); i++) {
                        if (animationList1.get(i).equals("END")) {
                            animationList1.set(i, "START");
                        }
                    }
                }

                super.onAnimationEnd(animation);
                Log.e(TAG, "Animation End");
                Log.e(TAG, "Is running: "+animation.isRunning());
                endList.add("END");

                for (int i = 0; i < animationList1.size(); i++) {
                    Log.e(TAG, "On animation end: "+animationList1.get(i));
                }

                animationList1.add("END");

                Log.e(TAG, "On animation end: "+animationList1.get(animationList1.size() - 1));

                for (int i = 0; i < animationList1.size(); i++) {
                    Log.e(TAG, "Final Animation List: "+animationList1.get(i));
                }

                if (startList.size() != 1 && endList.size() != 1) {
                    if (startList.size() == endList.size()) {

                        callFragment.showSecondFloor(position, dirList);

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
                if (animationList1.size() > 0) {
                    Log.e(TAG, "Last animation: "+animationList1.get(animationList1.size() - 1));
                    // animationList.set(animationList.size() - 1, "START");
                    for (int i = 0; i < animationList1.size(); i++) {
                        if (animationList1.get(i).equals("END")) {
                            animationList1.set(i, "START");
                        }
                    }
                }

                super.onAnimationStart(animation);
                startList.add("START");
                Log.e(TAG, "Animation Start");
                Log.e(TAG, "List Start: "+startList.size());

                animationList1.add("START");

                if (animationList1.size() > 0) {
                    Log.e(TAG, "Last animation: "+animationList1.get(animationList1.size() - 1));
                    // animationList.set(animationList.size() - 1, "START");
                    for (int i = 0; i < animationList1.size(); i++) {
                        if (animationList1.get(i).equals("END")) {
                            animationList1.set(i, "START");
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

        for (int i = 0; i < animationList1.size(); i++) {
            // Log.e(TAG, "Outside animation: "+animationList.get(i));
        }
        // Log.e(TAG, "Get Animation: "+this.getX());
    }

    private Path LeftCRToExit(Path path, int pos) {
        if (pos == 1) {
            // Left side
            path.moveTo(Device.convertPixelsToDp(getX(614.0f), context), Device.convertPixelsToDp(getY(700.0f), context));
            path.lineTo(Device.convertPixelsToDp(getX(614.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
            path.lineTo(Device.convertPixelsToDp(getX(730.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
            path.lineTo(Device.convertPixelsToDp(getX(730.0f), context), Device.convertPixelsToDp(getY(600.0f), context));
            path.lineTo(Device.convertPixelsToDp(getX(810.0f), context), Device.convertPixelsToDp(getY(600.0f), context));
            path.lineTo(Device.convertPixelsToDp(getX(810.0f), context), Device.convertPixelsToDp(getY(960.0f), context));
        } else {
            // Right side

        }

        return path;
    }

    private Path room309ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(getX(970.0f), context), Device.convertPixelsToDp(getY(980.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(970.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(730.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(730.0f), context), Device.convertPixelsToDp(getY(600.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(810.0f), context), Device.convertPixelsToDp(getY(600.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(810.0f), context), Device.convertPixelsToDp(getY(960.0f), context));

        return path;
    }

    private Path room308ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(getX(1540.0f), context), Device.convertPixelsToDp(getY(980.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(1540.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(730.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(730.0f), context), Device.convertPixelsToDp(getY(600.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(810.0f), context), Device.convertPixelsToDp(getY(600.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(810.0f), context), Device.convertPixelsToDp(getY(960.0f), context));

        return path;
    }

    private Path room307ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(getX(2100.0f), context), Device.convertPixelsToDp(getY(980.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(2100.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(730.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(730.0f), context), Device.convertPixelsToDp(getY(600.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(810.0f), context), Device.convertPixelsToDp(getY(600.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(810.0f), context), Device.convertPixelsToDp(getY(960.0f), context));

        return path;
    }

    private Path room306ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(getX(2650.0f), context), Device.convertPixelsToDp(getY(980.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(2650.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(730.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(730.0f), context), Device.convertPixelsToDp(getY(600.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(810.0f), context), Device.convertPixelsToDp(getY(600.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(810.0f), context), Device.convertPixelsToDp(getY(960.0f), context));

        return path;
    }

    private Path room305ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(getX(3000.0f), context), Device.convertPixelsToDp(getY(980.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(3000.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(730.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(730.0f), context), Device.convertPixelsToDp(getY(600.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(810.0f), context), Device.convertPixelsToDp(getY(600.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(810.0f), context), Device.convertPixelsToDp(getY(960.0f), context));

        return path;
    }

    private Path room304ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(getX(3530.0f), context), Device.convertPixelsToDp(getY(980.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(3530.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5320.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5320.0f), context), Device.convertPixelsToDp(getY(700.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5420.0f), context), Device.convertPixelsToDp(getY(700.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5420.0f), context), Device.convertPixelsToDp(getY(940.0f), context));

        return path;
    }

    private Path room303ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(getX(4070.0f), context), Device.convertPixelsToDp(getY(980.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(4070.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5320.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5320.0f), context), Device.convertPixelsToDp(getY(700.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5420.0f), context), Device.convertPixelsToDp(getY(700.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5420.0f), context), Device.convertPixelsToDp(getY(940.0f), context));

        return path;
    }

    private Path room302ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(getX(4620.0f), context), Device.convertPixelsToDp(getY(980.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(4620.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5320.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5320.0f), context), Device.convertPixelsToDp(getY(700.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5420.0f), context), Device.convertPixelsToDp(getY(700.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5420.0f), context), Device.convertPixelsToDp(getY(940.0f), context));

        return path;
    }

    private Path room301ToExit(Path path) {
        path.moveTo(Device.convertPixelsToDp(getX(5160.0f), context), Device.convertPixelsToDp(getY(980.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5160.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5320.0f), context), Device.convertPixelsToDp(getY(1050.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5320.0f), context), Device.convertPixelsToDp(getY(700.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5420.0f), context), Device.convertPixelsToDp(getY(700.0f), context));
        path.lineTo(Device.convertPixelsToDp(getX(5420.0f), context), Device.convertPixelsToDp(getY(940.0f), context));

        return path;
    }

    // is called by animator object
    public void setPhase(float phase) {
        Log.e(TAG, "setPhase called with:" + String.valueOf(phase));
        paint.setPathEffect(createPathEffect(length, phase, 0.0f));

        String explodePhase[] = String.valueOf(phase).split(".");
        // Log.e(TAG, "Explode Phase: "+explodePhase[0]+" "+explodePhase[1]);
        for (int i = 0; i < explodePhase.length; i++) {
            Log.e(TAG, "Phase: "+explodePhase[i]);
        }

        Log.e(TAG, "Phase>>"+phase * 1920.0f);
        Log.e(TAG, "Object Animator: "+animator.getValues());

        Log.e(TAG, "Object Animated Value: "+animator.getAnimatedValue());

        for (int i = 0; i < animator.getValues().length; i++) {
            Log.e(TAG, "Animator Values: "+animator.getValues()[i]);
        }

        Runnable run = null;
        if (dirList.size() > 1) {
            /*if (runList.size() >= 5) {
                callFragment.showSecondFloor(position, dirList);
            }*/
            if (phase == 0.0) {
                // callFragment.showSecondFloor(position, dirList);
            }

            // init(dirList.get(dirList.size() - 1), imgView);
            // invalidate();
            // dirList.clear();
            // return;
            // dirList.clear();
            // Idle of 5 seconds, then clear the dirList array
            if (phase == 0.0) {
                // invalidate();
                if (showSecondFlor) {
                    Log.e(TAG, "Yey!!!");
                }
            }
            for (int i = 0; i < dateList.size(); i++) {
                Log.e(TAG, "Time: "+dateList.get(i));
            }

            Date lastBuildTime = dateList.get(dateList.size() - 1);
            Date currentTime = Calendar.getInstance().getTime();

            Log.e(TAG, "Last Build Time: "+lastBuildTime+" Current Time: "+currentTime);


            run = new Runnable() {

                @Override
                public void run() {
                    time += 1;
                    Log.e(TAG, "Time Handler: "+time);
                    handler.postDelayed(this, 5000);
                }
            };
        } else {
            if (phase == 0.0) {
                // callFragment.showSecondFloor(position, dirList);
            }
            Log.e(TAG, "Call Second Floor!");
            // dirList.clear();
        }

        if (phase == 0.0 && dirList.size() > 0) {
            // dirList.clear();
        }

        float validPhase = phase * 10000000;

        Log.e(TAG, "Dir size: "+dirList.size());

        // Limit only
        if (tap > 2) {
            // invalidate();
        } else {
            if (phase == 0.0) {
                // callFragment.showSecondFloor(position);
            }
        }

        try {
            if (validPhase == 0.0) {
                // Log.e(TAG, "Call Second Floor!");
                // Proceed to second floor
                // callFragment.showSecondFloor(position);
                isFinishedBuildingPath = true;
            } else {
                isFinishedBuildingPath = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Put logic here, can handle multiple tapping of floors
        invalidate(); // will call onDraw
        handler.removeCallbacks(run);
    }

    private static PathEffect createPathEffect(float pathLength, float phase, float offset) {
        return new DashPathEffect(new float[]{pathLength, pathLength},
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
        // dirList.clear();
        super.onDraw(c);
        Log.e(TAG, "On Draw Called!");
        try {
            c.drawPath(path, paint);
            Log.e(TAG, "Clip Bounds: ");
            tap = 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e(TAG, "On draw: ");
        Log.e(TAG, "On Draw X: ");
        callFragment.finishDrawing(position, animationList1);
    }

    @Override
    public float getX() {
        Log.e(TAG, "GetX");
        return super.getX();
    }
}
