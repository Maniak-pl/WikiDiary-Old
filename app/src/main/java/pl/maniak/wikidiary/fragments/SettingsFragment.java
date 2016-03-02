package pl.maniak.wikidiary.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.Constants;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.utils.L;
import pl.maniak.wikidiary.views.FlowLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    @Bind(R.id.addTagEt)
    EditText addTagEt;
    @Bind(R.id.settingsContainerTags)
    pl.maniak.wikidiary.views.FlowLayout mFlowLayout;

    private Set<String> setTag = new TreeSet<String>();

    @Inject
    SharedPreferences preferences;

    public static SettingsFragment newInstance() {

        Bundle args = new Bundle();

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_settings, null);
        App.getAppComponent().inject(this);
        ButterKnife.bind(this, root);
        setTag.addAll(loadTag());

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        initTagContener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }





    @OnClick(R.id.addTagBtn)
    public void onClick() {
        if(!addTagEt.getText().toString().equals("")) {
            setTag.add(addTagEt.getText().toString());
            saveTag(setTag);
            addTagEt.setText("");
            reload();
        }
    }

    private void reload() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

    }

    private void initTagContener() {
        mFlowLayout.removeAllViews();
        List<String> listTag = new ArrayList();
        listTag.addAll(setTag);
        for (int i = 0; i < listTag.size(); i++) {
            TextView tv = (TextView) getActivity().getLayoutInflater().inflate(R.layout.tag_item, null);
            tv.setText(listTag.get(i));
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            tv.setLayoutParams(params);
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setTag.remove(((TextView) v).getText().toString());
                    saveTag(setTag);
                    reload();
                    return false;
                }
            });
            mFlowLayout.addView(tv);
        }
        mFlowLayout.invalidate();
    }

    public boolean saveTag(Set<String> set) {
        L.e("saveArray()");

        List<String> list = new ArrayList();
        list.addAll(set);

        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(Constants.PREF_TAG_SIZE, list.size());

        for (int i = 0; i < list.size(); i++) {
            edit.remove(Constants.PREF_TAG + i);
            edit.putString(Constants.PREF_TAG + i, list.get(i));
        }

        return edit.commit();
    }

    public List<String> loadTag() {
        L.e("loadArray()");
        List<String> list = new ArrayList();
        int size = preferences.getInt(Constants.PREF_TAG_SIZE, 0);

        for (int i = 0; i < size; i++) {
            list.add(preferences.getString(Constants.PREF_TAG + i, null));
        }
        return list;
    }
}
