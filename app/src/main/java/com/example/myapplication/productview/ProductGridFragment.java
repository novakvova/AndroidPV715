package com.example.myapplication.productview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.NavigationHost;
import com.example.myapplication.R;
import com.example.myapplication.productview.network.ProductNetworkService;
import com.example.myapplication.productview.dto.ProductDTO;
import com.example.myapplication.network.ProductEntry;
import com.example.myapplication.utils.network.CommonUtils;
import com.example.myapplication.utils.network.RequestErrorNavigate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductGridFragment extends Fragment {

    private static final String TAG = ProductGridFragment.class.getSimpleName();

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_grid, container, false);
        CommonUtils.showLoading(getActivity());
        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
                GridLayoutManager.VERTICAL, false));
        Button addButton = view.findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost)getActivity()).navigateTo(new ProductCreateFragment(), true);
            }
        });
//        List<ProductEntry> list = ProductEntry.initProductEntryList(getResources());
//        ProductCardRecyclerViewAdapter adapter = new ProductCardRecyclerViewAdapter(list);
//
//        recyclerView.setAdapter(adapter);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);


        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));


        ProductNetworkService.getInstance()
                .getJSONApi()
                .getAllProducts()
                .enqueue(new Callback<List<ProductDTO>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ProductDTO>> call, @NonNull Response<List<ProductDTO>> response) {

                        if (response.isSuccessful()) {
                            List<ProductDTO> list = response.body();
                            String res = list.get(0).toString();
                            Log.d(TAG, "--------result server-------" + res);

                            List<ProductEntry> newlist = new ArrayList<ProductEntry>();//ProductEntry.initProductEntryList(getResources());
                            for (ProductDTO item : list) {
                                ProductEntry pe = new ProductEntry(item.getTitle(), item.getUrl(), item.getUrl(), item.getPrice(), "sdfasd");
                                newlist.add(pe);
                            }
                            ProductCardRecyclerViewAdapter newAdapter = new ProductCardRecyclerViewAdapter(newlist);

                            recyclerView.swapAdapter(newAdapter, false);
                        }
                        else {
                            //  Log.e(TAG, "_______________________" + response.errorBody().charStream());

//                                    try {
//                                        String json = response.errorBody().string();
//                                        Gson gson = new Gson();
//                                        LoginDTOBadRequest resultBad = gson.fromJson(json, LoginDTOBadRequest.class);
//                                        //Log.d(TAG,"++++++++++++++++++++++++++++++++"+response.errorBody().string());
//                                        errorMessage.setText(resultBad.getInvalid());
//                                    } catch (Exception e) {
//                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                                    }
                        }
                        CommonUtils.hideLoading();

                        //Toast.makeText(getActivity(), "Hello result"+ res.size(), Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ProductDTO>> call, @NonNull Throwable t) {
                        CommonUtils.hideLoading();
                     //   ((RequestErrorNavigate) getActivity()).navigateErrorPage(new ProductGridFragment(), false, t.getMessage()); // Navigate to the next Fragment

                        t.printStackTrace();
                    }
                });


        return view;
    }
}
