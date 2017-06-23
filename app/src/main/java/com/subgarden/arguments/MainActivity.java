package com.subgarden.arguments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.subgarden.arguments.arguments.Arguments;
import com.subgarden.arguments.arguments.ClickInfo;
import com.subgarden.arguments.arguments.DataArguments;
import com.subgarden.arguments.arguments.SourceParams;
import com.subgarden.arguments.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ARGS = "args";
    public static final String KEY_SOURCE_PARAMS = "source_params";
    public static final String KEY_CLICK_INFO = "click_info";

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mButton = (Button) findViewById(R.id.navigateButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Using "0" as the data payload in order to easily convert to integers and increment
                String data = "0";
                navigateTo(data);
            }
        });
        setSupportActionBar(toolbar);

    }

    protected void navigateTo(String data) {
        DataArguments arguments = new DataArguments(data);

        Bundle instanceState = buildArgs(arguments, new SourceParams(), new ClickInfo());

        MainFragment fragment = (MainFragment) Fragment.instantiate(this,
                                                                    MainFragment.class.getName(),
                                                                    instanceState);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.main_content_frame, fragment, null);
        transaction.commit();
    }

    public static Bundle buildArgs(Arguments args,
                                   SourceParams params,
                                   ClickInfo clickInfo) {
        Bundle instanceState = new Bundle();

        instanceState.putBundle(KEY_ARGS, args.makeBundle());
        if (params != null) {
            instanceState.putSerializable(KEY_SOURCE_PARAMS, params);
        }
        if (clickInfo != null) {
            instanceState.putSerializable(KEY_CLICK_INFO, clickInfo);
        }

        return instanceState;
    }

    public void hideNavigationButton() {
        mButton.setVisibility(View.GONE);
    }

}
