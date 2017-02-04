package com.example.owner.winez;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owner.winez.Model.Model;
import com.example.owner.winez.Utils.ApiClasses.WineApiClass;
import com.example.owner.winez.Utils.WineApi;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllWinesFragment extends Fragment {

    List<WineApiClass> allWines;
    AllWinesAdapter mAdapter;

    public AllWinesFragment() {
        Log.d("stuf:", "asdjfsadjfklasdjflkasjdf");
     }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_wines, container, false);
        final ListView list = (ListView)view.findViewById(R.id.all_wines_list);
        mAdapter = new AllWinesAdapter();
        allWines = new ArrayList<>();
        WineApi.getInstance().GetWinesByCategory(new WineApi.GetResultOnRespons<WineApiClass>() {
            @Override
            public void onResult(ArrayList<WineApiClass> data) {
                allWines = data;
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {

            }
        });

        list.setAdapter(mAdapter);
        list.setEmptyView(view.findViewById(R.id.all_wines_empty_txt));

        return view;
    }

    class AllWinesAdapter extends BaseAdapter{



        @Override
        public int getCount() {
            return allWines.size();
        }

        @Override
        public Object getItem(int i) {
            return allWines.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                view = LayoutInflater.from(getActivity()).inflate(R.layout.row_all_wine_list,null);
                CheckBox cb = (CheckBox)view.findViewById(R.id.all_wine_favorite);

                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WineApiClass currentWine =
                                (WineApiClass)getItem((Integer) ((View)view.getParent()).getTag());
                        if(((CheckBox)view).isChecked()) {
                            Model.getInstance().addWine(currentWine);
                        }else{
                            Model.getInstance().removeWine(currentWine);
                        }
                    }
                });
            }

            WineApiClass item = (WineApiClass)this.getItem(i);
            TextView nameText = (TextView) view.findViewById(R.id.row_all_wine_name);
            nameText.setText( item.getName());
            TextView typeText = (TextView) view.findViewById(R.id.row_all_wine_type);
            typeText.setText( item.getType());
            CheckBox cb = (CheckBox)view.findViewById(R.id.all_wine_favorite);
            cb.setChecked(Model.getInstance().getCurrentUser().getUserWines().containsKey(item.getId()));
            view.setTag(i);

            return view;
        }
    }

}
