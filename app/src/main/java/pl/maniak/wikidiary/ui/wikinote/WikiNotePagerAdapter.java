package pl.maniak.wikidiary.ui.wikinote;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import pl.maniak.wikidiary.fragments.MainFragment;
import pl.maniak.wikidiary.fragments.PreparingNoteFragment;
import pl.maniak.wikidiary.ui.wikinote.list.ListNotesFragmentImpl;

public class WikiNotePagerAdapter extends FragmentStatePagerAdapter{

    public final static int ADD_PAGE_INDEX = 0;
    public final static int PREPARING_PAGE_INDEX = 1;
    public final static int LIST_PAGE_INDEX = 2;

    private final static int PAGE_NUMBER = 3;

    private SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public WikiNotePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case ADD_PAGE_INDEX:
                return MainFragment.newInstance();
            case PREPARING_PAGE_INDEX:
                return PreparingNoteFragment.newInstance();
            case LIST_PAGE_INDEX:
                return ListNotesFragmentImpl.newInstance();
            default:
                throw new IllegalArgumentException("Illegal position for page was requested");
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Add";
            case 1:
                return "Preparing Note";
            case 2:
                return "List";
        }
        return null;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }
}
