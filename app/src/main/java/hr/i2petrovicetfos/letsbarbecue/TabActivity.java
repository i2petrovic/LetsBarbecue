package hr.i2petrovicetfos.letsbarbecue;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {

    private final List<Fragment> myFragmentList = new ArrayList<>();
    private final List<String> myFragmentTitleList = new ArrayList<>();

    private ViewPager myViewPager;
    private SectionsPageAdapter mySectionsPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        mySectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());


        myViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(myViewPager);

        myViewPager.setOffscreenPageLimit(4);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(myViewPager);

//ovaj activity predstavlja fragment activity sa 5 tabova koji se definiraju u setupViewPager-u
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter myAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        myAdapter.addFragment(new TabPocetna(), "Home");
        myAdapter.addFragment(new TabHrana(), "Hrana");
        myAdapter.addFragment(new TabPice(), "Piće");
        myAdapter.addFragment(new TabOstalo(), "Ostalo");
        myAdapter.addFragment(new TabPregled(), "Pregled");
        viewPager.setAdapter(myAdapter);

    }

    public class SectionsPageAdapter extends FragmentPagerAdapter {

        public void addFragment(Fragment fragment, String title) {
            myFragmentList.add(fragment);
            myFragmentTitleList.add(title);
        }

        public SectionsPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return myFragmentTitleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return myFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return myFragmentList.size();
        }


    }

}

