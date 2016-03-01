package pl.maniak.wikidiary.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.db.DBHelper;
import pl.maniak.wikidiary.models.Tag;
import pl.maniak.wikidiary.models.WikiNote;
import pl.maniak.wikidiary.views.FlowLayout;

/**
 * Created by pliszka on 01.03.16.
 */
public class MainFragment extends Fragment {


    @Bind(R.id.editText)
    EditText mEditText;
    @Bind(R.id.numberOfDayTv)
    TextView numberOfDayTv;
    @Bind(R.id.containerTags)
    pl.maniak.wikidiary.views.FlowLayout mFlowLayout;

    @Inject
    public DBHelper dbHelper;

    private List<Tag> tag = new ArrayList();

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_main, null);
        App.getAppComponent().inject(this);
        ButterKnife.bind(this, root);
        initTagContener();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.numberOfDayTv)
    public void onClick() {
    }

    private void initTagContener() {
        mFlowLayout.removeAllViews();
        try {
            tag = dbHelper.getAllTags();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < tag.size(); i++) {
            TextView tv = (TextView) getActivity().getLayoutInflater().inflate(R.layout.tag_item, null);
            tv.setText(tag.get(i).getTag());
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            tv.setLayoutParams(params);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addWikiNote(((TextView) v).getText().toString());
                }
            });
            mFlowLayout.addView(tv);
        }
    }

    private void addWikiNote(String tag) {
        if (!mEditText.getText().toString().equals("")) {
            dbHelper.addWikiNote(new WikiNote(tag, mEditText.getText().toString(), new Date()));
            mEditText.setText("");
        }
    }
}
