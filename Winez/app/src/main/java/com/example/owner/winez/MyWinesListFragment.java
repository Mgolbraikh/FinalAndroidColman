package com.example.owner.winez;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owner.winez.Model.User;
import com.example.owner.winez.Utils.WinezAuth;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWinesListFragment extends Fragment {

    private MyWinesAdapter mAdapter;

    public MyWinesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wines_list, container, false);;
        ListView list = (ListView)view.findViewById(R.id.mmywines_list);
        mAdapter = new MyWinesAdapter();
        list.setAdapter(mAdapter);
        return view;

    }
    class MyWinesAdapter extends BaseAdapter{
        private User currentUser;

        public MyWinesAdapter(){
            currentUser = WinezAuth.getInstance().getCurrentUser();
        }
        @Override
        public int getCount() {
            return WinezAuth.getInstance().getCurrentUser().getUserWines().size();
        }

        @Override
        public Object getItem(int i) {
            String id = (String)currentUser.getUserWines().keySet().toArray()[i];
            return currentUser.getUserWines().get(id);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = LayoutInflater.from(getActivity()).inflate(R.layout.row_my_wines_list, null);
            }

            TextView text = (TextView) view.findViewById(R.id.mywines_row_text);
            text.setText(this.getItem(i).toString());
            return view;
        }
    }
}