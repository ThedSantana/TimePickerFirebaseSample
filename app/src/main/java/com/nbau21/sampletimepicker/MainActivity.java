package com.nbau21.sampletimepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TimePicker timePicker;
    Button saveButton;
    EditText nameEditText;
    LinearLayout timeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        timeLayout = (LinearLayout) this.findViewById(R.id.time_layout);
        nameEditText = (EditText) this.findViewById(R.id.name_edittext);
        saveButton = (Button) this.findViewById(R.id.save_button);
        timePicker = (TimePicker) this.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(false);
        timeLayout.bringToFront();
        saveButton.setOnClickListener(this);
        // if internet && data exists, set it to timePicker && editText

        Firebase ref = new Firebase(Endpoints.FirebaseEndpoint);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                DataSnapshot postSnapshot = snapshot.child(Endpoints.TimePicker);
                TimeNameModel timeNameModel = postSnapshot.getValue(TimeNameModel.class);
                nameEditText.setText(timeNameModel.getName());
                timePicker.setHour(Integer.parseInt(timeNameModel.getTime().substring(0, 2)));
                if (timeNameModel.getTime().length() == 3)
                    timePicker.setMinute(Integer.parseInt(timeNameModel.getTime().substring(3, 4)));
                else
                    timePicker.setMinute(Integer.parseInt(timeNameModel.getTime().substring(3, 5)));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // return error msg
            }
        });
    }

    @Override
    public void onClick(View v) {
        Firebase ref = new Firebase(Endpoints.FirebaseEndpoint);
        Firebase dataRef = ref.child(Endpoints.TimePicker);
        Map<String, String> mMap = new HashMap<>();
        mMap.put("name", nameEditText.getText().toString());
        mMap.put("time", timePicker.getHour() + ":" + timePicker.getMinute());

        dataRef.setValue(mMap);
    }
}
