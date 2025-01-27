package com.mydia.restaurantsmartqr.permission;



public interface OnRequestPermissionsCallBack{

    void onGrant();

    void onDenied(String permission);
}
