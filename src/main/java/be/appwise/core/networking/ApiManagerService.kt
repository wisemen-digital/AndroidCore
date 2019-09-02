package be.appwise.core.networking

import be.appwise.core.networking.models.AccessToken
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface ApiManagerService {
    //OAuth Refresh Token
    @FormUrlEncoded
    @POST("oauth/token")
    fun refreshToken(
        @Field(NetworkConstants.GRANT_TYPE_KEY) grantType: String,
        @Field(NetworkConstants.CLIENT_ID_KEY) clientId: String,
        @Field(NetworkConstants.CLIENT_SECRET_KEY) client_secret: String,
        @Field(NetworkConstants.REFRESH_TOKEN) refreshToken: String
    ): Call<AccessToken>
}