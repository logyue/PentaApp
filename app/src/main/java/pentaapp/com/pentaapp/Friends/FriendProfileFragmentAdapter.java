package pentaapp.com.pentaapp.Friends;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Kevin on 4/30/2017.
 */

public class FriendProfileFragmentAdapter extends FragmentPagerAdapter {


    /**
     * Create a new {@link pentaapp.com.pentaapp.Fragments.SimpleFragmentPagerAdapter} object.
     *
     * @param context is the context of the app
     * @param fm      is the fragment manager that will keep each fragment's state in the adapter
     *                across swipes.
     */
    public FriendProfileFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PhysicalStatsFragment();
        } else if (position == 1) {
            return new FriendInfoFragment();
        } else if (position == 2) {
            return new NutritionalStatsFragment();
        } else
            return new PhysicalStatsFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Physical Stats";
        }
        else if(position == 1)
            return "Friend Info";
        else
            return "Nutritional Stats";
    }
}


