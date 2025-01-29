package com.mydia.restaurantsmartqr.util




object Constants {

  //const val BASE_URL = "https://creativewebmaking.com/restaurant_pos/api_v1/"
  //var BASE_URL = "https://mydia.ai/smart-qr/api_v1/"
  var BASE_URL = "https://vite.biz/foodcus/WebServices/V1/"
  //const val BASE_URL = "https://tiffin.today/api_v1/"
 // const val BASE_URL = pref.getString(PrefKey.FCM_TOKEN)


  const val DEVICE_TYPE: String = "0"
    /*End points*/

    const val LOGIN_URL = "/login.asmx/Login_resturant"
    const val ORDER_LIST_URL = "/api_v1/orders/list"
    const val ORDER_STATUS_URL = "/api_v1/orders/order_status"
    const val TABLE_LIST = "/RestaurantTable.asmx/SelectAll"
    const val SECTION_LIST = "/RestaurantTable.asmx/SelectAllSection"
    const val REVIEW_LINK = "/api_v1/SendText/review_link"
    const val BRANCH_LINK = "/api_v1/SendText/branch_link"
    const val REDEEM_POINT = "/api_v1/loyalty_points/redeem"
    const val ADD_POINT = "/api_v1/loyalty_points/add"
    const val APP_VERSIONS = "/api_v1/app_versions/list"
    const val CATEGORY_LIST = "/Category.asmx/SelectAllCategoryByLanguageId"
    const val PRODUCT_LIST = "/Product.asmx/SelectAllProductByCategoryV1"
    const val PLACE_ORDER = "/Order.asmx/CreateOrderWithJsonDataV6"
    const val ADD_CUSTOMER = "/Customer.asmx/AddFoodcusCustomer"
    const val CUSTOMER_LIST = "/Customer.asmx/SelectAllFoodcusCustomer"







}
