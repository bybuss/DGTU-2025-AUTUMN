package bob.colbaskin.dgtu_2025_autumn.admin_panel.domain.models

data class BackupItem(
    val id: Int,
    val projectName: String,
    val changeDate: String,
    val author: Author,
    val changeDescription: ChangeDescription
)
