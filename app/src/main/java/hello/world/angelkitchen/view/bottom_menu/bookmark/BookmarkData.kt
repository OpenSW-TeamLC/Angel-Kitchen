package hello.world.angelkitchen.view.bottom_menu.bookmark

data class BookmarkData(
    val imgPath: String,
    val place: String,
    val address: String,
    val number: String,
    var isClicked: Boolean = false
)
