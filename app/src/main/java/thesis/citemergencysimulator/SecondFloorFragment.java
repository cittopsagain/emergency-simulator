package thesis.citemergencysimulator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import thesis.citemergencysimulator.builder.SecondFloorPathBuilder;
import thesis.citemergencysimulator.helpers.EventListener;
import thesis.citemergencysimulator.helpers.Image;
import thesis.citemergencysimulator.helpers.SessionHelper;
import thesis.citemergencysimulator.helpers.ZoomLayout;

/**
 * Created by Dave Tolentin on 10/27/2017.
 */
public class SecondFloorFragment extends Fragment implements ZoomLayout.CallFragment,
        ZoomLayout.OnClick, SecondFloorPathBuilder.CallFragment, View.OnClickListener {
    private SecondFloorPathBuilder pathView;
    private View rootView;
    private final static String TAG = SecondFloorFragment.class.getSimpleName();
    private ImageView imgSecondFloor;
    private Image imageHelper;
    private ImageView imgMarker;

    private String value;
    private ZoomLayout zoomLayout;
    private String currentDateTimeString;
    private CountDownTimer countDownTimer;
    private int pos;
    private EventListener listener;

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
        pathView = new SecondFloorPathBuilder(getActivity(), this, null, null);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            value = bundle.getString("pin");
            try {
                pos = bundle.getInt("stairs");
                // Toast.makeText(getActivity(), "Stairs: "+pos, Toast.LENGTH_SHORT).show();
                // Refresh the fragment inorder for us to draw again the path
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        try {
            countDownTimer.cancel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Inflate the dashboard, we need to access its buttons
        /*final LayoutInflater factory = getLayoutInflater();
        final View dashboardView = factory.inflate(R.layout.activity_dashboard, null);
        final Button btnSecondFloor = dashboardView.findViewById(R.id.btnSecondFloor);*/
        // View inflatedView = getLayoutInflater().inflate(R.layout.activity_dashboard, null);
        View inflatedView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_dashboard, null);
        Button btnSecondFloor = inflatedView.findViewById(R.id.btnSecondFloor);
        Log.e(TAG, "Button: "+btnSecondFloor);
        if (btnSecondFloor != null) {

        }

        /*btnSecondFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Hey!", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    /*@Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }*/

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
                    R.drawable.ic_second_floor_default, 200, 200));
        } catch (OutOfMemoryError outOfMemoryError) {
            Toast.makeText(getActivity(), outOfMemoryError.getMessage(), Toast.LENGTH_SHORT).show();
        }

        String rooms[] = {"", "Room 209", "Room 208", "Room 207", "Room 206", "Room 205",
            "Room 204", "Room 203", "Room 202", "Room 201", ""
        };

        Log.e(TAG, "Inside second floor on create view!");

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Second Floor Evacuation Route");

        try {
            countDownTimer = new CountDownTimer(1000000000, 1000) {

                public void onTick(long millisUntilFinished) {
                    Calendar c = Calendar.getInstance();
                    currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    try {
                        // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Third Floor Evacuation Route\t\t\t\t\t\t"+c.get(Calendar.MONTH)+" "+c.get(Calendar.DAY_OF_MONTH)+", "+c.get(Calendar.YEAR)+"\t"+c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND));
                        // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Second Floor Evacuation Route\t\t\t\t\t\t"+currentDateTimeString);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                public void onFinish() {

                }
            };
            countDownTimer.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e(TAG, "Position::: "+pos);

        // 1080 x 1920
        int x = 0;
        int y = 230;

        final Button roomButton[] = new Button[11];
        for (int i = 0; i < 11; i++) {
            // final Button roomButton = new Button(getActivity());
            roomButton[i] = new Button(getActivity());
            roomButton[i].setText(rooms[i]);
            roomButton[i].setId(i);
            // roomButton[i].setTextColor(Color.parseColor("#909090"));
            roomButton[i].setTextColor(Color.parseColor("#ffffff"));
            final int id = roomButton[i].getId();

            // Change the button text size
            roomButton[i].setTextSize(6f);

            // Change the button text style
            roomButton[i].setTypeface(Typeface.DEFAULT_BOLD);

            // roomButton[i].setBackgroundColor(Color.parseColor("#997829"));

            // Remove the background of button
            roomButton[i].setBackground(null);

            // Remove the padding inside button
            roomButton[i].setPadding(0,0,0,0);
            int btnWidth = 170;
            int btnHeight = 170;
            if (i == 0) {
                btnWidth = 100;
                btnHeight = 170;
            }

            // Change the button height and width
            roomButton[i].setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));

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
            roomButton[i].setX(x);
            roomButton[i].setY(y);

            RelativeLayout relativeLayout = rootView.findViewById(R.id.relative_layout_dynamic);
            relativeLayout.addView(roomButton[i]);

            roomButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Pressed state
                    // roomButton[v.getId()].setBackgroundColor(Color.parseColor("#9b1516"));

                    for (int j = 0; j < 10; j++) {
                        // Reset the background color of the button
                        // Only the clicked button will change
                        if (v.getId() != j) {
                            // Default state
                            // roomButton[j].setBackgroundColor(Color.parseColor("#997829"));
                        }
                    }

                    imgSecondFloor.setImageDrawable(null);

                    if (id == 0) {
                        imgSecondFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default, 200, 200));
                        pathView.init(0, imgMarker);
                    }

                    if (id == 1) {
                        imgSecondFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_1, 200, 200));
                        pathView.init(1, imgMarker);
                    }

                    if (id == 2) {
                        imgSecondFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_2, 200, 200));
                        pathView.init(2, imgMarker);
                    }

                    if (id == 3) {
                        imgSecondFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_3, 200, 200));
                        pathView.init(3, imgMarker);
                    }

                    if (id == 4) {
                        imgSecondFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_4, 200, 200));
                        pathView.init(4, imgMarker);
                    }

                    if (id == 5) {
                        imgSecondFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_5, 200, 200));
                        pathView.init(5, imgMarker);
                    }

                    if (id == 6) {
                        imgSecondFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_6, 200, 200));
                        pathView.init(6, imgMarker);
                    }

                    if (id == 7) {
                        imgSecondFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_7, 200, 200));
                        pathView.init(7, imgMarker);
                    }

                    if (id == 8) {
                        imgSecondFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_8, 200, 200));
                        pathView.init(8, imgMarker);
                    }

                    if (id == 9) {
                        imgSecondFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_9, 200, 200));
                        pathView.init(9, imgMarker);
                    }
                }
            });
        }

        if (pos > 0) {
            int index = -1;
            // pos = 10, 11 (From third floor stairs)
            if (pos == 5 || pos == 10) {
                index = 10; // Left stairs
                // pos = index;
            }
            if (pos == 10 || pos == 11) {
                index = 11; // Right stairs
                // pos = index;
            }
            pathView.init(index, imgMarker);
        }

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

                int resId = R.drawable.ic_second_floor_default;
                imgSecondFloor.setImageDrawable(null);

                if (index == 1) {
                    resId = R.drawable.ic_second_floor_default_1;
                }

                if (index == 2) {
                    resId = R.drawable.ic_second_floor_default_2;
                }

                if (index == 3) {
                    resId = R.drawable.ic_second_floor_default_3;
                }

                if (index == 4) {
                    resId = R.drawable.ic_second_floor_default_4;
                }

                if (index == 5) {
                    resId = R.drawable.ic_second_floor_default_5;
                }

                if (index == 6) {
                    resId = R.drawable.ic_second_floor_default_6;
                }

                if (index == 7) {
                    resId = R.drawable.ic_second_floor_default_7;
                }

                if (index == 8) {
                    resId = R.drawable.ic_second_floor_default_8;
                }

                if (index == 9) {
                    resId = R.drawable.ic_second_floor_default_9;
                }

                imgSecondFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                        resId, 200, 200));

                pathView.init(index, imgMarker);

                // Pressed state
                // roomButton[index].setBackgroundColor(Color.parseColor("#9b1516"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof EventListener) {
            listener = (EventListener)activity;
        } else {
            // Throw an error!
        }
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

    @Override
    public void showGroundFloor(int position) {
        // Stop the countdown timer
        countDownTimer.cancel();
        Log.e(TAG, "Position: "+position);
        // Show the ground floor
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        GroundFloorFragment groundFloorFragment = new GroundFloorFragment();
        Bundle bundle = new Bundle();
        // Pass the position, will be used to determine what stairs to use
        int stairs = 0;
        Log.e(TAG, "Position : "+position);
        Log.e(TAG, "Position >> "+position);
        if (position <= 5 || position == 10) {
            // Left stairs
            stairs = 5;
            Log.e(TAG, "Go to left stairs!");
        } else {
            // Right stairs
            stairs = 10;
            Log.e(TAG, "Go to right stairs!");
        }
        Log.e(TAG, "Show ground floor: "+stairs+" with position: "+position);
        Log.e(TAG, "What stairs >> "+stairs);
        bundle.putInt("stairs", stairs);
        groundFloorFragment.setArguments(bundle);

        listener.sendDataToActivity(2);

        fragmentTransaction.replace(R.id.container_body_frame_layout_id, groundFloorFragment);
        // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        DashboardActivity dashboardActivity = new DashboardActivity();
        // dashboardActivity.test();

        /*LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_dashboard, null);
        Button myButton = view.findViewById(R.id.btnGroundFloor);
        myButton.setVisibility(View.GONE);*/

        /*LayoutInflater inflater = LayoutInflater.from(this.getActivity());
        View view = inflater.inflate(R.layout.activity_dashboard, null);
        Button myButton = view.findViewById(R.id.btnGroundFloor);
        myButton.setVisibility(View.GONE);
        Log.e(TAG, ""+myButton);*/
    }

    @Override
    public void onClick(View v) {

    }
}
