package hello.world.angelkitchen.data.angel_api

data class AngelScanKitchenDataBody(
    val body: AngelScanKitchenData
)

data class AngelScanKitchenData(
    val items: ArrayList<AngelScanKitchenDataValue>
)

data class AngelScanKitchenDataValue(
    val id: Int,
    val fcltyNm: String,
    val rdnmadr: String,
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double,
    val scanResult: Double
)

//data class AngelNumber(
//    val number: Double
//)


data class test(
    val body: Body,
    val header: Header
)

data class Body(
    val items: List<Item>
)

data class Header(
    val resultCode: Int,
    val totalCount: Int,
    val type: String
)

data class Item(
    val fcltyNm: String,
    val latitude: Double,
    val longitude: Double,
    val phoneNumber: String,
    val rdnmadr: String,
    val scanResult: Int
)