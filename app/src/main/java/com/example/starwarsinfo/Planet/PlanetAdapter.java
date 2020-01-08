package com.example.starwarsinfo.Planet;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.starwarsinfo.R;

import java.util.List;

public class PlanetAdapter  extends RecyclerView.Adapter<PlanetAdapter.StarWarsViewHolder>{

    private List<PlanetUtils.StarWarsPlanet> mSWList;
    private OnPlanetItemClickListener mOnPlanetClickListener;

    public interface OnPlanetItemClickListener{
        void onPlanetItemClick(PlanetUtils.StarWarsPlanet swp);
    }

    public void updatePlanetResults(List<PlanetUtils.StarWarsPlanet> items){
        mSWList = items; //arraylist and list not working out
        notifyDataSetChanged();
    }
    public int adapterPositionToArrayIndex(int i){
        return mSWList.size() - i - 1;
    }

    public PlanetAdapter(OnPlanetItemClickListener click){
        mOnPlanetClickListener = click;
    }

    public void addSWList(PlanetUtils.StarWarsPlanet swPlanet) {
        mSWList.add(swPlanet);
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
    public PlanetAdapter.StarWarsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.planet_item, viewGroup, false);
        return new PlanetAdapter.StarWarsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanetAdapter.StarWarsViewHolder starWarsViewHolder, int i) {
        PlanetUtils.StarWarsPlanet todo = mSWList.get(mSWList.size() - i - 1);
        starWarsViewHolder.bind(todo);
    }

    class StarWarsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView gravityTV;
        private TextView nameTV;
        private TextView diameterTV;
        private TextView climateTV;
        private TextView rotation_periodTV;
        private TextView terrainTV;
        private TextView populationTV;

        public StarWarsViewHolder(View itemView) {
            super(itemView);
            gravityTV = itemView.findViewById(R.id.planet_gravity_tv);
            nameTV = itemView.findViewById(R.id.planet_name_tv);
            diameterTV = itemView.findViewById(R.id.planet_diameter_tv);
            climateTV = itemView.findViewById(R.id.planet_climate_tv);
            rotation_periodTV = itemView.findViewById(R.id.planet_rotation_tv);
            terrainTV = itemView.findViewById(R.id.planet_terrain_tv);
            populationTV = itemView.findViewById(R.id.planet_population_tv);
            itemView.setOnClickListener(this);

        }

        public void bind(PlanetUtils.StarWarsPlanet SWdetail){
            nameTV.setText(SWdetail.name);

            String gravityText = "Gravity: " + SWdetail.gravity;
            gravityTV.setText(gravityText);

            String terrainText = "Terrain: " + SWdetail.terrain;
            terrainTV.setText(terrainText);

            String diameterText = "Diameter: ";
            if(SWdetail.diameter != null && SWdetail.diameter.equals("0"))
            {
                diameterText = "Unknown";
            }
            else
            {
                diameterText += SWdetail.diameter + " kilometers";
            }
            diameterTV.setText(diameterText);

            String populationText = "Population: " + SWdetail.population;
            populationTV.setText(populationText);

            String climateText = "Climate: " + SWdetail.climate;
            climateTV.setText(climateText);

            String rotationText = "Rotation Period: " + SWdetail.rotation_period + " hours";
            rotation_periodTV.setText(rotationText);
        }
        @Override
        public void onClick(View v) {
            PlanetUtils.StarWarsPlanet swp = mSWList.get(adapterPositionToArrayIndex(getAdapterPosition()));
            mOnPlanetClickListener.onPlanetItemClick(swp);
        }

    }
}
