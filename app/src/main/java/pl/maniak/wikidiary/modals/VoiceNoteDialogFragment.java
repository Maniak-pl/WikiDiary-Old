package pl.maniak.wikidiary.modals;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.maniak.wikidiary.App;
import pl.maniak.wikidiary.R;
import pl.maniak.wikidiary.events.CommandEvent;
import pl.maniak.wikidiary.views.NumberKeyboardView;

/**
 * Created by Sony on 2016-01-17.
 */
public class VoiceNoteDialogFragment extends DialogFragment implements NumberKeyboardView.NumberKeyboardEventListener {

    private static final String VOICE_STRING_LIST = "VOICE_STRING_LIST";

    ListView mVoiceNotelist;
    ArrayList mListNote = new ArrayList();

    public static VoiceNoteDialogFragment newInstance(ArrayList<String> list) {

        Bundle args = new Bundle();
        args.putStringArrayList(VOICE_STRING_LIST, list);

        VoiceNoteDialogFragment fragment = new VoiceNoteDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public VoiceNoteDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.fragment_voice_note, null);
        mVoiceNotelist = (ListView) view.findViewById(R.id.voiceNotelist);

        mListNote = getArguments().getStringArrayList(VOICE_STRING_LIST);
        mVoiceNotelist.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mListNote));
        mVoiceNotelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                App.postEvent(CommandEvent.SHOW_VOICE_RESULT, mListNote.get(position).toString());
                dismiss();
            }
        });

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
//
    }

    @Override
    public void onNumberKeyboardReset() {

    }


}