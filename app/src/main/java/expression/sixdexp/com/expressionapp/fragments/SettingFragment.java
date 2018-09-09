package expression.sixdexp.com.expressionapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import expression.sixdexp.com.expressionapp.R;

/**
 * Created by Praveen on 6/30/2016.
 */
public class SettingFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.settingfragmentview, container, false);
        return view;
    }
}
