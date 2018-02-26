package thesis.citemergencysimulator;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

import thesis.citemergencysimulator.builder.GroundFloorPathBuilder;
import thesis.citemergencysimulator.builder.SecondFloorPathBuilder;
import thesis.citemergencysimulator.builder.ThirdFloorPathBuilder;
import thesis.citemergencysimulator.helpers.EventListener;
import thesis.citemergencysimulator.helpers.Image;
import thesis.citemergencysimulator.helpers.ZoomLayout;

/**
 * Created by Dave Tolentin on 10/27/2017.
 */
public class ThirdFloorFragment extends Fragment implements ZoomLayout.CallFragment,
        ZoomLayout.OnClick, ThirdFloorPathBuilder.CallFragment {
    private ThirdFloorPathBuilder pathView;
    private View rootView;
    private final static String TAG = ThirdFloorFragment.class.getSimpleName();
    private ImageView imgThirdFloor;
    private Image imageHelper;
    private ImageView imgMarker;
    private String currentDateTimeString;
    private CountDownTimer countDownTimer;

    private String value;
    private ZoomLayout zoomLayout;
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
        pathView = new ThirdFloorPathBuilder(getActivity(), this, null, null);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            value = bundle.getString("pin");
        }

        try {
            countDownTimer.cancel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

   /* @Override
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
        rootView = inflater.inflate(R.layout.fragment_third_floor, container,
                false);
        pathView = rootView.findViewById(R.id.img_view_id_third_floor_path);
        imgThirdFloor = rootView.findViewById(R.id.img_view_id_third_floor);
        try {
            imgThirdFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                    R.drawable.ic_second_floor_default, 200, 200));
        } catch (OutOfMemoryError outOfMemoryError) {
            Toast.makeText(getActivity(), outOfMemoryError.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Third Floor Evacuation Route\t\t\t\t\t\t"+currentDateTimeString);

        try {
            countDownTimer = new CountDownTimer(1000000000, 1000) {

                public void onTick(long millisUntilFinished) {
                    Calendar c = Calendar.getInstance();
                    currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    try {
                        // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Third Floor Evacuation Route\t\t\t\t\t\t"+c.get(Calendar.MONTH)+" "+c.get(Calendar.DAY_OF_MONTH)+", "+c.get(Calendar.YEAR)+"\t"+c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND));
                        // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Third Floor Evacuation Route\t\t\t\t\t\t"+currentDateTimeString);
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

        imgMarker = rootView.findViewById(R.id.img_view_marker);

        imgMarker.setVisibility(View.GONE);

        String rooms[] = {"", "Room 309", "Room 308", "Room 307", "Room 306", "Room 305",
                "Room 304", "Room 303", "Room 302", "Room 301"
        };

        // 1080 x 1920
        int x = 0;
        int y = 230;

        final Button roomButton[] = new Button[10];
        for (int i = 0; i < 10; i++) {
            roomButton[i] = new Button(getActivity());
            // final Button roomButton = new Button(getActivity());
            roomButton[i].setText(rooms[i]);
            roomButton[i].setId(i);
            roomButton[i].setTextColor(Color.parseColor("#909090"));
            final int id = roomButton[i].getId();

            // Change the button text size
            roomButton[i].setTextSize(6f);

            roomButton[i].setTextColor(Color.parseColor("#ffffff"));

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

                    imgThirdFloor.setImageDrawable(null);

                    Bitmap b = null;
                    int dir = -1;
                    if (id == 0) {
                        b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default, 200, 200);
                        dir = 0;
                    }

                    if (id == 1) {
                        b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_1, 200, 200);
                        dir = 1;
                    }

                    if (id == 2) {
                        b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_2, 200, 200);
                        dir = 2;
                    }

                    if (id == 3) {
                        b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_3, 200, 200);
                        dir = 3;
                    }

                    if (id == 4) {
                        b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_4, 200, 200);
                        dir = 4;
                    }

                    if (id == 5) {
                        b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_5, 200, 200);
                        dir = 5;
                    }

                    if (id == 6) {
                        b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_6, 200, 200);
                        dir = 6;
                    }

                    if (id == 7) {
                        b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_7, 200, 200);
                        dir = 7;
                    }

                    if (id == 8) {
                        b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_8, 200, 200);
                        dir = 8;
                    }

                    if (id == 9) {
                        b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_second_floor_default_9, 200, 200);
                        dir = 9;
                    }

                    pathView.init(dir, imgMarker);
                    imgThirdFloor.setImageBitmap(b);
                }
            });
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
                imgThirdFloor.setImageDrawable(null);

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

                imgThirdFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
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
            GroundFloorFragment groundFloorFragment = new GroundFloorFragment();
            fragmentTransaction.replace(R.id.container_body_frame_layout_id, groundFloorFragment);
        }

        if (position == 0) {
            SecondFloorFragment secondFloorFragment = new SecondFloorFragment();
            fragmentTransaction.replace(R.id.container_body_frame_layout_id, secondFloorFragment);
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
    }

    @Override
    public void onClick(float x, float y) {

    }

    @Override
    public void showSecondFloor(int position) {
        countDownTimer.cancel();
        Log.e(TAG, "Position: "+position);
        // Show the ground floor
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        SecondFloorFragment secondFloorFragment = new SecondFloorFragment();
        Bundle bundle = new Bundle();
        // Pass the position, will be used to determine what stairs to use
        int stairs = 0;
        Log.e(TAG, "Position: "+position);
        if (position <= 5) {
            // Left stairs
            stairs = 5;
        } else {
            // Right stairs
            stairs = 10;
        }
        Log.e(TAG, "Show second floor: "+stairs+" with position: "+position);
        bundle.putInt("stairs", stairs);
        secondFloorFragment.setArguments(bundle);

        listener.sendDataToActivity(3);

        fragmentTransaction.replace(R.id.container_body_frame_layout_id, secondFloorFragment);
        // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Stop the countdown timer
        // countDownTimer.cancel();
    }
}
