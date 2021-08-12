package hello.world.angelkitchen
data class FoodData(
    val response: response
)
data class response(
    val body: body,
    val header: header
)
data class body(
    val items: List<items>,
    val numOfRows: String,
    val pageNo: String,
    val totalCount: String
)

data class header(
    val resultCode: String,
    val resultMsg: String,
    val type: String
)
data class items(
    val fcltyNm: String,
    val insttCode: String,
    val latitude: String,
    val lnmadr: String,
    val longitude: String,
    val mlsvDate: String,
    val mlsvPlace: String,
    val mlsvTime: String,
    val mlsvTrget: String,
    val operCloseDate: String,
    val operInstitutionNm: String,
    val operOpenDate: String,
    val phoneNumber: String,
    val rdnmadr: String,
    val referenceDate: String
)