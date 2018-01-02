package thesis.citemergencysimulator;

import android.graphics.Color;
import android.graphics.Point;
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

import thesis.citemergencysimulator.areas.GroundFloor;
import thesis.citemergencysimulator.helpers.Device;
import thesis.citemergencysimulator.helpers.Image;
import thesis.citemergencysimulator.builder.GroundFloorPathBuilder;
import thesis.citemergencysimulator.helpers.Touch;
import thesis.citemergencysimulator.helpers.ZoomLayout;

/**
 * Created by Dave Tolentin on 10/27/2017.
 */
public class GroundFloorFragment extends Fragment implements ZoomLayout.CallFragment,
        ZoomLayout.OnClick {
    private GroundFloorPathBuilder pathView;
    private View rootView;
    private final static String TAG = GroundFloorFragment.class.getSimpleName();
    private ImageView imgGroundFloor;
    private ImageView imgMarker;
    private Image imageHelper;
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
        rootView = inflater.inflate(R.layout.fragment_ground_floor, container,
                false);
        pathView = rootView.findViewById(R.id.img_view_id_ground_floor_path);
        imgGroundFloor = rootView.findViewById(R.id.img_view_id_ground_floor);
        imgMarker = rootView.findViewById(R.id.img_view_marker);

        imgMarker.setVisibility(View.GONE);

        try {
            imgGroundFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                    R.drawable.ic_ground_floor_old, 360, 360));
        } catch (OutOfMemoryError outOfMemoryError) {
            Toast.makeText(getActivity(), outOfMemoryError.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Log.e(TAG, "Width: "+ Device.getScreenWidth()+" Height: "+ Device.getScreenHeight());

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ground Floor Evacuation Route");

        final String rooms[] = {"Elementary Library", "Room 112", "Room 111", "Room 110", "Room 109",
                "", "Room 108", "Room 107", "Room 106", "Room 105",
                "", "Elementary Library", ""
        };

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
        int x = 60;
        int y = 170;

        for (int i = 0; i < 13; i++) {
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
            int btnWidth = 120;
            int btnHeight = 110;
            if (i == 5 || i == 10) {
                // Left Stairs and Right Stairs
                btnWidth = 50;
                btnHeight = 115;
            }

            if (i == 11) {
                btnWidth = 210;
                btnHeight = 130;
            }

            if (i == 12) {
                btnWidth = 160;
                btnHeight = 230;
            }
            // Change the button height and width
            roomButton.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));

            // Left Rooms
            if (i == 1) {
                x += 207;
            }

            if (i > 1 && i < 5) {
                x += 110;
            }

            if (i == 5) {
                // Left Stairs
                x -= 372;
            }

            // Right Rooms
            if (i == 6) {
                x += 570;
            }

            if (i > 6 && i < 10) {
                x += 132;
            }

            if (i == 10) {
                x += 130;
            }

            if (i == 11) {
                x += 50;
                y -= 20;
            }

            if (i == 12) {
                x += 320;
                y += 30;
            }

            // Change the positioning
            roomButton.setX(x);
            roomButton.setY(y);

            RelativeLayout relativeLayout = rootView.findViewById(R.id.relative_layout_dynamic);
            relativeLayout.addView(roomButton);

            roomButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Left Rooms
                    if (id == 0) {
                        // Left Elementary Library
                        pathView.init(0, imgMarker);
                    }

                    if (id == 1) {
                        // Room 112
                        pathView.init(1, imgMarker);
                    }

                    if (id == 2) {
                        // Room 111
                        pathView.init(2, imgMarker);
                    }

                    if (id == 3) {
                        // Room 110
                        pathView.init(3, imgMarker);
                    }

                    if (id == 4) {
                        // Room 109
                        pathView.init(4, imgMarker);
                    }

                    if (id == 5) {
                        // Left stairs
                        pathView.init(5, imgMarker);
                    }

                    if (id == 6) {
                        // Room 108
                        pathView.init(6, imgMarker);
                    }

                    if (id == 7) {
                        // Room 107
                        pathView.init(7, imgMarker);
                    }

                    if (id == 8) {
                        // Room 106
                        pathView.init(8, imgMarker);
                    }

                    if (id == 9) {
                        // Room 105
                        pathView.init(9, imgMarker);
                    }

                    if (id == 10) {
                        // Right Stairs
                        pathView.init(10, imgMarker);
                    }

                    if (id == 11) {
                        // Right Elementary Library
                        pathView.init(11, imgMarker);
                    }

                    if (id == 12) {
                        pathView.init(12, imgMarker);
                    }
                }
            });
        }

        return rootView;
    }

    @Override
    public void swipeImageView(int position) {
        // Carousel type
        Log.e(TAG, "Position: "+position);
        // 1 = Right to left, proceed to second floor
        // 0 = Left to right, proceed to third floor
        // Note: Add animation functionality
        /*FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (position == 1) {
            SecondFloorFragment secondFloorFragment = new SecondFloorFragment();
            fragmentTransaction.replace(R.id.container_body_frame_layout_id, secondFloorFragment);
        }

        if (position == 0) {
            ThirdFloorFragment thirdFloorFragment = new ThirdFloorFragment();
            fragmentTransaction.replace(R.id.container_body_frame_layout_id, thirdFloorFragment);
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
    }

    @Override
    public void onClick(float x, float y) {
        // Note: This will only trigger if zoom out
        Point p = new Point();
        p.set(119, 213);


        Log.e(TAG, "X: "+x+" | Y: "+y);
        Touch touch = new Touch();
        for (int i = 0; i < GroundFloor.coordinates1080x1920X.length; i++) {
            boolean t = touch.nearestTo(Device.convertPixelsToDp(x, getActivity()),
                    Device.convertPixelsToDp(y, getActivity()),
                    Device.convertPixelsToDp(GroundFloor.coordinates1080x1920X[i], getActivity()),
                    Device.convertPixelsToDp(GroundFloor.coordinates1080x1920Y[i], getActivity()), 100);
            if (t) {
                Log.e(TAG, "CordX: "+GroundFloor.coordinates1080x1920X[i]);
                break;
            }
        }
    }
}