package com.example.starwarsinfo.Species;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.starwarsinfo.R;

import java.util.List;

public class SpeciesAdapter  extends RecyclerView.Adapter<SpeciesAdapter.StarWarsViewHolder>{

    private List<SpeciesUtils.StarWarsSpecies> mSWList;
    private OnSpeciesItemClickListener mOnSpeciesClickListener;

    public interface OnSpeciesItemClickListener{
        void onSpeciesItemClick(SpeciesUtils.StarWarsSpecies swp);
    }

    public void updateSpeciesResults(List<SpeciesUtils.StarWarsSpecies> items){
        mSWList = items; //arraylist and list not working out
        notifyDataSetChanged();
    }
    public int adapterPositionToArrayIndex(int i){
        return mSWList.size() - i - 1;
    }

    public SpeciesAdapter(OnSpeciesItemClickListener click){
        mOnSpeciesClickListener = click;
    }

    public void addSWList(SpeciesUtils.StarWarsSpecies swSpecies) {
        mSWList.add(swSpecies);
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
    public SpeciesAdapter.StarWarsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.species_item, viewGroup, false);
        return new SpeciesAdapter.StarWarsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeciesAdapter.StarWarsViewHolder starWarsViewHolder, int i) {
        SpeciesUtils.StarWarsSpecies todo = mSWList.get(mSWList.size() - i - 1);
        starWarsViewHolder.bind(todo);
    }

    class StarWarsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameTV;
        private TextView classificationTV;
        private TextView designationTV;
        private TextView lifespanTV;
        private TextView languageTV;
       // private TextView homeworldTV;
        private TextView heightTV;

        public StarWarsViewHolder(View itemView) {
            super(itemView);
            classificationTV = itemView.findViewById(R.id.species_classification_tv);
            nameTV = itemView.findViewById(R.id.species_name_tv);
            designationTV = itemView.findViewById(R.id.species_designation_tv);
            lifespanTV = itemView.findViewById(R.id.species_lifespan_tv);
            languageTV = itemView.findViewById(R.id.species_language_tv);
          //  homeworldTV = itemView.findViewById(R.id.species_homeworld_tv);
            heightTV = itemView.findViewById(R.id.species_height_tv);
            itemView.setOnClickListener(this);

        }

        public void bind(SpeciesUtils.StarWarsSpecies SWdetail){
            nameTV.setText(SWdetail.name);

            String classificationText = "Classification: " + SWdetail.classification;
            classificationTV.setText(classificationText);

            String designationText = "Designation: " + SWdetail.designation;
            designationTV.setText(designationText);

            String lifeText = "Average Lifespan: " + SWdetail.average_lifespan;
            if( SWdetail.average_lifespan != null && !SWdetail.average_lifespan.equals("unknown"))
            {
                lifeText += " standard years";
            }
            lifespanTV.setText(lifeText);

            String heightText = "Average height: " + SWdetail.average_height;
            if( SWdetail.average_height != null && !SWdetail.average_height.equals("unknown"))
            {
                heightText += " centimeters";
            }
            heightTV.setText(heightText);

            String languageText = "Language: " + SWdetail.language;
            languageTV.setText(languageText);

         //   String homeText = "Homeworld: " + SWdetail.homeworld;
          //  homeworldTV.setText(homeText); //TODO Homeworld if we are going to use it needs to be searched as well. Otherwise leave it out.
        }
        @Override
        public void onClick(View v) {
            SpeciesUtils.StarWarsSpecies swp = mSWList.get(adapterPositionToArrayIndex(getAdapterPosition()));
            mOnSpeciesClickListener.onSpeciesItemClick(swp);
        }

    }
}
