package com.example.starwarsinfo.People;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.starwarsinfo.R;

import java.util.List;

public class PeopleAdapter  extends RecyclerView.Adapter<PeopleAdapter.StarWarsViewHolder>{

    private List<PeopleUtils.StarWarsPerson> mSWList;
    private OnPersonItemClickListener mOnPersonClickListener;

    public interface OnPersonItemClickListener{
        void onPersonItemClick(PeopleUtils.StarWarsPerson swp);
    }

    public void updatePeopleResults(List<PeopleUtils.StarWarsPerson> items){
        mSWList = items; //arraylist and list not working out
        notifyDataSetChanged();
    }
    public int adapterPositionToArrayIndex(int i){
        return mSWList.size() - i - 1;
    }


    public PeopleAdapter(OnPersonItemClickListener click){
        mOnPersonClickListener = click;
    }

    public void addSWList(PeopleUtils.StarWarsPerson swPerson) {
        mSWList.add(swPerson);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mSWList == null){
            return 0;
        }
        else{
            return mSWList.size();
        }
    }

    @NonNull
    @Override
    public PeopleAdapter.StarWarsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.person_item, viewGroup, false);
        return new PeopleAdapter.StarWarsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleAdapter.StarWarsViewHolder starWarsViewHolder, int i) {
        PeopleUtils.StarWarsPerson todo = mSWList.get(mSWList.size() - i - 1);
        starWarsViewHolder.bind(todo);
    }

    class StarWarsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameTV;
        private TextView heightTV;
        private TextView massTV;
       // private TextView filmsTV;
       // private TextView homeworldTV;//homeworld acts weird. So we should just drop it for now.

        public StarWarsViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.person_name_tv);
            heightTV = itemView.findViewById(R.id.person_height_tv);
            massTV = itemView.findViewById(R.id.person_mass_tv);
            //filmsTV = itemView.findViewById(R.id.person_films_tv);
            //homeworldTV = itemView.findViewById(R.id.person_homeworld_tv);
            itemView.setOnClickListener(this);
        }

        public void bind(PeopleUtils.StarWarsPerson SWdetail) {
            nameTV.setText(SWdetail.name);

            String heightText = "Height: " + SWdetail.height;
            if(SWdetail.height!= null &&!SWdetail.height.equals("unknown"))
            {
                heightText += " centimeters";
            }

            String massText = "Mass: " + SWdetail.mass;
            if(SWdetail.mass!= null && !SWdetail.mass.equals("unknown"))
            {
                massText += " kilograms";
            }
            //String filmText = "Films: " + SWdetail.films;
           // String homeText = "Homeworld: " + SWdetail.homeworld;
            heightTV.setText(heightText);
            massTV.setText(massText);
          //  homeworldTV.setText(homeText);
        }

        @Override
        public void onClick(View v) {
            PeopleUtils.StarWarsPerson swp = mSWList.get(adapterPositionToArrayIndex(getAdapterPosition()));
            mOnPersonClickListener.onPersonItemClick(swp);
        }

        /* CANNOT HAVE MULTIPLE BINDS
        public void bind(PlanetUtils.StarWarsPlanet SWdetail){
            mStarWarsTV.setText(SWdetail.name);
            mStarWarsTV.setText(SWdetail.gravitiy);
            mStarWarsTV.setText(SWdetail.mass);
            mStarWarsTV.setText(SWdetail.population);
        }

        public void bind(SpeciesUtils.StarWarsSpecies SWdetail){
            mStarWarsTV.setText(SWdetail.name);
            mStarWarsTV.setText(SWdetail.classification);
            mStarWarsTV.setText(SWdetail.mass);
        }
        */
    }
}
