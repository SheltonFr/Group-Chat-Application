package dev.isutc.chatapplications.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import dev.isutc.chatapplications.ui.fragments.ChatsListFragment;
import dev.isutc.chatapplications.ui.fragments.ContactsListFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new ContactsListFragment();
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
