package thesis.citemergencysimulator;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import thesis.citemergencysimulator.builder.SecondFloorPathBuilder;
import thesis.citemergencysimulator.helpers.Image;
import thesis.citemergencysimulator.helpers.ZoomLayout;

/**
 * Created by Dave Tolentin on 10/27/2017.
 */
public class SecondFloorFragment extends Fragment implements ZoomLayout.CallFragment,
        ZoomLayout.OnClick {
    private SecondFloorPathBuilder pathView;
    private View rootView;
    private final static String TAG = SecondFloorFragment.class.getSimpleName();
    private ImageView imgSecondFloor;
    private Image imageHelper;
    private ImageView imgMarker;

    private String value;
    private ZoomLayout zoomLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Motion event
        zoomLayout = new ZoomLayout(getActivity(), this, this);
        imageHelper = new Image(getActivity());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            value = bundle.getString("pin");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_second_floor, container,
                false);
        pathView = rootView.findViewById(R.id.img_view_id_second_floor_path);
        imgSecondFloor = rootView.findViewById(R.id.img_view_id_second_floor);

        imgMarker = rootView.findViewById(R.id.img_view_marker);

        imgMarker.setVisibility(View.GONE);

        try {
            imgSecondFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                    R.drawable.ic_second_floor_old, 200, 200));
        } catch (OutOfMemoryError outOfMemoryError) {
            Toast.makeText(getActivity(), outOfMemoryError.getMessage(), Toast.LENGTH_SHORT).show();
        }

        String rooms[] = {"", "Room 209", "Room 208", "Room 207", "Room 206", "Room 205",
            "Room 204", "Room 203", "Room 202", "Room 201"
        };

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Second Floor Evacuation Route");

        try {
            int index = 0;
            if (!value.equals("")) {
                // Get the index
                for (int i = 0; i < rooms.length; i++) {
                    if (rooms[i] == value) {
                        index = i;
                        break;
                    }
                }
                pathView.init(index, imgMarker);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // 1080 x 1920
        int x = 0;
        int y = 230;

        for (int i = 0; i < 10; i++) {
            final Button roomButton = new Button(getActivity());
            roomButton.setText(rooms[i]);
            roomButton.setId(i);
            roomButton.setTextColor(Color.parseColor("#909090"));
            final int id = roomButton.getId();

            // Change the button text size
            roomButton.setTextSize(6f);

            // Change the button text style
            roomButton.setTypeface(Typeface.DEFAULT_BOLD);

            // Remove the background of button
            roomButton.setBackground(null);

            // Remove the padding inside button
            roomButton.setPadding(0,0,0,0);
            int btnWidth = 170;
            int btnHeight = 170;
            if (i == 0) {
                btnWidth = 100;
                btnHeight = 170;
            }

            // Change the button height and width
            roomButton.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));

            if (i == 0) {
                x += 130;
            }

            if (i == 1) {
                x += 160;
            }

            if (i > 1) {
                x += 163;
            }

            // Change the positioning
            roomButton.setX(x);
            roomButton.setY(y);

            RelativeLayout relativeLayout = rootView.findViewById(R.id.relative_layout_dynamic);
            relativeLayout.addView(roomButton);

            roomButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (id == 0) {
                        pathView.init(0, imgMarker);
                    }

                    if (id == 1) {
                        pathView.init(1, imgMarker);
                    }

                    if (id == 2) {
                        pathView.init(2, imgMarker);
                    }

                    if (id == 3) {
                        pathView.init(3, imgMarker);
                    }

                    if (id == 4) {
                        pathView.init(4, imgMarker);
                    }

                    if (id == 5) {
                        pathView.init(5, imgMarker);
                    }

                    if (id == 6) {
                        pathView.init(6, imgMarker);
                    }

                    if (id == 7) {
                        pathView.init(7, imgMarker);
                    }

                    if (id == 8) {
                        pathView.init(8, imgMarker);
                    }

                    if (id == 9) {
                        pathView.init(9, imgMarker);
                    }
                }
            });
        }

        return rootView;
    }

    @Override
    public void swipeImageView(int position) {
        Log.e(TAG, "Position: "+position);
        // 1 = Right to left, proceed to second floor
        // 0 = Left to right, proceed to third floor
        /*FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (position == 1) {
            ThirdFloorFragment thirdFloorFragment = new ThirdFloorFragment();
            fragmentTransaction.replace(R.id.container_body_frame_layout_id, thirdFloorFragment);
        }

        if (position == 0) {
            GroundFloorFragment groundFloorFragment = new GroundFloorFragment();
            fragmentTransaction.replace(R.id.container_body_frame_layout_id, groundFloorFragment);
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
    }

    @Override
    public void onClick(float x, float y) {

    }
}
