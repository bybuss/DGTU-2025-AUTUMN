package bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models

data class Directory(
    val id: String,
    val name: String,
    val items: List<String>
)