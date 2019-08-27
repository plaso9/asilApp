package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class KitOpeningActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton imgBtnLanguage;
    // ArrayList type String of paired devices
    ArrayList<String> arrayListpaired;
    // ArrayAdapter returns a view for each object in a collection of data objects
    ArrayAdapter<String> adapter, detectedAdapter;
    //Defined a BluetoothDevice object
    BluetoothDevice bdDevice;
    // ArrayList type BluetoothDevice of paired devices
    ArrayList<BluetoothDevice> arrayListPairedBluetoothDevices;
    // ButtonClicked variable used to switch between buttons
    private ButtonClicked clicked;
    // Item clicked while paired
    ListItemClickedonPaired listItemClickedonPaired;
    // Item clicked while discovered
    ListItemClicked listItemClicked;
    // Define a BluetoothAdapter to perform fundamental bluetooth tasks
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    // ArrayList type BluetoothDevice of discovered devices
    ArrayList<BluetoothDevice> arrayListBluetoothDevices = null;


    // Define view items
    private Button buttonSearch, buttonOn, buttonDesc, buttonOff;
    private ListView listViewPaired;
    private ListView listViewDetected;
    private DrawerLayout drawer;
    private MenuItem nav_addUser;
    private MenuItem nav_homeAdmin;
    private MenuItem nav_readRatings;
    private MenuItem nav_addAcceptance;
    private MenuItem nav_addRetrieveNecessities;
    private MenuItem nav_home;
    private MenuItem nav_info;
    private MenuItem nav_personalData;
    private MenuItem nav_medicalRecords;
    private MenuItem nav_questionnaires;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kit_opening);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);

        imgBtnLanguage.setImageResource(R.drawable.italy);
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (config.locale.getLanguage().equals("en")) {
            imgBtnLanguage.setImageResource(R.drawable.lang);
        }

        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);

        // find MenuItem you want to change
        nav_home = menu.findItem(R.id.nav_home);
        nav_info = menu.findItem(R.id.nav_info);
        nav_addUser = menu.findItem(R.id.nav_add_user);
        nav_homeAdmin = menu.findItem(R.id.nav_homeAdmin);
        nav_readRatings = menu.findItem(R.id.nav_read_ratings);
        nav_personalData = menu.findItem(R.id.nav_personalData);
        nav_addAcceptance = menu.findItem(R.id.nav_add_new_acceptance);
        nav_medicalRecords = menu.findItem(R.id.nav_medicalRecords);
        nav_questionnaires = menu.findItem(R.id.nav_questionnaires);
        nav_addRetrieveNecessities = menu.findItem(R.id.nav_add_retrive_necessities);
        //Set item visibility
        nav_home.setVisible(false);
        nav_info.setVisible(false);
        nav_addUser.setVisible(false);
        nav_homeAdmin.setVisible(false);
        nav_readRatings.setVisible(false);
        nav_personalData.setVisible(false);
        nav_addAcceptance.setVisible(false);
        nav_medicalRecords.setVisible(false);
        nav_questionnaires.setVisible(false);
        nav_addRetrieveNecessities.setVisible(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        listViewDetected = (ListView) findViewById(R.id.listViewDetected);
        listViewPaired = (ListView) findViewById(R.id.listViewPaired);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonOn = (Button) findViewById(R.id.buttonOn);
        buttonDesc = (Button) findViewById(R.id.buttonDesc);
        buttonOff = (Button) findViewById(R.id.buttonOff);

        // Initialize variables
        arrayListpaired = new ArrayList<String>();
        clicked = new ButtonClicked();
        arrayListPairedBluetoothDevices = new ArrayList<BluetoothDevice>();
        listItemClickedonPaired = new ListItemClickedonPaired();
        arrayListBluetoothDevices = new ArrayList<BluetoothDevice>();
        listItemClicked = new ListItemClicked();

        // Set the adapter to contain the list of paired devices
        adapter = new ArrayAdapter<String>(KitOpeningActivity.this, android.R.layout.simple_list_item_1, arrayListpaired);
        // Set the detectedAdapter to contain the list of detected devices
        detectedAdapter = new ArrayAdapter<String>(KitOpeningActivity.this, android.R.layout.simple_list_item_single_choice);
        // Set the adapter of the the ListView
        listViewDetected.setAdapter(detectedAdapter);
        // Notify the adapter that data set has been changed
        detectedAdapter.notifyDataSetChanged();
        // Set the adapter of the the ListView
        listViewPaired.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { // english
            if (resultCode == Activity.RESULT_CANCELED) {
                Intent refresh = new Intent(this, KitOpeningActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
        if (requestCode == 2) { //italian
            if (resultCode == Activity.RESULT_CANCELED) {
                Intent refresh = new Intent(this, KitOpeningActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent(KitOpeningActivity.this, PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.KitOpeningActivity");
            startActivity(languageIntent);
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        // Call to getPairedDevices
        getPairedDevices();

        // Set listeners for the buttons
        buttonOn.setOnClickListener(clicked);
        buttonSearch.setOnClickListener(clicked);
        buttonDesc.setOnClickListener(clicked);
        buttonOff.setOnClickListener(clicked);
        listViewDetected.setOnItemClickListener(listItemClicked);
        listViewPaired.setOnItemClickListener(listItemClickedonPaired);
    }

    // This function provides the list of paired devices
    private void getPairedDevices() {
        if (bluetoothAdapter == null) {
            // Bluetooth not supported
            finish();
        } else {
            // Create a Set of BluetoothDevices for paired devices by calling bluetoothAdapter.getBondedDevices()
            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
            if (pairedDevice.size() > 0) {
                // There is al least one paired device
                for (BluetoothDevice device : pairedDevice) {
                    // Get the name and address of the devices and add it to the arrays
                    arrayListpaired.add(device.getName() + "\n" + device.getAddress());
                    arrayListPairedBluetoothDevices.add(device);
                }
            }
            // Notify the adapter that data set has been changed
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_homeDoctor:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeDoctorIntent = new Intent(KitOpeningActivity.this, DoctorActivity.class);
                startActivity(nav_homeDoctorIntent);
                break;
            case R.id.nav_search_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_searchPatientIntent = new Intent(KitOpeningActivity.this, SearchPatientActivity.class);
                startActivity(nav_searchPatientIntent);
                break;
            case R.id.nav_kit_opening:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_kitOpeningIntent = new Intent(KitOpeningActivity.this, KitOpeningActivity.class);
                startActivity(nav_kitOpeningIntent);
                break;
            case R.id.nav_visited_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_visitedPatientIntent = new Intent(KitOpeningActivity.this, PatientListActivity.class);
                startActivity(nav_visitedPatientIntent);
                break;
            case R.id.nav_video:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_videoIntent = new Intent(KitOpeningActivity.this, VideoActivity.class);
                startActivity(nav_videoIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(KitOpeningActivity.this, MainActivity.class);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }


    // If the user clicks on a discovered device, it will call createBond
    class ListItemClicked implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Get the device in position passed as parameter
            bdDevice = arrayListBluetoothDevices.get(position);

            // Check if createBond worked successfully
            Boolean isBonded = false;
            try {
                isBonded = createBond(bdDevice);
                if (isBonded) {
                    // If it's bonded, call getPairedDevices
                    getPairedDevices();
                    // Notify the adapter that data set has been changed
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // If the user clicks on a paired device, it will call removeBond
    class ListItemClickedonPaired implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Get the device in position passed as parameter
            bdDevice = arrayListPairedBluetoothDevices.get(position);
            try {
                // Try to remove the bond of the device
                Boolean removeBonding = removeBond(bdDevice);
                if (removeBonding) {
                    // Removes the device from the array
                    arrayListpaired.remove(position);
                    // Notify the adapter that data set has been changed
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // This method will remove a btDevice previously bonded
    public boolean removeBond(BluetoothDevice btDevice)
            throws Exception {
        // Returns class object associated with the class BluetoothDevice
        Class btClass = Class.forName("android.bluetooth.BluetoothDevice");
        // Gets the method removeBond
        Method removeBondMethod = btClass.getMethod("removeBond");
        // Invoke the method on btDevice and returns the boolean result
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    // This method will create a bond with a btDevice
    public boolean createBond(BluetoothDevice btDevice)
            throws Exception {
        // Returns class object associated with the class BluetoothDevice
        Class class1 = Class.forName("android.bluetooth.BluetoothDevice");
        // Gets the method createBond
        Method createBondMethod = class1.getMethod("createBond");
        // Invoke the method on btDevice and returns the boolean result
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    // Switches between buttons clicked
    class ButtonClicked implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonOn:
                    onBluetooth();
                    break;
                case R.id.buttonSearch:
                    arrayListBluetoothDevices.clear();
                    startSearching();
                    break;
                case R.id.buttonDesc:
                    makeDiscoverable();
                    break;
                case R.id.buttonOff:
                    offBluetooth();
                    break;
                default:
                    break;
            }
        }
    }

    // Create a BroadcastReceiver
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // If the action is equal to a device discovered, get the device from the intent
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Toast.makeText(context, "ACTION_FOUND", Toast.LENGTH_SHORT).show();

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // Check if the size of bluetooth device array is 0
                if (arrayListBluetoothDevices.size() < 1)
                {
                    // Get the name and address of the devices and add it to the arrays
                    detectedAdapter.add(device.getName() + "\n" + device.getAddress());
                    arrayListBluetoothDevices.add(device);
                    // Notify the adapter that data set has been changed
                    detectedAdapter.notifyDataSetChanged();
                } else {
                    // Flag to indicate that particular device is already in the array or not
                    boolean flag = true;
                    for (int i = 0; i < arrayListBluetoothDevices.size(); i++) {
                        if (device.getAddress().equals(arrayListBluetoothDevices.get(i).getAddress())) {
                            // The device is already in the list, we don't have to add it to the array
                            flag = false;
                        }
                    }
                    if (flag == true) {
                        // Get the name and address of the devices and add it to the arrays
                        detectedAdapter.add(device.getName() + "\n" + device.getAddress());
                        arrayListBluetoothDevices.add(device);
                        // Notify the adapter that data set has been changed
                        detectedAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };


    private void startSearching() {
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        // Register the BroadcastReceiver to be run in the activity thread
        KitOpeningActivity.this.registerReceiver(myReceiver, intentFilter);
        // Start the discovery by calling startDiscovery
        bluetoothAdapter.startDiscovery();
    }


    // If bluetooth is not enabled, switch it on
    private void onBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }

    // If bluetooth is enabled, switch it off
    private void offBluetooth() {
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
        }
    }

    // Makes the device discoverable for 300 seconds
    private void makeDiscoverable() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
    }

}