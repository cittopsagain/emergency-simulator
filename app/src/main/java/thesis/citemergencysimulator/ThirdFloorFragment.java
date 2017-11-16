package thesis.citemergencysimulator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import thesis.citemergencysimulator.builder.GroundFloorPathBuilder;
import thesis.citemergencysimulator.helpers.Image;
import thesis.citemergencysimulator.helpers.OnPinchListener;

/**
 * Created by Dave Tolentin on 10/27/2017.
 */
public class ThirdFloorFragment extends Fragment {
    private GroundFloorPathBuilder pathView;
    private View rootView;
    private final static String TAG = ThirdFloorFragment.class.getSimpleName();
    private ImageView imgSecondFloor;
    private Image imageHelper;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_third_floor, container,
                false);
        pathView = rootView.findViewById(R.id.img_view_id_third_floor_path);
        imgSecondFloor = rootView.findViewById(R.id.img_view_id_third_floor);
        imgSecondFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                R.drawable.ic_third_floor, 200, 200));

        return rootView;
    }
}
