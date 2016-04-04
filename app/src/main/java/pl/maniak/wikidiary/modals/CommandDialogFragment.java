package pl.maniak.wikidiary.modals;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.events.CommandEvent;

/**
 * Created by Sony on 2016-01-17.
 */
public class CommandDialogFragment extends DialogFragment {


    public static CommandDialogFragment newInstance() {

        Bundle args = new Bundle();

        CommandDialogFragment fragment = new CommandDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CommandDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.fragment_command_modal, null);

        ButterKnife.bind(this, view);

        Dialog alertDialog = new Dialog(getActivity());
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(view);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return alertDialog;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (manager.findFragmentByTag(tag) == null) {
            super.show(manager, tag);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.commandMicBtn, R.id.commandHealthBtn, R.id.commandAddTagBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commandHealthBtn:
                App.postEvent(CommandEvent.SHOW_HEALTH);
                break;
            case R.id.commandMicBtn:
                App.postEvent(CommandEvent.SHOW_VOICE);
                break;
            case R.id.commandAddTagBtn:
                App.postEvent(CommandEvent.SHOW_ADD_TAG);
                break;
        }
        dismiss();
    }
}