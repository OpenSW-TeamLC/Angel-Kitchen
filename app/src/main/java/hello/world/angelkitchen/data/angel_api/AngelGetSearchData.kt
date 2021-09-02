package hello.world.angelkitchen.data.angel_api

data class AngelGetSearchData(
    val body: AngelGetSearchDataBody
)

data class AngelGetSearchDataBody(
    val items: List<Item>
)

data class Item(
    val fcltyNm: String,
    val id: Int,
    val latitude: Double,
    val lnmadr: String,
    val longitude: Double,
    val mlsvDate: String,
    val mlsvTime: String,
    val mlsvTrget: String,
    val operOpenDate: String,
    val phoneNumber: String,
    val rdnmadr: String
)