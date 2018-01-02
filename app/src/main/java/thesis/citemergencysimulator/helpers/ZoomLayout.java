package thesis.citemergencysimulator.helpers;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Dave Tolentin on 10/28/2017.
 */

public class ZoomLayout extends RelativeLayout implements ScaleGestureDetector.OnScaleGestureListener {

    private enum Mode {
        NONE,
        DRAG,
        ZOOM
    }

    private static final String TAG = "ZoomLayout";
    private static final float MIN_ZOOM = 1.0f;
    private static final float MAX_ZOOM = 4.0f;
    private static int CLICK = 3;

    // Remember some things for zooming
    PointF last = new PointF();
    PointF start = new PointF();

    private Mode mode = Mode.NONE;
    private float scale = 1.0f;
    private float lastScaleFactor = 0f;

    // Where the finger first touches the screen
    private float startX = 0f;
    private float startY = 0f;

    private float x1, x2;
    private ArrayList<Float> floatArrayList = new ArrayList<>();

    // How much to translate the canvas
    private float dx = 0f;
    private float dy = 0f;
    private float prevDx = 0f;
    private float prevDy = 0f;

    public int pos;

    private boolean touch = false;

    // TODO change this runtime based on screen resolution. for 1920 x 1080 is to small the 100 distance
    static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;

    public interface CallFragment {
        void swipeImageView(int position);
    }

    public interface OnClick {
        void onClick(float x, float y);
    }

    private static CallFragment callFragment = null;
    private static OnClick onClick = null;

    public ZoomLayout(Context context, CallFragment callFragment, OnClick onClick) {
        super(context);
        this.callFragment = callFragment;
        this.onClick = onClick;
        init(context);
    }

    public ZoomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(final Context context) {
        final ScaleGestureDetector scaleDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PointF curr = new PointF(motionEvent.getX(), motionEvent.getY());

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG, "DOWN");
                        last.set(curr);
                        start.set(last);
                        x1 = motionEvent.getX();
                        if (scale > MIN_ZOOM) {
                            mode = Mode.DRAG;
                            startX = motionEvent.getX() - prevDx;
                            startY = motionEvent.getY() - prevDy;
                        }

                        if (scale == 1.0 && floatArrayList.size() == 0) {
                            // onClick.onClick(x1, motionEvent.getY());
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        last.set(curr.x, curr.y);
                        if (mode == Mode.DRAG) {
                            dx = motionEvent.getX() - startX;
                            dy = motionEvent.getY() - startY;
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        mode = Mode.ZOOM;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = Mode.DRAG;
                        break;
                    case MotionEvent.ACTION_UP:
                        int xDiff = (int) Math.abs(curr.x - start.x);
                        int yDiff = (int) Math.abs(curr.y - start.y);
                        if (xDiff < CLICK && yDiff < CLICK && scale == 1.0) {
                            Log.e(TAG, "Perform Click!");
                            onClick.onClick(motionEvent.getX(), motionEvent.getY());
                        }
                        Log.e(TAG, "UP");
                        x2 = motionEvent.getX();
                        float deltaX = x2 - x1;

                        // Enable only if zoom out
                        if (scale == 1.0) {
                            boolean fromZoom = false;
                            // Detect if from zoom
                            for (int i = 0; i < floatArrayList.size(); i++) {
                                if (floatArrayList.get(i) != 1.0) {
                                    fromZoom = true;
                                    break;
                                }
                            }
                            Log.e(TAG, "From zoom: "+fromZoom);
                            if (!fromZoom) {
                                if (Math.abs(deltaX) > MIN_DISTANCE) {
                                    // Left to Right swipe action
                                    if (x2 > x1) {
                                        callFragment.swipeImageView(0);
                                    } else {
                                        // Right to left swipe action
                                        callFragment.swipeImageView(1);
                                    }
                                } else {
                                    // consider as something else - a screen tap for example
                                }
                            }
                            // Remove all the scales
                            floatArrayList.clear();
                        }
                        mode = Mode.NONE;
                        prevDx = dx;
                        prevDy = dy;
                        break;
                }
                scaleDetector.onTouchEvent(motionEvent);

                if ((mode == Mode.DRAG && scale >= MIN_ZOOM) || mode == Mode.ZOOM) {
                    // Called when a child does not want this parent and its ancestors to intercept touch events with
                    getParent().requestDisallowInterceptTouchEvent(true);
                    float maxDx = (child().getWidth() - (child().getWidth() / scale)) / 2 * scale;
                    float maxDy = (child().getHeight() - (child().getHeight() / scale))/ 2 * scale;
                    dx = Math.min(Math.max(dx, -maxDx), maxDx);
                    dy = Math.min(Math.max(dy, -maxDy), maxDy);
                    /*Log.e(TAG, "Width: " + child().getWidth() + ", scale " + scale + ", dx " + dx
                            + ", max " + maxDx);*/
                    floatArrayList.add(scale);
                    applyScaleAndTranslation();
                } else {

                }

                return true;
            }
        });
    }

    // ScaleGestureDetector

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleDetector) {
        Log.i(TAG, "onScaleBegin");
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleDetector) {
        float scaleFactor = scaleDetector.getScaleFactor();
        Log.i(TAG, "onScale" + scaleFactor);
        if (lastScaleFactor == 0 || (Math.signum(scaleFactor) == Math.signum(lastScaleFactor))) {
            scale *= scaleFactor;
            scale = Math.max(MIN_ZOOM, Math.min(scale, MAX_ZOOM));
            lastScaleFactor = scaleFactor;
        } else {
            lastScaleFactor = 0;
        }

        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleDetector) {
        Log.i(TAG, "onScaleEnd");
    }

    private void applyScaleAndTranslation() {
        child().setScaleX(scale);
        child().setScaleY(scale);
        child().setTranslationX(dx);
        child().setTranslationY(dy);
    }

    private View child() {
        return getChildAt(0);
    }

}