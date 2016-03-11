package pl.maniak.wikidiary.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.db.DBHelper;
import pl.maniak.wikidiary.helpers.DateHelper;
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
    FlowLayout mFlowLayout;

    @Inject
    public DBHelper dbHelper;

    private List<Tag> tag = new ArrayList();
    private Date dateWikiNote = new Date();
    private DatePickerDialog mPickerDialog;

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
        App.getComponent().inject(this);
        ButterKnife.bind(this, root);
        setUpView();

        return root;
    }

    private void setUpView() {
        numberOfDayTv.setText(DateHelper.getOnlyDayFromDate(dateWikiNote));
        initTagContener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
            dbHelper.addWikiNote(new WikiNote(tag, mEditText.getText().toString(), dateWikiNote));
            mEditText.setText("");
        }
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = getPickerDialog();
        datePickerDialog.setCancelable(false);

        Calendar c = Calendar.getInstance();
        c.setTime(dateWikiNote);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog.updateDate(year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private DatePickerDialog getPickerDialog() {
        if (mPickerDialog == null) {
            int[] dateParts = getDateParts(dateWikiNote);
            mPickerDialog = new DatePickerDialog(getActivity(), mDateSetListener, dateParts[0], dateParts[1], dateParts[2]);
            mPickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, this.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        mPickerDialog.dismiss();
                    }
                }
            });

        }
        return mPickerDialog;
    }

    private static int[] getDateParts(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int[] dateParts = {c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)};
        return dateParts;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (view.isShown()) {

                dateWikiNote = DateHelper.getDate(year, monthOfYear, dayOfMonth);
                numberOfDayTv.setText(DateHelper.getOnlyDayFromDate(dateWikiNote));

            }
        }

    };

    @OnClick(R.id.changeDateNote)
    public void onClick() {
        showDatePicker();
    }
}
