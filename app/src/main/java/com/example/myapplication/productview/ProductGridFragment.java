package com.example.myapplication.productview;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.myapplication.LoginFragment;
import com.example.myapplication.MainActivity;
import com.example.myapplication.NavigationHost;
import com.example.myapplication.R;
import com.example.myapplication.productview.click_listeners.OnDeleteListener;
import com.example.myapplication.productview.click_listeners.OnEditListener;
import com.example.myapplication.productview.network.ProductNetworkService;
import com.example.myapplication.productview.dto.ProductDTO;
import com.example.myapplication.network.ProductEntry;
import com.example.myapplication.utils.network.CommonUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductGridFragment extends Fragment implements OnEditListener, OnDeleteListener {

    private static final String TAG = ProductGridFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private Button addButton;
    private final int REQUEST_CODE_EDIT = 101;
    private List<ProductEntry> listProductEntry;
    private ProductCardRecyclerViewAdapter productEntryAdapter;

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
        setupViews(view);
        setRecyclerView();
        setButtonAddListener();
        loadProductEntryList();

        return view;
    }

    private void setupViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        addButton = view.findViewById(R.id.add_button);


    }

    private void setRecyclerView() {
        listProductEntry = new ArrayList();
        productEntryAdapter = new ProductCardRecyclerViewAdapter(listProductEntry, this, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
                GridLayoutManager.VERTICAL, false));

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);

        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

        recyclerView.setAdapter(productEntryAdapter);
    }

    private void setButtonAddListener() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new ProductCreateFragment(), true);
            }
        });
    }

    private void loadProductEntryList() {

        CommonUtils.showLoading(getActivity());
        ProductNetworkService.getInstance()
                .getJSONApi()
                .getAllProducts()
                .enqueue(new Callback<List<ProductDTO>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ProductDTO>> call, @NonNull Response<List<ProductDTO>> response) {
                        CommonUtils.hideLoading();

                        if (response.isSuccessful()) {
                            listProductEntry.clear();
                            List<ProductDTO> list = response.body();
                            for (ProductDTO item : list) {
                                ProductEntry pe = new ProductEntry(item.getId(), item.getTitle(), item.getUrl(), item.getUrl(), item.getPrice(), "sdfasd");
                                listProductEntry.add(pe);
                            }
                            productEntryAdapter.notifyDataSetChanged();


                        } else {
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

                        //Toast.makeText(getActivity(), "Hello result"+ res.size(), Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ProductDTO>> call, @NonNull Throwable t) {
                        CommonUtils.hideLoading();
                        //   ((RequestErrorNavigate) getActivity()).navigateErrorPage(new ProductGridFragment(), false, t.getMessage()); // Navigate to the next Fragment

                        t.printStackTrace();
                    }
                });


    }

    private void deleteConfirm(final ProductEntry productEntry) {
            CommonUtils.showLoading(getContext());
            ProductNetworkService.getInstance()
                    .getJSONApi()
                    .DeleteRequest(productEntry.id)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                            CommonUtils.hideLoading();

                            if (response.isSuccessful()) {
                                listProductEntry.remove(productEntry);
                                productEntryAdapter.notifyDataSetChanged();
                            } else {
                                //  Log.e(TAG, "_______________________" + response.errorBody().charStream());

                                try {
//                                                String json = response.errorBody().string();
//                                                Gson gson  = new Gson();
//                                                ProductCreateInvalidDTO resultBad = gson.fromJson(json, ProductCreateInvalidDTO.class);
                                    //Log.d(TAG,"++++++++++++++++++++++++++++++++"+response.errorBody().string());
                                    //errormessage.setText(resultBad.getInvalid());
                                } catch (Exception e) {
                                    //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                            CommonUtils.hideLoading();
                            Log.e("ERROR", "*************ERORR request***********");
                            t.printStackTrace();

                        }
                    });
    }

    @Override
    public void deleteItem(final ProductEntry productEntry) {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Видалення")
                .setMessage("Ви дійсно бажаєте видалити \"" + productEntry.title + "\"?")
                .setNegativeButton("Скасувати", null)
                .setPositiveButton("Видалити", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteConfirm(productEntry);
                    }
                })
                .show();
    }

    @Override
    public void editItem(ProductEntry productEntry, int index) {
        Intent intent = new Intent(getActivity(), ProductEditActivity.class);
        intent.putExtra(ConstantIds.PRODUCT_INTENT_ID, productEntry.id);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_EDIT){
            if(resultCode == -1){
                loadProductEntryList();
            }
        }
    }
}
