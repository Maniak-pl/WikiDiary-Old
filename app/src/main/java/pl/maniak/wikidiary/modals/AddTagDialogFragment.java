package pl.maniak.wikidiary.modals;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.repository.DBHelper;
import pl.maniak.wikidiary.events.CommandEvent;
import pl.maniak.wikidiary.domain.tag.Tag;
import pl.maniak.wikidiary.repository.tag.TagRepository;
import pl.maniak.wikidiary.views.FlowLayout;


public class AddTagDialogFragment extends DialogFragment {

    @BindView(R.id.addTagEt)
    EditText addTagEt;
    @BindView(R.id.settingsContainerTags)
    FlowLayout mFlowLayout;

    private Set<Tag> setTag = new TreeSet<Tag>();

    @Inject
    public TagRepository repository;

    @Inject
    SharedPreferences preferences;


    public static AddTagDialogFragment newInstance() {
        Bundle args = new Bundle();
        AddTagDialogFragment fragment = new AddTagDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AddTagDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.fragment_add_tag_modal, null);

        ButterKnife.bind(this, view);
        App.getAppComponent().inject(this);
        ButterKnife.bind(this, view);
        setTag.addAll(loadTag());

        Dialog alertDialog = new Dialog(getActivity());
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(view);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return alertDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        initTagContener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.addTagBtn)
    public void onClick() {
        if (!addTagEt.getText().toString().equals("")) {
            repository.addTag(addTagEt.getText().toString());
            addTagEt.setText("");
            reload();
        }
    }

    private void reload() {
        App.postEvent(CommandEvent.REFRESH);
        dismiss();

    }

    private void initTagContener() {
        mFlowLayout.removeAllViews();
        List<Tag> listTag = new ArrayList();
        listTag.addAll(setTag);
        for (int i = 0; i < listTag.size(); i++) {
            TextView tv = (TextView) getActivity().getLayoutInflater().inflate(R.layout.tag_item, null);
            tv.setText(listTag.get(i).getTag());
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            tv.setLayoutParams(params);
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String tag = ((TextView) v).getText().toString();
                    setTag.remove(new Tag(tag));
                    repository.deleteTag(tag);
                    reload();
                    return false;
                }
            });
            mFlowLayout.addView(tv);
        }
        mFlowLayout.invalidate();
    }

    public List<Tag> loadTag() {
        return repository.getAllTags();
    }
}
