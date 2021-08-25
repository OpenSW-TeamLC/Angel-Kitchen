package hello.world.angelkitchen.data.angel_api

data class AngelAllData(
    val id: Int,
    val fcltyNm: String,
    val rdnmadr: String,
    val lnmadr: String?,
    val phoneNumber: String,
    val mlsvTrget: String,
    val mlsvTime: String,
    val mlsvDate: String,
    val operOpenDate: String,
    val latitude: Double,
    val longitude: Double
)