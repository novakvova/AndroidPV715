package com.example.myapplication.productview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.jsonretro.ProdNetworkService;
import com.example.myapplication.jsonretro.ProductDTO;
import com.example.myapplication.network.ProductEntry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductGridFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_grid, container, false);

        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
                GridLayoutManager.VERTICAL, false));

        List<ProductEntry> list = ProductEntry.initProductEntryList(getResources());
        ProductCardRecyclerViewAdapter adapter = new ProductCardRecyclerViewAdapter(list);

        recyclerView.setAdapter(adapter);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);


        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));


//        ProdNetworkService.getInstance()
//                .getJSONApi()
//                .getAllProducts()
//                .enqueue(new Callback<List<ProductDTO>>() {
//                    @Override
//                    public void onResponse(@NonNull Call<List<ProductDTO>> call, @NonNull Response<List<ProductDTO>> response) {
//                        List<ProductDTO> res= response.body();
//                        Toast.makeText(getActivity(), "Hello result"+ res.size(), Toast.LENGTH_LONG);
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<List<ProductDTO>> call, @NonNull Throwable t) {
//
//                        //textView.append("Error occurred while getting request!");
//                        t.printStackTrace();
//                    }
//                });



        return view;
    }
}
