package bob.colbaskin.dgtu_2025_autumn.projects.domain.models

data class Project(
    val id: Int,
    val name: String,
    val year: Int,
    val segment: String,
    val inn: String,
    val manager: String,
    val organization: String,
    val service: String,
    val stage: String,
    val amount: String,
    val probability: String
)
