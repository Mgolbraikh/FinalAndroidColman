package com.example.owner.winez;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owner.winez.Model.Model;
import com.example.owner.winez.Model.User;
import com.example.owner.winez.Model.Wine;
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
        list.setEmptyView(view.findViewById(R.id.mywines_empty_txt));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("TAG", "row selected " + position);

                Fragment wineDetailFrag = new WineFragment();
                FragmentTransaction ftr  = getActivity().getFragmentManager().beginTransaction();
                Bundle WineToShow = new Bundle();
                WineToShow.putInt("WineId", (Integer)view.getTag());

                wineDetailFrag.setArguments(WineToShow);
                ftr.replace(R.id.WinezActivityMainView, wineDetailFrag);
                ftr.addToBackStack(null);
                ftr.show(wineDetailFrag);
                ftr.commit();
            }
        });

        return view;

    }

    class MyWinesAdapter extends BaseAdapter{
        private User currentUser;

        public MyWinesAdapter(){

            currentUser = Model.getInstance().getCurrentUser();
        }
        @Override
        public int getCount() {
            return currentUser.getUserWines().size();
        }

        @Override
        public Object getItem(int i) {
            return currentUser.getUserWines().keySet().toArray()[i];

        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.row_my_wines_list, null);
                ImageView removeImage = (ImageView) view.findViewById(R.id.mywines_remove_image);
                removeImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentUser.getUserWines().remove(((View)view.getParent()).getTag());
                        notifyDataSetChanged();
                        Model.getInstance().saveCurrentUser(currentUser);
                    }
                });
            }
            Object item = this.getItem(i);
            TextView text = (TextView) view.findViewById(R.id.mywines_row_text);
            text.setText(currentUser.getUserWines().get(item));
            ImageView removeImage = (ImageView) view.findViewById(R.id.mywines_remove_image);
            view.setTag(item);
            return view;
        }
    }
}
