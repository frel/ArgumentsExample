package com.subgarden.arguments.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.subgarden.arguments.MainActivity;
import com.subgarden.arguments.R;
import com.subgarden.arguments.arguments.DataArguments;


public class MainFragment extends BaseFragment {

    private TextView mLabel;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        ((MainActivity)getActivity()).hideNavigationButton();

        mLabel = (TextView) view.findViewById(R.id.label);
        mLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateArguments();
                logClick();
            }
        });
        mLabel.setText(getNavigationArgs().getData());

        return view;
    }

    @Override
    public DataArguments getNavigationArgs() {
        return getNavigationArgs(DataArguments.class);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLabel = null;
    }

    private void updateArguments() {
        int newValue = Integer.parseInt(getNavigationArgs().getData()) + 1;
        String data = String.valueOf(newValue);

        DataArguments arguments = new DataArguments(data);
        setNavigationArgs(arguments);
        mLabel.setText(data);
    }

    private void logClick() {
        String source = mSourceParams.getSource();
        String info = mClickInfo.getInfo();
        // Log something here
    }

}
