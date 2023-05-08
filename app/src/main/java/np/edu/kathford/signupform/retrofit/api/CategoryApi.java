package np.edu.kathford.signupform.retrofit.api;


import java.math.BigDecimal;
import java.util.List;

import np.edu.kathford.signupform.model.Category;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CategoryApi {

//    @POST(value = "api/admin/findall")
//    Call<List<Category>> getAllCategory();

    @FormUrlEncoded
    @POST("api/category/save-update")
    Call<ResponseBody> saveOrUpdateCategory(
            @Field("name") String name);

}
