package pl.maniak.wikidiary.ui.wikinote.add.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.utils.events.CommandEvent;
import pl.maniak.wikidiary.utils.views.NumberKeyboardView;

/**
 * Created by Sony on 2016-01-17.
 */
public class NumberKeyboardDialogFragment extends DialogFragment implements NumberKeyboardView.NumberKeyboardEventListener{

    private static final String KEYBOARD_HEADER_TITLE = "KEYBOARD_HEADER_TITLE";

    NumberKeyboardView numberKeyboardView;
    TextView headerNumberKeyboard;

    public static NumberKeyboardDialogFragment newInstance(String headerTitle) {

        Bundle args = new Bundle();
        args.putString(KEYBOARD_HEADER_TITLE,headerTitle);

        NumberKeyboardDialogFragment fragment = new NumberKeyboardDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NumberKeyboardDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.fragment_dialog_keyboard, null);
        numberKeyboardView = (NumberKeyboardView) view.findViewById(R.id.numberKeyboardView );
        headerNumberKeyboard = (TextView) view.findViewById(R.id.numberKeyboardHeaderTv);



        numberKeyboardView.setListener(this);

        String pinKeyboardHeader = getArguments().getString(KEYBOARD_HEADER_TITLE,"");
        headerNumberKeyboard.setText(pinKeyboardHeader);

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
    public void onNumberTextChanged(String numberString) {

    }

    @Override
    public void onMaxNumberReached() {

    }

    @Override
    public void onAcceptBtnClicked() {
        App.postEvent(CommandEvent.SHOW_HEALTH_RESULT, numberKeyboardView.getNumberText());
        dismiss();
    }

    @Override
    public void onNumberKeyboardReset() {

    }




}