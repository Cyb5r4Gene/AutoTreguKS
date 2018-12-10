package rks.youngdevelopers.autotreguks;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> listaFragmenteve = new ArrayList<>();
    private final List<String> listaTitujve = new ArrayList<>();

    public void addFragment(Fragment fragmenti, String titulli)
    {
        listaFragmenteve.add(fragmenti);
        listaTitujve.add(titulli);
    }

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listaTitujve.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return listaFragmenteve.get(position);
    }

    @Override
    public int getCount() {
        return listaTitujve.size();
    }
}
