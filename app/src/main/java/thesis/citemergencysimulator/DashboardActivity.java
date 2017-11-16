package thesis.citemergencysimulator;

import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import thesis.citemergencysimulator.areas.GroundFloor;

/**
 * Created by Dave Tolentin on 11/3/2017.
 */
public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = DashboardActivity.class.getSimpleName();

    private FrameLayout relativeLayout;
    private FloatingActionButton fabMainBtn;
    private FloatingActionButton fabGFloorBtn;
    private FloatingActionButton fabFFloorBtn;
    private FloatingActionButton fabSFloorBtn;
    private SearchView searchView;

    // Save the FAB's active status
    // false -> fab = close
    // true -> fab = open
    private boolean fabStatus = false;

    // Animations
    private Animation showFab1;
    private Animation hideFab1;
    private Animation showFab2;
    private Animation hideFab2;
    private Animation showFab3;
    private Animation hideFab3;

    private int area  = 1; // Default for ground floor

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Set the screen orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        relativeLayout = findViewById(R.id.container_body_frame_layout_id);
        relativeLayout.setOnClickListener(this);
        // Floating Action Buttons
        fabMainBtn = findViewById(R.id.fab);
        fabGFloorBtn = findViewById(R.id.fab_1);
        fabFFloorBtn = findViewById(R.id.fab_2);
        fabSFloorBtn = findViewById(R.id.fab_3);

        // Animations
        showFab1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        hideFab1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        showFab2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        hideFab2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        showFab3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        hideFab3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);

        fabMainBtn.setOnClickListener(this);
        fabGFloorBtn.setOnClickListener(this);
        fabFFloorBtn.setOnClickListener(this);
        fabSFloorBtn.setOnClickListener(this);

        displayView("Ground Floor Evacuation Route", new GroundFloorFragment(), "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Load the menu
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e(TAG, "Query: "+query);
                boolean found = false;
                for (int i = 0; i < GroundFloor.where.length; i++) {
                    if (GroundFloor.where[i].toUpperCase().contains(query.toUpperCase())) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    Log.e(TAG, "Contains: "+found);
                    // Extract all the matched queries
                    final ArrayList<String> matched = new ArrayList<>();
                    for (int i = 0; i < GroundFloor.where.length; i++) {
                        if (GroundFloor.where[i].toUpperCase().contains(query.toUpperCase())) {
                            if (area == 1) {
                                matched.add(GroundFloor.where[i]);
                            }
                        }
                    }
                    for (int i = 0; i < matched.size(); i++) {
                        Log.e(TAG, "Matched: "+matched.get(i));
                    }
                    showAlertDialog(matched);
                } else {
                    Toast.makeText(getApplicationContext(), "No results found for "+query,
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handling click events
        switch (item.getItemId()) {
            case R.id.search:
                // Do something
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayView(String title, Fragment fragment, String roomToPin) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString("pin", roomToPin);
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body_frame_layout_id, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onClick(View view) {
        // Determine the Fragment name
        GroundFloorFragment g = new GroundFloorFragment();
        if (view.getId() == R.id.fab) {
            if (fabStatus == false) {
                // Display Floating Action Button Menus
                expandFloatingActionButtons();
                fabStatus = true;
                Log.e(TAG, "Expand");
            } else {
                // Close Floating Action Button Menus
                hideFloatingActionButtons();
                fabStatus = false;
                Log.e(TAG, "Close");
            }
            // displayView("Ground Floor Evacuation Route", new GroundFloorFragment(), "");
        } else if (view.getId() == R.id.fab_1) {
            displayView("Ground Floor Evacuation Route", new GroundFloorFragment(), "");
            // Open already
            if (fabStatus) {
                hideFloatingActionButtons();
                fabStatus = false;
            }
            area = 1;
        } else if (view.getId() == R.id.fab_2) {
            if (fabStatus) {
                hideFloatingActionButtons();
                fabStatus = false;
            }
            area = 2;
            displayView("Second Floor Evacuation Route", new SecondFloorFragment(), "");
        } else if (view.getId() == R.id.fab_3) {
            if (fabStatus) {
                hideFloatingActionButtons();
                fabStatus = false;
            }
            area = 3;
            displayView("Third Floor Evacuation Route", new ThirdFloorFragment(), "");
        }
    }

    private void showAlertDialog(final ArrayList matched) {
        searchView.setIconified(true); // Close the search view
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this,
                    android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        // Display now the matched results
        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.list, null);
        builder.setView(convertView);
        builder.setTitle("Search Results");
        ListView lv = convertView.findViewById(R.id.list_view_results_id);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DashboardActivity.this,
                android.R.layout.simple_list_item_1, matched);
        lv.setAdapter(adapter);
        final AlertDialog dialog = builder.show();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "Pin now!");
                dialog.dismiss();
                if (area == 1) {
                    displayView("Ground Floor Evacuation Route", new GroundFloorFragment(),
                            matched.get(position).toString());
                }
                Log.e(TAG, "Area: "+area);
            }
        });
    }

    private void expandFloatingActionButtons() {
        // Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fabGFloorBtn.getLayoutParams();
        layoutParams.rightMargin += (int) (fabGFloorBtn.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fabGFloorBtn.getHeight() * 0.25);
        fabGFloorBtn.setLayoutParams(layoutParams);
        fabGFloorBtn.startAnimation(showFab1);
        fabGFloorBtn.setClickable(true);

        // Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fabFFloorBtn.getLayoutParams();
        layoutParams2.rightMargin += (int) (fabFFloorBtn.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fabFFloorBtn.getHeight() * 1.5);
        fabFFloorBtn.setLayoutParams(layoutParams2);
        fabFFloorBtn.startAnimation(showFab2);
        fabFFloorBtn.setClickable(true);

        // Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fabSFloorBtn.getLayoutParams();
        layoutParams3.rightMargin += (int) (fabSFloorBtn.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (fabSFloorBtn.getHeight() * 1.7);
        fabSFloorBtn.setLayoutParams(layoutParams3);
        fabSFloorBtn.startAnimation(showFab3);
        fabSFloorBtn.setClickable(true);
    }


    private void hideFloatingActionButtons() {

        // Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fabGFloorBtn.getLayoutParams();
        layoutParams.rightMargin -= (int) (fabGFloorBtn.getWidth() * 1.7);
        layoutParams.bottomMargin -= (fabGFloorBtn.getHeight() * 0.25);
        fabGFloorBtn.setLayoutParams(layoutParams);
        fabGFloorBtn.startAnimation(hideFab1);
        fabGFloorBtn.setClickable(false);

        // Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fabFFloorBtn.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fabFFloorBtn.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fabFFloorBtn.getHeight() * 1.5);
        fabFFloorBtn.setLayoutParams(layoutParams2);
        fabFFloorBtn.startAnimation(hideFab2);
        fabFFloorBtn.setClickable(false);

        // Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fabSFloorBtn.getLayoutParams();
        layoutParams3.rightMargin -= (int) (fabSFloorBtn.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (fabSFloorBtn.getHeight() * 1.7);
        fabSFloorBtn.setLayoutParams(layoutParams3);
        fabSFloorBtn.startAnimation(hideFab3);
        fabSFloorBtn.setClickable(false);
    }
}
