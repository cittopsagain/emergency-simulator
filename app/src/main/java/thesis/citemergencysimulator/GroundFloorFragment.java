package thesis.citemergencysimulator;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import thesis.citemergencysimulator.helpers.Image;
import thesis.citemergencysimulator.helpers.OnPinchListener;
import thesis.citemergencysimulator.builder.GroundFloorPathBuilder;

/**
 * Created by Dave Tolentin on 10/27/2017.
 */
public class GroundFloorFragment extends Fragment {
    private GroundFloorPathBuilder pathView;
    private View rootView;
    private final static String TAG = GroundFloorFragment.class.getSimpleName();
    private ImageView imgGroundFloor;
    private Image imageHelper;
    private String value;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Motion event
        new ScaleGestureDetector(getActivity(), new OnPinchListener(getActivity()));
        imageHelper = new Image(getActivity());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            value = bundle.getString("pin");
            Log.e(TAG, "Value: "+value);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ground_floor, container,
                false);
        pathView = rootView.findViewById(R.id.img_view_id_ground_floor_path);
        imgGroundFloor = rootView.findViewById(R.id.img_view_id_ground_floor);
        imgGroundFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                R.drawable.ic_ground_floor, 300, 300));
        pathView.init();
        /*if (!value.isEmpty()) {
            pathView.init();
        }*/
        return rootView;
    }
}