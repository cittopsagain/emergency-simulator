package thesis.citemergencysimulator.helpers;

/**
 * Created by Dave Tolentin on 11/29/2017.
 */
public class Touch {

    public boolean nearestTo(float x, float y, float circleCenterX,
                                      float circleCenterY, float circleRadius) {
        double dx = Math.pow(x - circleCenterX, 2);
        double dy = Math.pow(y - circleCenterY, 2);

        if ((dx + dy) < Math.pow(circleRadius, 2)) {
            return true;
        } else {
            return false;
        }
    }
}
