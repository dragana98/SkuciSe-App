data class Card(
    val id: Int,
    val title: String,
    val priceRange: String,
    val monthly: Boolean,
    val roomCount: String,
    val city: String,
    val amenities: List<String>,
    val images: List<String>
)