package com.numnu.android.fragments.search;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.numnu.android.R;
import com.numnu.android.activity.SliceActivity;
import com.numnu.android.fragments.auth.LoginFragment;
import com.numnu.android.fragments.detail.EventDetailFragment;
import com.numnu.android.fragments.detail.ItemDetailFragment;
import com.numnu.android.fragments.detail.SearchBusinessDetailFragment;
import com.numnu.android.fragments.detail.UserDetailsFragment;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.ServiceGenerator;
import com.numnu.android.network.request.BookmarkRequestData;
import com.numnu.android.network.response.BookmarkResponse;
import com.numnu.android.network.response.PostdataItem;
import com.numnu.android.utils.Constants;
import com.numnu.android.utils.PreferencesHelper;
import com.numnu.android.utils.Utils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SliceFragment extends Fragment {

    Context context;
    private PopupWindow pw;
    private ImageView barbqicon,cattgicon,eventicon;
    private String postId;
    private ImageView userImageIcon,contentImage;
    private TextView nameText,cottageHouseText,barbequeText,eventText,userNameTxt,title;
    private PostdataItem postsResponse;
    private Uri imagePath;
    // Create a storage reference from our app
    StorageReference storageRef ;
    private FirebaseStorage storage;
    private String businessId;
    boolean isVisibleToUser;
    private ProgressDialog mProgressDialog;

    public static SliceFragment newInstance() {
        SliceFragment fragment = new SliceFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            postId = bundle.getString("postId");
        }


    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_slice, container, false);

//        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
//        View bottomSheetView = inflater.inflate(R.layout.dialog_share_bookmark,null);
//        bottomSheetDialog.setContentView(bottomSheetView);

       barbqicon = view.findViewById(R.id.barbq_icon);
      cattgicon = view.findViewById(R.id.cottage_house_icon);
        eventicon = view.findViewById(R.id.barbados_icon);
        ImageView moreIcon = view.findViewById(R.id.event_dots);
        RelativeLayout toolbarBackIcon = view.findViewById(R.id.toolbar_back);
         userImageIcon = view.findViewById(R.id.slice_profile_image);
         contentImage = view.findViewById(R.id.content_image);
         nameText = view.findViewById(R.id.slice_toolbar_profile_name);
        userNameTxt = view.findViewById(R.id.user_name);
         cottageHouseText = view.findViewById(R.id.cottage_house_txt);
         barbequeText = view.findViewById(R.id.barbq_txt);
         eventText = view.findViewById(R.id.barbados_txt);
        title=view.findViewById(R.id.title);
        ImageView toolimg = view.findViewById(R.id.toolbar_image);
        toolimg.setVisibility(View.GONE);

        toolbarBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        TextView toolbarTitle=view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Post");

        userImageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, UserDetailsFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

        nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, UserDetailsFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });

  cottageHouseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, SearchBusinessDetailFragment.newInstance(businessId));
                transaction.addToBackStack(null).commit();
            }
        });

      cattgicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, SearchBusinessDetailFragment.newInstance(businessId));
                transaction.addToBackStack(null).commit();
            }
        });
        barbequeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout,
                        ItemDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        barbqicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout,
                        ItemDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        eventText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
       eventicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                transaction.replace(R.id.frame_layout, EventDetailFragment.newInstance());
                transaction.addToBackStack(null).commit();
            }
        });
        moreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(inflater);
            }
        });

        if(Utils.isNetworkAvailable(context)) {
            getData(postId);
        }else {
            showAlert();
        }
        contentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                initiatePopupWindow();
                if(imagePath!=null){
                    Intent intent=new Intent(getActivity(), SliceActivity.class);
                    intent.putExtra("imagepath",imagePath.toString());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    private void showAlert() {
    }

    private void getData(String id)
    {

        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<PostdataItem> call=apiServices.getPostById(id);
        call.enqueue(new Callback<PostdataItem>() {

            @Override
            public void onResponse(Call<PostdataItem> call, Response<PostdataItem> response) {

                int responsecode = response.code();
                if(responsecode==200) {
                    postsResponse = response.body();
                    businessId = String.valueOf(postsResponse.getBusiness().getId());
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<PostdataItem> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateUI() {

            storage = FirebaseStorage.getInstance();
            // Create a storage reference from our app
            storageRef = storage.getReference();

            if(!postsResponse.getPostimages().isEmpty()&&postsResponse.getPostimages().get(0).getImageurl()!=null) {
                storageRef.child(postsResponse.getPostimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        imagePath = uri;
                        // Got the download URL for 'users/me/profile.png'
                        Picasso.with(context).load(uri)
                                .placeholder(R.drawable.background)
                                .fit()
                                .into(contentImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }


        if(!postsResponse.getPostcreator().getUserimages().isEmpty()&&postsResponse.getPostcreator().getUserimages().get(0).getImageurl()!=null) {
            storageRef.child(postsResponse.getPostcreator().getUserimages().get(0).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Picasso.with(context).load(uri)
                            .placeholder(R.drawable.background)
                            .fit()
                            .into(userImageIcon);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }

        eventText.setText(postsResponse.getEvent().getName());
        userNameTxt.setText(postsResponse.getPostcreator().getUsername());
        nameText.setText(postsResponse.getPostcreator().getName());
        title.setText(postsResponse.getComment());
        cottageHouseText.setText(postsResponse.getBusiness().getBusinessname());
        if(!postsResponse.getTaggeditems().isEmpty()) {
           barbequeText.setText(postsResponse.getTaggeditems().get(0).getName());
        }
        
    }


    private void showBottomSheet(LayoutInflater inflater) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View bottomSheetView = inflater.inflate(R.layout.dialog_share_bookmark,null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        ImageView shareimg = bottomSheetView.findViewById(R.id.dialog_image);
        ImageView bookmarkimg = bottomSheetView.findViewById(R.id.bookmark_icon);
        TextView share = bottomSheetView.findViewById(R.id.share_title);
        TextView bookmark = bottomSheetView.findViewById(R.id.bookmark_title);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Post Content here..."+context.getPackageName());
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.share_using)));
                bottomSheetDialog.dismiss();
            }
        });
        shareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Post Content here..."+context.getPackageName());
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.share_using)));
                bottomSheetDialog.dismiss();
            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean loginStatus =  PreferencesHelper.getPreferenceBoolean(context,PreferencesHelper.PREFERENCE_LOGGED_IN);
                if (!loginStatus) {
                    Bundle bundle = new Bundle();
                    bundle.putString("BusinessBookmarkIntent","businessbookmark");
                    LoginFragment logFragment = new LoginFragment();
                    logFragment.setArguments(bundle);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                    transaction.replace(R.id.frame_layout, logFragment);
                    transaction.addToBackStack(null).commit();
//                    Intent intent = new Intent(context, LoginFragment.class);
//                    intent.putExtra("BusinessBookmarkIntent","businessbookmark");
//                    context.startActivity(intent);
                    bottomSheetDialog.dismiss();
                }else if (loginStatus){
                    postBookmark();
                    bottomSheetDialog.dismiss();
                }
            }
        });
        bookmarkimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean loginStatus =  PreferencesHelper.getPreferenceBoolean(context,PreferencesHelper.PREFERENCE_LOGGED_IN);
                if (!loginStatus) {
                    Bundle bundle = new Bundle();
                    bundle.putString("BusinessBookmarkIntent","businessbookmark");
                    LoginFragment logFragment = new LoginFragment();
                    logFragment.setArguments(bundle);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_righ);
                    transaction.replace(R.id.frame_layout, logFragment);
                    transaction.addToBackStack(null).commit();
//                    Intent intent = new Intent(context, LoginFragment.class);
//                    intent.putExtra("BusinessBookmarkIntent","businessbookmark");
//                    context.startActivity(intent);
                    bottomSheetDialog.dismiss();
                }else if (loginStatus){
                    postBookmark();
                    bottomSheetDialog.dismiss();
                }
            }
        });
    }

    private void postBookmark()
    {
        showProgressDialog();
        String userId = PreferencesHelper.getPreference(context, PreferencesHelper.PREFERENCE_ID);
        BookmarkRequestData bookmarkRequestData = new BookmarkRequestData();
        bookmarkRequestData.setClientapp(Constants.CLIENT_APP);
        bookmarkRequestData.setClientip(Utils.getLocalIpAddress(context));
        bookmarkRequestData.setType(Constants.BOOKMARK_POST);
        bookmarkRequestData.setEntityid(postId);
        bookmarkRequestData.setEntityname(eventText.getText().toString());

        ApiServices apiServices = ServiceGenerator.createServiceHeader(ApiServices.class);
        Call<BookmarkResponse> call=apiServices.postBookmark(userId,bookmarkRequestData);
        call.enqueue(new Callback<BookmarkResponse>() {
            @Override
            public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                int responsecode = response.code();
                if(responsecode==201) {
                    BookmarkResponse bookmarkResponse = response.body();
                    Toast.makeText(context, "Bookmarked this page", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }else if(responsecode==422) {

                    Toast.makeText(context, "Already Bookmarked!!", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });

    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void initiatePopupWindow() {
//       getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.image_popup,null);
        ImageView image=(ImageView)layout.findViewById(R.id.popup_image);
        Picasso.with(context).load(imagePath)
                .placeholder(R.drawable.background)
                .into(image);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        pw = new PopupWindow(layout, lp.width, lp.height, true);
        pw.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);
        Animation hide = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
        layout.startAnimation(hide);
        LinearLayout btncancel = layout.findViewById(R.id.btncancelcat);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.dismiss();
            }
        });

    }
}
