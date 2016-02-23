package com.nbau21.sampletimepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.time_picker)
    TimePicker timePicker;
    @Bind(R.id.save_button)
    Button saveButton;
    @Bind(R.id.name_edittext)
    EditText nameEditText;
    @Bind(R.id.time_layout)
    LinearLayout timeLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupLayout();
        setupFirebaseListener();
    }

    private void setupLayout() {
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        timePicker.setIs24HourView(false);
        timeLayout.bringToFront();
        saveButton.setOnClickListener(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    }

    private void setupFirebaseListener() {
        Firebase.setAndroidContext(this);

        Firebase ref = new Firebase(Endpoints.FirebaseEndpoint);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                DataSnapshot postSnapshot = snapshot.child(Endpoints.TimePicker);
                TimeNameModel timeNameModel = postSnapshot.getValue(TimeNameModel.class);
                if (timeNameModel != null) {
                    nameEditText.setText(timeNameModel.getName());

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timeNameModel.getTime());
                    int hours = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutes = calendar.get(Calendar.MINUTE);

                    Log.d("meow", Integer.toString(hours));
                    timePicker.setHour(hours);
                    timePicker.setMinute(minutes);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // return error msg
            }
        });
    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        calendar.set(year, month, day, hour, minute);
        long time = calendar.getTimeInMillis();

        Firebase ref = new Firebase(Endpoints.FirebaseEndpoint);
        Firebase dataRef = ref.child(Endpoints.TimePicker);
        Map<String, Object> mMap = new HashMap<>();
        mMap.put("name", nameEditText.getText().toString());
        mMap.put("time", time);

        dataRef.setValue(mMap);
    }
}
