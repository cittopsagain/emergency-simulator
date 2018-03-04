package thesis.citemergencysimulator;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PathEffect;
import android.graphics.Point;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import thesis.citemergencysimulator.areas.GroundFloor;
import thesis.citemergencysimulator.helpers.Device;
import thesis.citemergencysimulator.helpers.Image;
import thesis.citemergencysimulator.builder.GroundFloorPathBuilder;
import thesis.citemergencysimulator.helpers.SessionHelper;
import thesis.citemergencysimulator.helpers.Touch;
import thesis.citemergencysimulator.helpers.ZoomLayout;

import static android.content.Context.MODE_PRIVATE;

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
    private String currentDateTimeString;
    private int pos;
    private int idx;
    private int buttonClicked = 0;
    private int valAfterReload = -1;

    private int resId;

    private SessionHelper sessionHelper;

    private CountDownTimer countDownTimer;

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
        sessionHelper = new SessionHelper(getActivity());

        if (bundle != null) {
            value = bundle.getString("pin");
            try {
                pos = bundle.getInt("stairs");

                // Refresh the fragment inorder for us to draw again the path
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Log.e(TAG, "Value: "+value);
        } else {
            Log.e(TAG, "Value after fragment reload: "+valAfterReload);
        }

        float density = getContext().getResources().getDisplayMetrics().density;
        Log.e(TAG, "Density: "+density);

        try {
            countDownTimer.cancel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        rootView = inflater.inflate(R.layout.fragment_ground_floor, container,
                false);
        pathView = rootView.findViewById(R.id.img_view_id_ground_floor_path);
        imgGroundFloor = rootView.findViewById(R.id.img_view_id_ground_floor);
        imgMarker = rootView.findViewById(R.id.img_view_marker);

        imgMarker.setVisibility(View.GONE);

        // Bring the marker at the top of button
        /*imgMarker.bringToFront();
        imgMarker.invalidate();*/

        SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(SessionHelper.PREF_NAME, MODE_PRIVATE);
        int room = prefs.getInt("roomNum", -1);
        Log.e(TAG, "Shared Preferences: "+room);

        try {
            /*if (room >= 0) {
                if (room == 0) {
                    imgGroundFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                            R.drawable.ic_ground_floor_1, 360, 360));
                    Log.e(TAG, "Room 0");
                }

                if (room == 1) {
                    if (room == 0) {
                        imgGroundFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_2, 360, 360));
                        Log.e(TAG, "Room 1");
                    }
                }
            } else {
                imgGroundFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                        R.drawable.ic_ground_floor_default, 360, 360));
                Log.e(TAG, "Room 3");
            }*/
            // imgGroundFloor.setImageDrawable(null);
            if (room == 0) {
                /*imgGroundFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                        R.drawable.ic_ground_floor_1, 360, 360));*/
                Log.e(TAG, "Room 0");
            } else if (room == 1) {
                imgGroundFloor.setImageBitmap(null);
                Log.e(TAG, "Room 1");
            } else {
                /*imgGroundFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                        R.drawable.ic_ground_floor_default_1, 360, 360));*/
                Log.e(TAG, "Room 3");
            }
        } catch (OutOfMemoryError outOfMemoryError) {
            Toast.makeText(getActivity(), outOfMemoryError.getMessage(), Toast.LENGTH_SHORT).show();
        }

        /*Glide.with(getActivity()).load(R.drawable.ic_ground_floor_default_1).apply(
                new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
        ).into(imgGroundFloor);*/

        resId = R.drawable.ic_ground_floor_default_1;

        /*imgGroundFloor.getLayoutParams().width = Device.getScreenWidth();
        imgGroundFloor.requestLayout();*/

        Log.e(TAG, "Width: "+ Device.getScreenWidth()+" Height: "+ Device.getScreenHeight());

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ground Floor Evacuation Route");
        try {
            countDownTimer = new CountDownTimer(1000000000, 1000) {

                public void onTick(long millisUntilFinished) {
                    Calendar c = Calendar.getInstance();
                    currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    try {
                        // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Third Floor Evacuation Route\t\t\t\t\t\t"+c.get(Calendar.MONTH)+" "+c.get(Calendar.DAY_OF_MONTH)+", "+c.get(Calendar.YEAR)+"\t"+c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND));
                        // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ground Floor Evacuation Route\t\t\t\t\t\t"+currentDateTimeString);
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

        final String rooms[] = {"Elementary Library", "Room 112", "Room 111", "Room 110", "Room 109",
                "", "Room 108", "Room 107", "Room 106", "Room 105",
                "", "Elementary Library", ""
        };
        Log.e(TAG, "Here with position of "+pos);

        // 1080 x 1920
        int x = 60;
        int y = 170;

        final Button roomButton[] = new Button[13];
        for (int i = 0; i < 13; i++) {
            idx = i;
            // final Button roomButton = new Button(getActivity());
            roomButton[i] = new Button(getActivity());
            roomButton[i].setText(rooms[i]);
            roomButton[i].setId(i);

            // roomButton.setTextColor(Color.parseColor("#909090"));
            roomButton[i].setTextColor(Color.parseColor("#ffffff"));

            final int id = roomButton[i].getId();

            // Change the button text size
            roomButton[i].setTextSize(6f);

            // Change the button text style
            roomButton[i].setTypeface(Typeface.DEFAULT_BOLD);


            /*if (i == 5 || i == 10) {
                // Remove background color in right and left stairs
                roomButton[i].setBackground(null);
            } else {
                roomButton[i].setBackgroundColor(Color.parseColor("#997829"));
            }*/

            // Remove the background of button
            roomButton[i].setBackground(null);

            // Remove the padding inside button
            roomButton[i].setPadding(0,0,0,0);
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
            roomButton[i].setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));

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
            roomButton[i].setX(x);
            roomButton[i].setY(y);

            RelativeLayout relativeLayout = rootView.findViewById(R.id.relative_layout_dynamic);
            relativeLayout.addView(roomButton[i]);

            roomButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "Button id: "+v.getId()+"Index: "+idx);
                    buttonClicked = v.getId();
                    // imgGroundFloor.setImageDrawable(null);
                    // Change the default button color
                    // roomButton.setBackgroundColor(Color.parseColor("#997829"));
                    /*if (v.getId() == idx) {
                        // roomButton.setBackgroundColor(Color.parseColor("#997829"));
                        Toast.makeText(getActivity(), "Equal", Toast.LENGTH_SHORT).show();
                    } else {
                        // roomButton.setBackgroundColor(Color.parseColor("#9b1516"));
                        Toast.makeText(getActivity(), "Not equal", Toast.LENGTH_SHORT).show();
                    }*/
                    // roomButton.setBackgroundColor(Color.parseColor("#997829"));

                    if (buttonClicked == 5 || buttonClicked == 10) {
                        // Remove background color in right and left stairs
                        // roomButton[buttonClicked].setBackground(null);
                    } else {
                        // roomButton[buttonClicked].setBackgroundColor(Color.parseColor("#997829"));
                        // Pressed state
                        // roomButton[v.getId()].setBackgroundColor(Color.parseColor("#9b1516"));
                    }
                    // Pressed state
                    // roomButton[v.getId()].setBackgroundColor(Color.parseColor("#9b1516"));

                    for (int j = 0; j < 13; j++) {
                        if (j == 5 || j == 10) {
                            // Skip the right and left stairs
                            continue;
                        }
                        // Reset the background color of the button
                        // Only the clicked button will change
                        if (v.getId() != j) {
                            // Default state
                            // roomButton[j].setBackgroundColor(Color.parseColor("#997829"));
                        }
                    }

                    Bitmap b = null;
                    int dir = -1;

                    // Left Rooms
                    if (id == 0) {
                        // Left Elementary Library
                        // pathView.init(0, imgMarker);
                        /*Fragment frg = null;
                        frg = getActivity().getSupportFragmentManager().findFragmentByTag("GroundFloorFragment");
                        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.detach(frg);
                        ft.attach(frg);
                        ft.commit();*/
                        // sessionHelper.setRoomNum(1);
                        /*imgGroundFloor.setImageDrawable(null);
                        imgGroundFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_1, 360, 360));*/
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        Fragment f = new GroundFloorFragment();
                        sessionHelper.setRoomNum(0);
                        /*Bundle b = new Bundle();
                        b.putString("pin", "0");
                        f.setArguments(b);*/
                        // ft.detach(f).attach(f).commit();
                        /*b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_1, 360, 360);*/

                        // b = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_ground_floor_1);
                        resId = R.drawable.ic_ground_floor_1;
                        dir = 0;

                        /*value = String.valueOf(id);
                        valAfterReload = 0;*/
                    }

                    if (id == 1) {
                        // ic_ground_floor_2
                        // Room 112
                        // pathView.init(1, imgMarker);
                        /*b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_2, 360, 360);*/

                        // b = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_ground_floor_2);

                        resId = R.drawable.ic_ground_floor_2;

                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        Fragment f = new GroundFloorFragment();
                        /*Bundle b = new Bundle();
                        b.putString("pin", "1");
                        f.setArguments(b);*/
                        sessionHelper.setRoomNum(1);
                        // ft.detach(f).attach(f).commit();
                        dir = 1;
                    }

                    if (id == 2) {
                        // ic_ground_floor_3
                        // Room 111
                        /*b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_3, 360, 360);*/

                        // b = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_ground_floor_3);
                        resId = R.drawable.ic_ground_floor_3;
                        dir = 2;
                    }

                    if (id == 3) {
                        // ic_ground_floor_4
                        // Room 110
                        /*b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_4, 360, 360);*/

                        // b = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_ground_floor_4);
                        resId = R.drawable.ic_ground_floor_4;
                        dir = 3;
                    }

                    if (id == 4) {
                        // ic_ground_floor_5
                        // Room 109
                        /*b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_5, 360, 360);*/

                        // b = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_ground_floor_5);
                        resId = R.drawable.ic_ground_floor_5;
                        dir = 4;
                    }

                    if (id == 5) {
                        // Left stairs
                        /*b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_default_1, 360, 360);*/

                        // b = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_ground_floor_default_1);
                        resId = R.drawable.ic_ground_floor_default_1;
                        dir = 5;
                    }

                    if (id == 6) {
                        // ic_ground_floor_6
                        // Room 108
                        imgGroundFloor.setImageDrawable(null);
                        /*b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_6, 360, 360);*/

                        // b = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_ground_floor_6);
                        resId = R.drawable.ic_ground_floor_6;
                        dir = 6;
                    }

                    if (id == 7) {
                        // ic_ground_floor_7
                        // Room 107
                        /*b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_7, 360, 360);*/

                        // b = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_ground_floor_7);
                        resId = R.drawable.ic_ground_floor_7;
                        dir = 7;
                    }

                    if (id == 8) {
                        // ic_ground_floor_8
                        // Room 106
                        /*b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_8, 360, 360);*/

                        // b = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_ground_floor_8);
                        resId = R.drawable.ic_ground_floor_8;
                        dir = 8;
                    }

                    if (id == 9) {
                        // ic_ground_floor_9
                        // Room 105
                        /*b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_9, 360, 360);*/

                        // b = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_ground_floor_9);
                        resId = R.drawable.ic_ground_floor_9;
                        dir = 9;
                    }

                    if (id == 10) {
                        // Right Stairs
                        /*b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_default_1, 360, 360);*/

                        // b = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_ground_floor_default_1);
                        resId = R.drawable.ic_ground_floor_default_1;
                        dir = 10;
                    }

                    if (id == 11) {
                        // ic_ground_floor_10
                        // Right Elementary Library
                        /*b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_10, 360, 360);*/

                        // b = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_ground_floor_10);
                        resId = R.drawable.ic_ground_floor_10;
                        dir = 11;
                    }

                    if (id == 12) {
                        // ic_ground_floor_11
                       /*b = imageHelper.decodeSampledBitmapFromResource(getResources(),
                                R.drawable.ic_ground_floor_11, 360, 360);*/

                        // b = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_ground_floor_11);
                        resId = R.drawable.ic_ground_floor_11;
                        dir = 12;
                    }

                    Glide.with(getActivity()).load(resId).apply(
                            new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    ).into(imgGroundFloor);

                    Glide.get(getActivity()).clearMemory();

                    pathView.init(dir, imgMarker);
                    // imgGroundFloor.setImageBitmap(b);
                }
            });
        }

        // From second floor path
        if (pos > 0) {
            pathView.init(pos, imgMarker);
        }

        /*SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(SessionHelper.PREF_NAME, MODE_PRIVATE);
        int restoredText = prefs.getInt("roomNum", -1);
        Log.e(TAG, "Shared Preferences: "+restoredText);
        value = String.valueOf(restoredText);*/
        try {
            int index = 0;
            // From search
            if (!value.equals("")) {
                // imgGroundFloor.setImageDrawable(null);
                Log.e(TAG, "Here >> "+index+" Value: "+value);
                // Get the index
                for (int i = 0; i < rooms.length; i++) {
                    // if (rooms[i] == rooms[Integer.valueOf(value)]) {
                    if (rooms[i] == value) {
                        index = i;
                        Log.e(TAG, "Found: "+i);
                        break;
                    }
                }

                // int resId = R.drawable.ic_ground_floor_default;
                // imgGroundFloor.setImageDrawable(null);

                if (index == 1) {
                    resId = R.drawable.ic_ground_floor_2;
                }

                if (index == 2) {
                    resId = R.drawable.ic_ground_floor_3;
                }

                if (index == 3) {
                    resId = R.drawable.ic_ground_floor_4;
                }

                if (index == 4) {
                    resId = R.drawable.ic_ground_floor_5;
                }

                if (index == 6) {
                    resId = R.drawable.ic_ground_floor_6;
                }

                if (index == 7) {
                    resId = R.drawable.ic_ground_floor_7;
                }

                if (index == 8) {
                    resId = R.drawable.ic_ground_floor_8;
                }

                if (index == 9) {
                    resId = R.drawable.ic_ground_floor_9;
                }

                /*imgGroundFloor.setImageBitmap(imageHelper.decodeSampledBitmapFromResource(getResources(),
                        resId, 360, 360));*/
                Log.e(TAG, "Index: "+index);
                // imgMarker.bringToFront();
                pathView.init(index, imgMarker);

                // Reset the value of the session
                // sessionHelper.clearSession();

                // Pressed state
                // roomButton[index].setBackgroundColor(Color.parseColor("#9b1516"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Glide.with(getActivity()).load(resId).apply(
                new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
        ).into(imgGroundFloor);

        Glide.get(getActivity()).clearMemory();

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "Destroyed!");

        // Stop the countdown timer
        // countDownTimer.cancel();
    }
}