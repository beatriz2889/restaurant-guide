package ca.gbc.comp3074.personalrestaurantguide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EntryAdapter extends RecyclerView.Adapter<EntryViewHolder>implements Filterable {

    private Context context;
    private ArrayList<Entries>listEntries;
    private ArrayList<Entries>mArrayList;
    ItemClickListener itemClickListener;

    private SqliteDatabase mDatabase;

        public EntryAdapter(Context context, ArrayList<Entries> listEntries)
        {
            this.context=context;
            this.listEntries=listEntries;
            this.mArrayList=listEntries;
            mDatabase=new SqliteDatabase(context);
        }

        @Override
        public EntryViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_list_layout,parent,false);
            return new EntryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(EntryViewHolder holder,int position){
            final Entries entries=listEntries.get(position);

            holder.nameTxtView.setText(entries.getName());
            holder.tagTxtView.setText(entries.getTags());

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {

                    Intent intent=new Intent(context,RestaurantDetailsActivity.class);

                    intent.putExtra("COLUMN_ID",listEntries.get(pos).getId());
                    intent.putExtra("COLUMN_NAME",listEntries.get(pos).getName());
                    intent.putExtra("COLUMN_ADDRESS",listEntries.get(pos).getAddress());
                    intent.putExtra("COLUMN_PHONE",listEntries.get(pos).getPhone());
                    intent.putExtra("COLUMN_DESCRIPTION",listEntries.get(pos).getDescription());
                    intent.putExtra("COLUMN_RATING",listEntries.get(pos).getRating());
                    intent.putExtra("COLUMN_TAGS",listEntries.get(pos).getTags());

                    context.startActivity(intent);
                }
            });

            holder.editEntry.setOnClickListener(new View.OnClickListener(){
               @Override
                public void onClick(View v){
                  sendData(v, position);
                }
            });

            holder.deleteEntry.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    mDatabase.deleteEntry(entries.getId());
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            });
        }

        private void sendData(View v, int pos){

            Intent intent=new Intent(context,EditRestaurantActivity.class);
            intent.putExtra("COLUMN_ID",listEntries.get(pos).getId());
            intent.putExtra("COLUMN_NAME",listEntries.get(pos).getName());
            intent.putExtra("COLUMN_ADDRESS",listEntries.get(pos).getAddress());
            intent.putExtra("COLUMN_PHONE",listEntries.get(pos).getPhone());
            intent.putExtra("COLUMN_DESCRIPTION",listEntries.get(pos).getDescription());
            intent.putExtra("COLUMN_RATING",listEntries.get(pos).getRating());
            intent.putExtra("COLUMN_TAGS",listEntries.get(pos).getTags());

            context.startActivity(intent);
        }


        @Override
        public Filter getFilter(){
            return new Filter(){
                @Override
                protected FilterResults performFiltering(CharSequence charSequence){
                    String charString=charSequence.toString();
                    if(charString.isEmpty()){
                        listEntries=mArrayList;
                    }else{
                        ArrayList<Entries>filteredList=new ArrayList<>();

                        for(Entries entries:mArrayList){
                            if(entries.getName().toLowerCase().contains(charString)){
                                filteredList.add(entries);
                            }
                        }
                        listEntries=filteredList;
                    }
                    FilterResults filterResults=new FilterResults();
                    filterResults.values=listEntries;
                    return filterResults;
                }
                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults){
                    listEntries=(ArrayList<Entries>)filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        @Override
        public int getItemCount(){
            return listEntries.size();
        }
}
