package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.Callbacks {

    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";
    private static final String ADAPTER_POSITION_KEY = "adapter_position_key";
    private static final String TAG = "CrimeListFragment";

    private ViewPager mViewPager;
    private Button mJumpFirstButton;
    private Button mJumpLastButton;
    private List<Crime> mCrimes;
    private int mAdapterPosition;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Potato
            }

            @Override
            public void onPageSelected(int position) {
                updateButtons(position);
                mAdapterPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Blyat
            }
        });

        mJumpFirstButton = (Button) findViewById(R.id.jump_first_button);
        mJumpFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

        mJumpLastButton = (Button) findViewById(R.id.jump_last_button);
        mJumpLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mCrimes.size() - 1);
            }
        });

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                Log.i("CrimePagerActivity: ", "Creating new CrimeFragment");
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }

        });

        for (int i = 0; i < mCrimes.size(); ++i) {
            if(mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                updateButtons(i);
                mAdapterPosition = i;
                break;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(ADAPTER_POSITION_KEY, mAdapterPosition);
        Log.i(TAG, "onSaveInstanceState: ");
    }


    private void updateButtons(int position) {
        if(position == 0) {
            mJumpFirstButton.setEnabled(false);
            mJumpLastButton.setEnabled(true);
        }
        else if(position == (mCrimes.size() - 1)) {
            mJumpLastButton.setEnabled(false);
            mJumpFirstButton.setEnabled(true);
        }
        else {
            mJumpFirstButton.setEnabled(true);
            mJumpLastButton.setEnabled(true);
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {}

    @Override
    public void onCrimeDeleted(Activity activity) {
        activity.finish();
    }

}