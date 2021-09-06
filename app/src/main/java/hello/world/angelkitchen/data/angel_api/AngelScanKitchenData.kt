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