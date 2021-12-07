package com.ydhnwb.android_refresh_token_example.data._common.error

object HttpCodeMessageMapper {
    const val HTTP_BAD_REQUEST = "Kesalahan dalam membuat request ke server. Periksa kembali data yang dimasukkan"
    const val HTTP_UNPROCESSABLE_ENTITY = "Permintaan ke server tidak dapat dilakukan"
    const val HTTP_CONFLICT = "Terjadi konflik. Mungkin data dengan kode atau id yang sama sudah ada"

    fun map(code: Int) : String{
        return when(code){
            200 -> "Ok"
            400 -> HTTP_BAD_REQUEST
            409 -> HTTP_CONFLICT
            422 -> HTTP_UNPROCESSABLE_ENTITY
            else -> "Terjadi kesalahan ${code}"
        }
    }
}