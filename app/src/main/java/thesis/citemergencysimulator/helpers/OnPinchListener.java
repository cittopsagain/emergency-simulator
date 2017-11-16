package thesis.citemergencysimulator.helpers;

import android.content.Context;
import android.view.ScaleGestureDetector;
import android.widget.RelativeLayout;

/**
 * Created by Dave Tolentin on 10/28/2017.
 */

public class OnPinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private ZoomableRelativeLayout mZoomableRelativeLayout;
    float startingSpan;
    float endSpan;
    float startFocusX;
    float startFocusY;

    public OnPinchListener(Context context) {
        mZoomableRelativeLayout = new ZoomableRelativeLayout(context);
    }

    public boolean onScaleBegin(ScaleGestureDetector detector) {
        startingSpan = detector.getCurrentSpan();
        startFocusX = detector.getFocusX();
        startFocusY = detector.getFocusY();
        return true;
    }


    public boolean onScale(ScaleGestureDetector detector) {
        mZoomableRelativeLayout.scale(detector.getCurrentSpan()/startingSpan, startFocusX,
                startFocusY);
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
        mZoomableRelativeLayout.restore();
    }
}
