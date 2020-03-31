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
import android.widget.Toast;

import com.example.myapplication.NavigationHost;
import com.example.myapplication.R;
import com.example.myapplication.jsonretro.ProdNetworkService;
import com.example.myapplication.jsonretro.ProductDTO;
import com.example.myapplication.network.ProductEntry;
import com.example.myapplication.productCreate.ProductCreateFragment;
import com.example.myapplication.utils.network.CommonUtils;
import com.example.myapplication.utils.network.NoConnectivityException;
import com.example.myapplication.utils.network.RequestErrorNavigate;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

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
        MaterialButton addButton = view.findViewById(R.id.add_button);
        final TextInputEditText productTitleEditText = view.findViewById(R.id.product_title_edit_text);
        final TextInputEditText productPriceEditText = view.findViewById(R.id.price_edit_text);

        // Set an error if the password is less than 8 characters.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new ProductCreateFragment(), false);

            }
        });
        CommonUtils.showLoading(getActivity());
        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
                GridLayoutManager.VERTICAL, false));

//        List<ProductEntry> list = ProductEntry.initProductEntryList(getResources());
//        ProductCardRecyclerViewAdapter adapter = new ProductCardRecyclerViewAdapter(list);
//
//        recyclerView.setAdapter(adapter);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);


        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));


        ProdNetworkService.getInstance()
                .getJSONApi()
                .getAllProducts()
                .enqueue(new Callback<List<ProductDTO>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ProductDTO>> call, @NonNull Response<List<ProductDTO>> response) {
                        CommonUtils.hideLoading();
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


                        //Toast.makeText(getActivity(), "Hello result"+ res.size(), Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ProductDTO>> call, @NonNull Throwable t) {
                        CommonUtils.hideLoading();
                        ((RequestErrorNavigate) getActivity()).navigateErrorPage(new ProductGridFragment(), false, t.getMessage()); // Navigate to the next Fragment

                        t.printStackTrace();
                    }
                });


        return view;
    }
}
